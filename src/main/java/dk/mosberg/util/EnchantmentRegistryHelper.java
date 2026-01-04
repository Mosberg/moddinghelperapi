package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

/**
 * Utility for enchantment operations. Provides placeholder methods for accessing enchantment
 * information.
 *
 * Helper for enchantment registry lookups. Callers supply the registry, typically from
 * {@code world.getRegistryManager().get(RegistryKeys.ENCHANTMENT)}.
 */
public final class EnchantmentRegistryHelper {
    private EnchantmentRegistryHelper() {}

    /**
     * Gets an enchantment by identifier.
     *
     * @param id enchantment identifier (e.g., "minecraft:sharpness")
     * @return the enchantment or null if not found
     */
    public static Enchantment getEnchantment(@NotNull Registry<Enchantment> registry,
            @NotNull String id) {
        return registry.get(IdentifierHelper.of(id));
    }

    public static boolean isEnchantmentRegistered(@NotNull Registry<Enchantment> registry,
            @NotNull String id) {
        return registry.containsId(IdentifierHelper.of(id));
    }

    public static int getEnchantmentCount(@NotNull Registry<Enchantment> registry) {
        return registry.size();
    }

    /**
     * Gets the registry ID for an enchantment.
     *
     * <p>
     * Note: This method is a placeholder in 1.21.11. The enchantment registry access is not
     * available through the public API.
     *
     * @param enchantment the enchantment to look up
     * @return "unknown" (enchantment registry not accessible in 1.21.11)
     */
    @NotNull
    public static String getEnchantmentId(@NotNull Registry<Enchantment> registry,
            @NotNull Enchantment enchantment) {
        var id = registry.getId(enchantment);
        return id != null ? id.toString() : "unknown";
    }

    /** Convenience registry key for enchantments. */
    public static final RegistryKey<Registry<Enchantment>> ENCHANTMENT_REGISTRY_KEY =
            RegistryKeys.ENCHANTMENT;
}
