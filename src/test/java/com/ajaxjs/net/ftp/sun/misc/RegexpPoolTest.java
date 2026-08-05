package com.ajaxjs.net.ftp.sun.misc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link RegexpPool}.
 */
class RegexpPoolTest {

    @Test
    void testEmptyPool() {
        RegexpPool pool = new RegexpPool();
        
        // Empty pool should return null for any match
        assertNull(pool.match("anything"));
        assertNull(pool.match(""));
        assertNull(pool.match("test"));
    }

    @Test
    void testAddAndMatchExact() throws REException {
        RegexpPool pool = new RegexpPool();
        Object value = "testValue";
        
        pool.add("test", value);
        
        // Should match exact string
        assertEquals(value, pool.match("test"));
        assertEquals("testValue", pool.match("test"));
    }

    @Test
    void testMultiplePatterns() throws REException {
        RegexpPool pool = new RegexpPool();
        
        pool.add("local", Boolean.TRUE);
        pool.add("localhost", Boolean.FALSE);
        
        assertEquals(Boolean.TRUE, pool.match("local"));
        assertEquals(Boolean.FALSE, pool.match("localhost"));
    }

    @Test
    void testWildcardPattern() throws REException {
        RegexpPool pool = new RegexpPool();
        
        // Use * as wildcard
        pool.add("*.local", "matched");
        
        // The wildcard should match
        assertEquals("matched", pool.match("test.local"));
    }

    @Test
    void testNoMatch() throws REException {
        RegexpPool pool = new RegexpPool();
        
        pool.add("local", Boolean.TRUE);
        
        // Should not match different strings
        assertNull(pool.match("other"));
        assertNull(pool.match("locals"));
        assertNull(pool.match(""));
    }

    @Test
    void testEmptyStringPattern() throws REException {
        RegexpPool pool = new RegexpPool();
        
        pool.add("", "empty");
        
        assertEquals("empty", pool.match(""));
    }

    @Test
    void testNullInput() throws REException {
        RegexpPool pool = new RegexpPool();
        
        pool.add("test", "value");
        
        // null input should not throw and should return null
        assertNull(pool.match(null));
    }

    @Test
    void testDuplicatePatternLastWins() throws REException {
        RegexpPool pool = new RegexpPool();
        
        pool.add("same", "first");
        pool.add("same", "second");
        
        // Last value should win
        assertEquals("second", pool.match("same"));
    }
}
