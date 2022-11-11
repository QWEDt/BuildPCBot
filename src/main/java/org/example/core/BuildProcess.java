package org.example.core;

import com.google.gson.Gson;
import org.example.components.*;
import org.example.components.enums.EnumComponents;
import org.example.exceptions.ComponentNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Нужен для сборки пк по заданным параметрам .
 */
public final class BuildProcess {
    Components components;

    /**
     * @param path путь до файла с компонентами.
     */
    public BuildProcess(String path) {
        Gson gson = new Gson();
        try {
            components = gson.fromJson(Files.readString(Path.of(path)), Components.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для генерации сборки на основе переданных параметров.
     *
     * @param money    Бюджет на сборку пк.
     * @param brandCPU Производитель цпу и мат. платы.
     * @param brandGPU Производитель видеокарты.
     * @return Готовая сборка компьютера.
     */
    public Computer build(double money, String brandCPU, String brandGPU) {
        Computer computer = new Computer();
        ComputerContext context = new ComputerContext();

        for (EnumComponents type : computer.componentsParts) { // Важно чтобы бп шел после цпу и гпу
            System.out.println(type);
            Component bestComponent;
            try {
                bestComponent = getBestComponent(type, brandCPU, brandGPU,
                        money * computer.ratio.get(type), context);
            } catch (ComponentNotFoundException e) {
                try {
                    bestComponent = getBestComponent(type, brandCPU, brandGPU,
                            money * computer.ratio.get(type) + money * computer.ratio.get(EnumComponents.EXTRA), context);
                } catch (ComponentNotFoundException ignored) {
                    break;
                }
            }

            if (bestComponent instanceof Processor) {
                context.socket = ((Processor) bestComponent).getSocket();
                context.tdp += ((Processor) bestComponent).getTdp();
                context.cpuTdp = ((Processor) bestComponent).getTdp();
            } else if (bestComponent instanceof VideoCard) {
                context.tdp += ((VideoCard) bestComponent).getTdp();
            }

            computer.setComponent(type, bestComponent);
        }

        System.out.println(context.tdp);

        return computer;
    }

    /**
     * Ищет лучшую комплектующую с текущими параметрами.
     *
     * @param type     Компонент который мы хотим найти.
     * @param brandCPU Производитель цпу.
     * @param brandGPU Производитель гпу.
     * @param money    Бюджет для текущей компоненты.
     * @return Лучший компонент.
     * @throws ComponentNotFoundException В случае нехватки денег.
     */
    private Component getBestComponent(EnumComponents type, String brandCPU, String brandGPU,
                                       double money, ComputerContext context) throws ComponentNotFoundException {
        ArrayList<? extends Component> typeComponents = components.getComponents(type, brandCPU, brandGPU);
        Component bestComponent = null;
        for (Component typeComponent : typeComponents) {
            boolean isOk = typeComponent.getPrice() <= money && makeDecision(typeComponent, context);
            if (bestComponent != null) {
                if (isOk && typeComponent.getPoints() > bestComponent.getPoints()) {
                    bestComponent = typeComponent;
                }
            } else {
                if (isOk) {
                    bestComponent = typeComponent;
                }
            }
        }

        if (bestComponent == null) {
            throw new ComponentNotFoundException();
        }

        return bestComponent;
    }

    private boolean makeDecision(Component component, ComputerContext context) {
        if (component instanceof Motherboard) {
            return context.socket.equals(((Motherboard) component).getSocket());
        } else if (component instanceof Cooling) {
            // У кулеров нет максимального охлаждения в ватт(9(
        } else if (component instanceof Power) {
            return context.tdp <= ((Power) component).getWatts();
        }
        return true;
    }

    private static class ComputerContext {
        private String socket;
        private int tdp = 150;
        private int cpuTdp;
        private int typeDdr;
    }
}
