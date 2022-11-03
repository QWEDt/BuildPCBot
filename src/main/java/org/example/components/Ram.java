package org.example.components;

public class Ram extends Component {
    private final int type;

    public Ram (String name, int price, int points, int type){
        this.name = name;
        this.price = price;
        this.points = points;
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
