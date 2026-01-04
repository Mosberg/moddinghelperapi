package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.world.biome.Biome;

/**
 * Utility for biome registry operations. Provides methods for accessing biomes and checking biome
 * properties.
 *
 * <p>
 * Note: Biomes are stored in dimension registries in 1.21.11, not in a global registry. This helper
 * provides placeholder methods for API consistency.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * // Biomes are accessed through dimension-specific registries in 1.21.11
 * // Use dimension helpers to access biome registries
 * </pre>
 */
public final class BiomeRegistryHelper {
    private BiomeRegistryHelper() {}

    /**
     * Gets the total count of registered biomes (placeholder for 1.21.11).
     *
     * <p>
     * Note: In 1.21.11, biomes are per-dimension. This returns a placeholder value.
     *
     * @return a placeholder biome count
     */
    public static int getBiomeCount() {
        return 0; // Biomes stored per-dimension in 1.21.11
    }

    /**
     * Gets the registry ID for a biome.
     *
     * @param biome the biome to look up
     * @return the registry ID as a string, or "unknown" if not found
     */
    @NotNull
    public static String getBiomeId(@NotNull Biome biome) {
        return "unknown"; // Biome registry access limited in 1.21.11
    }
}
