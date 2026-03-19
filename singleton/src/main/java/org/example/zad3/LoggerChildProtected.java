package org.example.zad3;

public class LoggerChildProtected extends LoggerParentProtected {

    protected LoggerChildProtected() {
        super();
    }

    public static LoggerParentProtected getInstance() {
        if (instance == null) {
            instance = new LoggerChildProtected();
        }
        return instance;
    }
}
