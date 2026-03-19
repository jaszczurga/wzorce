package org.example.zad1;

public class LoggerSynchronized {

    private static LoggerSynchronized instance;

    private LoggerSynchronized() {
        System.out.println("Initializing LoggerSynchronized");
    }

    public static synchronized LoggerSynchronized getInstance() {
        if (instance == null) {
            instance = new LoggerSynchronized();
        }
        return instance;
    }

    public void logWithDelay(String message, int delayMs) {
        try {
            Thread.sleep(delayMs);
            System.out.println("[LOG]: " + message + " (after " + delayMs + "ms)");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Action interrupted");
        }
    }
}

