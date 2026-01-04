package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Utility for inventory operations. Provides methods for searching, adding, and removing items from
 * inventories.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * PlayerInventory inv = player.getInventory();
 * int count = InventoryHelper.count(inv, Items.DIAMOND);
 * boolean hasSpace = InventoryHelper.hasSpace(inv, stack);
 * InventoryHelper.addItem(inv, stack);
 * </pre>
 */
public final class InventoryHelper {
    private InventoryHelper() {}

    /**
     * Counts how many of a specific item are in the inventory.
     *
     * @param inventory the inventory to search
     * @param item the item to count
     * @return the total count of the item
     */
    public static int count(@NotNull Inventory inventory, @NotNull Item item) {
        int total = 0;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                total += stack.getCount();
            }
        }
        return total;
    }

    /**
     * Counts how many items matching the given stack are in the inventory (same item and NBT).
     *
     * @param inventory the inventory to search
     * @param stack the stack to match
     * @return the total count of matching items
     */
    public static int countMatching(@NotNull Inventory inventory, @NotNull ItemStack stack) {
        int total = 0;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack invStack = inventory.getStack(i);
            if (ItemStack.areItemsAndComponentsEqual(stack, invStack)) {
                total += invStack.getCount();
            }
        }
        return total;
    }

    /**
     * Checks if the inventory contains at least the specified amount of an item.
     *
     * @param inventory the inventory to check
     * @param item the item to look for
     * @param amount the minimum amount required
     * @return true if the inventory has at least the specified amount
     */
    public static boolean contains(@NotNull Inventory inventory, @NotNull Item item, int amount) {
        return count(inventory, item) >= amount;
    }

    /**
     * Checks if the inventory has space for the given stack.
     *
     * @param inventory the inventory to check
     * @param stack the stack to test
     * @return true if the stack can be fully added
     */
    public static boolean hasSpace(@NotNull Inventory inventory, @NotNull ItemStack stack) {
        int remaining = stack.getCount();

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack invStack = inventory.getStack(i);

            if (invStack.isEmpty()) {
                remaining -= stack.getMaxCount();
            } else if (ItemStack.areItemsAndComponentsEqual(stack, invStack)) {
                int spaceInSlot = invStack.getMaxCount() - invStack.getCount();
                remaining -= spaceInSlot;
            }

            if (remaining <= 0) {
                return true;
            }
        }

        return remaining <= 0;
    }

    /**
     * Finds the first slot containing the specified item.
     *
     * @param inventory the inventory to search
     * @param item the item to find
     * @return the slot index, or -1 if not found
     */
    public static int findSlot(@NotNull Inventory inventory, @NotNull Item item) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the first empty slot in the inventory.
     *
     * @param inventory the inventory to search
     * @return the slot index, or -1 if no empty slots
     */
    public static int findEmptySlot(@NotNull Inventory inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getStack(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Attempts to add an item stack to the inventory.
     *
     * @param inventory the inventory to add to
     * @param stack the stack to add
     * @return true if the stack was fully added, false if partially or not added
     */
    public static boolean addItem(@NotNull Inventory inventory, @NotNull ItemStack stack) {
        ItemStack remaining = stack.copy();

        // Try to merge with existing stacks first
        for (int i = 0; i < inventory.size() && !remaining.isEmpty(); i++) {
            ItemStack invStack = inventory.getStack(i);

            if (!invStack.isEmpty() && ItemStack.areItemsAndComponentsEqual(stack, invStack)) {
                int spaceInSlot = invStack.getMaxCount() - invStack.getCount();
                if (spaceInSlot > 0) {
                    int toAdd = Math.min(spaceInSlot, remaining.getCount());
                    invStack.increment(toAdd);
                    remaining.decrement(toAdd);
                }
            }
        }

        // Add to empty slots
        for (int i = 0; i < inventory.size() && !remaining.isEmpty(); i++) {
            if (inventory.getStack(i).isEmpty()) {
                int toAdd = Math.min(remaining.getMaxCount(), remaining.getCount());
                ItemStack newStack = remaining.copy();
                newStack.setCount(toAdd);
                inventory.setStack(i, newStack);
                remaining.decrement(toAdd);
            }
        }

        return remaining.isEmpty();
    }

    /**
     * Removes a specified amount of an item from the inventory.
     *
     * @param inventory the inventory to remove from
     * @param item the item to remove
     * @param amount the amount to remove
     * @return the actual amount removed
     */
    public static int removeItem(@NotNull Inventory inventory, @NotNull Item item, int amount) {
        int remaining = amount;

        for (int i = 0; i < inventory.size() && remaining > 0; i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                int toRemove = Math.min(remaining, stack.getCount());
                stack.decrement(toRemove);
                if (stack.isEmpty()) {
                    inventory.setStack(i, ItemStack.EMPTY);
                }
                remaining -= toRemove;
            }
        }

        return amount - remaining;
    }

    /**
     * Clears all items from the inventory.
     *
     * @param inventory the inventory to clear
     */
    public static void clear(@NotNull Inventory inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            inventory.setStack(i, ItemStack.EMPTY);
        }
    }

    /**
     * Checks if the inventory is completely empty.
     *
     * @param inventory the inventory to check
     * @return true if all slots are empty
     */
    public static boolean isEmpty(@NotNull Inventory inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            if (!inventory.getStack(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the total number of occupied slots in the inventory.
     *
     * @param inventory the inventory to check
     * @return the number of non-empty slots
     */
    public static int getOccupiedSlotCount(@NotNull Inventory inventory) {
        int count = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (!inventory.getStack(i).isEmpty()) {
                count++;
            }
        }
        return count;
    }
}
