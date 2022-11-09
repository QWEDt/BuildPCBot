package org.example.components;

public class Component {
    protected String name;
    protected int price;
    protected int points;

    public Component(String name, int price, int points) {
        this.name = name;
        this.price = price;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getPoints() {
        return points;
    }
}
