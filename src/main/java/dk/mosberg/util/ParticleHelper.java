package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

/**
 * Utility for spawning particles in the world. Provides methods for creating various particle
 * effects with different patterns.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * ParticleHelper.spawn(world, ParticleTypes.FLAME, pos, 10);
 * ParticleHelper.spawnCircle(world, ParticleTypes.HEART, pos, 2.0, 20);
 * ParticleHelper.spawnLine(world, ParticleTypes.END_ROD, start, end, 0.5);
 * </pre>
 */
public final class ParticleHelper {
    private ParticleHelper() {}

    /**
     * Spawns particles at a specific position.
     *
     * @param world the server world
     * @param particle the particle type
     * @param pos the position
     * @param count the number of particles
     */
    public static void spawn(@NotNull ServerWorld world, @NotNull ParticleEffect particle,
            @NotNull Vec3d pos, int count) {
        world.spawnParticles(particle, pos.x, pos.y, pos.z, count, 0, 0, 0, 0);
    }

    /**
     * Spawns particles with velocity.
     *
     * @param world the server world
     * @param particle the particle type
     * @param pos the position
     * @param count the number of particles
     * @param velocity the velocity vector
     */
    public static void spawnWithVelocity(@NotNull ServerWorld world,
            @NotNull ParticleEffect particle, @NotNull Vec3d pos, int count,
            @NotNull Vec3d velocity) {
        world.spawnParticles(particle, pos.x, pos.y, pos.z, count, velocity.x, velocity.y,
                velocity.z, 0);
    }

    /**
     * Spawns particles with random spread.
     *
     * @param world the server world
     * @param particle the particle type
     * @param pos the center position
     * @param count the number of particles
     * @param spread the spread distance
     */
    public static void spawnWithSpread(@NotNull ServerWorld world, @NotNull ParticleEffect particle,
            @NotNull Vec3d pos, int count, double spread) {
        world.spawnParticles(particle, pos.x, pos.y, pos.z, count, spread, spread, spread, 0);
    }

    /**
     * Spawns particles in a circle pattern.
     *
     * @param world the server world
     * @param particle the particle type
     * @param center the center position
     * @param radius the circle radius
     * @param points the number of particles
     */
    public static void spawnCircle(@NotNull ServerWorld world, @NotNull ParticleEffect particle,
            @NotNull Vec3d center, double radius, int points) {
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);
            world.spawnParticles(particle, x, center.y, z, 1, 0, 0, 0, 0);
        }
    }

    /**
     * Spawns particles in a line between two points.
     *
     * @param world the server world
     * @param particle the particle type
     * @param start the start position
     * @param end the end position
     * @param spacing the distance between particles
     */
    public static void spawnLine(@NotNull ServerWorld world, @NotNull ParticleEffect particle,
            @NotNull Vec3d start, @NotNull Vec3d end, double spacing) {
        Vec3d direction = end.subtract(start);
        double distance = direction.length();
        Vec3d step = direction.normalize().multiply(spacing);

        int steps = (int) (distance / spacing);
        Vec3d current = start;

        for (int i = 0; i <= steps; i++) {
            world.spawnParticles(particle, current.x, current.y, current.z, 1, 0, 0, 0, 0);
            current = current.add(step);
        }
    }

    /**
     * Spawns particles in a sphere pattern.
     *
     * @param world the server world
     * @param particle the particle type
     * @param center the center position
     * @param radius the sphere radius
     * @param count the number of particles
     */
    public static void spawnSphere(@NotNull ServerWorld world, @NotNull ParticleEffect particle,
            @NotNull Vec3d center, double radius, int count) {
        for (int i = 0; i < count; i++) {
            // Random point on sphere surface using spherical coordinates
            double theta = Math.random() * 2 * Math.PI;
            double phi = Math.acos(2 * Math.random() - 1);

            double x = center.x + radius * Math.sin(phi) * Math.cos(theta);
            double y = center.y + radius * Math.sin(phi) * Math.sin(theta);
            double z = center.z + radius * Math.cos(phi);

            world.spawnParticles(particle, x, y, z, 1, 0, 0, 0, 0);
        }
    }

    /**
     * Spawns particles in a helix pattern.
     *
     * @param world the server world
     * @param particle the particle type
     * @param start the start position
     * @param height the helix height
     * @param radius the helix radius
     * @param rotations the number of rotations
     * @param points the number of particles
     */
    public static void spawnHelix(@NotNull ServerWorld world, @NotNull ParticleEffect particle,
            @NotNull Vec3d start, double height, double radius, double rotations, int points) {
        for (int i = 0; i < points; i++) {
            double progress = (double) i / points;
            double angle = 2 * Math.PI * rotations * progress;
            double y = start.y + height * progress;

            double x = start.x + radius * Math.cos(angle);
            double z = start.z + radius * Math.sin(angle);

            world.spawnParticles(particle, x, y, z, 1, 0, 0, 0, 0);
        }
    }

    /**
     * Spawns particles in a spiral pattern on the ground.
     *
     * @param world the server world
     * @param particle the particle type
     * @param center the center position
     * @param maxRadius the maximum spiral radius
     * @param rotations the number of rotations
     * @param points the number of particles
     */
    public static void spawnSpiral(@NotNull ServerWorld world, @NotNull ParticleEffect particle,
            @NotNull Vec3d center, double maxRadius, double rotations, int points) {
        for (int i = 0; i < points; i++) {
            double progress = (double) i / points;
            double angle = 2 * Math.PI * rotations * progress;
            double radius = maxRadius * progress;

            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);

            world.spawnParticles(particle, x, center.y, z, 1, 0, 0, 0, 0);
        }
    }

    /**
     * Spawns an explosion-like particle burst.
     *
     * @param world the server world
     * @param particle the particle type
     * @param center the center position
     * @param count the number of particles
     * @param speed the particle speed
     */
    public static void spawnExplosion(@NotNull ServerWorld world, @NotNull ParticleEffect particle,
            @NotNull Vec3d center, int count, double speed) {
        for (int i = 0; i < count; i++) {
            double vx = (Math.random() - 0.5) * speed;
            double vy = (Math.random() - 0.5) * speed;
            double vz = (Math.random() - 0.5) * speed;

            world.spawnParticles(particle, center.x, center.y, center.z, 0, vx, vy, vz, 1);
        }
    }
}
