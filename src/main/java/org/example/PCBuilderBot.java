package org.example;

import org.example.exceptions.ComponentNotFoundException;
import org.example.exceptions.UserNotFoundException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class PCBuilderBot extends TelegramLongPollingBot {
    // Список всех юзеров, нужен для хранения состояния сборки пк.
    //HashMap<Long, User> users = new HashMap<>();
    Users users = new Users();
    BuildProcess buildProcess = new BuildProcess();
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
                        sendText(message, "Введите бюджет.");
                        users.appendUser(message.getChatId());
                    }
                    case "/start" -> sendText(message, "Start");
                }
            } else if (!"/cancel".equals(message.getText())) {
                User user = users.getUser(message.getChatId());
                switch (user.getStep()) {
                    case 0 -> {
                        user.setMoney(Integer.parseInt(message.getText()));
                        sendText(message, "Введите производителя цпу (intel/amd)");
                        user.nextStep();
                    }
                    case 1 -> {
                        user.setBrandCPU(message.getText());
                        sendText(message, "Введите производителя гпу (nvidia/amd)");
                        user.nextStep();
                    }
                    case 2 -> {
                        user.setBrandGPU(message.getText());
                        HashMap<String, HashMap<String, String>> PC = buildProcess.build(
                                user.getMoney(), user.getBrandCPU(), user.getBrandGPU());
                        try {
                            users.deleteUser(user.getChatId());
                        } catch (UserNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        sendText(message, generateAnswer(PC));
                    }
                    //TODO Default
                }
            } else {
                try {
                    users.deleteUser(message.getChatId());
                } catch (UserNotFoundException e) {
                    throw new RuntimeException(e);
                }
                sendText(message, "Сборка отменена");
            }
        }
    }

    private String generateAnswer(HashMap<String, HashMap<String, String>> PC) {
        String answer;
        try {
            if (PC == null)
            {
                throw new ComponentNotFoundException();
            }

            answer = "Ваш процессор: " + PC.get("cpu").get("name") + " | цена " + PC.get("cpu").get("price") + "\n";
            answer += "Ваша материнская плата: " + PC.get("motherboard").get("name") + " | цена " + PC.get("motherboard").get("price") + "\n";
            answer += "Ваша видеокарта: " + PC.get("gpu").get("name") + " | цена " + PC.get("gpu").get("price") + "\n";
            answer += "Ваша озу: " + PC.get("ram").get("name") + " | цена " + PC.get("ram").get("price") + "\n";
            answer += "Ваше охлаждение: " + PC.get("cooling").get("name") + " | цена " + PC.get("cooling").get("price") + "\n";
            answer += "Ваш блок питания: " + PC.get("power").get("name") + " | цена " + PC.get("power").get("price") + "\n";
            answer += "Ваш диск: " + PC.get("disk").get("name") + " | цена " + PC.get("disk").get("price") + "\n";
            answer += "Ваш корпус: " + PC.get("corpus").get("name") + " | цена " + PC.get("corpus").get("price") + "\n";
        } catch (ComponentNotFoundException e) {
            answer = "Сорри, времена тяжелые. На это ничего не собрать";
        }
        return answer;
    }

    private void sendText(Message message, String text) {
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
