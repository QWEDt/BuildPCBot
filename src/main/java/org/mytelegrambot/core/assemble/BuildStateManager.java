package org.mytelegrambot.core.assemble;

import org.mytelegrambot.enums.ComponentsEnum;

public class BuildStateManager {
    public static ComponentsEnum nextState(ComponentsEnum state) {
        switch (state) {
            case CPU -> {
                return ComponentsEnum.GPU;
            }
            case GPU -> {
                return ComponentsEnum.MOTHERBOARD;
            }
            case MOTHERBOARD -> {
                return ComponentsEnum.COOLING;
            }
            case COOLING -> {
                return ComponentsEnum.POWER;
            }
            case POWER -> {
                return ComponentsEnum.RAM;
            }
            case RAM -> {
                return ComponentsEnum.DISK;
            }
            case DISK -> {
                return ComponentsEnum.CORPUS;
            }
            case CORPUS -> {
                return ComponentsEnum.EXTRA;
            }
        }

        return state;
    }
}
