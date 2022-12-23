package org.mytelegrambot.computer;

import org.mytelegrambot.users.UsersService;

import java.util.Set;

public class PublicComputersService {
    private static Computers computers;

    public static void init() {
        computers = UsersService.getPublicComputers();
    }

    public static Set<String> getNames() {
        return computers.getNames();
    }

    public static int getSize() {
        return computers.getSize();
    }

    public static void append(String name, Computer computer) {
        computers.append(name, computer);
    }

    public static boolean contains(String name) {
        return computers.contains(name);
    }

    public static Computer getComputer(String name) {
        return computers.getComputer(name);
    }

    public static void deleteComputer(String name) {
        computers.deleteComputer(name);
    }
}
