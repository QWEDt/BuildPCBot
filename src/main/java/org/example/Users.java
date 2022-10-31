package org.example;

import org.example.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private final List<User> users = new ArrayList<>();

    public User getUser(Long chatId) {
        try {
            return users.get(searchUser(chatId));
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    public void appendUser(Long chatId) {
        try {
            searchUser(chatId);
        } catch (UserNotFoundException e) {
            users.add(new User(chatId));
        }
    }

    public void deleteUser(Long chatId) throws UserNotFoundException {
        users.remove(searchUser(chatId));
    }

    private int searchUser(Long chatId) throws UserNotFoundException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getChatId() == chatId) {
                return i;
            }
        }
        throw new UserNotFoundException();
    }
}
