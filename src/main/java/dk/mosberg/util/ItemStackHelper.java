package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

/**
 * Utility for ItemStack operations. Provides methods for creating, checking, and manipulating
 * ItemStacks.
 */
public final class ItemStackHelper {
    private ItemStackHelper() {}

    /**
     * Creates an ItemStack from an item ID string.
     *
     * @param itemId the item identifier (e.g., "minecraft:diamond")
     * @param count the stack count (1-64)
     * @return a new ItemStack
     */
    @NotNull
    public static ItemStack of(@NotNull String itemId, int count) {
        var item = Registries.ITEM.get(IdentifierHelper.of(itemId));
        return new ItemStack(item, count);
    }

    /**
     * Creates an ItemStack from an Item instance.
     *
     * @param item the Item
     * @param count the stack count
     * @return a new ItemStack
     */
    @NotNull
    public static ItemStack of(@NotNull Item item, int count) {
        return new ItemStack(item, count);
    }

    /**
     * Checks if an ItemStack is empty.
     *
     * @param stack the ItemStack
     * @return true if empty
     */
    public static boolean isEmpty(@NotNull ItemStack stack) {
        return stack.isEmpty();
    }

    /**
     * Checks if an ItemStack is at maximum capacity.
     *
     * @param stack the ItemStack
     * @return true if at max count
     */
    public static boolean isFull(@NotNull ItemStack stack) {
        return stack.getCount() >= stack.getMaxCount();
    }

    /**
     * Gets the maximum stack size for the item type.
     *
     * @param stack the ItemStack
     * @return maximum stack count
     */
    public static int getMaxCount(@NotNull ItemStack stack) {
        return stack.getMaxCount();
    }

    /**
     * Checks if two stacks contain the same item (ignores count and NBT).
     *
     * @param stack1 first ItemStack
     * @param stack2 second ItemStack
     * @return true if both contain the same item
     */
    public static boolean isSameItem(@NotNull ItemStack stack1, @NotNull ItemStack stack2) {
        return stack1.getItem() == stack2.getItem();
    }

    /**
     * Checks if the stack contains a specific item type.
     *
     * @param stack the ItemStack
     * @param item the item to check for
     * @return true if the stack contains this item
     */
    public static boolean matches(@NotNull ItemStack stack, @NotNull Item item) {
        return stack.getItem() == item;
    }

    /**
     * Gets the remaining space in the stack.
     *
     * @param stack the ItemStack
     * @return how many more items can fit
     */
    public static int getRemainingSpace(@NotNull ItemStack stack) {
        if (stack.isEmpty()) {
            return stack.getMaxCount();
        }
        return stack.getMaxCount() - stack.getCount();
    }
}
