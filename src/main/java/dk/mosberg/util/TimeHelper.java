package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;

/**
 * Utility for time and timing-related operations. Provides methods for converting between different
 * time units used in Minecraft (ticks, seconds, milliseconds).
 *
 * <p>
 * In Minecraft, a tick is the base unit of time. One tick = 50 milliseconds (20 ticks per second).
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * // Convert seconds to ticks
 * int ticks = TimeHelper.secondsToTicks(5); // 100 ticks
 *
 * // Convert ticks to seconds
 * double seconds = TimeHelper.ticksToSeconds(100); // 5.0 seconds
 *
 * // Get current time in milliseconds
 * long millis = TimeHelper.getCurrentTimeMs();
 *
 * // Format time duration
 * String formatted = TimeHelper.formatDuration(300); // "5s"
 * </pre>
 */
public final class TimeHelper {
    private TimeHelper() {}

    // Time conversion constants
    private static final int TICKS_PER_SECOND = 20;
    private static final long MILLIS_PER_SECOND = 1000L;
    private static final long MILLIS_PER_TICK = MILLIS_PER_SECOND / TICKS_PER_SECOND;

    /**
     * Converts seconds to Minecraft ticks.
     *
     * @param seconds the time in seconds
     * @return the time in ticks
     */
    public static int secondsToTicks(double seconds) {
        return (int) (seconds * TICKS_PER_SECOND);
    }

    /**
     * Converts Minecraft ticks to seconds.
     *
     * @param ticks the time in ticks
     * @return the time in seconds
     */
    public static double ticksToSeconds(int ticks) {
        return (double) ticks / TICKS_PER_SECOND;
    }

    /**
     * Converts minutes to Minecraft ticks.
     *
     * @param minutes the time in minutes
     * @return the time in ticks
     */
    public static int minutesToTicks(double minutes) {
        return (int) (minutes * TICKS_PER_SECOND * 60);
    }

    /**
     * Converts Minecraft ticks to minutes.
     *
     * @param ticks the time in ticks
     * @return the time in minutes
     */
    public static double ticksToMinutes(int ticks) {
        return ((double) ticks / TICKS_PER_SECOND) / 60.0;
    }

    /**
     * Converts milliseconds to Minecraft ticks.
     *
     * @param millis the time in milliseconds
     * @return the time in ticks
     */
    public static int millisToTicks(long millis) {
        return (int) (millis / MILLIS_PER_TICK);
    }

    /**
     * Converts Minecraft ticks to milliseconds.
     *
     * @param ticks the time in ticks
     * @return the time in milliseconds
     */
    public static long ticksToMillis(int ticks) {
        return (long) ticks * MILLIS_PER_TICK;
    }

    /**
     * Converts milliseconds to seconds.
     *
     * @param millis the time in milliseconds
     * @return the time in seconds
     */
    public static double millisToSeconds(long millis) {
        return (double) millis / MILLIS_PER_SECOND;
    }

    /**
     * Converts seconds to milliseconds.
     *
     * @param seconds the time in seconds
     * @return the time in milliseconds
     */
    public static long secondsToMillis(double seconds) {
        return (long) (seconds * MILLIS_PER_SECOND);
    }

    /**
     * Gets the current system time in milliseconds.
     *
     * @return current time in milliseconds
     */
    public static long getCurrentTimeMs() {
        return System.currentTimeMillis();
    }

    /**
     * Gets the current system time in seconds.
     *
     * @return current time in seconds
     */
    public static double getCurrentTimeSec() {
        return getCurrentTimeMs() / 1000.0;
    }

    /**
     * Calculates the elapsed time in milliseconds since a start time.
     *
     * @param startTimeMs the start time in milliseconds
     * @return the elapsed time in milliseconds
     */
    public static long getElapsedMs(long startTimeMs) {
        return getCurrentTimeMs() - startTimeMs;
    }

    /**
     * Calculates the elapsed time in seconds since a start time.
     *
     * @param startTimeSec the start time in seconds
     * @return the elapsed time in seconds
     */
    public static double getElapsedSec(double startTimeSec) {
        return getCurrentTimeSec() - startTimeSec;
    }

    /**
     * Formats a tick duration into a human-readable string (e.g., "5s", "2m", "1h").
     *
     * @param ticks the duration in ticks
     * @return a formatted string representation
     */
    @NotNull
    public static String formatDuration(int ticks) {
        double seconds = ticksToSeconds(ticks);

        if (seconds < 60) {
            return String.format("%.1fs", seconds).replace(".0s", "s");
        }

        double minutes = seconds / 60.0;
        if (minutes < 60) {
            return String.format("%.1fm", minutes).replace(".0m", "m");
        }

        double hours = minutes / 60.0;
        return String.format("%.1fh", hours).replace(".0h", "h");
    }

    /**
     * Checks if a duration in ticks has elapsed since a start tick.
     *
     * @param startTick the starting tick
     * @param currentTick the current tick
     * @param durationTicks the duration to check
     * @return true if the duration has passed
     */
    public static boolean hasElapsed(int startTick, int currentTick, int durationTicks) {
        return (currentTick - startTick) >= durationTicks;
    }

    /**
     * Gets the remaining ticks from a total duration and a start tick.
     *
     * @param startTick the starting tick
     * @param currentTick the current tick
     * @param durationTicks the total duration
     * @return the remaining ticks (0 if duration has passed)
     */
    public static int getRemainingTicks(int startTick, int currentTick, int durationTicks) {
        int elapsed = currentTick - startTick;
        return Math.max(0, durationTicks - elapsed);
    }

    /**
     * Gets the progress of a duration as a percentage (0.0 to 1.0).
     *
     * @param startTick the starting tick
     * @param currentTick the current tick
     * @param durationTicks the total duration
     * @return the progress (0.0 = start, 1.0 = complete)
     */
    public static double getProgress(int startTick, int currentTick, int durationTicks) {
        if (durationTicks <= 0) {
            return 1.0;
        }
        int elapsed = currentTick - startTick;
        return Math.min(1.0, (double) elapsed / durationTicks);
    }
}
