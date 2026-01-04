package dk.mosberg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * Unit tests for {@link ItemStackHelper} utilities.
 *
 * @since 1.0.0
 */
class ItemStackHelperTest {

    @Test
    void testOfWithStringIdentifier() {
        var stack = ItemStackHelper.of("minecraft:diamond", 1);
        assertNotNull(stack);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.getCount());
    }

    @Test
    void testOfWithItem() {
        var stack = ItemStackHelper.of(Items.DIAMOND, 64);
        assertNotNull(stack);
        assertFalse(stack.isEmpty());
        assertEquals(64, stack.getCount());
    }

    @Test
    void testIsEmpty() {
        var fullStack = ItemStackHelper.of("minecraft:diamond", 1);
        var emptyStack = ItemStack.EMPTY;

        assertFalse(ItemStackHelper.isEmpty(fullStack));
        assertTrue(ItemStackHelper.isEmpty(emptyStack));
    }

    @Test
    void testIsFull() {
        var diamond = Items.DIAMOND;
        var fullStack = ItemStackHelper.of("minecraft:diamond", diamond.getMaxCount());
        var partialStack = ItemStackHelper.of("minecraft:diamond", 1);

        assertTrue(ItemStackHelper.isFull(fullStack));
        assertFalse(ItemStackHelper.isFull(partialStack));
    }

    @Test
    void testMatches() {
        var diamondStack = ItemStackHelper.of("minecraft:diamond", 1);
        assertTrue(ItemStackHelper.matches(diamondStack, Items.DIAMOND));
        assertFalse(ItemStackHelper.matches(diamondStack, Items.GOLD_INGOT));
    }

    @Test
    void testGetRemainingSpace() {
        var diamond = Items.DIAMOND;
        var stack = ItemStackHelper.of("minecraft:diamond", 32);
        int remaining = ItemStackHelper.getRemainingSpace(stack);
        int maxCount = diamond.getMaxCount();

        assertEquals(maxCount - 32, remaining);
    }

    @Test
    void testGetRemainingSpaceForFullStack() {
        var diamond = Items.DIAMOND;
        var fullStack = ItemStackHelper.of("minecraft:diamond", diamond.getMaxCount());
        assertEquals(0, ItemStackHelper.getRemainingSpace(fullStack));
    }

    @Test
    void testOfThrowsOnNullIdentifier() {
        assertThrows(NullPointerException.class, () -> ItemStackHelper.of((String) null, 1));
    }

    @Test
    void testOfThrowsOnNullItem() {
        assertThrows(NullPointerException.class,
                () -> ItemStackHelper.of((net.minecraft.item.Item) null, 1));
    }

    @Test
    void testOfNegativeCount() {
        var stack = ItemStackHelper.of("minecraft:diamond", -1);
        assertTrue(stack.isEmpty(), "Negative count should result in empty stack");
    }
}
