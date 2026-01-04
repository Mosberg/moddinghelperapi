package dk.mosberg.util;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

/**
 * Helpers for resolving and rolling loot tables on the server.
 */
public final class LootTableHelper {
    private LootTableHelper() {}

    /**
     * Resolves a loot table from the dynamic loot table registry.
     *
     * @param server the active server
     * @param id loot table identifier (namespace:path)
     * @return optional loot table if present
     */
    public static Optional<LootTable> getLootTable(@NotNull MinecraftServer server,
            @NotNull String id) {
        var identifier = IdentifierHelper.of(id);
        return server.getRegistryManager().getOptional(RegistryKeys.LOOT_TABLE)
                .flatMap(registry -> registry.getOptionalValue(identifier));
    }

    /**
     * Builds a loot world context builder for the provided world.
     */
    public static @NotNull LootWorldContext.Builder context(@NotNull ServerWorld world) {
        return new LootWorldContext.Builder(world);
    }

    /**
     * Rolls a loot table with the provided context.
     *
     * @param server the active server
     * @param id loot table identifier
     * @param context the world context (parameters + luck)
     * @return generated loot; empty if table missing or yields nothing
     */
    public static List<ItemStack> generateLoot(@NotNull MinecraftServer server, @NotNull String id,
            @NotNull LootWorldContext context) {
        return getLootTable(server, id)
                .map(table -> (ObjectArrayList<ItemStack>) table.generateLoot(context))
                .map(List::copyOf).orElse(List.of());
    }
}
