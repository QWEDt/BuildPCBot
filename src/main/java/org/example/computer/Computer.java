package org.example.computer;

import org.example.computer.parts.Component;
import org.example.enums.ComponentsEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения и вывода результата сборки пк.
 */
public class Computer {
    private int totalPrice;
    private final Map<ComponentsEnum, Component> components = new HashMap<>();
    public ArrayList<ComponentsEnum> componentsParts = new ArrayList<>();
    public Map<ComponentsEnum, Double> ratio = new HashMap<>();

    public Computer() {
        componentsParts.add(ComponentsEnum.CPU);
        componentsParts.add(ComponentsEnum.GPU);
        componentsParts.add(ComponentsEnum.MOTHERBOARD);
        componentsParts.add(ComponentsEnum.RAM);
        componentsParts.add(ComponentsEnum.COOLING);
        componentsParts.add(ComponentsEnum.POWER);
        componentsParts.add(ComponentsEnum.CORPUS);
        componentsParts.add(ComponentsEnum.DISK);

        ratio.put(ComponentsEnum.CPU, 0.27);
        ratio.put(ComponentsEnum.GPU, 0.35);
        ratio.put(ComponentsEnum.MOTHERBOARD, 0.16);
        ratio.put(ComponentsEnum.RAM, 0.11);
        ratio.put(ComponentsEnum.COOLING, 0.03);
        ratio.put(ComponentsEnum.POWER, 0.07);
        ratio.put(ComponentsEnum.CORPUS, 0.03);
        ratio.put(ComponentsEnum.DISK, 0.05);
        ratio.put(ComponentsEnum.EXTRA, 0.01);
    }

    public Component getComponent(ComponentsEnum component) {
        return components.get(component);
    }

    public void setComponent(ComponentsEnum name, Component component) {
        components.put(name, component);
    }

    public String getInfo() {
        if (!CheckThePresenceOfAllElements()) return "Сорри, времена тяжелые. На это ничего не собрать";

        calculateTotalPrice();
        return generateStringComputer();
    }

    private boolean CheckThePresenceOfAllElements() {
        for (ComponentsEnum key : componentsParts) {
            if (components.get(key) == null) return false;
        }
        return true;
    }

    private void calculateTotalPrice() {
        totalPrice = 0;
        for (ComponentsEnum key : components.keySet()) {
            totalPrice += components.get(key).getPrice();
        }
    }

    private String generateStringComputer() {
        return "Ваш процессор: " + getComponent(ComponentsEnum.CPU).getName() + " | цена " +
                getComponent(ComponentsEnum.CPU).getPrice() + "\n" +
                "Ваша материнская плата: " + getComponent(ComponentsEnum.MOTHERBOARD).getName() + " | цена " +
                getComponent(ComponentsEnum.MOTHERBOARD).getPrice() + "\n" +
                "Ваша видеокарта: " + getComponent(ComponentsEnum.GPU).getName() + " | цена " +
                getComponent(ComponentsEnum.GPU).getPrice() + "\n" +
                "Ваша озу: " + getComponent(ComponentsEnum.RAM).getName() + " | цена " +
                getComponent(ComponentsEnum.RAM).getPrice() + "\n" +
                "Ваше охлаждение: " + getComponent(ComponentsEnum.COOLING).getName() + " | цена " +
                getComponent(ComponentsEnum.COOLING).getPrice() + "\n" +
                "Ваш блок питания: " + getComponent(ComponentsEnum.POWER).getName() + " | цена " +
                getComponent(ComponentsEnum.POWER).getPrice() + "\n" +
                "Ваш диск: " + getComponent(ComponentsEnum.DISK).getName() + " | цена " +
                getComponent(ComponentsEnum.DISK).getPrice() + "\n" +
                "Ваш корпус: " + getComponent(ComponentsEnum.CORPUS).getName() + " | цена " +
                getComponent(ComponentsEnum.CORPUS).getPrice() + "\n\n" +
                "Общая цена " + totalPrice + "\n";
    }
}
