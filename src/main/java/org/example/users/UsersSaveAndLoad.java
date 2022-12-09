package org.example.users;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UsersSaveAndLoad {
    private static final String pathToUserData = "src/main/resources/users data/";

    public static void saveUsersData(List<User> users) {
        for (User user : users) {
            saveUserData(user);
        }
    }

    public static void saveUserData(User user) {
        Gson gson = new Gson();
        Path pathToUser = Path.of(pathToUserData + user.getChatId());
        try {
            if (!Files.exists(pathToUser)) Files.createFile(pathToUser);
            Files.writeString(pathToUser, gson.toJson(user));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> loadUsersData() {
        Gson gson = new Gson();
        List<User> users = new ArrayList<>();
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
        return users;
    }
}
