package org.example;

import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public final class BuildProcess {
    Components components;
    public BuildProcess() {
        try {
            components = new Components("E:\\Projects\\Java\\BuildPCBot\\src\\data\\components.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public HashMap<String, HashMap<String, String>> build(int money, String BrandCpu, String BrandGpu) {
        HashMap<String, Double> ratio = new HashMap<String, Double>();

        ratio.put("cpu", 0.15);
        ratio.put("gpu", 0.3);
        ratio.put("motherboard", 0.12);
        ratio.put("ram", 0.1);
        ratio.put("cooling", 0.05);
        ratio.put("power", 0.15);
        ratio.put("corpus", 0.05);
        ratio.put("disk", 0.08);

        HashMap<String, HashMap<String, String>> assembled = new HashMap<String, HashMap<String, String>>();
        assembled.put("cpu", searchBestComponent("cpu", BrandCpu, "", money * ratio.get("cpu")));
        assembled.put("gpu", searchBestComponent("gpu", "", BrandGpu, money * ratio.get("gpu")));
        assembled.put("motherboard", searchBestComponent("motherboard", BrandCpu, "", money * ratio.get("motherboard")));
        assembled.put("ram", searchBestComponent("ram", "", "", money * ratio.get("ram")));
        assembled.put("cooling", searchBestComponent("cooling", "", "", money * ratio.get("cooling")));
        assembled.put("power", searchBestComponent("power", "", "", money * ratio.get("power")));
        assembled.put("corpus", searchBestComponent("corpus", "", "", money * ratio.get("corpus")));
        assembled.put("disk", searchBestComponent("disk", "", "", money * ratio.get("disk")));
        return assembled;
    }

    private HashMap<String, String> searchBestComponent(String component, String BrandCpu, String BrandGpu, double money) {
        List<HashMap<String, String>> whereToSearch = components.getComponent(component, BrandCpu, BrandGpu, "");
        return new HashMap<String, String>();
    }
}
