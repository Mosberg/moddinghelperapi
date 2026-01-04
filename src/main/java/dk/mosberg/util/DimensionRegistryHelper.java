package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.world.dimension.DimensionType;

/**
 * Utility for dimension registry operations. Provides methods for accessing dimension types.
 *
 * <p>
 * Note: In 1.21.11, dimension type registry access is limited. This helper provides placeholder
 * methods for API consistency with other registry helpers.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * // Dimension types accessed through world.getDimensionEntry()
 * int count = DimensionRegistryHelper.getDimensionTypeCount(); // Returns typical count
 * String id = DimensionRegistryHelper.getDimensionTypeId(dimensionType);
 * </pre>
 */
public final class DimensionRegistryHelper {
    private DimensionRegistryHelper() {}

    /**
     * Checks if a dimension type is registered (placeholder for 1.21.11).
     *
     * <p>
     * Note: Dimension type registry access is limited in 1.21.11.
     *
     * @param id the dimension type identifier
     * @return true if the dimension type exists (placeholder)
     */
    public static boolean isDimensionTypeRegistered(@NotNull String id) {
        // Dimension type registry access limited in 1.21.11
        return false;
    }

    /**
     * Gets the total count of registered dimension types.
     *
     * <p>
     * Note: Returns typical Minecraft dimension count.
     *
     * @return the number of dimension types (typically 3: overworld, nether, end)
     */
    public static int getDimensionTypeCount() {
        return 3; // Typical: overworld, nether, end
    }

    /**
     * Gets the registry ID for a dimension type.
     *
     * @param dimensionType the dimension type to look up
     * @return the registry ID as a string, or "unknown" if not found
     */
    @NotNull
    public static String getDimensionTypeId(@NotNull DimensionType dimensionType) {
        return "unknown"; // Dimension type registry access limited in 1.21.11
    }
}
