package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PCBuilderBot extends TelegramLongPollingBot {
    boolean isBuilding = false;
    List<String> arguments = new ArrayList<>();
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
            if (!isBuilding) {
                switch (message.getText()) {
                    case "/BuildPC":
                        sendText(message, "Введите бюджет.");
                        isBuilding = true;
                        break;
                    case "/start":
                        break;
                }
            } else if (!Objects.equals(message.getText(), "/cancel")){
                if (arguments.size() < 2)
                {
                    arguments.add(message.getText());
                    sendText(message, "ya jdu");
                } else {
                    arguments.add(message.getText());
                    HashMap<String, HashMap<String, String>> PC = buildProcess.build(Integer.parseInt(arguments.get(0)), arguments.get(1), arguments.get(2));
                    arguments = new ArrayList<>();

                    String answer;
                    try {
                        if (PC.size() < 8)
                        {
                            throw new Error();
                        }
                        for (String key : PC.keySet()) {
                            if (PC.get(key) == null) {
                                System.out.println(key);
                                throw new Error();
                            }
                        }

                        answer = "Ваш процессор: " + PC.get("cpu").get("name") + " | цена " + PC.get("cpu").get("price") + "\n";
                        answer += "Ваша материнская плата: " + PC.get("motherboard").get("name") + " | цена " + PC.get("motherboard").get("price") + "\n";
                        answer += "Ваша видеокарта: " + PC.get("gpu").get("name") + " | цена " + PC.get("gpu").get("price") + "\n";
                        answer += "Ваша озу: " + PC.get("ram").get("name") + " | цена " + PC.get("ram").get("price") + "\n";
                        answer += "Ваше охлаждение: " + PC.get("cooling").get("name") + " | цена " + PC.get("cooling").get("price") + "\n";
                        answer += "Ваш блок питания: " + PC.get("power").get("name") + " | цена " + PC.get("power").get("price") + "\n";
                        answer += "Ваш диск: " + PC.get("disk").get("name") + " | цена " + PC.get("disk").get("price") + "\n";
                        answer += "Ваш корпус: " + PC.get("corpus").get("name") + " | цена " + PC.get("corpus").get("price") + "\n";
                    } catch (Error e) {
                        answer = "Сорри, времена тяжелые. На это ничего не собрать";
                    }
                    sendText(message, answer);
                    isBuilding = false;
                }
            }
            else {
                arguments = new ArrayList<>();
                isBuilding = false;
                sendText(message, "Сборка отменена");
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
