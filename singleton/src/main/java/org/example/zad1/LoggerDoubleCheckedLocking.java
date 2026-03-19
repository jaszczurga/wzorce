package org.example.zad1;

public class LoggerDoubleCheckedLocking {

    private static volatile LoggerDoubleCheckedLocking instance;

    private LoggerDoubleCheckedLocking() {
        System.out.println("Initializing LoggerDoubleCheckedLocking");
    }

    public static LoggerDoubleCheckedLocking getInstance() {
        if (instance == null) {
            synchronized (LoggerDoubleCheckedLocking.class) {
                if (instance == null) {
                    instance = new LoggerDoubleCheckedLocking();
                }
            }
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

