package org.example.components;

public class VideoCard extends Component {
    private final int tdp;

    public VideoCard(String name, int price, int points, int tdp) {
        this.name = name;
        this.price = price;
        this.points = points;
        this.tdp = tdp;
    }

    public int getTdp() {
        return tdp;
    }
}
