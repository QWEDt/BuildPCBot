package org.example.components;

public class Ram extends Component {
    private final String type;

    public Ram (String name, int price, String type) {
        super(name, price);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
