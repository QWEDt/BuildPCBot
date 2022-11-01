package org.example.errors;

public class ComponentNotFoundException extends Error {
    public ComponentNotFoundException() {
        super("Не удалось найти компонент(ы)");
    }
}
