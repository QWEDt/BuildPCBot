package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.HashMap;
public class PCBuilderBot extends TelegramLongPollingBot {
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
        if (message != null && message.hasText()) {
            sendText(message, message.getText());
            switch(message.getText()) {
                case "/start":
                    //sendText();
                    break;
                case "/help":
                    //sendText();
                    break;
                case "/BuildPC":
                    //sendText();
                    HashMap<String, String> PC = new HashMap<String, String>();
                    sendText(message, " Вот ваша видеокарта:");
                    sendText(message, PC.get("GPUs"));
                    sendText(message, " Вот ваш процессор:");
                    sendText(message, PC.get("CPUs"));
                    sendText(message, " Вот ваша мат. плата:");
                    sendText(message, PC.get("motherboards"));
                    sendText(message, " Вот ваша оперативная память:");
                    sendText(message, PC.get("ram"));
                    sendText(message, " Вот ваш кулер:");
                    sendText(message, PC.get("cooling"));
                    sendText(message, " Вот ваш БП:");
                    sendText(message, PC.get("powers"));
                    sendText(message, " Вот ваш корпус:");
                    sendText(message, PC.get("corpuses"));
                    sendText(message, " Вот ваш диск:");
                    sendText(message, PC.get("disks"));
                    break;
                default:
            }
        }
    }

    public void sendText(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
