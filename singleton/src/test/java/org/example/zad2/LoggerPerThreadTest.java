package org.example.zad2;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

class LoggerPerThreadTest {

    @Test
    void getInstance_shouldReturnSameInstance_inSameThread() {
        // Arrange
        LoggerPerThread firstInstance = LoggerPerThread.getInstance();

        // Act
        LoggerPerThread secondInstance = LoggerPerThread.getInstance();

        // Assert
        assertThat(secondInstance).isSameAs(firstInstance);
    }

    @Test
    void getInstance_shouldReturnDifferentInstances_inDifferentThreads() throws InterruptedException, ExecutionException {
        // Arrange
        Callable<LoggerPerThread> task = LoggerPerThread::getInstance;

        try (var executor = Executors.newFixedThreadPool(2)) {
            // Act
            Future<LoggerPerThread> future1 = executor.submit(LoggerPerThread::getInstance);
            Future<LoggerPerThread> future2 = executor.submit(LoggerPerThread::getInstance);

            LoggerPerThread instance1 = future1.get();
            LoggerPerThread instance2 = future2.get();

            // Assert
            assertThat(instance1).isNotSameAs(instance2);
        }
    }

    @Test
    void testManyThreadsHaveUniqueInstances() throws InterruptedException {
        // Arrange
        int numberOfThreads = 100;
        Set<LoggerPerThread> instances = ConcurrentHashMap.newKeySet();
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(numberOfThreads);

        // Act
        try (var executor = Executors.newFixedThreadPool(numberOfThreads)) {
            for (int i = 0; i < numberOfThreads; i++) {
                executor.submit(() -> {
                    try {
                        startGate.await();
                        instances.add(LoggerPerThread.getInstance());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        endGate.countDown();
                    }
                });
            }

            startGate.countDown();
            endGate.await();
        }

        // Assert
        assertThat(instances).hasSize(numberOfThreads);
    }
}
