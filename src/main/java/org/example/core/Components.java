package org.example.core;

import org.example.components.*;
import org.example.components.enums.EnumComponents;

import java.util.ArrayList;
import java.util.HashMap;

public class Components {
    private HashMap<String, ArrayList<Processor>> cpu;
    private HashMap<String, ArrayList<VideoCard>> gpu;
    private HashMap<String, ArrayList<Motherboard>> motherboard;
    private HashMap<String, ArrayList<Ram>> ram;
    private ArrayList<Cooling> cooling;
    private ArrayList<Power> power;
    private ArrayList<Corpus> corpus;
    private ArrayList<Disk> disk;

    private ArrayList<Processor> getCpus(String vendor) {
        return cpu.get(vendor);
    }

    private ArrayList<VideoCard> getGpus(String vendor) {
        return gpu.get(vendor);
    }

    private ArrayList<Motherboard> getMotherboards(String vendor) {
        return motherboard.get(vendor);
    }

    private ArrayList<Ram> getRams(String type) {
        return ram.get(type);
    }

    private ArrayList<Cooling> getCooling() {
        return cooling;
    }

    private ArrayList<Power> getPowers() {
        return power;
    }

    private ArrayList<Corpus> getCorpuses() {
        return corpus;
    }

    private ArrayList<Disk> getDisks() {
        return disk;
    }

    public ArrayList<? extends Component> getComponents(EnumComponents type, String vendorCPU, String vendorGPU) {
        switch (type){
            case CPU -> {
                return getCpus(vendorCPU);
            }
            case GPU -> {
                return getGpus(vendorGPU);
            }
            case MOTHERBOARD -> {
                return getMotherboards(vendorCPU);
            }
            case COOLING -> {
                return getCooling();
            }
            case POWER -> {
                return getPowers();
            }
            case RAM -> {
                return getRams("ddr4");
            }
            case DISK -> {
                return getDisks();
            }
            case CORPUS -> {
                return getCorpuses();
            }
        }
        return null;
    }
}
