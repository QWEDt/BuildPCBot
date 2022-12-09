package org.example.users;

import org.example.computer.Computers;
import org.example.exceptions.UserNotFoundException;

public class UsersService {
    UsersStorage users;

    public UsersService() {
        users = new UsersStorage();
        users.setUsers(UsersSaveAndLoad.loadUsersData());
    }

    public void saveUsers() {
        UsersSaveAndLoad.saveUsersData(users.getUsers());
    }

    public void saveUser(User user) {
        UsersSaveAndLoad.saveUserData(user);
    }

    public User getUser(Long chatId) {
        try {
            return users.getUser(searchUser(chatId));
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    public void appendUser(User user) {
        try {
            searchUser(user.getChatId());
        } catch (UserNotFoundException e) {
            users.addUser(user);
        }
    }

    public void deleteUser(User user) {
        try {
            users.removeUser(searchUser(user.getChatId()));
        } catch (UserNotFoundException ignored) {}
    }

    public Computers getPublicComputers() {
        return users.getPublicComputers();
    }

    private int searchUser(Long chatId) throws UserNotFoundException {
        for (int i = 0; i < users.getSize(); i++) {
            if (users.getUser(i).getChatId() == chatId) {
                return i;
            }
        }
        throw new UserNotFoundException();
    }
}
