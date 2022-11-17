package org.example.core;

import org.example.components.*;
import org.example.components.computerParts.*;
import org.example.enums.EnumComponents;
import org.example.exceptions.ComponentNotFoundException;

import java.util.ArrayList;

/**
 * Нужен для сборки пк по заданным параметрам .
 */
public final class BuildProcess {
    private String socket;
    private int tdp = 150;
    private int cpuTdp;
    ComponentsService componentsService;

    public BuildProcess() {
        componentsService = new ComponentsService();
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

        for (EnumComponents type : computer.componentsParts) {
            Component bestComponent;
            try {
                bestComponent = getBestComponent(type, brandCPU, brandGPU,
                        money * computer.ratio.get(type));
            } catch (ComponentNotFoundException e) {
                try {
                    bestComponent = getBestComponent(type, brandCPU, brandGPU,
                            money * computer.ratio.get(type) + money * computer.ratio.get(EnumComponents.EXTRA));
                } catch (ComponentNotFoundException ignored) {
                    break;
                }
            }

            if (bestComponent instanceof Processor) {
                socket = ((Processor) bestComponent).getSocket();
                tdp += ((Processor) bestComponent).getTdp();
                cpuTdp = ((Processor) bestComponent).getTdp();
            } else if (bestComponent instanceof VideoCard) {
                tdp += ((VideoCard) bestComponent).getTdp();
            }

            computer.setComponent(type, bestComponent);
        }

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
                                       double money) throws ComponentNotFoundException {
        ArrayList<? extends Component> typeComponents = componentsService.getComponents(type, brandCPU, brandGPU);
        Component bestComponent = null;
        System.out.println(typeComponents);
        for (Component typeComponent : typeComponents) {
            boolean isOk = typeComponent.getPrice() <= money && makeDecision(typeComponent);

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

    private boolean makeDecision(Component component) {
        if (component instanceof Motherboard) {
            return socket.equals(((Motherboard) component).getSocket());
        } else if (component instanceof Cooling) {
            return cpuTdp <= ((Cooling) component).getTdp();
        } else if (component instanceof Power) {
            return tdp <= ((Power) component).getWatts();
        }
        return true;
    }
}
