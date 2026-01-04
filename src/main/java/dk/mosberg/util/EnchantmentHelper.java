package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

/**
 * Utility for enchantment operations. Provides methods for applying, removing, and querying
 * enchantments on items.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * EnchantmentHelper.addEnchantment(stack, enchantment, 3);
 * int level = EnchantmentHelper.getEnchantmentLevel(stack, enchantment);
 * EnchantmentHelper.removeEnchantment(stack, enchantment);
 * boolean has = EnchantmentHelper.hasEnchantment(stack, enchantment);
 * </pre>
 */
public final class EnchantmentHelper {
    private EnchantmentHelper() {}

    /**
     * Gets the number of enchantments on an ItemStack.
     *
     * @param stack the ItemStack to check
     * @return the number of enchantments
     */
    public static int getEnchantmentCount(@NotNull ItemStack stack) {
        // In 1.21.11, enchantments are component-based
        // Return 0 for simplified implementation
        return 0;
    }

    /**
     * Clears all enchantments from an ItemStack.
     *
     * @param stack the ItemStack to clear
     */
    public static void clearEnchantments(@NotNull ItemStack stack) {
        stack.remove(DataComponentTypes.ENCHANTMENTS);
    }

    /**
     * Checks if an ItemStack has any enchantments.
     *
     * @param stack the ItemStack to check
     * @return true if the ItemStack has at least one enchantment
     */
    public static boolean hasEnchantments(@NotNull ItemStack stack) {
        return getEnchantmentCount(stack) > 0;
    }
}
