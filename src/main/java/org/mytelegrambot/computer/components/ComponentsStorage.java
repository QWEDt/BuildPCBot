package org.mytelegrambot.computer.components;

import org.mytelegrambot.computer.parts.*;

import java.util.ArrayList;
import java.util.HashMap;

//todo record
/**
 * Содержит в себе все доступные комплектующие.
 */
public class ComponentsStorage {
    private HashMap<String, ArrayList<Processor>> cpu;
    private HashMap<String, ArrayList<VideoCard>> gpu;
    private HashMap<String, ArrayList<Motherboard>> motherboard;
    private HashMap<String, ArrayList<Ram>> ram;
    private ArrayList<Cooling> cooling;
    private ArrayList<Power> power;
    private ArrayList<Corpus> corpus;
    private ArrayList<Disk> disk;

    public ArrayList<Processor> getCpus(String vendor) {
        return cpu.get(vendor);
    }

    public ArrayList<VideoCard> getGpus(String vendor) {
        return gpu.get(vendor);
    }

    public ArrayList<Motherboard> getMotherboards(String vendor) {
        return motherboard.get(vendor);
    }

    public ArrayList<Ram> getRams(String type) {
        return ram.get(type);
    }

    public ArrayList<Cooling> getCooling() {
        return cooling;
    }

    public ArrayList<Power> getPowers() {
        return power;
    }

    public ArrayList<Corpus> getCorpuses() {
        return corpus;
    }

    public ArrayList<Disk> getDisks() {
        return disk;
    }
}
