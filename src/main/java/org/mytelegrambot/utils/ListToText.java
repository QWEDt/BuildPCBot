package org.mytelegrambot.utils;

import org.mytelegrambot.computer.parts.Component;

import java.util.List;

public class ListToText {
    public static String ToText(List<? extends Component> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Component o : list) {
            stringBuilder.append(o.getName()).append(" цена - ").append(o.getPrice()).append("\n");
        }

        return stringBuilder.toString();
    }
}
