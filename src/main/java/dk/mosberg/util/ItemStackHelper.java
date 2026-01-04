package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

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

    /**
     * Gets the durability of an item (max durability - current damage).
     *
     * <p>
     * For items without durability, returns -1.
     *
     * @param stack the ItemStack
     * @return the current durability, or -1 if not applicable
     */
    public static int getDurability(@NotNull ItemStack stack) {
        if (!stack.isDamaged()) {
            return stack.getMaxDamage();
        }
        return stack.getMaxDamage() - stack.getDamage();
    }

    /**
     * Gets the maximum durability of an item.
     *
     * @param stack the ItemStack
     * @return the maximum durability, or 0 if not applicable
     */
    public static int getMaxDurability(@NotNull ItemStack stack) {
        return stack.getMaxDamage();
    }

    /**
     * Checks if an item is at full durability.
     *
     * @param stack the ItemStack
     * @return true if not damaged or not damageable
     */
    public static boolean isFullDurability(@NotNull ItemStack stack) {
        return !stack.isDamaged();
    }

    /**
     * Repairs an item by reducing its damage.
     *
     * @param stack the ItemStack
     * @param amount the amount of durability to restore
     */
    public static void repair(@NotNull ItemStack stack, int amount) {
        int newDamage = Math.max(0, stack.getDamage() - amount);
        stack.setDamage(newDamage);
    }

    /**
     * Damages an item.
     *
     * @param stack the ItemStack
     * @param amount the amount of damage to apply
     */
    public static void damage(@NotNull ItemStack stack, int amount) {
        int newDamage = Math.min(stack.getMaxDamage(), stack.getDamage() + amount);
        stack.setDamage(newDamage);
    }

    /**
     * Checks if two items are of the same type (ignoring count and NBT).
     *
     * <p>
     * This is already provided by the isSameItem method, so this is an alias for convenience when
     * dealing with more granular item matching.
     *
     * @param stack1 the first ItemStack
     * @param stack2 the second ItemStack
     * @return true if both stacks contain the same item
     */
    public static boolean canStack(@NotNull ItemStack stack1, @NotNull ItemStack stack2) {
        return isSameItem(stack1, stack2);
    }

    /**
     * Gets the display name of an item.
     *
     * @param stack the ItemStack
     * @return the display name or the item's default name
     */
    @NotNull
    public static String getDisplayName(@NotNull ItemStack stack) {
        return stack.getName().getString();
    }

    /**
     * Checks if an item has custom NBT data.
     *
     * @param stack the ItemStack
     * @return true if the item has custom NBT data
     */
    public static boolean hasCustomNBT(@NotNull ItemStack stack) {
        return !stack.getComponents().isEmpty();
    }

    /**
     * Copies an ItemStack completely (including NBT and count).
     *
     * @param stack the ItemStack to copy
     * @return a new ItemStack with the same properties
     */
    @NotNull
    public static ItemStack copy(@NotNull ItemStack stack) {
        return stack.copy();
    }

    /**
     * Gets the weight/value of an item (based on stack count and item type).
     *
     * @param stack the ItemStack
     * @return a simple weight metric (count * rarity)
     */
    public static int getWeight(@NotNull ItemStack stack) {
        // Simple weight calculation: count * (1 for common items)
        // This can be enhanced based on item properties
        return stack.getCount();
    }

    /**
     * Adds items to a stack, respecting max stack size.
     *
     * @param stack the ItemStack to add to
     * @param amount the amount to add
     * @return the actual amount added
     */
    public static int add(@NotNull ItemStack stack, int amount) {
        int canAdd = getRemainingSpace(stack);
        int actualAdd = Math.min(amount, canAdd);
        stack.increment(actualAdd);
        return actualAdd;
    }

    /**
     * Removes items from a stack.
     *
     * @param stack the ItemStack to remove from
     * @param amount the amount to remove
     * @return the actual amount removed
     */
    public static int remove(@NotNull ItemStack stack, int amount) {
        int canRemove = Math.min(amount, stack.getCount());
        stack.decrement(canRemove);
        return canRemove;
    }

    /**
     * Completely restores the durability of an item to maximum.
     *
     * @param stack the ItemStack to restore
     */
    public static void restoreDurability(@NotNull ItemStack stack) {
        stack.setDamage(0);
    }

    /**
     * Breaks an item completely (sets durability to 0).
     *
     * @param stack the ItemStack to break
     */
    public static void breakItem(@NotNull ItemStack stack) {
        stack.setDamage(stack.getMaxDamage());
    }

    /**
     * Gets the durability percentage (0.0 to 1.0).
     *
     * @param stack the ItemStack
     * @return the durability as a percentage, or 1.0 if not damageable
     */
    public static double getDurabilityPercent(@NotNull ItemStack stack) {
        if (stack.getMaxDamage() == 0) {
            return 1.0;
        }
        return (double) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage();
    }

    // =============================================================================================
    // Enchantment Operations
    // =============================================================================================

    /**
     * Gets the level of a specific enchantment on an item.
     *
     * @param stack the ItemStack
     * @param enchantment the enchantment to check
     * @return the enchantment level, or 0 if not present
     */
    public static int getEnchantmentLevel(@NotNull ItemStack stack,
            @NotNull RegistryEntry<Enchantment> enchantment) {
        return EnchantmentHelper.getLevel(enchantment, stack);
    }

    /**
     * Checks if an item has a specific enchantment.
     *
     * @param stack the ItemStack
     * @param enchantment the enchantment to check for
     * @return true if the enchantment is present
     */
    public static boolean hasEnchantment(@NotNull ItemStack stack,
            @NotNull RegistryEntry<Enchantment> enchantment) {
        return getEnchantmentLevel(stack, enchantment) > 0;
    }

    /**
     * Checks if an item has any enchantments.
     *
     * @param stack the ItemStack
     * @return true if the item has at least one enchantment
     */
    public static boolean hasAnyEnchantments(@NotNull ItemStack stack) {
        ItemEnchantmentsComponent enchantments = EnchantmentHelper.getEnchantments(stack);
        return !enchantments.isEmpty();
    }

    /**
     * Gets the number of enchantments on an item.
     *
     * @param stack the ItemStack
     * @return the count of unique enchantments
     */
    public static int getEnchantmentCount(@NotNull ItemStack stack) {
        ItemEnchantmentsComponent enchantments = EnchantmentHelper.getEnchantments(stack);
        return enchantments.getSize();
    }

    // =============================================================================================
    // Advanced Comparison Operations
    // =============================================================================================

    /**
     * Checks if two ItemStacks are similar (same item, same NBT, ignoring count).
     *
     * @param stack1 first ItemStack
     * @param stack2 second ItemStack
     * @return true if item type and NBT match
     */
    public static boolean isSimilar(@NotNull ItemStack stack1, @NotNull ItemStack stack2) {
        return ItemStack.areItemsAndComponentsEqual(stack1, stack2);
    }

    /**
     * Checks if two ItemStacks are equal ignoring stack count.
     *
     * @param stack1 first ItemStack
     * @param stack2 second ItemStack
     * @return true if items and components match (count ignored)
     */
    public static boolean equalsIgnoreCount(@NotNull ItemStack stack1, @NotNull ItemStack stack2) {
        return isSimilar(stack1, stack2);
    }

    /**
     * Checks if two ItemStacks are exactly equal (including count).
     *
     * @param stack1 first ItemStack
     * @param stack2 second ItemStack
     * @return true if all properties match
     */
    public static boolean equals(@NotNull ItemStack stack1, @NotNull ItemStack stack2) {
        return ItemStack.areEqual(stack1, stack2);
    }

    /**
     * Compares two ItemStacks for sorting purposes.
     *
     * <p>
     * Comparison order:
     * <ol>
     * <li>Item registry ID (alphabetically)</li>
     * <li>Count (descending)</li>
     * <li>Damage (ascending)</li>
     * </ol>
     *
     * @param stack1 first ItemStack
     * @param stack2 second ItemStack
     * @return negative if stack1 &lt; stack2, positive if stack1 &gt; stack2, zero if equal
     */
    public static int compare(@NotNull ItemStack stack1, @NotNull ItemStack stack2) {
        // Empty stacks always sort last
        if (stack1.isEmpty() && stack2.isEmpty())
            return 0;
        if (stack1.isEmpty())
            return 1;
        if (stack2.isEmpty())
            return -1;

        // Compare by item ID
        String id1 = Registries.ITEM.getId(stack1.getItem()).toString();
        String id2 = Registries.ITEM.getId(stack2.getItem()).toString();
        int idCompare = id1.compareTo(id2);
        if (idCompare != 0)
            return idCompare;

        // Same item - compare by count (descending)
        int countCompare = Integer.compare(stack2.getCount(), stack1.getCount());
        if (countCompare != 0)
            return countCompare;

        // Same count - compare by damage (ascending)
        return Integer.compare(stack1.getDamage(), stack2.getDamage());
    }

    // =============================================================================================
    // Utility Methods
    // =============================================================================================

    /**
     * Checks if an item can accept a specific enchantment.
     *
     * @param stack the ItemStack
     * @param enchantment the enchantment to check
     * @return true if the enchantment is compatible
     */
    public static boolean canAcceptEnchantment(@NotNull ItemStack stack,
            @NotNull RegistryEntry<Enchantment> enchantment) {
        return enchantment.value().isAcceptableItem(stack);
    }

    /**
     * Gets the rarity of an item stack.
     *
     * @param stack the ItemStack
     * @return the rarity as a string ("common", "uncommon", "rare", "epic")
     */
    @NotNull
    public static String getRarity(@NotNull ItemStack stack) {
        return stack.getRarity().toString().toLowerCase();
    }

    /**
     * Checks if an item is a tool (has durability and can break blocks).
     *
     * @param stack the ItemStack
     * @return true if the item is a tool
     */
    public static boolean isTool(@NotNull ItemStack stack) {
        return stack.getMaxDamage() > 0 && stack.isDamageable();
    }

    /**
     * Checks if an item is enchantable.
     *
     * @param stack the ItemStack
     * @return true if the item can receive enchantments
     */
    public static boolean isEnchantable(@NotNull ItemStack stack) {
        return stack.isEnchantable();
    }
}

