package org.example.core;

import org.example.computer.Computer;
import org.example.computer.Computers;
import org.example.enums.UserStepEnum;
import org.example.text.TextContainer;
import org.example.users.User;
import org.example.users.UsersService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Singleton
public class PCBuilderBot extends TelegramLongPollingBot {
    UsersService users;
    BuildProcess buildProcess;
    Computers publicComputers;
    public PCBuilderBot() {
        users = new UsersService();
        buildProcess = new BuildProcess();
        publicComputers = users.getPublicComputers();
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/help", "Информация о возможностях"));
        commands.add(new BotCommand("/start", "Приветственное сообщение"));
        commands.add(new BotCommand("/buildpc", "Начать сборку пк"));
        commands.add(new BotCommand("/cancel", "Отменить текущее действие"));
        commands.add(new BotCommand("/saved", "Посмотреть сохраненные сборки пк"));
        commands.add(new BotCommand("/builds", "Посмотреть сборки от других пользователей"));
        commands.add(new BotCommand("/delete", "Удалить всю информацию обо мне"));

        try {
            execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("Бот запущен");
    }

    @Override
    public String getBotUsername() {
        return System.getenv("botName");
    }

    @Override
    public String getBotToken() {
        return System.getenv("botToken");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            callbackDataProcessing(update.getCallbackQuery().getMessage(), update.getCallbackQuery().getData());
        }

        Message message = update.getMessage();
        if (message == null || !message.hasText()) {
            return;
        }

        User user = users.getUser(message.getChatId());

        if (user == null) {
            user = new User(message.getChatId(), message.getFrom().getUserName());
            users.appendUser(user);
            users.saveUser(user);
        }

        if (message.getText().equals("/cancel"))
        {
            sendText(message.getChatId(), "Сборка отменена");
            user.setRest();
            return;
        }

        if (user.getStep() == UserStepEnum.Resting) {
            System.out.println(message.getFrom().getUserName());
            switch (message.getText()) {
                case "/buildpc" -> {
                    user.startBuilding();
                    sendText(message.getChatId(), TextContainer.enterMoney);
                }
                case "/start" -> sendText(message.getChatId(), TextContainer.start);

                case "/saved" -> sendTextWithKeyboard(message.getChatId(), TextContainer.myBuildsInfo(user),
                        generateInlineKeyboard(user.getComputerNames(), ""));
                case "/delete" -> {
                    users.deleteUser(user);
                    sendText(user.getChatId(), TextContainer.delete);
                }
                case "/builds" -> sendTextWithKeyboard(user.getChatId(), TextContainer.publicBuildsInfo,
                        generateInlineKeyboard(publicComputers.getNames(), "public_"));

            }
        } else userProcessing(user, message.getText().toLowerCase());
    }

    private void callbackDataProcessing(Message message, String data) {
        User user = users.getUser(message.getChatId());

        switch (user.getStep()) {
            case WaitForChooseSave -> {
                if ("Публично".equals(data)) {
                    user.getLastComputer().setPublic(true);
                } else if ("Для себя".equals(data)) {
                    user.getLastComputer().setPublic(false);
                }
                editMessage(message, TextContainer.enterSaveName, null);
                user.nextStep();
            }
            case Resting -> {
                if (checkPrefix("public_", data)) {
                    processPublicBuilds(message, data.replace("public_", ""), user);
                } else {
                    processLocalBuilds(message, data, user);
                }
            }
        }
    }

    private void processPublicBuilds(Message message, String data, User user) {
        if ("Назад".equals(data)) {
            editMessage(message, TextContainer.publicBuildsInfo,
                    generateInlineKeyboard(publicComputers.getNames(), "public_"));
        } else if ("Поставить лайк".equals(data)) {
            String PCName = getPublicPCName(message.getText());
            Computer computer = publicComputers.getComputer(PCName);
            computer.addUserLike(user);
            editMessage(message, computer.getInfo(true, PCName),
                    generateInlineKeyboard(List.of("Убрать лайк", "Назад"), "public_"));
        } else if ("Убрать лайк".equals(data)) {
            String PCName = getPublicPCName(message.getText());
            Computer computer = publicComputers.getComputer(PCName);
            computer.deleteUserLike(user);
            editMessage(message, computer.getInfo(true, PCName),
                    generateInlineKeyboard(List.of("Поставить лайк", "Назад"), "public_"));
        } else {
            Computer computer = publicComputers.getComputer(data);
            String text;
            if (computer.isUserLiked(user)) {
                text = "Убрать лайк";
            } else {
                text = "Поставить лайк";
            }
            editMessage(message, computer.getInfo(true, data),
                    generateInlineKeyboard(List.of(text, "Назад"), "public_"));
        }
    }

    private void processLocalBuilds(Message message, String data, User user) {
        if ("Сохранить".equals(data)) {
            editMessage(message, message.getText(), null);
            sendTextWithKeyboard(message.getChatId(), TextContainer.chooseSaveMethod,
                    generateInlineKeyboard(List.of("Публично", "Для себя"), ""));
            user.setWaitForChooseSave();
        } else if (user.isContains(data)) {
            editMessage(message, data,
                    generateInlineKeyboard(Arrays.asList("Посмотреть", "Удалить", "Назад"), ""));
        } else if ("Назад".equals(data)) {
            editMessage(message, "Ваши сборки:",
                    generateInlineKeyboard(user.getComputerNames(), ""));
        } else if ("Посмотреть".equals(data)) {
            editMessage(message, user.getComputer(message.getText()).getInfo(false, ""),
                    generateInlineKeyboard(List.of("Назад"), ""));
        } else if ("Удалить".equals(data)) {
            user.deleteComputer(message.getText());
            users.saveUser(user);
            editMessage(message, TextContainer.myBuildsInfo(user),
                    generateInlineKeyboard(user.getComputerNames(), ""));
        }
    }

    private void userProcessing(User user, String text) {
        switch (user.getStep()) {
            case WaitForMoney -> {
                user.setMoney(Integer.parseInt(text));
                sendTextWithKeyboard(user.getChatId(), "Введите производителя цпу (Intel/AMD)",
                        generateKeyboard(Arrays.asList("Intel", "AMD")));
                user.nextStep();
            }
            case WaitForCPU -> {
                user.setBrandCPU(text);
                sendTextWithKeyboard(user.getChatId(), "Введите производителя гпу (Nvidia/AMD)",
                        generateKeyboard(Arrays.asList("Nvidia", "AMD")));
                user.nextStep();
            }
            case WaitForGPU -> {
                user.setBrandGPU(text);
                sendText(user.getChatId(), TextContainer.assemblyInfo);
                user.setLastComputer(buildProcess.build(user.getMoney(), user.getBrandCPU(), user.getBrandGPU()));
                sendTextWithKeyboard(user.getChatId(), user.getLastComputer().getInfo(false, ""),
                        generateInlineKeyboard(List.of("Сохранить"), ""));
                user.nextStep();
            }
            case WaitForNamePC -> {
                Computer lastComputer = user.getLastComputer();
                if (user.addComputer(text, lastComputer)) {
                    if (lastComputer.isPublic()) {
                        lastComputer.setCreator(user.getUserName());
                        publicComputers.append(text, lastComputer);
                    }
                    sendText(user.getChatId(), TextContainer.buildSaved);
                    user.nextStep();
                    users.saveUser(user);
                } else {
                    sendText(user.getChatId(), TextContainer.buildNameIsTaken);
                }
            }
        }
    }

    private void editMessage(Message oldMessage, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        editMessageText.setChatId(oldMessage.getChatId());
        editMessageText.setMessageId(oldMessage.getMessageId());
        editMessageText.setText(text);


        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyKeyboardMarkup generateKeyboard(List<String> words) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        for (String word : words) {
            row.add(word);
        }

        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    private InlineKeyboardMarkup generateInlineKeyboard(Collection<String> words, String callBackDataPrefix) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline;
        InlineKeyboardButton button;

        for (String word : words) {
            rowInline = new ArrayList<>();
            button = new InlineKeyboardButton();
            button.setText(word);
            button.setCallbackData(callBackDataPrefix + word);
            rowInline.add(button);
            rowsInline.add(rowInline);
        }

        keyboardMarkup.setKeyboard(rowsInline);

        return keyboardMarkup;
    }

    private void sendText(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTextWithKeyboard(long chatId, String text, ReplyKeyboard keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPrefix(String prefix, String text) {
        return text.indexOf(prefix) == 0;
    }

    private String getPublicPCName(String data) {
        int index = data.indexOf("\n");
        return data.substring(0, index);
    }
}
