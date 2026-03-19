package org.example.zad3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class LoggerProtectedTest {

    @BeforeEach
    void resetSingleton() throws Exception {
        Field instanceField = LoggerParentProtected.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    @Test
    void testLoggerParentProtectedIsSingleton() {

        // Act
        LoggerParentProtected first = LoggerParentProtected.getInstance();
        LoggerParentProtected second = LoggerParentProtected.getInstance();

        // Assert
        assertThat(first).isSameAs(second);
        assertThat(first).isExactlyInstanceOf(LoggerParentProtected.class);
    }

    @Test
    void testLoggerChildProtectedIsSingleton() {

        // Act
        LoggerParentProtected first = LoggerChildProtected.getInstance();
        LoggerParentProtected second = LoggerChildProtected.getInstance();

        // Assert
        assertThat(first).isSameAs(second);
        assertThat(first).isExactlyInstanceOf(LoggerChildProtected.class);
    }

    @Test
    void testChildInitializesParentField() {

        // Act
        LoggerParentProtected child = LoggerChildProtected.getInstance();
        LoggerParentProtected parent = LoggerParentProtected.getInstance();

        // Assert
        assertThat(child).isSameAs(parent);
        assertThat(parent).isExactlyInstanceOf(LoggerChildProtected.class);
    }

    @Test
    void testInvalidTypeWhenChildInitializedFirst() {
        // Arrange

        // Act
        LoggerParentProtected child = LoggerChildProtected.getInstance();
        LoggerParentProtected parent = LoggerParentProtected.getInstance();

        // Assert
        assertThat(parent).isSameAs(child);
        assertThat(parent).isInstanceOf(LoggerChildProtected.class);

        // Logical verification note:
        // This confirms that if a child is instantiated first, the shared
        // static 'instance' field in the parent is "polluted" by the child type.
    }
}