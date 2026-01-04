package dk.mosberg.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Utility for file I/O operations. Provides methods for reading and writing files safely.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * String content = FileHelper.readString(path);
 * boolean success = FileHelper.writeString(path, "Hello World");
 * List&lt;String&gt; lines = FileHelper.readLines(path);
 * </pre>
 */
public final class FileHelper {
    private static final Executor ASYNC_EXECUTOR = Executors.newCachedThreadPool();

    private FileHelper() {}

    /**
     * Reads the entire content of a file as a string.
     *
     * @param path the file path
     * @return the file content, or empty string if error
     */
    @NotNull
    public static String readString(@NotNull Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Reads all lines from a file.
     *
     * @param path the file path
     * @return list of lines, or empty list if error
     */
    @NotNull
    public static List<String> readLines(@NotNull Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Writes a string to a file, creating parent directories if needed.
     *
     * @param path the file path
     * @param content the content to write
     * @return true if successful
     */
    public static boolean writeString(@NotNull Path path, @NotNull String content) {
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, content, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Writes lines to a file.
     *
     * @param path the file path
     * @param lines the lines to write
     * @return true if successful
     */
    public static boolean writeLines(@NotNull Path path, @NotNull List<String> lines) {
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, lines, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Appends a string to a file.
     *
     * @param path the file path
     * @param content the content to append
     * @return true if successful
     */
    public static boolean appendString(@NotNull Path path, @NotNull String content) {
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Checks if a file exists.
     *
     * @param path the file path
     * @return true if the file exists
     */
    public static boolean exists(@NotNull Path path) {
        return Files.exists(path);
    }

    /**
     * Checks if a path is a directory.
     *
     * @param path the path to check
     * @return true if the path is a directory
     */
    public static boolean isDirectory(@NotNull Path path) {
        return Files.isDirectory(path);
    }

    /**
     * Checks if a path is a file.
     *
     * @param path the path to check
     * @return true if the path is a file
     */
    public static boolean isFile(@NotNull Path path) {
        return Files.isRegularFile(path);
    }

    /**
     * Creates a directory and all parent directories if they don't exist.
     *
     * @param path the directory path
     * @return true if successful or already exists
     */
    public static boolean createDirectories(@NotNull Path path) {
        try {
            Files.createDirectories(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Deletes a file.
     *
     * @param path the file path
     * @return true if deleted or didn't exist
     */
    public static boolean delete(@NotNull Path path) {
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Gets the file size in bytes.
     *
     * @param path the file path
     * @return the file size, or -1 if error
     */
    public static long getSize(@NotNull Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * Reads a JSON file and parses it.
     *
     * @param path the file path
     * @return the parsed JSON object, or empty object if error
     */
    @NotNull
    public static JsonObject readJson(@NotNull Path path) {
        try {
            String content = Files.readString(path);
            JsonElement element = JsonParser.parseString(content);
            if (element.isJsonObject()) {
                return element.getAsJsonObject();
            }
            return new JsonObject();
        } catch (IOException e) {
            return new JsonObject();
        }
    }

    /**
     * Writes a JSON object to a file with pretty printing.
     *
     * @param path the file path
     * @param json the JSON object to write
     * @return true if successful
     */
    public static boolean writeJson(@NotNull Path path, @NotNull JsonObject json) {
        String content = GsonInstance.pretty().toJson(json);
        return writeString(path, content);
    }

    /**
     * Copies a file from source to destination.
     *
     * @param source the source path
     * @param destination the destination path
     * @return true if successful
     */
    public static boolean copy(@NotNull Path source, @NotNull Path destination) {
        try {
            Files.createDirectories(destination.getParent());
            Files.copy(source, destination);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Moves a file from source to destination.
     *
     * @param source the source path
     * @param destination the destination path
     * @return true if successful
     */
    public static boolean move(@NotNull Path source, @NotNull Path destination) {
        try {
            Files.createDirectories(destination.getParent());
            Files.move(source, destination);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Lists all files in a directory.
     *
     * @param directory the directory path
     * @return list of file paths, or empty list if error
     */
    @NotNull
    public static List<Path> listFiles(@NotNull Path directory) {
        try {
            return Files.list(directory).filter(Files::isRegularFile).toList();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // =============================================================================================
    // Async Operations
    // =============================================================================================

    /**
     * Reads a file asynchronously.
     *
     * @param path the file path
     * @return a CompletableFuture that completes with the file content
     */
    @NotNull
    public static CompletableFuture<String> readStringAsync(@NotNull Path path) {
        return CompletableFuture.supplyAsync(() -> readString(path), ASYNC_EXECUTOR);
    }

    /**
     * Writes a file asynchronously.
     *
     * @param path the file path
     * @param content the content to write
     * @return a CompletableFuture that completes with success status
     */
    @NotNull
    public static CompletableFuture<Boolean> writeStringAsync(@NotNull Path path,
            @NotNull String content) {
        return CompletableFuture.supplyAsync(() -> writeString(path, content), ASYNC_EXECUTOR);
    }

    /**
     * Reads lines from a file asynchronously.
     *
     * @param path the file path
     * @return a CompletableFuture that completes with the list of lines
     */
    @NotNull
    public static CompletableFuture<List<String>> readLinesAsync(@NotNull Path path) {
        return CompletableFuture.supplyAsync(() -> readLines(path), ASYNC_EXECUTOR);
    }

    /**
     * Reads a JSON file asynchronously.
     *
     * @param path the file path
     * @return a CompletableFuture that completes with the JSON object
     */
    @NotNull
    public static CompletableFuture<JsonObject> readJsonAsync(@NotNull Path path) {
        return CompletableFuture.supplyAsync(() -> readJson(path), ASYNC_EXECUTOR);
    }

    /**
     * Writes a JSON file asynchronously.
     *
     * @param path the file path
     * @param json the JSON object to write
     * @return a CompletableFuture that completes with success status
     */
    @NotNull
    public static CompletableFuture<Boolean> writeJsonAsync(@NotNull Path path,
            @NotNull JsonObject json) {
        return CompletableFuture.supplyAsync(() -> writeJson(path, json), ASYNC_EXECUTOR);
    }

    /**
     * Copies a file asynchronously.
     *
     * @param source the source path
     * @param destination the destination path
     * @return a CompletableFuture that completes with success status
     */
    @NotNull
    public static CompletableFuture<Boolean> copyAsync(@NotNull Path source,
            @NotNull Path destination) {
        return CompletableFuture.supplyAsync(() -> copy(source, destination), ASYNC_EXECUTOR);
    }
}
