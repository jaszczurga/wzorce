package org.example.zad1;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class LoggerDoubleCheckedLockingTest {

    @Test
    void getInstance_shouldReturnSameInstance() {
        //Arrange
        LoggerDoubleCheckedLocking firstInstance = LoggerDoubleCheckedLocking.getInstance();

        //Act
        LoggerDoubleCheckedLocking secondInstance = LoggerDoubleCheckedLocking.getInstance();

        //Assert
        assertThat(secondInstance).isSameAs(firstInstance);
    }

    @Test
    void testThreadSafetyWithVirtualThreads() throws InterruptedException {
        //Arrange
        int numberOfTasks = 200_000;
        Set<LoggerDoubleCheckedLocking> instances = ConcurrentHashMap.newKeySet();
        CountDownLatch startGate = new CountDownLatch(1);

        //Act
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < numberOfTasks; i++) {
                executor.submit(() -> {
                    try {
                        startGate.await();
                        instances.add(LoggerDoubleCheckedLocking.getInstance());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            startGate.countDown();
        }

        //Assert
        assertThat(instances).hasSize(1);
    }
}

