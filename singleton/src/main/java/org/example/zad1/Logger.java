package org.example.zad1;

public class Logger {

    private Logger() {
        System.out.println("Inicjalizacja okey Loggera");
    }

    private static class SingletonHelper {
        private static final Logger INSTANCE = new Logger();
    }

    public static Logger getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void logWithDelay(String message, int delayMs) {
        try {
            Thread.sleep(delayMs);
            System.out.println("[LOG]: " + message + " (po " + delayMs + "ms)");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Akcja przerwana!");
        }
    }
}