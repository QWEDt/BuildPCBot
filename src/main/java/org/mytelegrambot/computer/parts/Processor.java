package org.mytelegrambot.computer.parts;

public class Processor extends Component {
    private final String socket;
    private final int tdp;
    private final String igpu;

    public Processor(String name, int price, int points, String socket, int tdp, String igpu) {
        super(name, price, points);
        this.socket = socket;
        this.tdp = tdp;
        this.igpu = igpu;
    }

    public String getSocket() {
        return socket;
    }

    public int getTdp() {
        return tdp;
    }

    public String getIgpu() {
        return igpu;
    }
}
