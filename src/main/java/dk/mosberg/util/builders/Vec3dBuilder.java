package dk.mosberg.util.builders;

import org.jetbrains.annotations.NotNull;
import net.minecraft.util.math.Vec3d;

/**
 * Fluent builder for constructing Vec3d vectors with method chaining.
 *
 * <p>
 * Provides a convenient way to build Vec3d objects with transformations.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * var vec = new Vec3dBuilder(10, 5, 3).add(1, 0, 0).scale(2.0).build();
 * </pre>
 *
 * @since 1.0.0
 */
public final class Vec3dBuilder {
    private double x;
    private double y;
    private double z;

    /**
     * Creates a new Vec3dBuilder starting at origin (0, 0, 0).
     */
    public Vec3dBuilder() {
        this(0, 0, 0);
    }

    /**
     * Creates a new Vec3dBuilder with initial coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     */
    public Vec3dBuilder(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new Vec3dBuilder from an existing Vec3d.
     *
     * @param vec the vector to copy
     * @throws NullPointerException if vec is null
     */
    public Vec3dBuilder(@NotNull Vec3d vec) {
        this(vec.x, vec.y, vec.z);
    }

    /**
     * Sets the X coordinate.
     *
     * @param x the X value
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder x(double x) {
        this.x = x;
        return this;
    }

    /**
     * Sets the Y coordinate.
     *
     * @param y the Y value
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder y(double y) {
        this.y = y;
        return this;
    }

    /**
     * Sets the Z coordinate.
     *
     * @param z the Z value
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder z(double z) {
        this.z = z;
        return this;
    }

    /**
     * Adds to the current coordinates.
     *
     * @param dx the X delta
     * @param dy the Y delta
     * @param dz the Z delta
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder add(double dx, double dy, double dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
        return this;
    }

    /**
     * Adds a vector to the current coordinates.
     *
     * @param vec the vector to add
     * @return this builder for chaining
     * @throws NullPointerException if vec is null
     */
    @NotNull
    public Vec3dBuilder add(@NotNull Vec3d vec) {
        return add(vec.x, vec.y, vec.z);
    }

    /**
     * Subtracts from the current coordinates.
     *
     * @param dx the X delta
     * @param dy the Y delta
     * @param dz the Z delta
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder subtract(double dx, double dy, double dz) {
        this.x -= dx;
        this.y -= dy;
        this.z -= dz;
        return this;
    }

    /**
     * Subtracts a vector from the current coordinates.
     *
     * @param vec the vector to subtract
     * @return this builder for chaining
     * @throws NullPointerException if vec is null
     */
    @NotNull
    public Vec3dBuilder subtract(@NotNull Vec3d vec) {
        return subtract(vec.x, vec.y, vec.z);
    }

    /**
     * Scales all coordinates by a factor.
     *
     * @param scale the scaling factor
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder scale(double scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        return this;
    }

    /**
     * Scales each coordinate independently.
     *
     * @param sx the X scale
     * @param sy the Y scale
     * @param sz the Z scale
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder scale(double sx, double sy, double sz) {
        this.x *= sx;
        this.y *= sy;
        this.z *= sz;
        return this;
    }

    /**
     * Normalizes the vector to unit length.
     *
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder normalize() {
        double length = Math.sqrt(x * x + y * y + z * z);
        if (length > 0) {
            this.x /= length;
            this.y /= length;
            this.z /= length;
        }
        return this;
    }

    /**
     * Rounds all coordinates to the nearest integer.
     *
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder round() {
        this.x = Math.round(x);
        this.y = Math.round(y);
        this.z = Math.round(z);
        return this;
    }

    /**
     * Floors all coordinates.
     *
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder floor() {
        this.x = Math.floor(x);
        this.y = Math.floor(y);
        this.z = Math.floor(z);
        return this;
    }

    /**
     * Inverts the vector (negates all components).
     *
     * @return this builder for chaining
     */
    @NotNull
    public Vec3dBuilder invert() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }

    /**
     * Builds and returns the final Vec3d vector.
     *
     * @return the constructed Vec3d
     */
    @NotNull
    public Vec3d build() {
        return new Vec3d(x, y, z);
    }

    /**
     * Returns the X coordinate.
     *
     * @return the X value
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the Y coordinate.
     *
     * @return the Y value
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the Z coordinate.
     *
     * @return the Z value
     */
    public double getZ() {
        return z;
    }
}
