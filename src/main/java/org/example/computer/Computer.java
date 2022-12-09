package org.example.computer;

import org.example.computer.parts.Component;
import org.example.enums.ComponentsEnum;
import org.example.text.TextContainer;
import org.example.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для хранения и вывода результата сборки пк.
 */
public class Computer {
    private int totalPrice;
    private final Map<ComponentsEnum, Component> components = new HashMap<>();
    public ArrayList<ComponentsEnum> componentsParts = new ArrayList<>();
    public Map<ComponentsEnum, Double> ratio = new HashMap<>();
    private boolean isPublic;
    private int likes;
    private final List<User> userLikes = new ArrayList<>();
    private String creator;

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

    public boolean isUserLiked(User user) {
        return userLikes.contains(user);
    }

    public void addUserLike(User user) {
        likes += 1;
        this.userLikes.add(user);
    }

    public void deleteUserLike(User user) {
        likes -= 1;
        userLikes.remove(user);
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getLikes() {
        return likes;
    }

    public Component getComponent(ComponentsEnum component) {
        return components.get(component);
    }

    public void setComponent(ComponentsEnum name, Component component) {
        components.put(name, component);
    }

    public String getInfo(boolean fromPublic, String name) {
        if (!CheckThePresenceOfAllElements()) return "Сорри, времена тяжелые. На это ничего не собрать";

        calculateTotalPrice();
        return generateStringComputer(fromPublic, name);
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

    private String generateStringComputer(boolean fromPublic, String name) {
        String extraInfo = "";
        if (isPublic && fromPublic) {
            extraInfo += name + "\n\n" +
                    creator + " - создатель\n" +
                    "Лайков " + likes + "\n\n";
        } else if (!fromPublic) {
            extraInfo += TextContainer.isPublic(this) + "\n\n";
        }
        return  extraInfo +
                "Процессор: " + getComponent(ComponentsEnum.CPU).getName() + " | цена " +
                getComponent(ComponentsEnum.CPU).getPrice() + "\n" +
                "Материнская плата: " + getComponent(ComponentsEnum.MOTHERBOARD).getName() + " | цена " +
                getComponent(ComponentsEnum.MOTHERBOARD).getPrice() + "\n" +
                "Видеокарта: " + getComponent(ComponentsEnum.GPU).getName() + " | цена " +
                getComponent(ComponentsEnum.GPU).getPrice() + "\n" +
                "ОЗУ: " + getComponent(ComponentsEnum.RAM).getName() + " | цена " +
                getComponent(ComponentsEnum.RAM).getPrice() + "\n" +
                "Охлаждение: " + getComponent(ComponentsEnum.COOLING).getName() + " | цена " +
                getComponent(ComponentsEnum.COOLING).getPrice() + "\n" +
                "Блок питания: " + getComponent(ComponentsEnum.POWER).getName() + " | цена " +
                getComponent(ComponentsEnum.POWER).getPrice() + "\n" +
                "Диск: " + getComponent(ComponentsEnum.DISK).getName() + " | цена " +
                getComponent(ComponentsEnum.DISK).getPrice() + "\n" +
                "Корпус: " + getComponent(ComponentsEnum.CORPUS).getName() + " | цена " +
                getComponent(ComponentsEnum.CORPUS).getPrice() + "\n\n" +
                "Общая цена " + totalPrice + "\n";
    }
}
