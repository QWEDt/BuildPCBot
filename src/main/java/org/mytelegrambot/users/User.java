package org.mytelegrambot.users;

import org.mytelegrambot.computer.Computer;
import org.mytelegrambot.computer.Computers;
import org.mytelegrambot.computer.parts.Component;
import org.mytelegrambot.enums.ComponentsEnum;
import org.mytelegrambot.enums.UserStepEnum;

import java.util.List;
import java.util.Set;

public class User {
    private final long chatId;
    private final String userName;
    private int money;
    private String brandCPU;
    private String brandGPU;
    private String whatToSearch;
    private UserStepEnum step = UserStepEnum.Resting;
    private ComponentsEnum buildStep = ComponentsEnum.EXTRA;
    private final Computers computers;
    private Computer lastComputer;
    private List<Component> tempComponents;

    public User(long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
        computers = new Computers();
    }

    public List<Component> getTempComponents() {
        return tempComponents;
    }

    public void setTempComponents(List<Component> tempComponents) {
        this.tempComponents = tempComponents;
    }

    public ComponentsEnum getBuildStep() {
        return buildStep;
    }

    public void setBuildStep(ComponentsEnum buildStep) {
        this.buildStep = buildStep;
    }

    public String getWhatToSearch() {
        return whatToSearch;
    }

    public void setWhatToSearch(String whatToSearch) {
        this.whatToSearch = whatToSearch;
    }

    public String getUserName() {
        return userName;
    }

    public void deleteComputer(String name) {
        computers.deleteComputer(name);
        UsersService.saveUser(this);
    }

    public boolean isContains(String name) {
        return computers.contains(name);
    }

    public Set<String> getComputerNames() {
        return computers.getNames();
    }

    public int getComputerSize() {
        return computers.getSize();
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

    public void setStep(UserStepEnum step) {
        this.step = step;
        UsersService.saveUser(this);
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
