package dk.mosberg.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

/**
 * Read-only helpers for interacting with the server recipe manager.
 */
public final class RecipeHelper {
    private RecipeHelper() {}

    /**
     * Fetches a recipe by identifier.
     *
     * @param manager the recipe manager (e.g., from ServerWorld or MinecraftServer)
     * @param id the recipe identifier string (namespace:path)
     * @return optional recipe if present
     */
    public static Optional<RecipeEntry<?>> getRecipe(@NotNull ServerRecipeManager manager,
            @NotNull String id) {
        RegistryKey<Recipe<?>> key = RegistryKey.of(RegistryKeys.RECIPE, IdentifierHelper.of(id));
        return manager.get(key);
    }

    /**
     * Checks if a recipe exists.
     *
     * @param manager the recipe manager
     * @param id the recipe identifier
     * @return true if present
     */
    public static boolean hasRecipe(@NotNull ServerRecipeManager manager, @NotNull String id) {
        return getRecipe(manager, id).isPresent();
    }

    /**
     * Retrieves all recipes of a given type.
     *
     * @param manager the recipe manager
     * @param type the recipe type
     * @return immutable list of recipes of the given type
     */
    @SuppressWarnings({"unchecked", "null"})
    public static <I extends RecipeInput, T extends Recipe<I>> List<RecipeEntry<T>> getAllOfType(
            @NotNull ServerRecipeManager manager, @NotNull RecipeType<T> type) {
        Stream<RecipeEntry<?>> stream = manager.values().stream();
        return stream.filter(entry -> entry.value().getType().equals(type))
                .map(entry -> (RecipeEntry<T>) entry).toList();
    }
}
