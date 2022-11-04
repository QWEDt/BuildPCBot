package org.example.core;

import org.example.components.*;
import org.example.exceptions.ComponentNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class BuildProcess {
    Components components;
    public BuildProcess() {
        try {
            components = new Components("src/main/resources/components.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param money Бюджет на сборку пк
     * @param BrandCpu Производитель цпу и мат. платы
     * @param BrandGpu Производитель видеокарты
     * @return Сборка компьютера из элементов или null в случае нехватки денег.
     */
    public Computer build(int money, String BrandCpu, String BrandGpu) {
        HashMap<EnumComponents, Double> ratio = new HashMap<>();

        ratio.put(EnumComponents.CPU, 0.27);
        ratio.put(EnumComponents.GPU, 0.35);
        ratio.put(EnumComponents.MOTHERBOARD, 0.16);
        ratio.put(EnumComponents.RAM, 0.11);
        ratio.put(EnumComponents.COOLING, 0.03);
        ratio.put(EnumComponents.POWER, 0.07);
        ratio.put(EnumComponents.CORPUS, 0.03);
        ratio.put(EnumComponents.DISK, 0.05);
        ratio.put(EnumComponents.EXTRA, 0.05);

        Computer computer = new Computer();
        List<EnumComponents> componentsParts = new ArrayList<>();
        componentsParts.add(EnumComponents.CPU);
        componentsParts.add(EnumComponents.GPU);
        componentsParts.add(EnumComponents.MOTHERBOARD);
        componentsParts.add(EnumComponents.RAM);
        componentsParts.add(EnumComponents.COOLING);
        componentsParts.add(EnumComponents.POWER);
        componentsParts.add(EnumComponents.CORPUS);
        componentsParts.add(EnumComponents.DISK);


        for (EnumComponents componentsPart : componentsParts) {
            try {
                computer.setComponent(componentsPart, searchBestComponent(componentsPart, BrandCpu, BrandGpu,
                        money * ratio.get(componentsPart)));
            } catch (ComponentNotFoundException e) {
                try {
                    computer.setComponent(componentsPart, searchBestComponent(componentsPart, BrandCpu, BrandGpu,
                            money * ratio.get(componentsPart) + money * ratio.get(EnumComponents.EXTRA)));
                } catch (ComponentNotFoundException ignored) {
                }
            }
        }

        return computer;
    }

    /**
     * Ищет лучшую комплектующую с текущими параметрами
     * @param component Компонент который мы хотим найти
     * @param BrandCpu Производитель цпу
     * @param BrandGpu Производитель гпу
     * @param money Бюджет для текущей компоненты
     * @return Лучший компонент
     * @throws ComponentNotFoundException В случае нехватки денег
     */
    private Component searchBestComponent(EnumComponents component, String BrandCpu, String BrandGpu, double money) throws ComponentNotFoundException {
        List<HashMap<String, String>> whereToSearch = components.getComponents(component, BrandCpu, BrandGpu, "");
        HashMap<String, String> bestComponent = null;
        for (HashMap<String, String> toSearch : whereToSearch) {
            if (bestComponent != null) {
                if (Integer.parseInt(toSearch.get("price")) <= money && Integer.parseInt(toSearch.get("points")) > Integer.parseInt(bestComponent.get("points"))) {
                    bestComponent = toSearch;
                }
            } else {
                if (Integer.parseInt(toSearch.get("price")) <= money) {
                    bestComponent = toSearch;
                }
            }
        }

        if (bestComponent == null)
        {
            throw new ComponentNotFoundException();
        }

        switch (component) {
            case CPU -> {
                return new Processor(bestComponent.get("name"), Integer.parseInt(bestComponent.get("price")),
                        bestComponent.get("socket"), Integer.parseInt(bestComponent.get("tdp")), bestComponent.get("igpu"));
            }
            case GPU -> {
                return new VideoCard(bestComponent.get("name"), Integer.parseInt(bestComponent.get("price")),
                        Integer.parseInt(bestComponent.get("tdp")));
            }
            case MOTHERBOARD -> {
                return new Motherboard(bestComponent.get("name"), Integer.parseInt(bestComponent.get("price")),
                        bestComponent.get("socket"), bestComponent.get("chipset"),
                        Boolean.parseBoolean(bestComponent.get("radiators")), bestComponent.get("form"),
                        Integer.parseInt(bestComponent.get("slots_ram")));
            }
            case COOLING -> {
                return new Cooling(bestComponent.get("name"), Integer.parseInt(bestComponent.get("price")),
                        Boolean.parseBoolean(bestComponent.get("water")));
            }
            case POWER -> {
                return new Power(bestComponent.get("name"), Integer.parseInt(bestComponent.get("price")),
                        Integer.parseInt(bestComponent.get("watt")), bestComponent.get("plus"));
            }
            case RAM -> {
                return new Ram(bestComponent.get("name"), Integer.parseInt(bestComponent.get("price")),
                        bestComponent.get("type"));
            }
            case DISK -> {
                return new Disk(bestComponent.get("name"), Integer.parseInt(bestComponent.get("price")),
                        bestComponent.get("type"));
            }
            case CORPUS -> {
                return new Corpus(bestComponent.get("name"), Integer.parseInt(bestComponent.get("price")));
            }
        }
        return null;
    }
}
