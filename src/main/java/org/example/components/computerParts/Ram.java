package org.example.components.computerParts;

public class Ram extends Component {
    private final String type;

    public Ram (String name, int price, int points, String type) {
        super(name, price, points);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
