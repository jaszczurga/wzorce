package org.example.zad3;

public class LoggerParentProtected {
    protected static LoggerParentProtected instance;

    protected LoggerParentProtected() {
        System.out.println("Initializing " + this.getClass().getSimpleName());
    }

    public static LoggerParentProtected getInstance() {
        if (instance == null) {
            instance = new LoggerParentProtected();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[" + this.getClass().getSimpleName().toUpperCase() + "]: " + message);
    }
}
