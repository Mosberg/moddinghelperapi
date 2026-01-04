package dk.mosberg.util;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Utility for dropping items in the world. Provides methods for spawning ItemEntities with various
 * drop patterns and velocities.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * // Drop a single item
 * DropHelper.dropItem(world, pos, new ItemStack(Items.DIAMOND));
 *
 * // Drop multiple items in an explosion pattern
 * DropHelper.dropExplosion(world, pos, stacks);
 *
 * // Drop items with custom velocity
 * DropHelper.dropWithVelocity(world, pos, stack, new Vec3d(0, 0.3, 0));
 * </pre>
 */
public final class DropHelper {
    private DropHelper() {}

    /**
     * Drops an ItemStack at a position in the world.
     *
     * @param world the world to drop in
     * @param pos the position to drop at
     * @param stack the ItemStack to drop
     * @return the ItemEntity created
     */
    @NotNull
    public static ItemEntity dropItem(@NotNull World world, @NotNull Vec3d pos,
            @NotNull ItemStack stack) {
        if (stack.isEmpty()) {
            var emptyStack = new ItemStack(net.minecraft.item.Items.AIR);
            return new ItemEntity(world, pos.x, pos.y, pos.z, emptyStack);
        }
        var itemEntity = new ItemEntity(world, pos.x, pos.y, pos.z, stack.copy());
        itemEntity.setPickupDelay(10);
        world.spawnEntity(itemEntity);
        return itemEntity;
    }

    /**
     * Drops multiple ItemStacks at a position.
     *
     * @param world the world to drop in
     * @param pos the position to drop at
     * @param stacks the ItemStacks to drop
     */
    public static void dropItems(@NotNull World world, @NotNull Vec3d pos,
            @NotNull List<ItemStack> stacks) {
        for (var stack : stacks) {
            if (!stack.isEmpty()) {
                dropItem(world, pos, stack);
            }
        }
    }

    /**
     * Drops items in an explosion pattern (radiating outward).
     *
     * @param world the world to drop in
     * @param pos the center position
     * @param stacks the ItemStacks to drop
     */
    public static void dropExplosion(@NotNull World world, @NotNull Vec3d pos,
            @NotNull List<ItemStack> stacks) {
        int count = 0;
        for (var stack : stacks) {
            if (!stack.isEmpty()) {
                var itemEntity = dropItem(world, pos.add(0, 0.5, 0), stack);
                // Apply outward velocity
                double angle = (2 * Math.PI * count) / Math.max(1, stacks.size());
                double speed = 0.3;
                double vx = Math.cos(angle) * speed;
                double vz = Math.sin(angle) * speed;
                itemEntity.setVelocity(vx, 0.2, vz);
                count++;
            }
        }
    }

    /**
     * Drops an item with a specific velocity.
     *
     * @param world the world to drop in
     * @param pos the position to drop at
     * @param stack the ItemStack to drop
     * @param velocity the velocity to apply
     * @return the ItemEntity created
     */
    @NotNull
    public static ItemEntity dropWithVelocity(@NotNull World world, @NotNull Vec3d pos,
            @NotNull ItemStack stack, @NotNull Vec3d velocity) {
        var itemEntity = dropItem(world, pos, stack);
        itemEntity.setVelocity(velocity);
        return itemEntity;
    }

    /**
     * Drops items in a circular pattern around a point.
     *
     * @param world the world to drop in
     * @param pos the center position
     * @param stacks the ItemStacks to drop
     * @param radius the radius of the circle
     */
    public static void dropCircle(@NotNull World world, @NotNull Vec3d pos,
            @NotNull List<ItemStack> stacks, double radius) {
        int count = 0;
        for (var stack : stacks) {
            if (!stack.isEmpty()) {
                double angle = (2 * Math.PI * count) / Math.max(1, stacks.size());
                double x = pos.x + Math.cos(angle) * radius;
                double z = pos.z + Math.sin(angle) * radius;
                var dropPos = new Vec3d(x, pos.y, z);
                dropItem(world, dropPos, stack);
                count++;
            }
        }
    }

    /**
     * Drops items in a column stack (stacking on top of each other).
     *
     * @param world the world to drop in
     * @param pos the position to drop at
     * @param stacks the ItemStacks to drop
     * @param verticalSpacing the spacing between items vertically
     */
    public static void dropStack(@NotNull World world, @NotNull Vec3d pos,
            @NotNull List<ItemStack> stacks, double verticalSpacing) {
        for (int i = 0; i < stacks.size(); i++) {
            var stack = stacks.get(i);
            if (!stack.isEmpty()) {
                var dropPos = pos.add(0, i * verticalSpacing, 0);
                dropItem(world, dropPos, stack);
            }
        }
    }

    /**
     * Drops a single item with a slight upward velocity (like a block drop).
     *
     * @param world the world to drop in
     * @param pos the position to drop at
     * @param stack the ItemStack to drop
     * @return the ItemEntity created
     */
    @NotNull
    public static ItemEntity dropBlock(@NotNull World world, @NotNull Vec3d pos,
            @NotNull ItemStack stack) {
        return dropWithVelocity(world, pos.add(0.5, 0.5, 0.5), stack, new Vec3d(0, 0.2, 0));
    }

    /**
     * Combines multiple stacks into a single stack if possible, dropping overflow.
     *
     * @param world the world to drop overflow in
     * @param targetStack the stack to combine into
     * @param sourceStack the stack to combine from
     * @return the combined stack
     */
    @NotNull
    public static ItemStack combine(@NotNull World world, @NotNull ItemStack targetStack,
            @NotNull ItemStack sourceStack) {
        if (ItemStackHelper.isSameItem(targetStack, sourceStack)) {
            int canAdd = ItemStackHelper.getRemainingSpace(targetStack);
            int toAdd = Math.min(canAdd, sourceStack.getCount());
            targetStack.increment(toAdd);
            sourceStack.decrement(toAdd);

            if (!sourceStack.isEmpty()) {
                dropItem(world, new Vec3d(0, 0, 0), sourceStack);
            }
        }
        return targetStack;
    }
}
