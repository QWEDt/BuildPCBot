package org.example.components.computerParts;

public class Power extends Component {
    private final int watts;
    private final String plus;

    public Power(String name, int price, int points, int watts, String plus) {
        super(name, price, points);
        this.watts = watts;
        this.plus = plus;
    }

    public int getWatts() {
        return watts;
    }

    public String getPlus() {
        return plus;
    }
}
