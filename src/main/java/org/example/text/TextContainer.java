package org.example.text;

import org.example.computer.Computer;
import org.example.users.User;

public class TextContainer {
    public static final String start = "start";
    public static final String delete = "Информация удалена.";
    public static final String enterMoney = "Введите бюджет.";
    public static final String enterSaveName = "Введите имя под которым мы сохраним эту сборку.";
    public static final String chooseSaveMethod = "Выберит вариант сохранения.";
    public static final String buildSaved = "Сборка сохранена. Для просмотра всех своих сборок введите /saved.";
    public static final String buildNameIsTaken = "Это имя уже занято, попробуйте другое.";
    public static final String assemblyInfo = "Собираем ваш пк...";
    public static final String publicBuildsInfo = "Сборки других пользователей";
    private static final String myBuildsInfoOk = "Ваши сборки:";
    private static final String myBuildsInfoEmpty = "У вас нет сохраненных сборок.";
    private static final String buildPublic = "Эту сборку видят и другие пользователи.";
    private static final String buildNotPublic = "Эту сборку видно только вам";

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
}
