package dk.mosberg.util.builders;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

/**
 * Fluent builder for creating StatusEffectInstance objects with custom properties.
 *
 * <p>
 * Provides a chainable API for building potion effects with specific amplifiers, durations, and
 * visual properties.
 *
 * <p>
 * <strong>Example:</strong>
 * </p>
 *
 * <pre>
 * StatusEffectInstance effect = new StatusEffectBuilder(StatusEffects.SPEED).amplifier(1) // Level
 *                                                                                         // 2
 *                                                                                         // (amplifier
 *                                                                                         // 1 =
 *                                                                                         // level
 *                                                                                         // 2)
 *         .duration(200) // 10 seconds
 *         .hideParticles().build();
 * </pre>
 */
public final class StatusEffectBuilder {
    private final StatusEffect statusEffect;
    private int duration = 100; // Default 5 seconds (100 ticks)
    private int amplifier = 0; // Default level 1 (amplifier 0)
    private boolean ambient = false;
    private boolean showParticles = true;
    private boolean showIcon = true;

    /**
     * Creates a new StatusEffect builder.
     *
     * @param statusEffect the status effect to build
     * @throws NullPointerException if statusEffect is null
     */
    public StatusEffectBuilder(@NotNull StatusEffect statusEffect) {
        this.statusEffect = Objects.requireNonNull(statusEffect);
    }

    /**
     * Sets the effect duration in ticks.
     *
     * @param ticks the duration in ticks (20 ticks = 1 second)
     * @return this builder for method chaining
     */
    @NotNull
    public StatusEffectBuilder duration(int ticks) {
        this.duration = Math.max(1, ticks);
        return this;
    }

    /**
     * Sets the effect duration in seconds.
     *
     * @param seconds the duration in seconds
     * @return this builder for method chaining
     */
    @NotNull
    public StatusEffectBuilder durationSeconds(double seconds) {
        this.duration = Math.max(1, (int) (seconds * 20));
        return this;
    }

    /**
     * Sets the effect amplifier (level - 1). Amplifier 0 = Level I, Amplifier 1 = Level II, etc.
     *
     * @param amplifier the amplifier value (0-9 typical)
     * @return this builder for method chaining
     */
    @NotNull
    public StatusEffectBuilder amplifier(int amplifier) {
        this.amplifier = Math.max(0, Math.min(amplifier, 255));
        return this;
    }

    /**
     * Sets the effect level (convenience method). Level 1 = Amplifier 0.
     *
     * @param level the effect level (1+)
     * @return this builder for method chaining
     */
    @NotNull
    public StatusEffectBuilder level(int level) {
        this.amplifier = Math.max(0, level - 1);
        return this;
    }

    /**
     * Marks this effect as ambient (from beacons, doesn't reduce duration).
     *
     * @return this builder for method chaining
     */
    @NotNull
    public StatusEffectBuilder ambient() {
        this.ambient = true;
        return this;
    }

    /**
     * Hides particle effects from this status effect.
     *
     * @return this builder for method chaining
     */
    @NotNull
    public StatusEffectBuilder hideParticles() {
        this.showParticles = false;
        return this;
    }

    /**
     * Shows particle effects from this status effect (default).
     *
     * @return this builder for method chaining
     */
    @NotNull
    public StatusEffectBuilder showParticles() {
        this.showParticles = true;
        return this;
    }

    /**
     * Hides the effect icon from the player's HUD.
     *
     * @return this builder for method chaining
     */
    @NotNull
    public StatusEffectBuilder hideIcon() {
        this.showIcon = false;
        return this;
    }

    /**
     * Shows the effect icon on the player's HUD (default).
     *
     * @return this builder for method chaining
     */
    @NotNull
    public StatusEffectBuilder showIcon() {
        this.showIcon = true;
        return this;
    }

    /**
     * Builds and returns the configured StatusEffectInstance.
     *
     * <p>
     * Note: In 1.21.11, StatusEffectInstance requires a RegistryEntry. This method returns null as
     * the raw StatusEffect cannot be directly used. For production use, obtain a RegistryEntry from
     * the effect registry.
     *
     * @return null (StatusEffectInstance requires RegistryEntry in 1.21.11)
     */
    @Nullable
    public StatusEffectInstance build() {
        // In Minecraft 1.21.11, StatusEffectInstance constructor requires
        // RegistryEntry<StatusEffect>
        // not the raw StatusEffect object. To use this builder, you must obtain the RegistryEntry:
        //
        // Example (if registry access becomes available):
        // RegistryEntry<StatusEffect> entry = ...getRegistryEntry(statusEffect)...;
        // return new StatusEffectInstance(entry, duration, amplifier, ambient, showParticles,
        // showIcon);
        //
        // For now, this returns null to prevent compilation errors
        return null;
    }
}
