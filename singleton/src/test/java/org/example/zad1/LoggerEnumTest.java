package org.example.zad1;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggerEnumTest {

    @Test
    void getInstance_shouldReturnSameInstance() {
        // Arrange
        LoggerEnum firstInstance = LoggerEnum.getInstance();

        // Act
        LoggerEnum secondInstance = LoggerEnum.getInstance();

        // Assert
        assertThat(secondInstance).isSameAs(firstInstance);
    }

    @Test
    void testThreadSafetyWithVirtualThreads() throws InterruptedException {
        // Arrange
        int numberOfTasks = 1_000;
        Set<LoggerEnum> instances = ConcurrentHashMap.newKeySet();
        CountDownLatch startGate = new CountDownLatch(1);

        // Act
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < numberOfTasks; i++) {
                executor.submit(() -> {
                    try {
                        startGate.await();
                        instances.add(LoggerEnum.getInstance());
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
