package org.example.components;

public class Cooling extends Component {
    private final boolean water;

    public Cooling(String name, int price, int points, boolean water) {
        this.name = name;
        this.price = price;
        this.points = points;
        this.water = water;
    }

    public boolean isWater() {
        return water;
    }
}
