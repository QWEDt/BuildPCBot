package org.mytelegrambot.users;

import org.mytelegrambot.computer.Computer;
import org.mytelegrambot.computer.Computers;

import java.util.ArrayList;
import java.util.List;

public class UsersStorage {
    private List<User> users = new ArrayList<>();

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public Computers getPublicComputers() {
        Computers computers = new Computers();
        for (User user : users) {
            for (String computerName : user.getComputerNames()) {
                Computer computer = user.getComputer(computerName);
                if (computer.isPublic()) {
                    computers.append(computerName, computer);
                }
            }
        }

        return computers;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUser(int id) {
        return users.get(id);
    }

    public void removeUser(int userId) {
        users.remove(userId);
    }

    public int getSize() {
        return users.size();
    }
}
