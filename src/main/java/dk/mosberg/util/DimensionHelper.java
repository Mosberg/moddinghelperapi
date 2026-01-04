package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * Utility for dimension and world operations. Provides methods for accessing different dimensions
 * and checking world properties.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * ServerWorld overworld = DimensionHelper.getOverworld(server);
 * ServerWorld nether = DimensionHelper.getNether(server);
 * boolean isOverworld = DimensionHelper.isOverworld(world);
 * </pre>
 */
public final class DimensionHelper {
    private DimensionHelper() {}

    /**
     * Gets the overworld dimension.
     *
     * @param server the server instance
     * @return the overworld ServerWorld
     */
    @NotNull
    public static ServerWorld getOverworld(@NotNull MinecraftServer server) {
        return server.getOverworld();
    }

    /**
     * Gets the Nether dimension.
     *
     * @param server the server instance
     * @return the Nether ServerWorld, or null if not loaded
     */
    @Nullable
    public static ServerWorld getNether(@NotNull MinecraftServer server) {
        return server.getWorld(World.NETHER);
    }

    /**
     * Gets the End dimension.
     *
     * @param server the server instance
     * @return the End ServerWorld, or null if not loaded
     */
    @Nullable
    public static ServerWorld getEnd(@NotNull MinecraftServer server) {
        return server.getWorld(World.END);
    }

    /**
     * Gets a dimension by its registry key.
     *
     * @param server the server instance
     * @param key the dimension registry key
     * @return the ServerWorld, or null if not found
     */
    @Nullable
    public static ServerWorld getWorld(@NotNull MinecraftServer server,
            @NotNull RegistryKey<World> key) {
        return server.getWorld(key);
    }

    /**
     * Gets a dimension by its identifier.
     *
     * @param server the server instance
     * @param id the dimension identifier
     * @return the ServerWorld, or null if not found
     */
    @Nullable
    public static ServerWorld getWorld(@NotNull MinecraftServer server, @NotNull Identifier id) {
        RegistryKey<World> key = RegistryKey.of(net.minecraft.registry.RegistryKeys.WORLD, id);
        return server.getWorld(key);
    }

    /**
     * Checks if a world is the overworld.
     *
     * @param world the world to check
     * @return true if the world is the overworld
     */
    public static boolean isOverworld(@NotNull World world) {
        return world.getRegistryKey() == World.OVERWORLD;
    }

    /**
     * Checks if a world is the Nether.
     *
     * @param world the world to check
     * @return true if the world is the Nether
     */
    public static boolean isNether(@NotNull World world) {
        return world.getRegistryKey() == World.NETHER;
    }

    /**
     * Checks if a world is the End.
     *
     * @param world the world to check
     * @return true if the world is the End
     */
    public static boolean isEnd(@NotNull World world) {
        return world.getRegistryKey() == World.END;
    }

    /**
     * Gets the dimension identifier for a world.
     *
     * @param world the world
     * @return the dimension identifier
     */
    @NotNull
    public static Identifier getDimensionId(@NotNull World world) {
        return world.getRegistryKey().getValue();
    }

    /**
     * Gets the dimension name for a world.
     *
     * @param world the world
     * @return the dimension name (e.g., "minecraft:overworld")
     */
    @NotNull
    public static String getDimensionName(@NotNull World world) {
        return getDimensionId(world).toString();
    }

    /**
     * Checks if a world is a ServerWorld.
     *
     * @param world the world to check
     * @return true if the world is a ServerWorld
     */
    public static boolean isServerWorld(@NotNull World world) {
        return world instanceof ServerWorld;
    }

    /**
     * Checks if two worlds are the same dimension.
     *
     * @param world1 first world
     * @param world2 second world
     * @return true if both worlds are the same dimension
     */
    public static boolean isSameDimension(@NotNull World world1, @NotNull World world2) {
        return world1.getRegistryKey() == world2.getRegistryKey();
    }

    /**
     * Gets the time of day in the world.
     *
     * @param world the world
     * @return the time of day (0-24000)
     */
    public static long getTimeOfDay(@NotNull World world) {
        return world.getTimeOfDay();
    }

    /**
     * Checks if it is daytime in the world.
     *
     * @param world the world
     * @return true if it is day (time between 0 and 12000)
     */
    public static boolean isDay(@NotNull World world) {
        long time = world.getTimeOfDay() % 24000;
        return time >= 0 && time < 12000;
    }

    /**
     * Checks if it is nighttime in the world.
     *
     * @param world the world
     * @return true if it is night (time between 12000 and 24000)
     */
    public static boolean isNight(@NotNull World world) {
        return !isDay(world);
    }
}
