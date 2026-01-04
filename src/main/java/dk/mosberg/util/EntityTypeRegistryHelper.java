package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;

/**
 * Utility for entity type registry operations. Provides methods for accessing entity types from the
 * registry.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * EntityType<?> zombie = EntityTypeRegistryHelper.getEntityType("minecraft:zombie");
 * if (EntityTypeRegistryHelper.isEntityTypeRegistered("minecraft:creeper")) {
 *     // Entity type exists
 * }
 * int count = EntityTypeRegistryHelper.getEntityTypeCount();
 * </pre>
 */
public final class EntityTypeRegistryHelper {
    private EntityTypeRegistryHelper() {}

    /**
     * Gets an entity type from the registry by ID.
     *
     * @param id the entity type identifier (e.g., "minecraft:zombie")
     * @return the EntityType, or null if not found
     */
    public static EntityType<?> getEntityType(@NotNull String id) {
        return Registries.ENTITY_TYPE.get(IdentifierHelper.of(id));
    }

    /**
     * Checks if an entity type is registered.
     *
     * @param id the entity type identifier
     * @return true if the entity type exists
     */
    public static boolean isEntityTypeRegistered(@NotNull String id) {
        return Registries.ENTITY_TYPE.containsId(IdentifierHelper.of(id));
    }

    /**
     * Gets the total count of registered entity types.
     *
     * @return the number of registered entity types
     */
    public static int getEntityTypeCount() {
        return Registries.ENTITY_TYPE.size();
    }

    /**
     * Gets the registry ID for an entity type.
     *
     * @param entityType the entity type to look up
     * @return the registry ID as a string, or "unknown" if not found
     */
    @NotNull
    public static String getEntityTypeId(@NotNull EntityType<?> entityType) {
        var id = Registries.ENTITY_TYPE.getId(entityType);
        return id != null ? id.toString() : "unknown";
    }
}
