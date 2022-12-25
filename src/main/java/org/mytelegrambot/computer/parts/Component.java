package org.mytelegrambot.computer.parts;

/**
 * Родительский класс для всех комплектующих.
 */
public class Component {
    protected String name;
    protected int price;
    protected int points;

    /**
     * @param name имя компонента
     * @param price цена компонента
     * @param points очки производительности компоненты
     */
    public Component(String name, int price, int points) {
        this.name = name;
        this.price = price;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getPoints() {
        return points;
    }
}
