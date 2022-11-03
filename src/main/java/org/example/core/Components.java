package org.example.core;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Хранит все компоненты из передаваемого json файла
 * и отдает списки с нужным типом компонентов
 */
public final class Components {
    //TODO Переделать в классы
    private final HashMap<String, List<HashMap<String, String>>> CPUs;
    private final HashMap<String, List<HashMap<String, String>>> GPUs;
    private final HashMap<String, List<HashMap<String, String>>> motherboards;
    private final HashMap<String, List<HashMap<String, String>>> ram;
    private final List<HashMap<String, String>> cooling;
    private final List<HashMap<String, String>> powers;
    private final List<HashMap<String, String>> corpuses;
    private final List<HashMap<String, String>> disks;

    public Components(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String json = "";
        while (scanner.hasNextLine()) {
            json = json.concat(scanner.nextLine());
        }
        ReadContext context = JsonPath.parse(json);
        CPUs = context.read("$.cpu");
        GPUs = context.read("$.gpu");
        motherboards = context.read("$.motherboard");
        ram = context.read("$.ram");
        cooling = context.read("$.cooling");
        powers = context.read("$.power");
        corpuses = context.read("$.corpus");
        disks = context.read("$.disk");
    }

    public List<HashMap<String, String>> getCPUs(String type) {
        return CPUs.get(type);
    }

    public List<HashMap<String, String>> getGPUs(String type) {
        return GPUs.get(type);
    }

    public List<HashMap<String, String>> getMotherboards(String type) {
        return motherboards.get(type);
    }

    public List<HashMap<String, String>> getRAM(String type) {
        return ram.get(type);
    }

    public List<HashMap<String, String>> getCooling() {
        return cooling;
    }

    public List<HashMap<String, String>> getPowers() {
        return powers;
    }

    public List<HashMap<String, String>> getCorpuses() {
        return corpuses;
    }

    public List<HashMap<String, String>> getDisks() {
        return disks;
    }

    public List<HashMap<String, String>> getComponent(String type, String cpu, String gpu, String ram) {
        return switch (type) {
            case "cpu" -> getCPUs(cpu);
            case "gpu" -> getGPUs(gpu);
            case "motherboard" -> getMotherboards(cpu);
            case "ram" -> getRAM("ddr4");
            case "cooling" -> getCooling();
            case "power" -> getPowers();
            case "corpus" -> getCorpuses();
            case "disk" -> getDisks();
            default -> null;
        };
    }
}
