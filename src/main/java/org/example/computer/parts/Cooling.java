package org.example.computer.parts;

public class Cooling extends Component {
    private final boolean water;
    private final int tdp;

    public Cooling(String name, int price, int points, boolean water, int tdp) {
        super(name, price, points);
        this.water = water;
        this.tdp = tdp;
    }

    public int getTdp() {
        return tdp;
    }

    public boolean isWater() {
        return water;
    }
}
