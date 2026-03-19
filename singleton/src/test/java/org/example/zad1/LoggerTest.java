package org.example.zad1;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Set;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggerTest {

    @Test
    void getInstance_shouldReturnSameInstance() {
        // Arrange
        Logger firstInstance = Logger.getInstance();

        // Act
        Logger secondInstance = Logger.getInstance();

        // Assert
        assertThat(secondInstance).isSameAs(firstInstance);
    }

    @Test
    void testThreadSafetyWithVirtualThreads() throws InterruptedException {
        // Arrange
        int numberOfTasks = 1_000_000;
        Set<Logger> instances = ConcurrentHashMap.newKeySet();
        CountDownLatch startGate = new CountDownLatch(1);

        // Act
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < numberOfTasks; i++) {
                executor.submit(() -> {
                    try {
                        startGate.await();
                        instances.add(Logger.getInstance());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            startGate.countDown();
        }

        // Assert
        assertThat(instances).hasSize(1);
    }
}

