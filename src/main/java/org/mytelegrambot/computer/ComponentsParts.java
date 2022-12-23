package org.mytelegrambot.computer;

import org.mytelegrambot.enums.ComponentsEnum;

import java.util.ArrayList;
import java.util.List;

public class ComponentsParts {
    public static List<ComponentsEnum> componentsParts = new ArrayList<>();

    public static void init() {
        componentsParts.add(ComponentsEnum.CPU);
        componentsParts.add(ComponentsEnum.GPU);
        componentsParts.add(ComponentsEnum.MOTHERBOARD);
        componentsParts.add(ComponentsEnum.RAM);
        componentsParts.add(ComponentsEnum.COOLING);
        componentsParts.add(ComponentsEnum.POWER);
        componentsParts.add(ComponentsEnum.CORPUS);
        componentsParts.add(ComponentsEnum.DISK);
    }

    public static List<String> getAllParts() {
        List<String> allParts = new ArrayList<>();
        for (ComponentsEnum part : componentsParts) {
            allParts.add(part.toString());
        }

        return allParts;
    }
}
