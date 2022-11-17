package org.example.core;

import org.example.exceptions.UserNotFoundException;
import org.example.users.User;
import org.example.users.Users;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class PCBuilderBot extends TelegramLongPollingBot {
    Users users;
    BuildProcess buildProcess;

    public PCBuilderBot() {
        users = new Users();
        buildProcess = new BuildProcess();
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/help", "Информация о возможностях"));
        commands.add(new BotCommand("/start", "Приветственное сообщение"));
        commands.add(new BotCommand("/buildpc", "Начать сборку пк"));

        try {
            execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

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
        Message message = update.getMessage();
        if (message == null || !message.hasText()) {
            return;
        }

        if (users.getUser(message.getChatId()) == null) {
            //todo normal commands
            switch (message.getText()) {
                case "/buildpc" -> {
                    sendText(message.getChatId(), "Введите бюджет.");
                    users.appendUser(message.getChatId());
                }
                case "/start" -> sendText(message.getChatId(), "start");
            }
        } else if (!"/cancel".equals(message.getText())) {
            User user = users.getUser(message.getChatId());
            switch (user.getStep()) {
                case 0 -> {
                    user.setMoney(Integer.parseInt(message.getText()));

                    ArrayList<String> words = new ArrayList<>();
                    words.add("Intel");
                    words.add("AMD");
                    ReplyKeyboardMarkup replyKeyboardMarkup = generateKeyboard(words);

                    sendTextWithKeyboard(message.getChatId(), "Введите производителя цпу (Intel/AMD)",
                            replyKeyboardMarkup);
                    user.nextStep();
                }
                case 1 -> {
                    user.setBrandCPU(message.getText().toLowerCase());

                    ArrayList<String> words = new ArrayList<>();
                    words.add("Nvidia");
                    words.add("AMD");
                    ReplyKeyboardMarkup replyKeyboardMarkup = generateKeyboard(words);

                    sendTextWithKeyboard(message.getChatId(), "Введите производителя гпу (Nvidia/AMD)",
                            replyKeyboardMarkup);
                    user.nextStep();
                }
                case 2 -> {
                    user.setBrandGPU(message.getText().toLowerCase());
                    Computer computer = buildProcess.build(user.getMoney(), user.getBrandCPU(), user.getBrandGPU());
                    try {
                        users.deleteUser(user.getChatId());
                    } catch (UserNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    sendText(message.getChatId(), computer.getComputer());
                }
            }
        } else {
            try {
                users.deleteUser(message.getChatId());
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
            sendText(message.getChatId(), "Сборка отменена");
        }
    }

    private ReplyKeyboardMarkup generateKeyboard(ArrayList<String> words) {
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

    private void sendTextWithKeyboard(long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) {
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
}
