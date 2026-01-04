package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

/**
 * Utility for entity operations. Provides methods for type checking, distance calculations, and
 * entity state queries.
 */
public final class EntityHelper {
    private EntityHelper() {}

    /**
     * Checks if an entity is a living entity.
     *
     * @param entity the entity to check
     * @return true if the entity is a LivingEntity
     */
    public static boolean isLiving(@NotNull Entity entity) {
        return entity instanceof LivingEntity;
    }

    /**
     * Checks if an entity is a player.
     *
     * @param entity the entity to check
     * @return true if the entity is a PlayerEntity
     */
    public static boolean isPlayer(@NotNull Entity entity) {
        return entity instanceof PlayerEntity;
    }

    /**
     * Calculates the distance between two entities.
     *
     * @param e1 first entity
     * @param e2 second entity
     * @return the distance between the entities
     */
    public static double distance(@NotNull Entity e1, @NotNull Entity e2) {
        double dx = e1.getX() - e2.getX();
        double dy = e1.getY() - e2.getY();
        double dz = e1.getZ() - e2.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Calculates the squared distance between two entities (faster than distance()).
     *
     * @param e1 first entity
     * @param e2 second entity
     * @return the squared distance between the entities
     */
    public static double distanceSquared(@NotNull Entity e1, @NotNull Entity e2) {
        double dx = e1.getX() - e2.getX();
        double dy = e1.getY() - e2.getY();
        double dz = e1.getZ() - e2.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates horizontal distance between two entities (X/Z plane only).
     *
     * @param e1 first entity
     * @param e2 second entity
     * @return the horizontal distance between the entities
     */
    public static double distanceHorizontal(@NotNull Entity e1, @NotNull Entity e2) {
        double dx = e1.getX() - e2.getX();
        double dz = e1.getZ() - e2.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /**
     * Gets the position of an entity as a Vec3d.
     *
     * @param entity the entity
     * @return the entity's position
     */
    @NotNull
    public static Vec3d getPos(@NotNull Entity entity) {
        return new Vec3d(entity.getX(), entity.getY(), entity.getZ());
    }

    /**
     * Checks if an entity is on ground.
     *
     * @param entity the entity to check
     * @return true if the entity is on the ground
     */
    public static boolean isOnGround(@NotNull Entity entity) {
        return entity.isOnGround();
    }

    /**
     * Checks if an entity is in lava.
     *
     * @param entity the entity to check
     * @return true if the entity is in lava
     */
    public static boolean isInLava(@NotNull Entity entity) {
        return entity.isInLava();
    }
}

