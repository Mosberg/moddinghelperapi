package dk.mosberg.util;

import java.util.Collection;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;

/**
 * Utility for potion and status effect operations. Provides methods for applying, removing, and
 * querying status effects on players and living entities.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * PotionHelper.addEffect(player, StatusEffects.STRENGTH, 300, 2, true);
 * PotionHelper.removeEffect(player, StatusEffects.WEAKNESS);
 * int amplifier = PotionHelper.getEffectAmplifier(player, StatusEffects.SPEED);
 * boolean hasPoison = PotionHelper.hasEffect(player, StatusEffects.POISON);
 * </pre>
 */
public final class PotionHelper {
    private PotionHelper() {}

    /**
     * Applies a status effect to a player.
     *
     * @param player the player to affect
     * @param effect the effect to apply
     * @param durationTicks the duration in game ticks (20 ticks = 1 second)
     * @param amplifier the effect amplifier level (0-based)
     * @param showParticles whether to show particle effects
     */
    public static void addEffect(@NotNull PlayerEntity player,
            @NotNull RegistryEntry<StatusEffect> effect, int durationTicks, int amplifier,
            boolean showParticles) {
        var effectInstance =
                new StatusEffectInstance(effect, durationTicks, amplifier, false, showParticles);
        player.addStatusEffect(effectInstance);
    }

    /**
     * Applies a status effect with default particle visibility.
     *
     * @param player the player to affect
     * @param effect the effect to apply
     * @param durationTicks the duration in game ticks
     * @param amplifier the effect amplifier level
     */
    public static void addEffect(@NotNull PlayerEntity player,
            @NotNull RegistryEntry<StatusEffect> effect, int durationTicks, int amplifier) {
        addEffect(player, effect, durationTicks, amplifier, true);
    }

    /**
     * Removes a specific status effect from a player.
     *
     * @param player the player to affect
     * @param effect the effect to remove
     */
    public static void removeEffect(@NotNull PlayerEntity player,
            @NotNull RegistryEntry<StatusEffect> effect) {
        player.removeStatusEffect(effect);
    }

    /**
     * Clears all status effects from a player.
     *
     * @param player the player to clear
     */
    public static void clearEffects(@NotNull PlayerEntity player) {
        for (var effect : player.getStatusEffects()) {
            player.removeStatusEffect(effect.getEffectType());
        }
    }

    /**
     * Checks if a player has a specific status effect.
     *
     * @param player the player to check
     * @param effect the effect to look for
     * @return true if the player has the effect
     */
    public static boolean hasEffect(@NotNull PlayerEntity player,
            @NotNull RegistryEntry<StatusEffect> effect) {
        return player.hasStatusEffect(effect);
    }

    /**
     * Gets the amplifier (level) of a status effect on a player.
     *
     * @param player the player to check
     * @param effect the effect to query
     * @return the amplifier (0-based), or -1 if not present
     */
    public static int getEffectAmplifier(@NotNull PlayerEntity player,
            @NotNull RegistryEntry<StatusEffect> effect) {
        var instance = player.getStatusEffect(effect);
        return instance == null ? -1 : instance.getAmplifier();
    }

    /**
     * Gets the remaining duration of a status effect on a player.
     *
     * @param player the player to check
     * @param effect the effect to query
     * @return the remaining duration in ticks, or 0 if not present
     */
    public static int getEffectDuration(@NotNull PlayerEntity player,
            @NotNull RegistryEntry<StatusEffect> effect) {
        var instance = player.getStatusEffect(effect);
        return instance == null ? 0 : instance.getDuration();
    }

    /**
     * Gets all status effects currently on a player.
     *
     * @param player the player to check
     * @return a collection of status effects
     */
    @NotNull
    public static Collection<StatusEffectInstance> getAllEffects(@NotNull PlayerEntity player) {
        return player.getStatusEffects();
    }

    /**
     * Gets the count of active status effects on a player.
     *
     * @param player the player to check
     * @return the number of active effects
     */
    public static int getEffectCount(@NotNull PlayerEntity player) {
        return player.getStatusEffects().size();
    }

    /**
     * Checks if a player has any status effects.
     *
     * @param player the player to check
     * @return true if the player has at least one effect
     */
    public static boolean hasAnyEffect(@NotNull PlayerEntity player) {
        return !player.getStatusEffects().isEmpty();
    }

    /**
     * Gets a list of all negative effects on a player (weakness, poison, etc.).
     *
     * @param player the player to check
     * @return a collection of negative effects
     */
    @NotNull
    public static Collection<StatusEffectInstance> getNegativeEffects(
            @NotNull PlayerEntity player) {
        return player.getStatusEffects().stream()
                .filter(effect -> !effect.getEffectType().value().isBeneficial())
                .collect(Collectors.toList());
    }

    /**
     * Gets a list of all positive effects on a player (speed, strength, etc.).
     *
     * @param player the player to check
     * @return a collection of positive effects
     */
    @NotNull
    public static Collection<StatusEffectInstance> getPositiveEffects(
            @NotNull PlayerEntity player) {
        return player.getStatusEffects().stream()
                .filter(effect -> effect.getEffectType().value().isBeneficial())
                .collect(Collectors.toList());
    }
}
