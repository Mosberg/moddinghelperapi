package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.nbt.NbtCompound;

/**
 * Utility for NBT operations. Provides safe read/write methods for named binary tag data.
 *
 * <p>
 * <b>Important (Minecraft 1.21.11):</b> NBT getters return {@code Optional<T>} values. This helper
 * handles Optional unwrapping with sensible defaults automatically.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * NbtCompound compound = entity.writeNbt(new NbtCompound());
 * String name = NBTHelper.getString(compound, "CustomName", "Unknown");
 * int level = NBTHelper.getInt(compound, "Level", 0);
 * </pre>
 */
public final class NBTHelper {
    private NBTHelper() {}

    /**
     * Gets a string value from NBT with a default fallback.
     *
     * @param compound the NBT compound
     * @param key the key to retrieve
     * @param defaultValue the value to return if key is missing or invalid
     * @return the string value or defaultValue
     */
    @NotNull
    public static String getString(@NotNull NbtCompound compound, @NotNull String key,
            @NotNull String defaultValue) {
        if (compound.contains(key)) {
            try {
                return compound.getString(key).orElse(defaultValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Gets an int value from NBT with a default fallback.
     *
     * @param compound the NBT compound
     * @param key the key to retrieve
     * @param defaultValue the value to return if key is missing or invalid
     * @return the int value or defaultValue
     */
    public static int getInt(@NotNull NbtCompound compound, @NotNull String key, int defaultValue) {
        if (compound.contains(key)) {
            try {
                return compound.getInt(key).orElse(defaultValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Gets a double value from NBT with a default fallback.
     *
     * @param compound the NBT compound
     * @param key the key to retrieve
     * @param defaultValue the value to return if key is missing or invalid
     * @return the double value or defaultValue
     */
    public static double getDouble(@NotNull NbtCompound compound, @NotNull String key,
            double defaultValue) {
        if (compound.contains(key)) {
            try {
                return compound.getDouble(key).orElse(defaultValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Gets a long value from NBT with a default fallback.
     *
     * @param compound the NBT compound
     * @param key the key to retrieve
     * @param defaultValue the value to return if key is missing or invalid
     * @return the long value or defaultValue
     */
    public static long getLong(@NotNull NbtCompound compound, @NotNull String key,
            long defaultValue) {
        if (compound.contains(key)) {
            try {
                return compound.getLong(key).orElse(defaultValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Gets a boolean value from NBT with a default fallback.
     *
     * @param compound the NBT compound
     * @param key the key to retrieve
     * @param defaultValue the value to return if key is missing or invalid
     * @return the boolean value or defaultValue
     */
    public static boolean getBoolean(@NotNull NbtCompound compound, @NotNull String key,
            boolean defaultValue) {
        if (compound.contains(key)) {
            try {
                return compound.getBoolean(key).orElse(defaultValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Puts a string value in NBT.
     *
     * @param compound the NBT compound
     * @param key the key to set
     * @param value the value to store
     */
    public static void putString(@NotNull NbtCompound compound, @NotNull String key,
            @NotNull String value) {
        compound.putString(key, value);
    }

    /**
     * Puts an int value in NBT.
     *
     * @param compound the NBT compound
     * @param key the key to set
     * @param value the value to store
     */
    public static void putInt(@NotNull NbtCompound compound, @NotNull String key, int value) {
        compound.putInt(key, value);
    }

    /**
     * Puts a double value in NBT.
     *
     * @param compound the NBT compound
     * @param key the key to set
     * @param value the value to store
     */
    public static void putDouble(@NotNull NbtCompound compound, @NotNull String key, double value) {
        compound.putDouble(key, value);
    }

    /**
     * Puts a long value in NBT.
     *
     * @param compound the NBT compound
     * @param key the key to set
     * @param value the value to store
     */
    public static void putLong(@NotNull NbtCompound compound, @NotNull String key, long value) {
        compound.putLong(key, value);
    }

    /**
     * Puts a boolean value in NBT.
     *
     * @param compound the NBT compound
     * @param key the key to set
     * @param value the value to store
     */
    public static void putBoolean(@NotNull NbtCompound compound, @NotNull String key,
            boolean value) {
        compound.putBoolean(key, value);
    }

    /**
     * Checks if a key exists in the NBT compound.
     *
     * @param compound the NBT compound
     * @param key the key to check
     * @return true if the key exists
     */
    public static boolean contains(@NotNull NbtCompound compound, @NotNull String key) {
        return compound.contains(key);
    }

    /**
     * Removes a key from the NBT compound.
     *
     * @param compound the NBT compound
     * @param key the key to remove
     */
    public static void remove(@NotNull NbtCompound compound, @NotNull String key) {
        compound.remove(key);
    }
}
