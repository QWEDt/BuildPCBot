package org.mytelegrambot.core;


import org.mytelegrambot.computer.ComponentsParts;
import org.mytelegrambot.computer.PublicComputersService;
import org.mytelegrambot.computer.components.ComponentsService;
import org.mytelegrambot.core.assemble.BuildProcessService;
import org.mytelegrambot.core.assemble.RatioContainer;
import org.mytelegrambot.core.datacontrol.ProcessedData;
import org.mytelegrambot.users.UsersService;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import javax.inject.Singleton;
import java.util.ArrayList;

import java.util.List;

@Singleton
public class PCBuilderBot extends TelegramLongPollingBot {
    BuildProcessService buildProcess;
    Manager manager;
    public PCBuilderBot() {
        manager = new Manager();
        buildProcess = new BuildProcessService();

        UsersService.init();
        PublicComputersService.init();
        ComponentsParts.init();
        ComponentsService.init();
        RatioContainer.init();

        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/help", "Информация о возможностях"));
        commands.add(new BotCommand("/start", "Приветственное сообщение"));
        commands.add(new BotCommand("/buildpc", "Начать сборку пк"));
        commands.add(new BotCommand("/cancel", "Отменить текущее действие"));
        commands.add(new BotCommand("/saved", "Посмотреть сохраненные сборки пк"));
        commands.add(new BotCommand("/builds", "Посмотреть сборки от других пользователей"));
        commands.add(new BotCommand("/search", "Найти копмлектующие в нужном ценовом сегменте"));
        commands.add(new BotCommand("/delete", "Удалить всю информацию обо мне"));
        commands.add(new BotCommand("/yesno", "Случайный ответ да или нет, крайне редко иногда"));

        try {
            execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("Бот запущен");
    }

    @Override
    public String getBotUsername() {
        return System.getProperty("botName");
    }

    @Override
    public String getBotToken() {
        return System.getProperty("botToken");
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<ProcessedData> processedesData = manager.updateReceived(update);
        if (processedesData == null || processedesData.size() == 0)
            return;

        for (ProcessedData processedData : processedesData) {
            if (processedData.isBuildingPC())
                buildProcess.build(update.getMessage(), processedData);

            switch (processedData.getOptionToSend()) {

                case SEND ->
                        sendText(update.getMessage().getChatId(), processedData.getMessageText()); // мб можно с клавиатурой сюда

                case EDIT -> editMessage(update.getCallbackQuery().getMessage(), processedData.getMessageText(),
                        processedData.getKeyboardMarkup());

                case SENDWITHKEYBOARD -> sendTextWithKeyboard(update.getMessage().getChatId(),
                        processedData.getMessageText(), processedData.getKeyboardMarkup());
            }
        }
    }

    private void editMessage(Message oldMessage, String text, ReplyKeyboard inlineKeyboardMarkup) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setReplyMarkup((InlineKeyboardMarkup) inlineKeyboardMarkup);
        editMessageText.setChatId(oldMessage.getChatId());
        editMessageText.setMessageId(oldMessage.getMessageId());
        editMessageText.setText(text);


        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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
}
