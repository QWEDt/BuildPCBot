package org.example.components;

public class Disk extends Component {
    private final String type;

    public Disk (String name, int price, String type){
        super(name, price);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
