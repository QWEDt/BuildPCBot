package org.example.users;

import com.google.gson.Gson;
import org.example.exceptions.UserNotFoundException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Users {
    private final List<User> users = new ArrayList<>();
    private final String pathToUserData = "src/main/resources/users data/";
    public Users() {
        loadUserData();
    }
    
    public void saveUserData() {
        Gson gson = new Gson();
        for (User user : users) {
            Path pathToUser = Path.of(pathToUserData + user.getChatId());
            try {
                if (!Files.exists(pathToUser)) Files.createFile(pathToUser);
                Files.writeString(pathToUser, gson.toJson(user));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public void loadUserData() {
        Gson gson = new Gson();
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Path.of(pathToUserData));
            for (Path path : directoryStream) {
                String data = Files.readString(path);
                User user = gson.fromJson(data, User.class);
                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(Long chatId) {
        try {
            return users.get(searchUser(chatId));
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    public void appendUser(User user) {
        try {
            searchUser(user.getChatId());
        } catch (UserNotFoundException e) {
            users.add(user);
        }
    }

    public void deleteUser(User user) {
        try {
            users.remove(searchUser(user.getChatId()));
        } catch (UserNotFoundException ignored) {}
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
