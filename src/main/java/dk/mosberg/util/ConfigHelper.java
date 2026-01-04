package dk.mosberg.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.jetbrains.annotations.NotNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Utility for configuration file management. Provides methods for reading and writing JSON
 * configuration files with type-safe access.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * Path configPath = ConfigHelper.getConfigPath("mymod.json");
 * JsonObject config = ConfigHelper.load(configPath);
 * String value = ConfigHelper.getString(config, "key", "default");
 * ConfigHelper.save(configPath, config);
 * </pre>
 */
public final class ConfigHelper {
    private ConfigHelper() {}

    /**
     * Gets the path to a config file in the config directory.
     *
     * @param fileName the config file name
     * @return the full path to the config file
     */
    @NotNull
    public static Path getConfigPath(@NotNull String fileName) {
        return Path.of("config", fileName);
    }

    /**
     * Loads a JSON configuration file.
     *
     * @param path the path to the config file
     * @return the parsed JSON object, or empty object if file doesn't exist
     */
    @NotNull
    public static JsonObject load(@NotNull Path path) {
        if (!Files.exists(path)) {
            return new JsonObject();
        }

        try {
            String content = Files.readString(path);
            return JsonParser.parseString(content).getAsJsonObject();
        } catch (IOException e) {
            return new JsonObject();
        }
    }

    /**
     * Saves a JSON object to a configuration file.
     *
     * @param path the path to save to
     * @param config the JSON object to save
     * @return true if saved successfully
     */
    public static boolean save(@NotNull Path path, @NotNull JsonObject config) {
        try {
            Files.createDirectories(path.getParent());
            String json = GsonInstance.pretty().toJson(config);
            Files.writeString(path, json, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Gets a string value from a config object.
     *
     * @param config the config object
     * @param key the key to retrieve
     * @param defaultValue the default value if key is missing
     * @return the string value or default
     */
    @NotNull
    public static String getString(@NotNull JsonObject config, @NotNull String key,
            @NotNull String defaultValue) {
        if (config.has(key) && config.get(key).isJsonPrimitive()) {
            return config.get(key).getAsString();
        }
        return defaultValue;
    }

    /**
     * Gets an integer value from a config object.
     *
     * @param config the config object
     * @param key the key to retrieve
     * @param defaultValue the default value if key is missing
     * @return the integer value or default
     */
    public static int getInt(@NotNull JsonObject config, @NotNull String key, int defaultValue) {
        if (config.has(key) && config.get(key).isJsonPrimitive()) {
            try {
                return config.get(key).getAsInt();
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Gets a double value from a config object.
     *
     * @param config the config object
     * @param key the key to retrieve
     * @param defaultValue the default value if key is missing
     * @return the double value or default
     */
    public static double getDouble(@NotNull JsonObject config, @NotNull String key,
            double defaultValue) {
        if (config.has(key) && config.get(key).isJsonPrimitive()) {
            try {
                return config.get(key).getAsDouble();
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Gets a boolean value from a config object.
     *
     * @param config the config object
     * @param key the key to retrieve
     * @param defaultValue the default value if key is missing
     * @return the boolean value or default
     */
    public static boolean getBoolean(@NotNull JsonObject config, @NotNull String key,
            boolean defaultValue) {
        if (config.has(key) && config.get(key).isJsonPrimitive()) {
            try {
                return config.get(key).getAsBoolean();
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Sets a string value in a config object.
     *
     * @param config the config object
     * @param key the key to set
     * @param value the value to store
     */
    public static void putString(@NotNull JsonObject config, @NotNull String key,
            @NotNull String value) {
        config.addProperty(key, value);
    }

    /**
     * Sets an integer value in a config object.
     *
     * @param config the config object
     * @param key the key to set
     * @param value the value to store
     */
    public static void putInt(@NotNull JsonObject config, @NotNull String key, int value) {
        config.addProperty(key, value);
    }

    /**
     * Sets a double value in a config object.
     *
     * @param config the config object
     * @param key the key to set
     * @param value the value to store
     */
    public static void putDouble(@NotNull JsonObject config, @NotNull String key, double value) {
        config.addProperty(key, value);
    }

    /**
     * Sets a boolean value in a config object.
     *
     * @param config the config object
     * @param key the key to set
     * @param value the value to store
     */
    public static void putBoolean(@NotNull JsonObject config, @NotNull String key, boolean value) {
        config.addProperty(key, value);
    }

    /**
     * Checks if a config file exists.
     *
     * @param path the path to check
     * @return true if the file exists
     */
    public static boolean exists(@NotNull Path path) {
        return Files.exists(path);
    }

    /**
     * Deletes a config file.
     *
     * @param path the path to delete
     * @return true if deleted successfully
     */
    public static boolean delete(@NotNull Path path) {
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }
}
