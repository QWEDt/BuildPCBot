package org.example.components;

public class Power extends Component {
    private final int watt;
    private final String plus;

    public Power(String name, int price, int points, int watt, String plus) {
        this.name = name;
        this.price = price;
        this.points = points;
        this.watt = watt;
        this.plus = plus;
    }

    public int getWatt() {
        return watt;
    }

    public String getPlus() {
        return plus;
    }
}
