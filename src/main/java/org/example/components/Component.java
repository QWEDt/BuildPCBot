package org.example.components;

public class Component {
    protected String name;
    protected int price;

    public Component(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
