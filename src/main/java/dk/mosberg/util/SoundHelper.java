package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Utility for playing sounds in the world. Provides methods for playing sounds at specific
 * positions or to specific players.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * SoundHelper.playAt(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
 * SoundHelper.playToPlayer(player, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);
 * </pre>
 */
public final class SoundHelper {
    private SoundHelper() {}

    /**
     * Plays a sound at a specific position in the world.
     *
     * @param world the world
     * @param pos the position
     * @param sound the sound event
     * @param volume the volume (1.0 = normal)
     * @param pitch the pitch (1.0 = normal)
     */
    public static void playAt(@NotNull World world, @NotNull BlockPos pos,
            @NotNull SoundEvent sound, float volume, float pitch) {
        world.playSound(null, pos, sound, SoundCategory.MASTER, volume, pitch);
    }

    /**
     * Plays a sound at a specific position with a category.
     *
     * @param world the world
     * @param pos the position
     * @param sound the sound event
     * @param category the sound category
     * @param volume the volume (1.0 = normal)
     * @param pitch the pitch (1.0 = normal)
     */
    public static void playAt(@NotNull World world, @NotNull BlockPos pos,
            @NotNull SoundEvent sound, @NotNull SoundCategory category, float volume, float pitch) {
        world.playSound(null, pos, sound, category, volume, pitch);
    }

    /**
     * Plays a sound at a Vec3d position.
     *
     * @param world the world
     * @param pos the position
     * @param sound the sound event
     * @param volume the volume (1.0 = normal)
     * @param pitch the pitch (1.0 = normal)
     */
    public static void playAt(@NotNull World world, @NotNull Vec3d pos, @NotNull SoundEvent sound,
            float volume, float pitch) {
        world.playSound(null, pos.x, pos.y, pos.z, sound, SoundCategory.MASTER, volume, pitch);
    }

    /**
     * Plays a sound at a position for all nearby players.
     *
     * @param world the world
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @param sound the sound event
     * @param volume the volume (1.0 = normal)
     * @param pitch the pitch (1.0 = normal)
     */
    public static void playAtPosition(@NotNull World world, double x, double y, double z,
            @NotNull SoundEvent sound, float volume, float pitch) {
        world.playSound(null, x, y, z, sound, SoundCategory.MASTER, volume, pitch);
    }

    /**
     * Plays a sound with normal volume and pitch.
     *
     * @param world the world
     * @param pos the position
     * @param sound the sound event
     */
    public static void play(@NotNull World world, @NotNull BlockPos pos,
            @NotNull SoundEvent sound) {
        playAt(world, pos, sound, 1.0f, 1.0f);
    }

    /**
     * Plays a sound with random pitch variation.
     *
     * @param world the world
     * @param pos the position
     * @param sound the sound event
     * @param volume the volume (1.0 = normal)
     * @param pitchVariation the pitch variation range (e.g., 0.2)
     */
    public static void playWithVariation(@NotNull World world, @NotNull BlockPos pos,
            @NotNull SoundEvent sound, float volume, float pitchVariation) {
        float pitch = 1.0f + (float) (Math.random() * pitchVariation * 2 - pitchVariation);
        playAt(world, pos, sound, volume, pitch);
    }
}
