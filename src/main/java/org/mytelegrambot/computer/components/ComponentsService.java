package org.mytelegrambot.computer.components;

import com.google.gson.Gson;
import org.mytelegrambot.computer.parts.Component;
import org.mytelegrambot.computer.parts.Motherboard;
import org.mytelegrambot.computer.parts.Processor;
import org.mytelegrambot.computer.parts.VideoCard;
import org.mytelegrambot.enums.ComponentsEnum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentsService {
    private static ComponentsStorage componentsStorage = null;

    public static void init() {
        Gson gson = new Gson();
        try {
            //TODO read from resources
            try (var is = ComponentsService.class.getClassLoader().getResourceAsStream("components.json")) {
                //todo
            }
            String path = "src/main/resources/components.json";
            componentsStorage = gson.fromJson(Files.readString(Path.of(path)), ComponentsStorage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Component> getComponentsInPriceRange(int money, ComponentsEnum type) {
        List<Component> findComponents = new ArrayList<>();
        List<? extends Component> allComponents = getComponentsByType(type);
        assert allComponents != null;
        for (Component component : allComponents) {
            if (inRange(component, money)) {
                findComponents.add(component);
            }
        }

        return findComponents;
    }

    private static boolean inRange(Component component, int money) {
        return component.getPrice() > money * 0.75 && component.getPrice() < money * 1.25;
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
     */  //todo
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
