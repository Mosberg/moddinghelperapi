package dk.mosberg.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import net.minecraft.nbt.NbtCompound;

/**
 * Unit tests for {@link NBTHelper} utilities.
 *
 * @since 1.0.0
 */
class NBTHelperTest {
    private NbtCompound compound;

    @BeforeEach
    void setUp() {
        compound = new NbtCompound();
    }

    @Test
    void testGetStringWithExistingValue() {
        compound.putString("name", "Steve");
        assertEquals("Steve", NBTHelper.getString(compound, "name", "default"));
    }

    @Test
    void testGetStringWithMissingValue() {
        assertEquals("default", NBTHelper.getString(compound, "missing", "default"));
    }

    @Test
    void testGetIntWithExistingValue() {
        compound.putInt("level", 42);
        assertEquals(42, NBTHelper.getInt(compound, "level", 0));
    }

    @Test
    void testGetIntWithMissingValue() {
        assertEquals(0, NBTHelper.getInt(compound, "missing", 0));
    }

    @Test
    void testGetDoubleWithExistingValue() {
        compound.putDouble("health", 20.5);
        assertEquals(20.5, NBTHelper.getDouble(compound, "health", 0.0));
    }

    @Test
    void testGetDoubleWithMissingValue() {
        assertEquals(0.0, NBTHelper.getDouble(compound, "missing", 0.0));
    }

    @Test
    void testGetBooleanWithExistingValue() {
        compound.putBoolean("flag", true);
        assertTrue(NBTHelper.getBoolean(compound, "flag", false));
    }

    @Test
    void testGetBooleanWithMissingValue() {
        assertFalse(NBTHelper.getBoolean(compound, "missing", false));
    }

    @Test
    void testPutString() {
        NBTHelper.putString(compound, "key", "value");
        assertEquals("value", compound.getString("key").orElse(""));
    }

    @Test
    void testPutInt() {
        NBTHelper.putInt(compound, "key", 100);
        assertEquals(100, compound.getInt("key").orElse(0));
    }

    @Test
    void testPutDouble() {
        NBTHelper.putDouble(compound, "key", 3.14);
        assertEquals(3.14, compound.getDouble("key").orElse(0.0));
    }

    @Test
    void testPutBoolean() {
        NBTHelper.putBoolean(compound, "flag", true);
        assertTrue(compound.getBoolean("flag").orElse(false));
    }

    @Test
    void testContains() {
        compound.putString("exists", "value");
        assertTrue(NBTHelper.contains(compound, "exists"));
        assertFalse(NBTHelper.contains(compound, "missing"));
    }

    @Test
    void testRemove() {
        compound.putString("key", "value");
        assertTrue(compound.contains("key"));

        NBTHelper.remove(compound, "key");
        assertFalse(compound.contains("key"));
    }

    @Test
    void testRemoveMissingKey() {
        assertDoesNotThrow(() -> NBTHelper.remove(compound, "missing"));
    }

    @Test
    void testGetLong() {
        compound.putLong("big_number", 1234567890L);
        assertEquals(1234567890L, NBTHelper.getLong(compound, "big_number", 0L));
    }

    @Test
    void testMultipleOperations() {
        NBTHelper.putString(compound, "name", "Test");
        NBTHelper.putInt(compound, "count", 5);
        NBTHelper.putDouble(compound, "value", 2.5);

        assertEquals("Test", NBTHelper.getString(compound, "name", ""));
        assertEquals(5, NBTHelper.getInt(compound, "count", 0));
        assertEquals(2.5, NBTHelper.getDouble(compound, "value", 0.0));
    }
}
