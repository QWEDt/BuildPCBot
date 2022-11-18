package org.example.users;

import org.example.computer.Computer;
import org.example.computer.Computers;
import org.example.enums.UserStepEnum;

import java.util.Set;

public class User {
    private final long chatId;
    private int money;
    private String brandCPU;
    private String brandGPU;
    private UserStepEnum step = UserStepEnum.Resting;
    private final Computers computers;
    private Computer lastComputer;


    public User(long chatId) {
        this.chatId = chatId;
        computers = new Computers();
    }

    public void deleteComputer(String name) {
        computers.deleteComputer(name);
    }

    public boolean isContains(String name) {
        return computers.contains(name);
    }

    public Set<String> getComputerNames() {
        return computers.getNames();
    }

    public Computer getLastComputer() {
        return lastComputer;
    }

    public void setLastComputer(Computer lastComputer) {
        this.lastComputer = lastComputer;
    }

    public boolean addComputer(String name, Computer computer) {
        if (computers.contains(name)) return false;
        computers.append(name, computer);
        return true;
    }

    public Computer getComputer(String name) {
        return computers.getComputer(name);
    }

    public void nextStep() {
        switch (step) {
            case WaitForMoney -> step = UserStepEnum.WaitForCPU;
            case WaitForCPU -> step = UserStepEnum.WaitForGPU;
            case WaitForGPU, WaitForNamePC -> step = UserStepEnum.Resting;
        }
    }

    public void setRest() {
        step = UserStepEnum.Resting;
    }

    public void setWaitingForName() {
        step = UserStepEnum.WaitForNamePC;
    }

    public void startBuilding() {
        step = UserStepEnum.WaitForMoney;
    }

    public UserStepEnum getStep() {
        return step;
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
