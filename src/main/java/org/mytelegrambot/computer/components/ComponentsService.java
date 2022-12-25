package org.mytelegrambot.computer.components;

import com.google.gson.Gson;
import org.mytelegrambot.computer.parts.Component;
import org.mytelegrambot.computer.parts.Motherboard;
import org.mytelegrambot.computer.parts.Processor;
import org.mytelegrambot.computer.parts.VideoCard;
import org.mytelegrambot.core.assemble.RatioContainer;
import org.mytelegrambot.enums.ComponentsEnum;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ComponentsService {
    private static ComponentsStorage componentsStorage = null;

    public static void init() {
        Gson gson = new Gson();
        try (InputStream is = ComponentsService.class.getClassLoader().getResourceAsStream("components.json")){
            assert is != null;
            componentsStorage = gson.fromJson(new InputStreamReader(is), ComponentsStorage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Component> getComponentsInPriceRange(double money, ComponentsEnum type) {
        List<Component> findComponents = new ArrayList<>();
        List<? extends Component> allComponents = getComponentsByType(type);
        assert allComponents != null;
        for (Component component : allComponents) {
            if (inRange(component, money, type)) {
                findComponents.add(component);
            }
        }

        return findComponents;
    }

    private static boolean inRange(Component component, double money, ComponentsEnum type) {
        return component.getPrice() > money * (1 - RatioContainer.getRange(type))
                && component.getPrice() < money * (1 + RatioContainer.getRange(type));
    }

    private static List<? extends Component> getComponentsByType(ComponentsEnum type) {
        switch (type) {
            case CPU -> {
                List<Processor> toReturn = componentsStorage.getCpus("intel");
                toReturn.addAll(componentsStorage.getCpus("amd"));
                return toReturn;
            }
            case GPU -> {
                List<VideoCard> toReturn = componentsStorage.getGpus("nvidia");
                toReturn.addAll(componentsStorage.getGpus("amd"));
                return toReturn;
            }
            case MOTHERBOARD -> {
                List<Motherboard> toReturn = componentsStorage.getMotherboards("intel");
                toReturn.addAll(componentsStorage.getMotherboards("amd"));
                return toReturn;
            }
            case COOLING -> {
                return componentsStorage.getCooling();
            }
            case POWER -> {
                return componentsStorage.getPowers();
            }
            case RAM -> {
                return componentsStorage.getRams("ddr4");
            }
            case DISK -> {
                return componentsStorage.getDisks();
            }
            case CORPUS -> {
                return componentsStorage.getCorpuses();
            }
        }
        return null;
    }

    /**
     * Отдает список комплектующих необходиого типа.
     * @param type тип комплектующих.
     * @param vendorCPU бренд цпу.
     * @param vendorGPU бренд гпу.
     * @return список всех комплектующих по выбранному типу.
     */
    public static ArrayList<? extends Component> getComponents(ComponentsEnum type, String vendorCPU, String vendorGPU) {
        switch (type){
            case CPU -> {
                return componentsStorage.getCpus(vendorCPU);
            }
            case GPU -> {
                return componentsStorage.getGpus(vendorGPU);
            }
            case MOTHERBOARD -> {
                return componentsStorage.getMotherboards(vendorCPU);
            }
            case COOLING -> {
                return componentsStorage.getCooling();
            }
            case POWER -> {
                return componentsStorage.getPowers();
            }
            case RAM -> {
                return componentsStorage.getRams("ddr4");
            }
            case DISK -> {
                return componentsStorage.getDisks();
            }
            case CORPUS -> {
                return componentsStorage.getCorpuses();
            }
        }
        return null;
    }
}
