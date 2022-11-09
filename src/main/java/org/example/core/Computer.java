package org.example.core;

import org.example.components.*;
import org.example.components.enums.EnumComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения и вывода результата сборки пк.
 */
public class Computer {
    private int totalPrice;
    private final Map<EnumComponents, Component> components = new HashMap<>();
    public ArrayList<EnumComponents> componentsParts = new ArrayList<>();
    public HashMap<EnumComponents, Double> ratio = new HashMap<>();

    public Computer() {
        componentsParts.add(EnumComponents.CPU);
        componentsParts.add(EnumComponents.GPU);
        componentsParts.add(EnumComponents.MOTHERBOARD);
        componentsParts.add(EnumComponents.RAM);
        componentsParts.add(EnumComponents.COOLING);
        componentsParts.add(EnumComponents.POWER);
        componentsParts.add(EnumComponents.CORPUS);
        componentsParts.add(EnumComponents.DISK);

        ratio.put(EnumComponents.CPU, 0.27);
        ratio.put(EnumComponents.GPU, 0.35);
        ratio.put(EnumComponents.MOTHERBOARD, 0.16);
        ratio.put(EnumComponents.RAM, 0.11);
        ratio.put(EnumComponents.COOLING, 0.03);
        ratio.put(EnumComponents.POWER, 0.07);
        ratio.put(EnumComponents.CORPUS, 0.03);
        ratio.put(EnumComponents.DISK, 0.05);
        ratio.put(EnumComponents.EXTRA, 0.05);
    }

    public Component getComponent(EnumComponents component) {
        return components.get(component);
    }

    public void setComponent(EnumComponents name, Component component) {
        components.put(name, component);
    }

    public String getComputer() {
        if (components.size() < 8) {
            return "Сорри, времена тяжелые. На это ничего не собрать";
        }
        calculateTotalPrice();
        return generateStringComputer();
    }

    private void calculateTotalPrice() {
        totalPrice = 0;
        for (EnumComponents key : components.keySet()) {
            totalPrice += components.get(key).getPrice();
        }
    }

    private String generateStringComputer() {
        return "Ваш процессор: " + getComponent(EnumComponents.CPU).getName() + " | цена " +
                getComponent(EnumComponents.CPU).getPrice() + "\n" +
                "Ваша материнская плата: " + getComponent(EnumComponents.MOTHERBOARD).getName() + " | цена " +
                getComponent(EnumComponents.MOTHERBOARD).getPrice() + "\n" +
                "Ваша видеокарта: " + getComponent(EnumComponents.GPU).getName() + " | цена " +
                getComponent(EnumComponents.GPU).getPrice() + "\n" +
                "Ваша озу: " + getComponent(EnumComponents.RAM).getName() + " | цена " +
                getComponent(EnumComponents.RAM).getPrice() + "\n" +
                "Ваше охлаждение: " + getComponent(EnumComponents.COOLING).getName() + " | цена " +
                getComponent(EnumComponents.COOLING).getPrice() + "\n" +
                "Ваш блок питания: " + getComponent(EnumComponents.POWER).getName() + " | цена " +
                getComponent(EnumComponents.POWER).getPrice() + "\n" +
                "Ваш диск: " + getComponent(EnumComponents.DISK).getName() + " | цена " +
                getComponent(EnumComponents.DISK).getPrice() + "\n" +
                "Ваш корпус: " + getComponent(EnumComponents.CORPUS).getName() + " | цена " +
                getComponent(EnumComponents.CORPUS).getPrice() + "\n\n" +
                "Общая цена " + totalPrice + "\n";
    }
}
