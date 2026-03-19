package org.example.zad1;

public enum LoggerEnum {
    INSTANCE;

    public static LoggerEnum getInstance() {
        return INSTANCE;
    }

    public void logWithDelay(String message, int delayMs) {
        try {
            Thread.sleep(delayMs);
            System.out.println("[ENUM LOG]: " + message + " (after " + delayMs + "ms)");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
