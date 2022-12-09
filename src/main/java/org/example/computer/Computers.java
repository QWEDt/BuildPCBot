package org.example.computer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Computers {
    private final Map<String, Computer> computers;

    public Computers() {
        computers = new HashMap<>();
    }

    public Set<String> getNames() {
        return computers.keySet();
    }

    public int getSize() {
        return computers.size();
    }

    public void append(String name, Computer computer) {
        computers.put(name, computer);
    }

    public boolean contains(String name) {
        return computers.containsKey(name);
    }

    public Computer getComputer(String name) {
        return computers.get(name);
    }

    public void deleteComputer(String name) {
        computers.remove(name);
    }
}
