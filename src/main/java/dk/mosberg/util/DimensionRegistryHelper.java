package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.dimension.DimensionType;

/**
 * Utility for dimension registry operations. Provides methods for accessing dimension types.
 *
 * Helper for dimension type registry lookups. Callers supply the registry, typically from
 * {@code world.getRegistryManager().get(RegistryKeys.DIMENSION_TYPE)}.
 */
public final class DimensionRegistryHelper {
    private DimensionRegistryHelper() {}

    /**
     * Gets a dimension type by identifier.
     *
     * @param id dimension type identifier (e.g., "minecraft:overworld")
     * @return the DimensionType, or null if not found
     */
    public static DimensionType getDimensionType(@NotNull Registry<DimensionType> registry,
            @NotNull String id) {
        return registry.get(IdentifierHelper.of(id));
    }

    /**
     * Gets the total count of registered dimension types.
     *
     * <p>
     * Note: Returns typical Minecraft dimension count.
     *
     * @return the number of dimension types (typically 3: overworld, nether, end)
     */
    public static boolean isDimensionTypeRegistered(@NotNull Registry<DimensionType> registry,
            @NotNull String id) {
        return registry.containsId(IdentifierHelper.of(id));
    }

    public static int getDimensionTypeCount(@NotNull Registry<DimensionType> registry) {
        return registry.size();
    }

    /**
     * Gets the registry ID for a dimension type.
     *
     * @param dimensionType the dimension type to look up
     * @return the registry ID as a string, or "unknown" if not found
     */
    @NotNull
    public static String getDimensionTypeId(@NotNull Registry<DimensionType> registry,
            @NotNull DimensionType dimensionType) {
        var id = registry.getId(dimensionType);
        return id != null ? id.toString() : "unknown";
    }

    /** Convenience registry key for dimension types. */
    public static final RegistryKey<Registry<DimensionType>> DIMENSION_TYPE_REGISTRY_KEY =
            RegistryKeys.DIMENSION_TYPE;
}
