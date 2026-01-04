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
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern URL_PATTERN =
            Pattern.compile("^https?://[\\w.-]+(:\\d+)?(/[\\w./-]*)?$");
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#?[0-9A-Fa-f]{6}$");
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

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

    // =============================================================================================
    // Advanced Pattern Validation
    // =============================================================================================

    /**
     * Checks if a string is a valid email address.
     *
     * @param email the email to validate
     * @return true if valid email format
     */
    public static boolean isValidEmail(@Nullable String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Checks if a string is a valid URL.
     *
     * @param url the URL to validate
     * @return true if valid URL format
     */
    public static boolean isValidUrl(@Nullable String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

    /**
     * Checks if a string is a valid hex color code.
     *
     * @param color the color string to validate
     * @return true if valid hex color format (#RRGGBB or RRGGBB)
     */
    public static boolean isValidHexColor(@Nullable String color) {
        return color != null && HEX_COLOR_PATTERN.matcher(color).matches();
    }

    /**
     * Checks if a string is a valid UUID.
     *
     * @param uuid the UUID string to validate
     * @return true if valid UUID format
     */
    public static boolean isValidUuid(@Nullable String uuid) {
        return uuid != null && UUID_PATTERN.matcher(uuid).matches();
    }

    /**
     * Checks if a string matches a custom regex pattern.
     *
     * @param str the string to check
     * @param regex the regex pattern string
     * @return true if the string matches the pattern
     */
    public static boolean matchesRegex(@Nullable String str, @NotNull String regex) {
        if (str == null) {
            return false;
        }
        try {
            return Pattern.compile(regex).matcher(str).matches();
        } catch (Exception e) {
            return false;
        }
    }

    // =============================================================================================
    // Character Validation
    // =============================================================================================

    /**
     * Checks if a string contains only characters from an allowed set.
     *
     * @param str the string to check
     * @param allowed the allowed characters
     * @return true if string contains only allowed characters
     */
    public static boolean containsOnly(@Nullable String str, @NotNull String allowed) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (allowed.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a string contains any of the specified characters.
     *
     * @param str the string to check
     * @param characters the characters to look for
     * @return true if any character is found
     */
    public static boolean containsAny(@Nullable String str, @NotNull String characters) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : characters.toCharArray()) {
            if (str.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a string contains only letters.
     *
     * @param str the string to check
     * @return true if string contains only letters
     */
    public static boolean isAlpha(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a string contains only digits.
     *
     * @param str the string to check
     * @return true if string contains only digits
     */
    public static boolean isNumeric(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // =============================================================================================
    // Input Sanitization
    // =============================================================================================

    /**
     * Sanitizes a string by removing all non-alphanumeric characters.
     *
     * @param str the string to sanitize
     * @return the sanitized string
     */
    @NotNull
    public static String sanitize(@Nullable String str) {
        if (str == null) {
            return "";
        }
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Sanitizes a string by removing characters not in the whitelist.
     *
     * @param str the string to sanitize
     * @param whitelist the allowed characters
     * @return the sanitized string
     */
    @NotNull
    public static String sanitizeWith(@Nullable String str, @NotNull String whitelist) {
        if (str == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (whitelist.indexOf(c) != -1) {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * Removes all whitespace from a string.
     *
     * @param str the string to process
     * @return the string with whitespace removed
     */
    @NotNull
    public static String removeWhitespace(@Nullable String str) {
        if (str == null) {
            return "";
        }
        return str.replaceAll("\\s+", "");
    }

    /**
     * Removes dangerous characters that could be used in exploits.
     *
     * @param str the string to sanitize
     * @return the sanitized string
     */
    @NotNull
    public static String removeDangerousChars(@Nullable String str) {
        if (str == null) {
            return "";
        }
        // Remove: < > & " ' ; \\ / etc.
        return str.replaceAll("[<>&\"';\\\\/%]", "");
    }

    /**
     * Escapes special regex characters in a string.
     *
     * @param str the string to escape
     * @return the escaped string safe for use in regex
     */
    @NotNull
    public static String escapeRegex(@Nullable String str) {
        if (str == null) {
            return "";
        }
        return str.replaceAll("([\\[\\]{}()*+?.\\\\^$|])", "\\\\$1");
    }

    // =============================================================================================
    // Range and Bounds Validation
    // =============================================================================================

    /**
     * Checks if a string length is exactly the specified length.
     *
     * @param str the string to check
     * @param length the required length
     * @return true if string length matches
     */
    public static boolean hasExactLength(@Nullable String str, int length) {
        return str != null && str.length() == length;
    }

    /**
     * Checks if a string length is at least the minimum.
     *
     * @param str the string to check
     * @param minLength the minimum length
     * @return true if string is long enough
     */
    public static boolean hasMinLength(@Nullable String str, int minLength) {
        return str != null && str.length() >= minLength;
    }

    /**
     * Checks if a string length is at most the maximum.
     *
     * @param str the string to check
     * @param maxLength the maximum length
     * @return true if string is short enough
     */
    public static boolean hasMaxLength(@Nullable String str, int maxLength) {
        return str != null && str.length() <= maxLength;
    }

    /**
     * Throws an exception if the value is null.
     *
     * @param value the value to check
     * @param message the exception message
     * @param <T> the type of the value
     * @return the non-null value
     * @throws IllegalArgumentException if value is null
     */
    @NotNull
    public static <T> T requireNonNull(@Nullable T value, @NotNull String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Throws an exception if the string is null or empty.
     *
     * @param str the string to check
     * @param message the exception message
     * @return the non-empty string
     * @throws IllegalArgumentException if string is null or empty
     */
    @NotNull
    public static String requireNonEmpty(@Nullable String str, @NotNull String message) {
        if (isNullOrEmpty(str)) {
            throw new IllegalArgumentException(message);
        }
        return str;
    }
}

