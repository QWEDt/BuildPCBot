package org.example.components;

public class Cooling extends Component {
    private final boolean water;

    public Cooling(String name, int price, boolean water) {
        super(name, price);
        this.water = water;
    }

    public boolean isWater() {
        return water;
    }
}
