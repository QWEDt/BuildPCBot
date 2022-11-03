package org.example.components;

public class VideoCard extends Component {
    private final int tdp;

    public VideoCard(String name, int price, int tdp) {
        super(name, price);
        this.name = name;
        this.price = price;
        this.tdp = tdp;
    }

    public int getTdp() {
        return tdp;
    }
}
