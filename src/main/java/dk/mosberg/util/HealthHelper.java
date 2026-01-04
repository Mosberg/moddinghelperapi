package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

/**
 * Utility for health and status effect operations. Provides methods for managing entity health and
 * status effects.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * HealthHelper.heal(entity, 5.0f);
 * boolean hasEffect = HealthHelper.hasEffect(entity, StatusEffects.REGENERATION);
 * HealthHelper.addEffect(entity, StatusEffects.SPEED, 200, 1);
 * </pre>
 */
public final class HealthHelper {
    private HealthHelper() {}

    /**
     * Gets the current health of an entity.
     *
     * @param entity the entity
     * @return the current health value
     */
    public static float getHealth(@NotNull LivingEntity entity) {
        return entity.getHealth();
    }

    /**
     * Gets the maximum health of an entity.
     *
     * @param entity the entity
     * @return the maximum health value
     */
    public static float getMaxHealth(@NotNull LivingEntity entity) {
        return entity.getMaxHealth();
    }

    /**
     * Gets the health percentage of an entity (0.0 to 1.0).
     *
     * @param entity the entity
     * @return the health percentage
     */
    public static float getHealthPercent(@NotNull LivingEntity entity) {
        return entity.getHealth() / entity.getMaxHealth();
    }

    /**
     * Checks if an entity is at full health.
     *
     * @param entity the entity
     * @return true if health equals max health
     */
    public static boolean isFullHealth(@NotNull LivingEntity entity) {
        return entity.getHealth() >= entity.getMaxHealth();
    }

    /**
     * Checks if an entity is low on health (below 25%).
     *
     * @param entity the entity
     * @return true if health is below 25%
     */
    public static boolean isLowHealth(@NotNull LivingEntity entity) {
        return getHealthPercent(entity) < 0.25f;
    }

    /**
     * Heals an entity by a specific amount.
     *
     * @param entity the entity to heal
     * @param amount the amount of health to restore
     */
    public static void heal(@NotNull LivingEntity entity, float amount) {
        entity.heal(amount);
    }

    /**
     * Heals an entity to full health.
     *
     * @param entity the entity to heal
     */
    public static void healToFull(@NotNull LivingEntity entity) {
        entity.setHealth(entity.getMaxHealth());
    }

    /**
     * Damages an entity by a specific amount.
     *
     * @param entity the entity to damage
     * @param amount the amount of damage
     * @param source the damage source
     */
    public static void damage(@NotNull LivingEntity entity, float amount,
            @NotNull DamageSource source) {
        entity.damage(null, source, amount);
    }

    /**
     * Checks if an entity has a specific status effect.
     *
     * @param entity the entity
     * @param effect the status effect to check
     * @return true if the entity has the effect
     */
    public static boolean hasEffect(@NotNull LivingEntity entity,
            @NotNull RegistryEntry<StatusEffect> effect) {
        return entity.hasStatusEffect(effect);
    }

    /**
     * Gets a status effect instance from an entity.
     *
     * @param entity the entity
     * @param effect the status effect to get
     * @return the effect instance, or null if not present
     */
    @Nullable
    public static StatusEffectInstance getEffect(@NotNull LivingEntity entity,
            @NotNull RegistryEntry<StatusEffect> effect) {
        return entity.getStatusEffect(effect);
    }

    /**
     * Adds a status effect to an entity.
     *
     * @param entity the entity
     * @param effect the status effect to add
     * @param duration the duration in ticks
     * @param amplifier the amplifier level (0 = level 1)
     * @return true if the effect was added successfully
     */
    public static boolean addEffect(@NotNull LivingEntity entity,
            @NotNull RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        StatusEffectInstance instance = new StatusEffectInstance(effect, duration, amplifier);
        return entity.addStatusEffect(instance);
    }

    /**
     * Adds a status effect with ambient particles hidden.
     *
     * @param entity the entity
     * @param effect the status effect to add
     * @param duration the duration in ticks
     * @param amplifier the amplifier level (0 = level 1)
     * @return true if the effect was added successfully
     */
    public static boolean addEffectHidden(@NotNull LivingEntity entity,
            @NotNull RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        StatusEffectInstance instance =
                new StatusEffectInstance(effect, duration, amplifier, false, false);
        return entity.addStatusEffect(instance);
    }

    /**
     * Removes a status effect from an entity.
     *
     * @param entity the entity
     * @param effect the status effect to remove
     * @return true if the effect was removed
     */
    public static boolean removeEffect(@NotNull LivingEntity entity,
            @NotNull RegistryEntry<StatusEffect> effect) {
        return entity.removeStatusEffect(effect);
    }

    /**
     * Removes all status effects from an entity.
     *
     * @param entity the entity
     */
    public static void clearEffects(@NotNull LivingEntity entity) {
        entity.clearStatusEffects();
    }

    /**
     * Gets the remaining duration of a status effect in ticks.
     *
     * @param entity the entity
     * @param effect the status effect
     * @return the remaining duration, or 0 if not present
     */
    public static int getEffectDuration(@NotNull LivingEntity entity,
            @NotNull RegistryEntry<StatusEffect> effect) {
        StatusEffectInstance instance = entity.getStatusEffect(effect);
        return instance != null ? instance.getDuration() : 0;
    }

    /**
     * Gets the amplifier level of a status effect.
     *
     * @param entity the entity
     * @param effect the status effect
     * @return the amplifier level, or -1 if not present
     */
    public static int getEffectAmplifier(@NotNull LivingEntity entity,
            @NotNull RegistryEntry<StatusEffect> effect) {
        StatusEffectInstance instance = entity.getStatusEffect(effect);
        return instance != null ? instance.getAmplifier() : -1;
    }

    /**
     * Checks if an entity is alive.
     *
     * @param entity the entity
     * @return true if the entity is alive
     */
    public static boolean isAlive(@NotNull LivingEntity entity) {
        return entity.isAlive();
    }

    /**
     * Checks if an entity is dead.
     *
     * @param entity the entity
     * @return true if the entity is dead
     */
    public static boolean isDead(@NotNull LivingEntity entity) {
        return entity.isDead();
    }
}
