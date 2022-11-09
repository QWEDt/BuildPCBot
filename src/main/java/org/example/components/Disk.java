package org.example.components;

public class Disk extends Component {
    private final String type;

    public Disk (String name, int price, int points, String type){
        super(name, price, points);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
