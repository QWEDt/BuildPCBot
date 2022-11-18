package org.example.computer.parts;

public class Motherboard extends Component {
    private final String socket;
    private final String chipset;
    private final boolean radiators;
    private final String form;
    private final int slotsRam;

    public Motherboard(String name, int price, int points, String socket, String chipset, boolean radiators, String form, int slotsRam) {
        super(name, price, points);
        this.socket = socket;
        this.chipset = chipset;
        this.radiators = radiators;
        this.form = form;
        this.slotsRam = slotsRam;
    }

    public String getSocket() {
        return socket;
    }

    public String getChipset() {
        return chipset;
    }

    public boolean isRadiators() {
        return radiators;
    }

    public String getForm() {
        return form;
    }

    public int getSlotsRam() {
        return slotsRam;
    }
}
