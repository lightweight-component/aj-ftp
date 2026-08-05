package com.ajaxjs.net.ftp.sun.misc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link REException}.
 */
class REExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Regular expression error";
        REException exception = new REException(message);
        
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testInheritance() {
        REException exception = new REException("test");
        
        // Should be a subclass of Exception
        assertTrue(exception instanceof Exception);
    }

    @Test
    void testEmptyMessage() {
        REException exception = new REException("");
        assertEquals("", exception.getMessage());
    }

    @Test
    void testNullMessage() {
        REException exception = new REException(null);
        assertNull(exception.getMessage());
    }

    @Test
    void testExceptionChaining() {
        Throwable cause = new IllegalArgumentException("Invalid regex pattern");
        REException exception = new REException("Regex compilation failed");
        exception.initCause(cause);
        
        assertEquals(cause, exception.getCause());
        assertEquals("Regex compilation failed", exception.getMessage());
    }

    @Test
    void testStackTrace() {
        REException exception = new REException("Error");
        
        StackTraceElement[] stackTrace = exception.getStackTrace();
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
        
        // The first element should be this test method
        assertEquals(getClass().getName(), stackTrace[0].getClassName());
    }

    @Test
    void testToString() {
        REException exception = new REException("Test error");
        String toString = exception.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("REException"));
        assertTrue(toString.contains("Test error"));
    }

    @Test
    void testLongMessage() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longMessage.append("Very long error message ");
        }
        
        REException exception = new REException(longMessage.toString());
        assertEquals(longMessage.toString(), exception.getMessage());
    }
}
