package org.mytelegrambot.core;

import org.mytelegrambot.core.datacontrol.ProcessedData;
import org.mytelegrambot.core.processings.CallBackProcessing;
import org.mytelegrambot.core.processings.TextProcessing;
import org.mytelegrambot.enums.OptionsToSendEnum;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Calendar;
import java.util.List;

public class Manager {
    CallBackProcessing callBackProcessing;
    TextProcessing textProcessing;

    Manager() {
        callBackProcessing = new CallBackProcessing();
        textProcessing = new TextProcessing();
    }
    public List<ProcessedData> updateReceived(Update update) {
        List<ProcessedData> processedData;

        if (update.hasCallbackQuery()) {
            processedData = callBackProcessing.process(update.getCallbackQuery());
            checkData(processedData, OptionsToSendEnum.EDIT);
        } else {
            processedData = textProcessing.process(update.getMessage());
            checkData(processedData, OptionsToSendEnum.SEND);
        }

        return processedData;
    }

    private void checkData(List<ProcessedData> processedData, OptionsToSendEnum optionToSendEnum) {
        for (int i = 0; i < processedData.size(); i++) {
            ProcessedData data = processedData.get(i);
            if (data == null ||
                    (!data.isBuildingPC() &&
                    (data.getMessageText().equals("") || data.getMessageText() == null || data.getOptionToSend() == null))) {
                processedData.set(i, setErrorData(optionToSendEnum));
            }
        }
    }

    private ProcessedData setErrorData(OptionsToSendEnum optionToSendEnum) {
        switch (optionToSendEnum) {
            //todo logger
            case SEND -> {
                System.out.println("Ошибка в текстовом запросе " + Calendar.getInstance().getTime());
            }
            case EDIT -> {
                System.out.println("Ошибка в callbackData " + Calendar.getInstance().getTime());
            }
        }
        return new ProcessedData("Ошибка", optionToSendEnum, null);
    }
}
