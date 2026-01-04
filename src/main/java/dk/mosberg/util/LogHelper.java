package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Structured logging utility for consistent mod logging with categories and levels.
 *
 * <p>
 * Provides categorized loggers for different components of a mod, making it easy to filter and
 * organize log output by module.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * var logger = LogHelper.getLogger("mymod", "ItemHandler");
 * logger.info("Processing item: {}", itemName);
 * logger.warn("Unexpected item property: {}", property);
 * logger.error("Failed to process item", exception);
 * </pre>
 *
 * @since 1.0.0
 */
public final class LogHelper {
    private static final String DEFAULT_MOD_ID = "moddinghelperapi";

    private LogHelper() {}

    /**
     * Gets or creates a categorized logger for a mod component.
     *
     * @param modId the mod identifier
     * @param category the logging category/component name
     * @return a configured Logger for this mod and category
     * @throws NullPointerException if modId or category is null
     */
    @NotNull
    public static Logger getLogger(@NotNull String modId, @NotNull String category) {
        String loggerName = modId + ":" + category;
        return LoggerFactory.getLogger(loggerName);
    }

    /**
     * Gets or creates a categorized logger using the default mod ID.
     *
     * @param category the logging category/component name
     * @return a configured Logger for this category
     * @throws NullPointerException if category is null
     */
    @NotNull
    public static Logger getLogger(@NotNull String category) {
        return getLogger(DEFAULT_MOD_ID, category);
    }

    /**
     * Logs a debug message for development and troubleshooting.
     *
     * @param logger the logger to use
     * @param message the message to log
     * @throws NullPointerException if logger or message is null
     */
    public static void debug(@NotNull Logger logger, @NotNull String message) {
        logger.debug(message);
    }

    /**
     * Logs an info message for general information.
     *
     * @param logger the logger to use
     * @param message the message to log
     * @throws NullPointerException if logger or message is null
     */
    public static void info(@NotNull Logger logger, @NotNull String message) {
        logger.info(message);
    }

    /**
     * Logs a warning message for potentially problematic situations.
     *
     * @param logger the logger to use
     * @param message the message to log
     * @throws NullPointerException if logger or message is null
     */
    public static void warn(@NotNull Logger logger, @NotNull String message) {
        logger.warn(message);
    }

    /**
     * Logs an error message for error conditions.
     *
     * @param logger the logger to use
     * @param message the message to log
     * @throws NullPointerException if logger or message is null
     */
    public static void error(@NotNull Logger logger, @NotNull String message) {
        logger.error(message);
    }

    /**
     * Logs an error message with an exception.
     *
     * @param logger the logger to use
     * @param message the message to log
     * @param throwable the exception to log
     * @throws NullPointerException if logger, message, or throwable is null
     */
    public static void error(@NotNull Logger logger, @NotNull String message,
            @NotNull Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Logs a debug message with formatted arguments.
     *
     * @param logger the logger to use
     * @param message the message with {} placeholders
     * @param args the arguments to insert
     * @throws NullPointerException if logger or message is null
     */
    public static void debug(@NotNull Logger logger, @NotNull String message, Object... args) {
        logger.debug(message, args);
    }

    /**
     * Logs an info message with formatted arguments.
     *
     * @param logger the logger to use
     * @param message the message with {} placeholders
     * @param args the arguments to insert
     * @throws NullPointerException if logger or message is null
     */
    public static void info(@NotNull Logger logger, @NotNull String message, Object... args) {
        logger.info(message, args);
    }

    /**
     * Logs a warning message with formatted arguments.
     *
     * @param logger the logger to use
     * @param message the message with {} placeholders
     * @param args the arguments to insert
     * @throws NullPointerException if logger or message is null
     */
    public static void warn(@NotNull Logger logger, @NotNull String message, Object... args) {
        logger.warn(message, args);
    }

    /**
     * Logs an error message with formatted arguments.
     *
     * @param logger the logger to use
     * @param message the message with {} placeholders
     * @param args the arguments to insert
     * @throws NullPointerException if logger or message is null
     */
    public static void error(@NotNull Logger logger, @NotNull String message, Object... args) {
        logger.error(message, args);
    }
}
