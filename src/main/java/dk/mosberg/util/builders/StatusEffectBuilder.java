package dk.mosberg.util.builders;

import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

/**
 * Fluent builder for creating StatusEffectInstance objects with custom properties.
 */
public final class StatusEffectBuilder {
    private final StatusEffect statusEffect;
    private int duration = 100; // Default 5 seconds (100 ticks)
    private int amplifier = 0; // Default level 1 (amplifier 0)
    private boolean ambient = false;
    private boolean showParticles = true;
    private boolean showIcon = true;

    public StatusEffectBuilder(@NotNull StatusEffect statusEffect) {
        this.statusEffect = Objects.requireNonNull(statusEffect);
    }

    public @NotNull StatusEffectBuilder duration(int ticks) {
        this.duration = Math.max(1, ticks);
        return this;
    }

    public @NotNull StatusEffectBuilder durationSeconds(double seconds) {
        this.duration = Math.max(1, (int) (seconds * 20));
        return this;
    }

    public @NotNull StatusEffectBuilder amplifier(int amplifier) {
        this.amplifier = Math.max(0, Math.min(amplifier, 255));
        return this;
    }

    public @NotNull StatusEffectBuilder level(int level) {
        this.amplifier = Math.max(0, level - 1);
        return this;
    }

    public @NotNull StatusEffectBuilder ambient() {
        this.ambient = true;
        return this;
    }

    public @NotNull StatusEffectBuilder hideParticles() {
        this.showParticles = false;
        return this;
    }

    public @NotNull StatusEffectBuilder showParticles() {
        this.showParticles = true;
        return this;
    }

    public @NotNull StatusEffectBuilder hideIcon() {
        this.showIcon = false;
        return this;
    }

    public @NotNull StatusEffectBuilder showIcon() {
        this.showIcon = true;
        return this;
    }

    /**
     * Builds the StatusEffectInstance using the configured properties. Returns empty if creation
     * fails (e.g., registry entry required in this version).
     */
    public @NotNull Optional<StatusEffectInstance> build() {
        try {
            RegistryEntry<StatusEffect> entry = Registries.STATUS_EFFECT.getEntry(statusEffect);
            return Optional.of(new StatusEffectInstance(entry, duration, amplifier, ambient,
                    showParticles, showIcon));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
