package org.example.zad2;

public class LoggerPerThread {

    private static final ThreadLocal<LoggerPerThread> INSTANCE = ThreadLocal.withInitial(LoggerPerThread::new);

    private LoggerPerThread() {
        System.out.println("Inicjalizacja Loggera dla wątku: " + Thread.currentThread().getName());
    }

    public static LoggerPerThread getInstance() {
        return INSTANCE.get();
    }

    public void logWithDelay(String message, int delayMs) {
        try {
            Thread.sleep(delayMs);
            System.out.println("[LOG - " + Thread.currentThread().getName() + "]: " + message + " (po " + delayMs + "ms)");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Akcja przerwana!");
        }
    }
}
