package org.example.core;

import org.example.components.*;

import java.util.HashMap;
import java.util.Map;

public class Computer {
    private int totalPrice;
    Map<EnumComponents, Component> components = new HashMap<>();

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
                getComponent(EnumComponents.CORPUS).getPrice() + "\n" +
                "Общая цена " + totalPrice + "\n";
    }
}
