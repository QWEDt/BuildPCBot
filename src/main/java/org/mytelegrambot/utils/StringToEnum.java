package org.mytelegrambot.utils;

import org.mytelegrambot.enums.ComponentsEnum;

public class StringToEnum {
    public static ComponentsEnum StringToComponentsEnum(String toConvert) {
        for (ComponentsEnum value : ComponentsEnum.values()) {
            if (value.toString().equals(toConvert)) {
                return value;
            }
        }

        return null;
    }

}
