package dk.mosberg.util;

import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    // ═════════════════════════════════════════════════════════════════════════════════
    // Relationship Management - Passengers, Vehicles, Mounting
    // ═════════════════════════════════════════════════════════════════════════════════

    /**
     * Gets all passengers riding this entity.
     *
     * @param entity the entity to check
     * @return an unmodifiable set of passengers
     */
    @NotNull
    public static Set<Entity> getPassengers(@NotNull Entity entity) {
        // Note: getPassengers() not available in 1.21.11, use getPassengerList()
        return new HashSet<>(entity.getPassengerList());
    }

    /**
     * Gets the vehicle this entity is riding, if any.
     *
     * @param entity the entity to check
     * @return the vehicle entity, or null if not riding
     */
    @Nullable
    public static Entity getVehicle(@NotNull Entity entity) {
        return entity.getVehicle();
    }

    /**
     * Checks if this entity is riding another entity.
     *
     * @param entity the entity to check
     * @return true if the entity is riding a vehicle
     */
    public static boolean isRiding(@NotNull Entity entity) {
        return entity.getVehicle() != null;
    }

    /**
     * Mounts an entity onto a vehicle.
     *
     * @param entity the entity to mount
     * @param vehicle the vehicle to mount onto
     * @return true if mounting was successful
     */
    public static boolean mount(@NotNull Entity entity, @NotNull Entity vehicle) {
        try {
            entity.startRiding(vehicle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dismounts an entity from its vehicle.
     *
     * @param entity the entity to dismount
     * @return true if dismounting was successful
     */
    public static boolean dismount(@NotNull Entity entity) {
        try {
            entity.stopRiding();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Removes all passengers from an entity.
     *
     * @param entity the entity to clear passengers from
     */
    public static void removeAllPassengers(@NotNull Entity entity) {
        entity.getPassengerList().forEach(Entity::stopRiding);
    }

    /**
     * Counts the number of passengers on this entity.
     *
     * @param entity the entity to check
     * @return the number of passengers
     */
    public static int getPassengerCount(@NotNull Entity entity) {
        return entity.getPassengerList().size();
    }

    // ═════════════════════════════════════════════════════════════════════════════════
    // Advanced Pathfinding & Spatial Queries
    // ═════════════════════════════════════════════════════════════════════════════════

    /**
     * Checks if one entity can reach another within specified distance.
     *
     * @param from source entity
     * @param to target entity
     * @param maxDistance maximum distance to consider "reachable"
     * @return true if distance between entities is less than or equal to maxDistance
     */
    public static boolean canReach(@NotNull Entity from, @NotNull Entity to, double maxDistance) {
        return distance(from, to) <= maxDistance;
    }

    /**
     * Gets the direction vector from one entity to another (normalized).
     *
     * @param from source entity
     * @param to target entity
     * @return normalized direction vector from 'from' to 'to'
     */
    @NotNull
    public static Vec3d getDirectionTo(@NotNull Entity from, @NotNull Entity to) {
        Vec3d fromPos = new Vec3d(from.getX(), from.getY(), from.getZ());
        Vec3d toPos = new Vec3d(to.getX(), to.getY(), to.getZ());
        return toPos.subtract(fromPos).normalize();
    }

    /**
     * Gets the angle from one entity to another (in degrees).
     *
     * @param from source entity
     * @param to target entity
     * @return angle in degrees (0-360)
     */
    public static float getAngleTo(@NotNull Entity from, @NotNull Entity to) {
        Vec3d dir = getDirectionTo(from, to);
        double angle = Math.atan2(dir.z, dir.x) * 180.0 / Math.PI;
        return (float) ((angle + 360) % 360);
    }

    /**
     * Checks if an entity is looking at another entity (within angle tolerance).
     *
     * @param from source entity
     * @param to target entity
     * @param maxAngle maximum angle tolerance in degrees
     * @return true if 'from' is looking towards 'to'
     */
    public static boolean isLooking(@NotNull LivingEntity from, @NotNull Entity to,
            float maxAngle) {
        Vec3d dir = getDirectionTo(from, to);
        Vec3d look = from.getRotationVecClient();
        double dot = dir.dotProduct(look);
        return dot > Math.cos(Math.toRadians(maxAngle));
    }

    // ═════════════════════════════════════════════════════════════════════════════════
    // Entity Attributes & State Management
    // ═════════════════════════════════════════════════════════════════════════════════

    /**
     * Gets the speed of an entity (magnitude of velocity).
     *
     * @param entity the entity
     * @return the speed in blocks per tick
     */
    public static double getSpeed(@NotNull Entity entity) {
        Vec3d vel = entity.getVelocity();
        return Math.sqrt(vel.x * vel.x + vel.y * vel.y + vel.z * vel.z);
    }

    /**
     * Gets the entity's age in ticks (how long it has existed).
     *
     * @param entity the entity
     * @return age in ticks
     */
    public static int getAge(@NotNull Entity entity) {
        return entity.age;
    }

    /**
     * Checks if an entity is in water.
     *
     * @param entity the entity to check
     * @return true if the entity is in water
     */
    public static boolean isInWater(@NotNull Entity entity) {
        return entity.isTouchingWater();
    }

    /**
     * Checks if an entity is burning.
     *
     * @param entity the entity to check
     * @return true if the entity is on fire
     */
    public static boolean isBurning(@NotNull Entity entity) {
        return entity.isOnFire();
    }

    /**
     * Extinguishes a burning entity.
     *
     * @param entity the entity to extinguish
     */
    public static void extinguish(@NotNull Entity entity) {
        entity.extinguish();
    }

    /**
     * Sets an entity on fire.
     *
     * @param entity the entity to set on fire
     * @param durationTicks how many ticks to burn for
     */
    public static void ignite(@NotNull Entity entity, int durationTicks) {
        entity.setOnFireFor(durationTicks);
    }

    /**
     * Teleports an entity to a new position.
     *
     * @param entity the entity to teleport
     * @param x target x coordinate
     * @param y target y coordinate
     * @param z target z coordinate
     * @return true if teleportation was successful
     */
    public static boolean teleport(@NotNull Entity entity, double x, double y, double z) {
        try {
            entity.setPosition(x, y, z);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Teleports an entity to a Vec3d position.
     *
     * @param entity the entity to teleport
     * @param position the target position
     * @return true if teleportation was successful
     */
    public static boolean teleport(@NotNull Entity entity, @NotNull Vec3d position) {
        return teleport(entity, position.x, position.y, position.z);
    }
}

