package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

/**
 * Utility for registry operations. Provides methods for accessing Minecraft's built-in item and
 * block registries.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * Item diamond = RegistryHelper.getItem("minecraft:diamond");
 * if (RegistryHelper.isItemRegistered("modname:custom_item")) {
 *     // Item exists
 * }
 * </pre>
 */
public final class RegistryHelper {
    private RegistryHelper() {}

    /**
     * Gets an item from the item registry.
     *
     * @param id the item identifier (e.g., "minecraft:diamond")
     * @return the Item, or air if not found
     */
    @NotNull
    public static Item getItem(@NotNull String id) {
        return Registries.ITEM.get(IdentifierHelper.of(id));
    }

    /**
     * Checks if an item exists in the registry.
     *
     * @param id the item identifier
     * @return true if the item is registered
     */
    public static boolean isItemRegistered(@NotNull String id) {
        return Registries.ITEM.containsId(IdentifierHelper.of(id));
    }

    /**
     * Gets the total count of registered items.
     *
     * @return the number of registered items
     */
    public static int getItemCount() {
        return Registries.ITEM.size();
    }

    /**
     * Gets a block from the block registry.
     *
     * @param id the block identifier (e.g., "minecraft:diamond_block")
     * @return the Block, or air block if not found
     */
    @NotNull
    public static Block getBlock(@NotNull String id) {
        return Registries.BLOCK.get(IdentifierHelper.of(id));
    }

    /**
     * Checks if a block exists in the registry.
     *
     * @param id the block identifier
     * @return true if the block is registered
     */
    public static boolean isBlockRegistered(@NotNull String id) {
        return Registries.BLOCK.containsId(IdentifierHelper.of(id));
    }

    /**
     * Gets the total count of registered blocks.
     *
     * @return the number of registered blocks
     */
    public static int getBlockCount() {
        return Registries.BLOCK.size();
    }
}
