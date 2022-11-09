package org.example.users;

public class User {
    private final long chatId;
    private int money;
    private String brandCPU;
    private String brandGPU;
    private int Step = 0;

    public User(long chatId) {
        this.chatId = chatId;
    }

    public void nextStep() {
        Step++;
    }

    public int getStep() {
        return Step;
    }

    public long getChatId() {
        return chatId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getBrandCPU() {
        return brandCPU;
    }

    public void setBrandCPU(String brandCPU) {
        this.brandCPU = brandCPU;
    }

    public String getBrandGPU() {
        return brandGPU;
    }

    public void setBrandGPU(String brandGPU) {
        this.brandGPU = brandGPU;
    }
}
