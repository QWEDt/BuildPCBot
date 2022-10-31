package org.example.exceptions;

public class ComponentNotFoundException extends Exception {
    public ComponentNotFoundException() {
        super("Не удалось найти компонент(ы)");
    }
}
