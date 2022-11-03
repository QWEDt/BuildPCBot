package org.example.components;

public class Disk extends Component {
    private final String type;

    public Disk (String name, int price, int points, String type){
        this.name = name;
        this.price = price;
        this.points = points;
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
