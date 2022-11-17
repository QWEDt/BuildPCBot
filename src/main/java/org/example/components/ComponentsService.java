package org.example.components;

import com.google.gson.Gson;
import org.example.components.computerParts.Component;
import org.example.enums.EnumComponents;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ComponentsService {
    private final ComponentsStorage componentsStorage;

    public ComponentsService() {
        Gson gson = new Gson();
        try {
            String path = "src/main/resources/components.json";
            componentsStorage = gson.fromJson(Files.readString(Path.of(path)), ComponentsStorage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отдает список комплектующих необходиого типа.
     * @param type тип комплектующих.
     * @param vendorCPU бренд цпу.
     * @param vendorGPU бренд гпу.
     * @return список всех комплектующих по выбранному типу.
     */
    public ArrayList<? extends Component> getComponents(EnumComponents type, String vendorCPU, String vendorGPU) {
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
