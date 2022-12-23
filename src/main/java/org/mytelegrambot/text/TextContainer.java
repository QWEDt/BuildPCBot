package org.mytelegrambot.text;

import org.mytelegrambot.computer.Computer;
import org.mytelegrambot.users.User;

public class TextContainer {
    public static final String start = "start";
    public static final String useHelp = "Для помощи используйте /help";
    public static final String delete = "Информация удалена.";
    public static final String enterMoney = "Введите бюджет.";
    public static final String enterSaveName = "Введите имя под которым мы сохраним эту сборку.";
    public static final String chooseSaveMethod = "Выберите вариант сохранения.";
    public static final String buildSaved = "Сборка сохранена. Для просмотра всех своих сборок введите /saved. \nЖелаете добавить комментарий?";
    public static final String buildNameIsTaken = "Это имя уже занято, попробуйте другое.";
    public static final String assemblyInfo = "Собираем ваш пк...";

    public static final String chooseCpu = "Введите производителя цпу (Intel/AMD)";
    public static final String chooseGpu = "Введите производителя гпу (Nvidia/AMD)";
    public static final String pcBuildFailed = "Сорри, времена тяжелые. На это ничего не собрать";
    private static final String myBuildsInfoOk = "Ваши сборки:";
    private static final String myBuildsInfoEmpty = "У вас нет сохраненных сборок.";
    private static final String buildPublic = "Эту сборку видят и другие пользователи.";
    private static final String buildNotPublic = "Эту сборку видно только вам";
    private static final String publicBuildsInfoOk = "Сборки других пользователей";
    private static final String publicBuildsInfoNotOk = "Сборки других пользователей отсутсвуют. Будьте первыми!";
    public static final String enterComment = "Введите комментарий";
    public static final String noComment = "Комментарий не будет добавлен";
    public static final String commentOk = "Комментарий добавлен";
    public static final String searchComponent = "Выберите из списка";
    public static String waitForMoneyForComponent = "Введите ценовую категорию";

    public static String myBuildsInfo(User user) {
        if (user.getComputerSize() == 0) {
            return myBuildsInfoEmpty;
        }
        return myBuildsInfoOk;
    }

    public static String isPublic(Computer computer) {
        if (computer.isPublic()) {
            return buildPublic;
        }
        return buildNotPublic;
    }

    public static String isPublicBuilds(int size) {
        if (size == 0)
            return publicBuildsInfoNotOk;
        return publicBuildsInfoOk;
    }
}
