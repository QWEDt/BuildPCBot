package org.example.core;

import com.google.gson.Gson;
import org.example.components.Component;
import org.example.components.enums.EnumComponents;
import org.example.exceptions.ComponentNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class BuildProcess {
    Components components;

    /**
     * @param path путь до файла с компонентами
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
     * @param money Бюджет на сборку пк
     * @param brandCPU Производитель цпу и мат. платы
     * @param brandGPU Производитель видеокарты
     * @return Сборка компьютера из элементов или null в случае нехватки денег.
     */
    public Computer build(double money, String brandCPU, String brandGPU) {
        Computer computer = new Computer();

        for (EnumComponents type : computer.componentsParts) {
            try {
                computer.setComponent(type, getBestComponent(type, brandCPU, brandGPU,
                        money * computer.ratio.get(type)));
            } catch (ComponentNotFoundException e) {
                try {
                    computer.setComponent(type, getBestComponent(type, brandCPU, brandGPU,
                            money * computer.ratio.get(type) + money * computer.ratio.get(EnumComponents.EXTRA)));
                } catch (ComponentNotFoundException ignored) {}
            }
        }

        return computer;
    }
    /**
     * Ищет лучшую комплектующую с текущими параметрами
     * @param type Компонент который мы хотим найти
     * @param brandCPU Производитель цпу
     * @param brandGPU Производитель гпу
     * @param money Бюджет для текущей компоненты
     * @return Лучший компонент
     * @throws ComponentNotFoundException В случае нехватки денег
     */
    private Component getBestComponent(EnumComponents type, String brandCPU, String brandGPU, double money) throws ComponentNotFoundException {
        ArrayList<? extends Component> typeComponents = components.getComponents(type, brandCPU, brandGPU);
        Component bestComponent = null;
        for (Component typeComponent : typeComponents) {
            if (bestComponent != null) {
                if (typeComponent.getPrice() <= money && typeComponent.getPoints() > bestComponent.getPoints()) {
                    bestComponent = typeComponent;
                }
            } else {
                if (typeComponent.getPrice() <= money) {
                    bestComponent = typeComponent;
                }
            }

            if (bestComponent == null)
            {
                throw new ComponentNotFoundException();
            }
        }
        return bestComponent;
    }
}
