package org.example.components;

public class Processor extends Component {
    private final String socket;
    private final int tdp;

    public Processor(String name, int price, int points, String socket, int tdp) {
        this.name = name;
        this.price = price;
        this.points = points;
        this.socket = socket;
        this.tdp = tdp;
    }

    public String getSocket() {
        return socket;
    }

    public int getTdp() {
        return tdp;
    }
}
