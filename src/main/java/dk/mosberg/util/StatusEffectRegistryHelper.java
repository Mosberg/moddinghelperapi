package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;

/**
 * Utility for status effect registry operations. Provides methods for accessing status effects from
 * the registry.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * StatusEffect speed = StatusEffectRegistryHelper.getStatusEffect("minecraft:speed");
 * if (StatusEffectRegistryHelper.isStatusEffectRegistered("minecraft:poison")) {
 *     // Status effect exists
 * }
 * int count = StatusEffectRegistryHelper.getStatusEffectCount();
 * </pre>
 */
public final class StatusEffectRegistryHelper {
    private StatusEffectRegistryHelper() {}

    /**
     * Gets a status effect from the registry by ID.
     *
     * @param id the status effect identifier (e.g., "minecraft:speed")
     * @return the StatusEffect, or null if not found
     */
    public static StatusEffect getStatusEffect(@NotNull String id) {
        return Registries.STATUS_EFFECT.get(IdentifierHelper.of(id));
    }

    /**
     * Checks if a status effect is registered.
     *
     * @param id the status effect identifier
     * @return true if the status effect exists
     */
    public static boolean isStatusEffectRegistered(@NotNull String id) {
        return Registries.STATUS_EFFECT.containsId(IdentifierHelper.of(id));
    }

    /**
     * Gets the total count of registered status effects.
     *
     * @return the number of registered status effects
     */
    public static int getStatusEffectCount() {
        return Registries.STATUS_EFFECT.size();
    }

    /**
     * Gets the registry ID for a status effect.
     *
     * @param statusEffect the status effect to look up
     * @return the registry ID as a string, or "unknown" if not found
     */
    @NotNull
    public static String getStatusEffectId(@NotNull StatusEffect statusEffect) {
        var id = Registries.STATUS_EFFECT.getId(statusEffect);
        return id != null ? id.toString() : "unknown";
    }

    /**
     * Checks if a status effect is beneficial (speed, strength, regeneration, etc.).
     *
     * @param statusEffect the status effect to check
     * @return true if the effect is beneficial
     */
    public static boolean isBeneficial(@NotNull StatusEffect statusEffect) {
        return statusEffect.isBeneficial();
    }
}
