package org.example.zad3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class LoggerRegistryTest {

    @BeforeEach
    void clearRegistry() throws Exception {
        Field registryField = LoggerParent.class.getDeclaredField("registry");
        registryField.setAccessible(true);
        Map<?, ?> map = (Map<?, ?>) registryField.get(null);
        map.clear();
    }

    @Test
    void testLoggerParentIsSingleton() {
        // Act
        LoggerParent first = LoggerParent.getInstance("LoggerParent");
        LoggerParent second = LoggerParent.getInstance("LoggerParent");

        // Assert
        assertThat(first).isSameAs(second);
        assertThat(first).isExactlyInstanceOf(LoggerParent.class);
    }

    @Test
    void testLoggerChildIsSingleton() {
        // Act
        LoggerParent first = LoggerParent.getInstance("LoggerChild");
        LoggerParent second = LoggerParent.getInstance("LoggerChild");

        // Assert
        assertThat(first).isSameAs(second);
        assertThat(first).isExactlyInstanceOf(LoggerChild.class);
    }

    @Test
    void testParentAndChildAreDifferentInstances() {
        // Act
        LoggerParent parent = LoggerParent.getInstance("LoggerParent");
        LoggerParent child = LoggerParent.getInstance("LoggerChild");

        // Assert
        assertThat(parent).isNotSameAs(child);
        assertThat(parent).isExactlyInstanceOf(LoggerParent.class);
        assertThat(child).isExactlyInstanceOf(LoggerChild.class);
    }

    @Test
    void testPolymorphism() {
        // Act
        LoggerParent child = LoggerParent.getInstance("LoggerChild");

        // Assert
        assertThat(child).isInstanceOf(LoggerParent.class);
        assertThat(child).isInstanceOf(LoggerChild.class);
    }

    @Test
    void testThreadSafety() throws InterruptedException {
        // Arrange
        int numberOfThreads = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        Set<LoggerParent> instances = Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap<>());
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // Act
        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                try {
                    instances.add(LoggerParent.getInstance("LoggerChild"));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();

        // Assert
        assertThat(instances).hasSize(1);
    }
}