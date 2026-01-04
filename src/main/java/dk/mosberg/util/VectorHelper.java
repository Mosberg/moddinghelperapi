package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.util.math.Vec3d;

/**
 * Utility for 3D vector mathematics. Provides convenience methods for common vector operations.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * Vec3d pos1 = new Vec3d(0, 0, 0);
 * Vec3d pos2 = new Vec3d(10, 0, 0);
 * double dist = VectorHelper.distance(pos1, pos2);
 * Vec3d direction = VectorHelper.direction(pos1, pos2);
 * </pre>
 */
public final class VectorHelper {
    private VectorHelper() {}

    /**
     * Calculates distance between two vectors.
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return the distance between the vectors
     */
    public static double distance(@NotNull Vec3d v1, @NotNull Vec3d v2) {
        return v1.distanceTo(v2);
    }

    /**
     * Calculates squared distance (faster than distance, useful for comparisons).
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return the squared distance between the vectors
     */
    public static double squaredDistance(@NotNull Vec3d v1, @NotNull Vec3d v2) {
        return v1.squaredDistanceTo(v2);
    }

    /**
     * Calculates normalized direction vector from v1 to v2.
     *
     * @param v1 start vector
     * @param v2 end vector
     * @return unit vector pointing from v1 to v2
     */
    @NotNull
    public static Vec3d direction(@NotNull Vec3d v1, @NotNull Vec3d v2) {
        return v2.subtract(v1).normalize();
    }

    /**
     * Normalizes a vector to unit length.
     *
     * @param vector the vector to normalize
     * @return the normalized vector
     */
    @NotNull
    public static Vec3d normalize(@NotNull Vec3d vector) {
        return vector.normalize();
    }

    /**
     * Scales a vector by a scalar value.
     *
     * @param vector the vector to scale
     * @param scale the scale factor
     * @return the scaled vector
     */
    @NotNull
    public static Vec3d scale(@NotNull Vec3d vector, double scale) {
        return vector.multiply(scale);
    }

    /**
     * Calculates midpoint between two vectors.
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return the midpoint
     */
    @NotNull
    public static Vec3d midpoint(@NotNull Vec3d v1, @NotNull Vec3d v2) {
        return new Vec3d((v1.x + v2.x) / 2, (v1.y + v2.y) / 2, (v1.z + v2.z) / 2);
    }

    /**
     * Calculates vector length (magnitude).
     *
     * @param vector the vector
     * @return the length of the vector
     */
    public static double length(@NotNull Vec3d vector) {
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
    }

    /**
     * Adds two vectors.
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return the sum of the vectors
     */
    @NotNull
    public static Vec3d add(@NotNull Vec3d v1, @NotNull Vec3d v2) {
        return v1.add(v2);
    }

    /**
     * Subtracts two vectors.
     *
     * @param v1 first vector (minuend)
     * @param v2 second vector (subtrahend)
     * @return the difference of the vectors
     */
    @NotNull
    public static Vec3d subtract(@NotNull Vec3d v1, @NotNull Vec3d v2) {
        return v1.subtract(v2);
    }

    /**
     * Calculates dot product of two vectors.
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return the dot product
     */
    public static double dotProduct(@NotNull Vec3d v1, @NotNull Vec3d v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
}
