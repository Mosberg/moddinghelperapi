package dk.mosberg.util;

/**
 * Utility for advanced mathematical operations. Provides methods for common math calculations
 * beyond standard Java Math library.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * double lerp = MathHelper.lerp(0.0, 10.0, 0.5); // 5.0
 * int random = MathHelper.randomInt(1, 10);
 * double clamped = MathHelper.clamp(15.0, 0.0, 10.0); // 10.0
 * </pre>
 */
public final class MathHelper {
    private MathHelper() {}

    /**
     * Linear interpolation between two values.
     *
     * @param start the start value
     * @param end the end value
     * @param t the interpolation factor (0.0 to 1.0)
     * @return the interpolated value
     */
    public static double lerp(double start, double end, double t) {
        return start + (end - start) * t;
    }

    /**
     * Clamps a value between min and max.
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
     * Generates a random integer between min (inclusive) and max (inclusive).
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return a random integer
     */
    public static int randomInt(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    /**
     * Generates a random double between min (inclusive) and max (exclusive).
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return a random double
     */
    public static double randomDouble(double min, double max) {
        return min + Math.random() * (max - min);
    }

    /**
     * Calculates the percentage of a value relative to a total.
     *
     * @param value the value
     * @param total the total
     * @return the percentage (0.0 to 1.0)
     */
    public static double percentage(double value, double total) {
        if (total == 0) {
            return 0;
        }
        return value / total;
    }

    /**
     * Rounds a value to a specific number of decimal places.
     *
     * @param value the value to round
     * @param places the number of decimal places
     * @return the rounded value
     */
    public static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    /**
     * Checks if two doubles are approximately equal (within epsilon).
     *
     * @param a first value
     * @param b second value
     * @param epsilon the tolerance
     * @return true if the values are within epsilon of each other
     */
    public static boolean approxEqual(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }

    /**
     * Checks if two doubles are approximately equal (within 0.0001).
     *
     * @param a first value
     * @param b second value
     * @return true if the values are approximately equal
     */
    public static boolean approxEqual(double a, double b) {
        return approxEqual(a, b, 0.0001);
    }

    /**
     * Converts degrees to radians.
     *
     * @param degrees the angle in degrees
     * @return the angle in radians
     */
    public static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    /**
     * Converts radians to degrees.
     *
     * @param radians the angle in radians
     * @return the angle in degrees
     */
    public static double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }

    /**
     * Calculates the distance between two 2D points.
     *
     * @param x1 first point x
     * @param y1 first point y
     * @param x2 second point x
     * @param y2 second point y
     * @return the distance
     */
    public static double distance2D(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates the distance between two 3D points.
     *
     * @param x1 first point x
     * @param y1 first point y
     * @param z1 first point z
     * @param x2 second point x
     * @param y2 second point y
     * @param z2 second point z
     * @return the distance
     */
    public static double distance3D(double x1, double y1, double z1, double x2, double y2,
            double z2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Wraps an angle to the range [0, 360).
     *
     * @param angle the angle in degrees
     * @return the wrapped angle
     */
    public static double wrapAngle(double angle) {
        angle = angle % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    /**
     * Calculates the shortest angular difference between two angles.
     *
     * @param from the starting angle in degrees
     * @param to the target angle in degrees
     * @return the shortest angular difference (-180 to 180)
     */
    public static double angleDifference(double from, double to) {
        double diff = wrapAngle(to - from);
        if (diff > 180) {
            diff -= 360;
        }
        return diff;
    }

    /**
     * Checks if a value is a power of two.
     *
     * @param value the value to check
     * @return true if the value is a power of two
     */
    public static boolean isPowerOfTwo(int value) {
        return value > 0 && (value & (value - 1)) == 0;
    }

    /**
     * Finds the next power of two greater than or equal to a value.
     *
     * @param value the value
     * @return the next power of two
     */
    public static int nextPowerOfTwo(int value) {
        if (value <= 0) {
            return 1;
        }
        value--;
        value |= value >> 1;
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        value |= value >> 16;
        return value + 1;
    }

    /**
     * Calculates the factorial of a number.
     *
     * @param n the number
     * @return the factorial
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial not defined for negative numbers");
        }
        if (n <= 1) {
            return 1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * Calculates the greatest common divisor of two integers.
     *
     * @param a first number
     * @param b second number
     * @return the GCD
     */
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    /**
     * Calculates the least common multiple of two integers.
     *
     * @param a first number
     * @param b second number
     * @return the LCM
     */
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return Math.abs(a * b) / gcd(a, b);
    }
}
