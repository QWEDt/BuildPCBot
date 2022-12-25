package org.mytelegrambot.utils;

import org.mytelegrambot.computer.parts.Component;

import java.util.List;

public class ListHelper {
    public static Component getComponent(List<? extends Component> list, String toSearch) {
        for (Component component : list) {
            if (component.getName().equals(toSearch))
                return component;
        }
        return null;
    }
}
