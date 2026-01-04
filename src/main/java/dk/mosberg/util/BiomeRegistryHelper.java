package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

/**
 * Utility for biome registry operations. Provides methods for accessing biomes and checking biome
 * properties.
 *
 * Helper for biome registry lookups. Callers supply the registry, typically from
 * {@code world.getRegistryManager().get(RegistryKeys.BIOME)}.
 */
public final class BiomeRegistryHelper {
    private BiomeRegistryHelper() {}

    /**
     * Gets a biome by identifier.
     *
     * @param id biome identifier (e.g., "minecraft:plains")
     * @return the biome or null if not found
     */
    public static Biome getBiome(@NotNull Registry<Biome> registry, @NotNull String id) {
        return registry.get(IdentifierHelper.of(id));
    }

    /**
     * Checks if a biome is registered.
     *
     * @param id biome identifier
     * @return true if the biome exists
     */
    public static boolean isBiomeRegistered(@NotNull Registry<Biome> registry, @NotNull String id) {
        return registry.containsId(IdentifierHelper.of(id));
    }

    /**
     * Gets the total count of registered biomes.
     *
     * @return biome count
     */
    public static int getBiomeCount(@NotNull Registry<Biome> registry) {
        return registry.size();
    }

    /**
     * Gets the registry ID for a biome.
     *
     * @param biome the biome to look up
     * @return the registry ID as a string, or "unknown" if not found
     */
    @NotNull
    public static String getBiomeId(@NotNull Registry<Biome> registry, @NotNull Biome biome) {
        var id = registry.getId(biome);
        return id != null ? id.toString() : "unknown";
    }

    /** Convenience registry key for biome lookups. */
    public static final RegistryKey<Registry<Biome>> BIOME_REGISTRY_KEY = RegistryKeys.BIOME;
}
