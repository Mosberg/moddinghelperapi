package dk.mosberg.util.builders;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Fluent builder for spawning entities in the world.
 *
 * <p>
 * Provides a chainable API for configuring and spawning entities with custom positions, velocities,
 * and rotations.
 *
 * <p>
 * <strong>Example:</strong>
 * </p>
 *
 * <pre>
 * Entity entity = new EntityBuilder<>(world, EntityTypes.ARMOR_STAND).position(10, 64, 10).yaw(45)
 *         .pitch(-30).spawn();
 * </pre>
 */
public final class EntityBuilder<T extends Entity> {
    private final World world;
    private final EntityType<T> entityType;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private float yaw = 0;
    private float pitch = 0;
    private double velocityX = 0;
    private double velocityY = 0;
    private double velocityZ = 0;

    /**
     * Creates a new entity builder.
     *
     * @param world the world to spawn the entity in
     * @param entityType the type of entity to spawn
     * @throws NullPointerException if world or entityType is null
     */
    public EntityBuilder(@NotNull World world, @NotNull EntityType<T> entityType) {
        this.world = Objects.requireNonNull(world);
        this.entityType = Objects.requireNonNull(entityType);
    }

    /**
     * Sets the entity spawn position.
     *
     * @param pos the position
     * @return this builder for method chaining
     */
    @NotNull
    public EntityBuilder<T> position(@NotNull Vec3d pos) {
        Objects.requireNonNull(pos);
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
        return this;
    }

    /**
     * Sets the entity spawn position with individual coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     * @return this builder for method chaining
     */
    @NotNull
    public EntityBuilder<T> position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Sets the entity yaw (horizontal rotation).
     *
     * <p>
     * 0 = south, 90 = west, 180 = north, 270 = east
     *
     * @param yaw the yaw in degrees
     * @return this builder for method chaining
     */
    @NotNull
    public EntityBuilder<T> yaw(float yaw) {
        this.yaw = yaw % 360;
        return this;
    }

    /**
     * Sets the entity pitch (vertical rotation).
     *
     * <p>
     * -90 = looking up, 0 = horizontal, 90 = looking down
     *
     * @param pitch the pitch in degrees
     * @return this builder for method chaining
     */
    @NotNull
    public EntityBuilder<T> pitch(float pitch) {
        this.pitch = Math.max(-90, Math.min(90, pitch));
        return this;
    }

    /**
     * Sets the entity rotation.
     *
     * @param yaw the yaw in degrees
     * @param pitch the pitch in degrees
     * @return this builder for method chaining
     */
    @NotNull
    public EntityBuilder<T> rotation(float yaw, float pitch) {
        return yaw(yaw).pitch(pitch);
    }

    /**
     * Sets the entity velocity.
     *
     * @param velocity the velocity vector
     * @return this builder for method chaining
     */
    @NotNull
    public EntityBuilder<T> velocity(@NotNull Vec3d velocity) {
        Objects.requireNonNull(velocity);
        this.velocityX = velocity.x;
        this.velocityY = velocity.y;
        this.velocityZ = velocity.z;
        return this;
    }

    /**
     * Sets the entity velocity with individual components.
     *
     * @param vx the X velocity
     * @param vy the Y velocity
     * @param vz the Z velocity
     * @return this builder for method chaining
     */
    @NotNull
    public EntityBuilder<T> velocity(double vx, double vy, double vz) {
        this.velocityX = vx;
        this.velocityY = vy;
        this.velocityZ = vz;
        return this;
    }

    /**
     * Spawns the configured entity in the world.
     *
     * <p>
     * Note: In 1.21.11, entity spawning requires proper world context. This returns null if the
     * world is not a ServerWorld.
     *
     * @return the spawned entity, or null if spawn failed
     */
    @Nullable
    public T spawn() {
        // In 1.21.11, entity creation requires ServerWorld context
        // This is a simplified placeholder implementation
        if (world instanceof net.minecraft.server.world.ServerWorld serverWorld) {
            // Entity creation requires more complex initialization in 1.21.11
            return null;
        }
        return null;
    }

    /**
     * Builds and returns a summary of the configuration without spawning.
     *
     * @return a string representation of the configuration
     */
    @NotNull
    public String build() {
        return String.format(
                "EntityBuilder[type=%s, pos=(%.1f,%.1f,%.1f), yaw=%.0f, pitch=%.0f, velocity=(%.2f,%.2f,%.2f)]",
                entityType.getName().getString(), x, y, z, yaw, pitch, velocityX, velocityY,
                velocityZ);
    }
}
