package org.mytelegrambot.core.processings;

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
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ArrayList;
import java.util.List;

public class CallBackProcessing {
    User user;

    public List<ProcessedData> process(CallbackQuery callbackQuery) {
        user = UsersService.getUser(callbackQuery.getMessage());

        List<ProcessedData> processedData = new ArrayList<>();

        switch (user.getStep()) {
            case WaitForChooseSave -> {
                processedData.add(processChooseSave(
                        callbackQuery.getData().replace(DataPrefixEnum.DEFAULT.toString(), "")));
                user.setStep(UserStepEnum.WaitForNamePC);
            }
            case WaitForChooseComment -> {
                processedData.add(processChooseComment(
                        callbackQuery.getData().replace(DataPrefixEnum.DEFAULT.toString(), "")));
            }
            case Resting -> {
                String data = callbackQuery.getData();
                DataPrefixEnum prefix = getPrefix(data); //todo проверку
                String text = removePrefix(prefix.toString(), data); //todo что-то не так
                processedData.add(processRestingState(text, prefix, callbackQuery.getMessage().getText()));
            }
        }
        return processedData;
    }

    private ProcessedData processRestingState(String callbackText, DataPrefixEnum prefix, String messageText) {
        switch (prefix) {
            case PUBLICPC -> {
                return processPublicBuilds(callbackText, messageText);
            }
            case PRIVATEPC -> {
                return processLocalBuilds(callbackText, messageText);
            }
            case SEARCH -> { //todo
            }
        }
        return null;
    }

    private ProcessedData processChooseComment(String text) {
        switch (text) {
            case "Да" -> {
                user.setStep(UserStepEnum.WaitForComment);
                return new ProcessedData(TextContainer.enterComment, OptionsToSendEnum.EDIT, null);
            }
            case "Нет" -> {
                user.setStep(UserStepEnum.Resting);
                return new ProcessedData(TextContainer.noComment, OptionsToSendEnum.EDIT, null);
            }
        }
        return null;
    }

    private ProcessedData processChooseSave(String data) {
        switch (data) {
            case "Публично" -> user.getLastComputer().setPublic(true);
            case "Для себя" -> user.getLastComputer().setPublic(false);
        }

        return new ProcessedData(TextContainer.enterSaveName, OptionsToSendEnum.EDIT, null);
    }

    private ProcessedData processLocalBuilds(String data, String messageText) {
        if (user.isContains(data)) {
            return new ProcessedData(data, OptionsToSendEnum.EDIT,
                    KeyboardGenerator.generateInlineKeyboard(List.of("Посмотреть", "Удалить", "Назад"), ""));
        }
        switch (data) {
            case "Сохранить" -> {
                user.setStep(UserStepEnum.WaitForChooseSave);
                return new ProcessedData(TextContainer.chooseSaveMethod, OptionsToSendEnum.EDIT,
                        KeyboardGenerator.generateInlineKeyboard(List.of("Публично", "Для себя"), ""));
            }

            case "Назад" -> {
                return new ProcessedData("Ваши сборки:", OptionsToSendEnum.EDIT,
                        KeyboardGenerator.generateInlineKeyboard(user.getComputerNames(), ""));
            }

            case "Посмотреть" -> {
                return new ProcessedData(user.getComputer(messageText).getInfo(false, ""),
                        OptionsToSendEnum.EDIT,
                        KeyboardGenerator.generateInlineKeyboard(List.of("Назад"), ""));
            }

            case "Удалить" -> {
                user.deleteComputer(messageText);
                PublicComputersService.deleteComputer(messageText);
                return new ProcessedData(TextContainer.myBuildsInfo(user), OptionsToSendEnum.EDIT,
                        KeyboardGenerator.generateInlineKeyboard(user.getComputerNames(), ""));
            }
        }

        return null;
    }

    private ProcessedData processPublicBuilds(String data, String messageText) {
        switch (data) {
            case "Назад" -> {
                return new ProcessedData(TextContainer.isPublicBuilds(PublicComputersService.getSize()),
                        OptionsToSendEnum.EDIT,
                        KeyboardGenerator.generateInlineKeyboard(PublicComputersService.getNames(),
                                DataPrefixEnum.PUBLICPC.toString()));
            }

            case "Поставить лайк" -> {
                String PCName = getPublicPCName(messageText);
                Computer computer = PublicComputersService.getComputer(PCName);
                computer.addUserLike(user);
                return new ProcessedData(computer.getInfo(true, PCName), OptionsToSendEnum.EDIT,
                        KeyboardGenerator.generateInlineKeyboard(List.of("Убрать лайк", "Назад"),
                                DataPrefixEnum.PUBLICPC.toString()));
            }

            case "Убрать лайк" -> {
                String PCName = getPublicPCName(messageText);
                Computer computer = PublicComputersService.getComputer(PCName);
                computer.deleteUserLike(user);
                return new ProcessedData(computer.getInfo(true, PCName), OptionsToSendEnum.EDIT,
                        KeyboardGenerator.generateInlineKeyboard(List.of("Поставить лайк", "Назад"),
                                DataPrefixEnum.PUBLICPC.toString()));
            }

            default -> {
                if (!PublicComputersService.contains(data))
                    return null;
                Computer computer = PublicComputersService.getComputer(data);
                String text;
                if (computer.isUserLiked(user)) {
                    text = "Убрать лайк";
                } else {
                    text = "Поставить лайк";
                }
                return new ProcessedData(computer.getInfo(true, data), OptionsToSendEnum.EDIT,
                        KeyboardGenerator.generateInlineKeyboard(List.of(text, "Назад"),
                                DataPrefixEnum.PUBLICPC.toString()));
            }
        }
    }

    private boolean checkPrefix(String prefix, String text) {
        return text.indexOf(prefix) == 0;
    }

    private DataPrefixEnum getPrefix(String text) {
        for (DataPrefixEnum value : DataPrefixEnum.values()) {
            if (checkPrefix(value.toString(), text))
                return value;
        }
        return null;
    }

    private String removePrefix(String prefix, String text) {
        return text.replace(prefix, "");
    }

    private String getPublicPCName(String data) {
        int index = data.indexOf("\n");
        return data.substring(0, index);
    }
}
