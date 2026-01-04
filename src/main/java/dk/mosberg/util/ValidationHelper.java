package dk.mosberg.util;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility for input validation. Provides methods for validating strings, numbers, and other common
 * input types.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * boolean valid = ValidationHelper.isValidUsername("Steve123");
 * boolean inRange = ValidationHelper.isInRange(5, 1, 10);
 * String safe = ValidationHelper.sanitizeString(input, 50);
 * </pre>
 */
public final class ValidationHelper {
    private ValidationHelper() {}

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,16}$");
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^-?\\d+$");
    private static final Pattern DECIMAL_PATTERN = Pattern.compile("^-?\\d*\\.?\\d+$");

    /**
     * Checks if a string is null or empty.
     *
     * @param str the string to check
     * @return true if the string is null or empty
     */
    public static boolean isNullOrEmpty(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if a string is null, empty, or contains only whitespace.
     *
     * @param str the string to check
     * @return true if the string is blank
     */
    public static boolean isBlank(@Nullable String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Checks if a string is a valid Minecraft username (3-16 alphanumeric + underscore).
     *
     * @param username the username to validate
     * @return true if valid
     */
    public static boolean isValidUsername(@Nullable String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Checks if a string contains only alphanumeric characters.
     *
     * @param str the string to check
     * @return true if alphanumeric
     */
    public static boolean isAlphanumeric(@Nullable String str) {
        return str != null && !str.isEmpty() && ALPHANUMERIC_PATTERN.matcher(str).matches();
    }

    /**
     * Checks if a string is a valid integer.
     *
     * @param str the string to check
     * @return true if the string represents a valid integer
     */
    public static boolean isInteger(@Nullable String str) {
        return str != null && NUMERIC_PATTERN.matcher(str).matches();
    }

    /**
     * Checks if a string is a valid decimal number.
     *
     * @param str the string to check
     * @return true if the string represents a valid decimal
     */
    public static boolean isDecimal(@Nullable String str) {
        return str != null && DECIMAL_PATTERN.matcher(str).matches();
    }

    /**
     * Checks if a number is within a specific range (inclusive).
     *
     * @param value the value to check
     * @param min the minimum value
     * @param max the maximum value
     * @return true if the value is between min and max (inclusive)
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Checks if a double is within a specific range (inclusive).
     *
     * @param value the value to check
     * @param min the minimum value
     * @param max the maximum value
     * @return true if the value is between min and max (inclusive)
     */
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * Checks if a string length is within a specific range.
     *
     * @param str the string to check
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return true if the string length is valid
     */
    public static boolean isLengthValid(@Nullable String str, int minLength, int maxLength) {
        return str != null && str.length() >= minLength && str.length() <= maxLength;
    }

    /**
     * Sanitizes a string by removing invalid characters and limiting length.
     *
     * @param str the string to sanitize
     * @param maxLength the maximum allowed length
     * @return the sanitized string
     */
    @NotNull
    public static String sanitizeString(@Nullable String str, int maxLength) {
        if (str == null) {
            return "";
        }

        String sanitized = str.replaceAll("[^a-zA-Z0-9_ -]", "");

        if (sanitized.length() > maxLength) {
            sanitized = sanitized.substring(0, maxLength);
        }

        return sanitized;
    }

    /**
     * Clamps an integer value between min and max.
     *
     * @param value the value to clamp
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped value
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Clamps a double value between min and max.
     *
     * @param value the value to clamp
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped value
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Parses an integer with a default value on error.
     *
     * @param str the string to parse
     * @param defaultValue the default value if parsing fails
     * @return the parsed integer or default value
     */
    public static int parseInt(@Nullable String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses a double with a default value on error.
     *
     * @param str the string to parse
     * @param defaultValue the default value if parsing fails
     * @return the parsed double or default value
     */
    public static double parseDouble(@Nullable String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Checks if a value is positive.
     *
     * @param value the value to check
     * @return true if the value is greater than zero
     */
    public static boolean isPositive(int value) {
        return value > 0;
    }

    /**
     * Checks if a value is non-negative (zero or positive).
     *
     * @param value the value to check
     * @return true if the value is greater than or equal to zero
     */
    public static boolean isNonNegative(int value) {
        return value >= 0;
    }

    /**
     * Checks if a string matches a pattern.
     *
     * @param str the string to check
     * @param pattern the regex pattern
     * @return true if the string matches the pattern
     */
    public static boolean matches(@Nullable String str, @NotNull Pattern pattern) {
        return str != null && pattern.matcher(str).matches();
    }
}
