package org.example;

import org.example.errors.ComponentNotFoundException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Singleton
public class PCBuilderBot extends TelegramLongPollingBot {
    // Список всех юзеров, нужен для хранения состояния сборки пк.
    HashMap<Long, List<String>> users = new HashMap<>();
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
        System.out.println("Start");
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (users.get(message.getChatId()) == null) {
                switch (message.getText()) {
                    case "/BuildPC" -> {
                        sendText(message, "Введите бюджет.");
                        users.put(message.getChatId(), new ArrayList<>());
                    }
                    case "/start" -> sendText(message, "Start");
                }
            } else if (!Objects.equals(message.getText(), "/cancel")) {
                List<String> userArguments = users.get(message.getChatId());
                if (userArguments.size() < 2)
                {
                    userArguments.add(message.getText());
                    sendText(message, "ya jdu");
                } else {
                    userArguments.add(message.getText());

                    HashMap<String, HashMap<String, String>> PC = buildProcess.build(Integer.parseInt(
                            userArguments.get(0)), userArguments.get(1), userArguments.get(2));

                    users.remove(message.getChatId());

                    String answer;
                    try {
                        if (PC == null)
                        {
                            throw new ComponentNotFoundException();
                        }
/*                        for (String key : PC.keySet()) {
                            if (PC.get(key) == null) {
                                System.out.println(key);
                                throw new Error();
                            }
                        }*/

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
                    sendText(message, answer);
                }
            }
            else {
                users.remove(message.getChatId());
                sendText(message, "Сборка отменена");
            }
        }
        System.out.println(users);
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
