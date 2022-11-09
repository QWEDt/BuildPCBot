package org.example.core;

import org.example.exceptions.UserNotFoundException;
import org.example.users.User;
import org.example.users.Users;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Singleton;

@Singleton
public class PCBuilderBot extends TelegramLongPollingBot {
    // Список всех юзеров, нужен для хранения состояния сборки пк.
    Users users;
    BuildProcess buildProcess;

    public PCBuilderBot() {
        users = new Users();
        buildProcess = new BuildProcess("src/main/resources/components.json");
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
        if (message != null && message.hasText()) {
            if (users.getUser(message.getChatId()) == null) {

                switch (message.getText()) {
                    case "/BuildPC" -> {
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
                        sendText(message.getChatId(), "Введите производителя цпу (intel/amd)");
                        user.nextStep();
                    }
                    case 1 -> {
                        user.setBrandCPU(message.getText().toLowerCase());
                        sendText(message.getChatId(), "Введите производителя гпу (nvidia/amd)");
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
                    //TODO Default
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
    }

    public void sendText(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
