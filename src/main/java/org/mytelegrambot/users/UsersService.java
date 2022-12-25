package org.mytelegrambot.users;

import org.mytelegrambot.computer.Computers;
import org.mytelegrambot.exceptions.UserNotFoundException;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UsersService {
    private static UsersStorage users;

    public static void init() {
        users = new UsersStorage();
        users.setUsers(UsersSaveAndLoad.loadUsersData());
    }

    public static void saveUsers() {
        UsersSaveAndLoad.saveUsersData(users.getUsers());
    }

    public static void saveUser(User user) {
        UsersSaveAndLoad.saveUserData(user);
    }

    public static User getUser(Message message) {
        try {
            return users.getUser(searchUser(message.getChatId()));
        } catch (UserNotFoundException e) {
            User user = new User(message.getChatId(), message.getFrom().getUserName());
            users.addUser(user);
            saveUser(user);
            return user;
        }
    }

    public static void appendUser(User user) {
        try {
            searchUser(user.getChatId());
        } catch (UserNotFoundException e) {
            users.addUser(user);
        }
    }

    public static void deleteUser(User user) {
        try {
            users.removeUser(searchUser(user.getChatId()));
        } catch (UserNotFoundException ignored) {}
    }

    public static Computers getPublicComputers() {
        return users.getPublicComputers();
    }

    private static int searchUser(Long chatId) throws UserNotFoundException {
        for (int i = 0; i < users.getSize(); i++) {
            if (users.getUser(i).getChatId() == chatId) {
                return i;
            }
        }
        throw new UserNotFoundException();
    }
}
