package org.example;

import org.example.exceptions.ComponentNotFoundException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class BuildProcess {
    Components components;
    public BuildProcess() {
        try {
            components = new Components("src/main/resources/components.json");
        } catch (FileNotFoundException e) {
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
    public HashMap<String, HashMap<String, String>> build(int money, String BrandCpu, String BrandGpu) {
        HashMap<String, Double> ratio = new HashMap<>();

        ratio.put("cpu", 0.27);
        ratio.put("gpu", 0.35);
        ratio.put("motherboard", 0.16);
        ratio.put("ram", 0.06);
        ratio.put("cooling", 0.03);
        ratio.put("power", 0.07);
        ratio.put("corpus", 0.03);
        ratio.put("disk", 0.05);
        ratio.put("extra", 0.05);

        HashMap<String, HashMap<String, String>> assembled = new HashMap<>();
        List<String> componentsParts = new ArrayList<>();
        componentsParts.add("cpu");
        componentsParts.add("gpu");
        componentsParts.add("motherboard");
        componentsParts.add("ram");
        componentsParts.add("cooling");
        componentsParts.add("power");
        componentsParts.add("corpus");
        componentsParts.add("disk");

        for (String componentsPart : componentsParts) {
            try {
                assembled.put(componentsPart, searchBestComponent(componentsPart, BrandCpu, BrandGpu, money * ratio.get(componentsPart)));
            } catch (ComponentNotFoundException e) {
                try {
                    assembled.put(componentsPart, searchBestComponent(componentsPart, BrandCpu, BrandGpu, money * ratio.get(componentsPart) + money * ratio.get("extra")));
                } catch (ComponentNotFoundException er) {
                    return null;
                }
            }
        }

        return assembled;
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
    private HashMap<String, String> searchBestComponent(String component, String BrandCpu, String BrandGpu, double money) throws ComponentNotFoundException {
        List<HashMap<String, String>> whereToSearch = components.getComponent(component, BrandCpu, BrandGpu, "");
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
        return bestComponent;
    }
}
