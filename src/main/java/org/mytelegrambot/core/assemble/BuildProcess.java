package org.mytelegrambot.core.assemble;

import org.mytelegrambot.computer.ComponentsParts;
import org.mytelegrambot.computer.Computer;
import org.mytelegrambot.computer.components.ComponentsService;
import org.mytelegrambot.computer.parts.*;
import org.mytelegrambot.enums.ComponentsEnum;
import org.mytelegrambot.exceptions.ComponentNotFoundException;
import org.mytelegrambot.exceptions.ComponentStorageException;

import java.util.ArrayList;
import java.util.List;

/**
 * Нужен для сборки пк по заданным параметрам .
 */
public final class BuildProcess {
    private static String socket;
    private static int tdp = 150;
    private static int cpuTdp;

    /**
     * Метод для генерации сборки на основе переданных параметров.
     *
     * @param money    Бюджет на сборку пк.
     * @param brandCPU Производитель цпу и мат. платы.
     * @param brandGPU Производитель видеокарты.
     * @return Готовая сборка компьютера.
     */
    public Computer build(double money, String brandCPU, String brandGPU) throws ComponentStorageException, ComponentNotFoundException {
        resetValues();
        Computer computer = new Computer();

        int attempts = 6;

        for (ComponentsEnum type : ComponentsParts.componentsParts) {
            Component bestComponent = null;
            for (int i = 0; i < attempts; i++) {
                try {
                    bestComponent = getBestComponent(type, brandCPU, brandGPU, money * RatioContainer.getRatio(type)
                            + money * RatioContainer.getRatio(ComponentsEnum.EXTRA) * i);
                    break;
                } catch (ComponentNotFoundException ignored) {
                    if (i == attempts - 1) throw new ComponentNotFoundException();
                }
            }

            buildChecks(bestComponent);

            computer.setComponent(type, bestComponent);
        }

        return computer;
    }

    /**
     * @param money Ценовой сегмент.
     * @param stage на каком этапе сборки пк мы сейчас.
     */
    public static List<Component> extraBuild(double money, ComponentsEnum stage) {
        List<Component> components = ComponentsService.getComponentsInPriceRange(money, stage);
        System.out.println(components.get(0));
        components.removeIf(component -> !makeDecision(component));

        return components;
    }

    public static void buildChecks(Component bestComponent) {
        if (bestComponent instanceof Processor) {
            socket = ((Processor) bestComponent).getSocket();
            tdp += ((Processor) bestComponent).getTdp();
            cpuTdp = ((Processor) bestComponent).getTdp();
        } else if (bestComponent instanceof VideoCard) {
            tdp += ((VideoCard) bestComponent).getTdp();
        }
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
    private Component getBestComponent(ComponentsEnum type, String brandCPU, String brandGPU,
                                       double money) throws ComponentNotFoundException, ComponentStorageException {
        ArrayList<? extends Component> typeComponents = ComponentsService.getComponents(type, brandCPU, brandGPU);

        if (typeComponents == null)
            throw new ComponentStorageException();

        Component bestComponent = null;
        for (Component typeComponent : typeComponents) {
            if (typeComponent.getPrice() > money || !makeDecision(typeComponent)) continue;

            if (bestComponent == null) {
                bestComponent = typeComponent;
            } else if (typeComponent.getPoints() > bestComponent.getPoints()) {
                bestComponent = typeComponent;
            }
        }

        if (bestComponent == null) {
            throw new ComponentNotFoundException();
        }

        return bestComponent;
    }

    private static boolean makeDecision(Component component) {
        if (component instanceof Motherboard) {
            return socket.equals(((Motherboard) component).getSocket());
        } else if (component instanceof Cooling) {
            return cpuTdp <= ((Cooling) component).getTdp();
        } else if (component instanceof Power) {
            return tdp <= ((Power) component).getWatts();
        }
        return true;
    }

    public static void resetValues() {
        socket = null;
        tdp = 150;
        cpuTdp = 0;
    }
}
