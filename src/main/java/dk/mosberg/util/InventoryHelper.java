package dk.mosberg.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

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

    // =============================================================================================
    // Sorting Operations
    // =============================================================================================

    /**
     * Sorts the inventory using the default comparator (by item ID, count, damage).
     *
     * @param inventory the inventory to sort
     */
    public static void sort(@NotNull Inventory inventory) {
        sortWith(inventory, ItemStackHelper::compare);
    }

    /**
     * Sorts the inventory by item type (registry ID).
     *
     * @param inventory the inventory to sort
     */
    public static void sortByType(@NotNull Inventory inventory) {
        sortWith(inventory, (s1, s2) -> {
            if (s1.isEmpty() && s2.isEmpty())
                return 0;
            if (s1.isEmpty())
                return 1;
            if (s2.isEmpty())
                return -1;

            String id1 = Registries.ITEM.getId(s1.getItem()).toString();
            String id2 = Registries.ITEM.getId(s2.getItem()).toString();
            return id1.compareTo(id2);
        });
    }

    /**
     * Sorts the inventory by display name alphabetically.
     *
     * @param inventory the inventory to sort
     */
    public static void sortByName(@NotNull Inventory inventory) {
        sortWith(inventory, (s1, s2) -> {
            if (s1.isEmpty() && s2.isEmpty())
                return 0;
            if (s1.isEmpty())
                return 1;
            if (s2.isEmpty())
                return -1;

            String name1 = s1.getName().getString();
            String name2 = s2.getName().getString();
            return name1.compareTo(name2);
        });
    }

    /**
     * Sorts the inventory by stack count (descending).
     *
     * @param inventory the inventory to sort
     */
    public static void sortByCount(@NotNull Inventory inventory) {
        sortWith(inventory, (s1, s2) -> {
            if (s1.isEmpty() && s2.isEmpty())
                return 0;
            if (s1.isEmpty())
                return 1;
            if (s2.isEmpty())
                return -1;

            return Integer.compare(s2.getCount(), s1.getCount());
        });
    }

    /**
     * Sorts the inventory using a custom comparator.
     *
     * @param inventory the inventory to sort
     * @param comparator the comparator to use
     */
    public static void sortWith(@NotNull Inventory inventory,
            @NotNull Comparator<ItemStack> comparator) {
        List<ItemStack> stacks = new ArrayList<>();

        // Collect all stacks
        for (int i = 0; i < inventory.size(); i++) {
            stacks.add(inventory.getStack(i).copy());
        }

        // Sort
        stacks.sort(comparator);

        // Put back
        for (int i = 0; i < inventory.size() && i < stacks.size(); i++) {
            inventory.setStack(i, stacks.get(i));
        }
    }

    // =============================================================================================
    // Bulk Operations
    // =============================================================================================

    /**
     * Moves all items from one inventory to another.
     *
     * @param from the source inventory
     * @param to the destination inventory
     * @return the number of items successfully moved
     */
    public static int moveAll(@NotNull Inventory from, @NotNull Inventory to) {
        int moved = 0;

        for (int i = 0; i < from.size(); i++) {
            ItemStack stack = from.getStack(i);
            if (!stack.isEmpty()) {
                boolean added = addItem(to, stack.copy());
                if (added) {
                    moved += stack.getCount();
                    from.setStack(i, ItemStack.EMPTY);
                }
            }
        }

        return moved;
    }

    /**
     * Clears all items matching a predicate from the inventory.
     *
     * @param inventory the inventory to clear from
     * @param filter the predicate to match items
     * @return the number of items removed
     */
    public static int clearMatching(@NotNull Inventory inventory,
            @NotNull Predicate<ItemStack> filter) {
        int removed = 0;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && filter.test(stack)) {
                removed += stack.getCount();
                inventory.setStack(i, ItemStack.EMPTY);
            }
        }

        return removed;
    }

    /**
     * Counts all items matching a predicate in the inventory.
     *
     * @param inventory the inventory to search
     * @param filter the predicate to match items
     * @return the total count of matching items
     */
    public static int countMatchingWith(@NotNull Inventory inventory,
            @NotNull Predicate<ItemStack> filter) {
        int count = 0;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && filter.test(stack)) {
                count += stack.getCount();
            }
        }

        return count;
    }

    /**
     * Transfers a specific item from one inventory to another.
     *
     * @param from the source inventory
     * @param to the destination inventory
     * @param slot the slot in the source inventory
     * @return true if the transfer was successful
     */
    public static boolean transferItem(@NotNull Inventory from, @NotNull Inventory to, int slot) {
        if (slot < 0 || slot >= from.size()) {
            return false;
        }

        ItemStack stack = from.getStack(slot);
        if (stack.isEmpty()) {
            return false;
        }

        boolean added = addItem(to, stack.copy());
        if (added) {
            from.setStack(slot, ItemStack.EMPTY);
            return true;
        }

        return false;
    }

    // =============================================================================================
    // Distribution Operations
    // =============================================================================================

    /**
     * Distributes items evenly across all empty slots in the inventory.
     *
     * @param inventory the inventory to distribute to
     * @param source the ItemStack to distribute
     * @param totalCount the total number of items to distribute
     * @return the number of items actually distributed
     */
    public static int distributeEvenly(@NotNull Inventory inventory, @NotNull ItemStack source,
            int totalCount) {
        List<Integer> emptySlots = new ArrayList<>();

        // Find empty slots
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getStack(i).isEmpty()) {
                emptySlots.add(i);
            }
        }

        if (emptySlots.isEmpty()) {
            return 0;
        }

        int distributed = 0;
        int perSlot = totalCount / emptySlots.size();
        int remainder = totalCount % emptySlots.size();

        for (int i = 0; i < emptySlots.size() && distributed < totalCount; i++) {
            int slot = emptySlots.get(i);
            int count = perSlot + (i < remainder ? 1 : 0);
            count = Math.min(count, source.getMaxCount());

            ItemStack newStack = source.copy();
            newStack.setCount(count);
            inventory.setStack(slot, newStack);
            distributed += count;
        }

        return distributed;
    }

    /**
     * Fills the target inventory from a source inventory until target is full or source is empty.
     *
     * @param target the inventory to fill
     * @param source the inventory to take from
     * @return the number of items transferred
     */
    public static int fillFrom(@NotNull Inventory target, @NotNull Inventory source) {
        int transferred = 0;

        for (int i = 0; i < source.size(); i++) {
            ItemStack stack = source.getStack(i);
            if (!stack.isEmpty()) {
                ItemStack copy = stack.copy();
                boolean added = addItem(target, copy);

                if (added) {
                    transferred += stack.getCount();
                    source.setStack(i, ItemStack.EMPTY);
                } else {
                    // Partially added - update source stack
                    int remaining = copy.getCount();
                    if (remaining < stack.getCount()) {
                        transferred += stack.getCount() - remaining;
                        stack.setCount(remaining);
                    }
                }
            }
        }

        return transferred;
    }

    /**
     * Compacts the inventory by merging similar stacks and removing empty slots.
     *
     * @param inventory the inventory to compact
     */
    public static void compact(@NotNull Inventory inventory) {
        List<ItemStack> stacks = new ArrayList<>();

        // Collect all non-empty stacks
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                stacks.add(stack.copy());
            }
        }

        // Clear inventory
        clear(inventory);

        // Re-add stacks (will merge automatically)
        for (ItemStack stack : stacks) {
            addItem(inventory, stack);
        }
    }

    /**
     * Gets the total weight/value of all items in the inventory.
     *
     * @param inventory the inventory to calculate
     * @return the total weight
     */
    public static int getTotalWeight(@NotNull Inventory inventory) {
        int weight = 0;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                weight += ItemStackHelper.getWeight(stack);
            }
        }

        return weight;
    }
}

