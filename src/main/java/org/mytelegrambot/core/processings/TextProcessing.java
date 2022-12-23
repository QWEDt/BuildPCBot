package org.mytelegrambot.core.processings;

import org.mytelegrambot.computer.ComponentsParts;
import org.mytelegrambot.computer.Computer;
import org.mytelegrambot.computer.PublicComputersService;
import org.mytelegrambot.core.datacontrol.ProcessedData;
import org.mytelegrambot.core.utils.KeyboardGenerator;
import org.mytelegrambot.enums.DataPrefixEnum;
import org.mytelegrambot.enums.OptionsToSendEnum;
import org.mytelegrambot.enums.UserStepEnum;
import org.mytelegrambot.text.TextContainer;
import org.mytelegrambot.users.User;
import org.mytelegrambot.users.UsersService;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextProcessing {
    User user;

    public List<ProcessedData> process(Message message) {
        if (message == null || !message.hasText()) {
            return null;
        }

        user = UsersService.getUser(message);
        List<ProcessedData> processedesData = new ArrayList<>();

        String text = message.getText().toLowerCase();
        if (text.equals("/cancel"))
        {
            user.setStep(UserStepEnum.Resting);
            processedesData.add(cancelCurrentOperation());
            return processedesData;
        }

        switch (user.getStep()) {
            case WaitForMoney -> {
                processedesData.add(moneyProcessing(text));
                user.setStep(UserStepEnum.WaitForCPU);
            }
            case WaitForCPU -> {
                processedesData.add(cpuProcessing(text));
                user.setStep(UserStepEnum.WaitForGPU);
            }
            case WaitForGPU -> {
                processedesData.add(gpuProcessing(text));
                processedesData.add(new ProcessedData(true));
                user.setStep(UserStepEnum.Resting);
            }
            case WaitForComment -> {
                processedesData.add(commentProcessing(text));
                user.setStep(UserStepEnum.Resting);
            }
            case WaitForNamePC -> {
                processedesData.add(nameProcessing(text));
            }
            case Resting -> processedesData.add(setCommand(text));
        }

        return processedesData;
    }

    private ProcessedData commentProcessing(String text) {
        user.getLastComputer().setComment(text);
        return new ProcessedData(TextContainer.commentOk, OptionsToSendEnum.SEND, null);
    }

    private ProcessedData cancelCurrentOperation() {
        return new ProcessedData("Текущая операция отменена", OptionsToSendEnum.SEND, null);
    }

    private ProcessedData nameProcessing(String text) {
        Computer lastComputer = user.getLastComputer();

        if (user.addComputer(text, lastComputer)) {
            if (lastComputer.isPublic()) {
                lastComputer.setCreator(user.getUserName());
                PublicComputersService.append(text, lastComputer);
            }

            user.setStep(UserStepEnum.WaitForChooseComment);
            UsersService.saveUser(user);
            return new ProcessedData(TextContainer.buildSaved, OptionsToSendEnum.SENDWITHKEYBOARD,
                    KeyboardGenerator.generateInlineKeyboard(Arrays.asList("Да", "Нет"), DataPrefixEnum.DEFAULT.toString()));
        } else {
            return new ProcessedData(TextContainer.buildNameIsTaken, OptionsToSendEnum.SEND, null);
        }
    }

    private ProcessedData moneyProcessing(String text) {
        user.setMoney(Integer.parseInt(text));
        return new ProcessedData(TextContainer.chooseCpu, OptionsToSendEnum.SENDWITHKEYBOARD,
                KeyboardGenerator.generateKeyboard(Arrays.asList("Intel", "AMD")));
    }

    private ProcessedData cpuProcessing(String text) {
        user.setBrandCPU(text);
        return new ProcessedData(TextContainer.chooseGpu, OptionsToSendEnum.SENDWITHKEYBOARD,
                KeyboardGenerator.generateKeyboard(Arrays.asList("Nvidia", "AMD")));
    }

    private ProcessedData gpuProcessing(String text) {
        user.setBrandGPU(text);
        return new ProcessedData(TextContainer.assemblyInfo, OptionsToSendEnum.SEND, null);
    }

    private ProcessedData setCommand(String text) {
        switch (text) {
            case "/buildpc" -> {
                user.setStep(UserStepEnum.WaitForMoney);
                return new ProcessedData(TextContainer.enterMoney, OptionsToSendEnum.SEND, null);
            }

            case "/start" -> {
                return new ProcessedData(TextContainer.start, OptionsToSendEnum.SEND, null);
            }

            case "/saved" -> {
                return new ProcessedData(TextContainer.myBuildsInfo(user), OptionsToSendEnum.SENDWITHKEYBOARD,
                        KeyboardGenerator.generateInlineKeyboard(user.getComputerNames(),
                                DataPrefixEnum.PRIVATEPC.toString()));
            }

            case "/help" -> {
                return new ProcessedData("Help info", OptionsToSendEnum.SEND, null);
            }
            //todo при удалении ник меняется на PCBuilderBot
            case "/delete" -> {
                UsersService.deleteUser(user);
                return new ProcessedData(TextContainer.delete, OptionsToSendEnum.SEND, null);
            }

            case "/builds" -> {
                return new ProcessedData(TextContainer.isPublicBuilds(PublicComputersService.getSize()),
                        OptionsToSendEnum.SENDWITHKEYBOARD,
                        KeyboardGenerator.generateInlineKeyboard(PublicComputersService.getNames(),
                                DataPrefixEnum.PUBLICPC.toString()));
            }

            case "/search" -> {
                return new ProcessedData(TextContainer.searchComponent, OptionsToSendEnum.SENDWITHKEYBOARD,
                        KeyboardGenerator.generateInlineKeyboard(ComponentsParts.getAllParts(), DataPrefixEnum.SEARCH.toString()));
            }

            default -> {
                return new ProcessedData(TextContainer.useHelp, OptionsToSendEnum.SEND, null);
            }
        }
    }
}
