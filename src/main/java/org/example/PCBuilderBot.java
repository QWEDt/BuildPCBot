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
                if (arguments.size() < 3)
                {
                    arguments.add(message.getText());
                    sendText(message, "ya jdu");
                } else {
                    System.out.println(arguments);
                    HashMap<String, HashMap<String, String>> PC = buildProcess.build(Integer.parseInt(arguments.get(0)), arguments.get(1), arguments.get(2));
                    arguments = new ArrayList<>();

                    String answer;
                    try {
                        for (String key : PC.keySet()) {
                            if (PC.get(key) == null) {
                                throw new Error();
                            }
                        }

                        answer = "Ваш процессор: Наименование " + PC.get("cpu").get("name") + " | цена " + PC.get("cpu").get("price") + "\n";
                        answer += "Ваша материнская плата: Наименование " + PC.get("motherboard").get("name") + " | цена " + PC.get("motherboard").get("price") + "\n";
                        answer += "Ваша видеокарта: Наименование " + PC.get("gpu").get("name") + " | цена " + PC.get("gpu").get("price") + "\n";
                        answer += "Ваша озу: Наименование " + PC.get("ram").get("name") + " | цена " + PC.get("ram").get("price") + "\n";
                        answer += "Ваше охлаждение: Наименование " + PC.get("cooling").get("name") + " | цена " + PC.get("cooling").get("price") + "\n";
                        answer += "Ваш блок питания: Наименование " + PC.get("power").get("name") + " | цена " + PC.get("power").get("price") + "\n";
                        answer += "Ваш диск: Наименование " + PC.get("disk").get("name") + " | цена " + PC.get("disk").get("price") + "\n";
                        answer += "Ваш корпус: Наименование " + PC.get("corpus").get("name") + " | цена " + PC.get("corpus").get("price") + "\n";
                    } catch (Error e) {
                        answer = "Сорри, времена тяжелые. На это ничего не собрать";
                    }

                    sendText(message, answer);
                    isBuilding = false;
                }
            }
            else {
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
