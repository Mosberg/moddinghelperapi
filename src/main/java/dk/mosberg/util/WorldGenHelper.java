package dk.mosberg.util;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

/**
 * Lightweight helpers for querying world-gen registries. Placement execution is intentionally not
 * handled here; use retrieved entries with the appropriate world-gen pipelines.
 */
public final class WorldGenHelper {
    private WorldGenHelper() {}

    /** Convenience registry keys for world-gen registries. */
    public static final RegistryKey<Registry<ConfiguredFeature<?, ?>>> CONFIGURED_FEATURE_REGISTRY_KEY =
            RegistryKeys.CONFIGURED_FEATURE;
    public static final RegistryKey<Registry<PlacedFeature>> PLACED_FEATURE_REGISTRY_KEY =
            RegistryKeys.PLACED_FEATURE;

    /** Gets a configured feature by ID from the provided registry. */
    public static Optional<ConfiguredFeature<?, ?>> getConfiguredFeature(
            @NotNull Registry<ConfiguredFeature<?, ?>> registry, @NotNull String id) {
        return Optional.ofNullable(registry.get(IdentifierHelper.of(id)));
    }

    /** Gets a placed feature by ID from the provided registry. */
    public static Optional<PlacedFeature> getPlacedFeature(
            @NotNull Registry<PlacedFeature> registry, @NotNull String id) {
        return Optional.ofNullable(registry.get(IdentifierHelper.of(id)));
    }

    /** Checks if a configured feature exists in the provided registry. */
    public static boolean isConfiguredFeatureRegistered(
            @NotNull Registry<ConfiguredFeature<?, ?>> registry, @NotNull String id) {
        return registry.containsId(IdentifierHelper.of(id));
    }

    /** Checks if a placed feature exists in the provided registry. */
    public static boolean isPlacedFeatureRegistered(@NotNull Registry<PlacedFeature> registry,
            @NotNull String id) {
        return registry.containsId(IdentifierHelper.of(id));
    }
}
