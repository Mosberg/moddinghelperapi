package dk.mosberg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link GsonInstance} helper.
 *
 * @since 1.0.0
 */
class GsonInstanceTest {

    @Test
    void testCompactGsonNotNull() {
        assertNotNull(GsonInstance.compact(), "Compact Gson instance should not be null");
    }

    @Test
    void testPrettyGsonNotNull() {
        assertNotNull(GsonInstance.pretty(), "Pretty Gson instance should not be null");
    }

    @Test
    void testCompactGsonIsSingleton() {
        var gson1 = GsonInstance.compact();
        var gson2 = GsonInstance.compact();
        assertSame(gson1, gson2, "Compact Gson should return same instance");
    }

    @Test
    void testPrettyGsonIsSingleton() {
        var gson1 = GsonInstance.pretty();
        var gson2 = GsonInstance.pretty();
        assertSame(gson1, gson2, "Pretty Gson should return same instance");
    }

    @Test
    void testCompactJsonSerialization() {
        var gson = GsonInstance.compact();
        var json = gson.toJson(new TestData("test", 42));
        assertFalse(json.contains("\n"), "Compact JSON should not have newlines");
        assertTrue(json.contains("\"name\":\"test\""), "Compact JSON should contain data");
    }

    @Test
    void testPrettyJsonSerialization() {
        var gson = GsonInstance.pretty();
        var json = gson.toJson(new TestData("test", 42));
        assertTrue(json.contains("\n"), "Pretty JSON should have newlines");
        assertTrue(json.contains("\"name\""), "Pretty JSON should contain keys");
    }

    @Test
    void testJsonDeserialization() {
        var gson = GsonInstance.compact();
        var json = "{\"name\":\"test\",\"value\":42}";
        var data = gson.fromJson(json, TestData.class);
        assertEquals("test", data.name);
        assertEquals(42, data.value);
    }

    /**
     * Test data class for serialization tests.
     */
    static class TestData {
        String name;
        int value;

        TestData(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }
}
