package org.mytelegrambot.core.assemble;

import org.mytelegrambot.enums.ComponentsEnum;

import java.util.HashMap;
import java.util.Map;

public class RatioContainer {
    private static final Map<ComponentsEnum, Double> ratio = new HashMap<>();
    private static final Map<ComponentsEnum, Double> range = new HashMap<>();

    public static void init() {
        ratio.put(ComponentsEnum.CPU, 0.27);
        ratio.put(ComponentsEnum.GPU, 0.35);
        ratio.put(ComponentsEnum.MOTHERBOARD, 0.19);
        ratio.put(ComponentsEnum.RAM, 0.11);
        ratio.put(ComponentsEnum.COOLING, 0.03);
        ratio.put(ComponentsEnum.POWER, 0.07);
        ratio.put(ComponentsEnum.CORPUS, 0.03);
        ratio.put(ComponentsEnum.DISK, 0.05);
        ratio.put(ComponentsEnum.EXTRA, 0.01);

        range.put(ComponentsEnum.CPU, 0.1);
        range.put(ComponentsEnum.GPU, 0.1);
        range.put(ComponentsEnum.MOTHERBOARD, 0.15);
        range.put(ComponentsEnum.RAM, 0.15);
        range.put(ComponentsEnum.COOLING, 0.5);
        range.put(ComponentsEnum.POWER, 0.3);
        range.put(ComponentsEnum.CORPUS, 1.0);
        range.put(ComponentsEnum.DISK, 1.0);
    }

    public static double getRatio(ComponentsEnum type) {
        return ratio.get(type);
    }

    public static double getRange(ComponentsEnum type) {
        return range.get(type);
    }
}