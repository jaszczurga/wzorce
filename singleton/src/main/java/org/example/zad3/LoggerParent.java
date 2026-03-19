package org.example.zad3;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class LoggerParent {

    private static final Map<String, LoggerParent> registry = new ConcurrentHashMap<>();

    protected LoggerParent() {
        System.out.println("Initializing " + this.getClass().getSimpleName());
    }

    public static LoggerParent getInstance(String className) {
        String fullClassName = className.contains(".") ? className : LoggerParent.class.getPackageName() + "." + className;

        if (!registry.containsKey(fullClassName)) {
            synchronized (LoggerParent.class) {
                if (!registry.containsKey(fullClassName)) {
                    try {
                        Class<?> cls = Class.forName(fullClassName);
                        LoggerParent instance = (LoggerParent) cls.getDeclaredConstructor().newInstance();
                        registry.put(fullClassName, instance);
                    } catch (Exception e) {
                        System.err.println("Could not create instance for " + fullClassName);
                        e.printStackTrace();
                    }
                }
            }
        }
        return registry.get(fullClassName);
    }

    public void logWithDelay(String message, int delayMs) {
        try {
            Thread.sleep(delayMs);
            System.out.println("[" + this.getClass().getSimpleName().toUpperCase() + "]: " + message + " (after " + delayMs + "ms)");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Action interrupted");
        }
    }
}
