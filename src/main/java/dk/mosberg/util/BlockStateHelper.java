package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Utility for block state operations. Provides methods for getting and setting block state
 * properties safely.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * BlockState state = world.getBlockState(pos);
 * boolean powered = BlockStateHelper.getBoolean(state, Properties.POWERED, false);
 * state = BlockStateHelper.withBoolean(state, Properties.POWERED, true);
 * </pre>
 */
public final class BlockStateHelper {
    private BlockStateHelper() {}

    /**
     * Gets a boolean property value from a block state.
     *
     * @param state the block state
     * @param property the property to get
     * @param defaultValue the default value if property doesn't exist
     * @return the property value or default
     */
    public static boolean getBoolean(@NotNull BlockState state, @NotNull BooleanProperty property,
            boolean defaultValue) {
        if (state.contains(property)) {
            return state.get(property);
        }
        return defaultValue;
    }

    /**
     * Gets an integer property value from a block state.
     *
     * @param state the block state
     * @param property the property to get
     * @param defaultValue the default value if property doesn't exist
     * @return the property value or default
     */
    public static int getInt(@NotNull BlockState state, @NotNull IntProperty property,
            int defaultValue) {
        if (state.contains(property)) {
            return state.get(property);
        }
        return defaultValue;
    }

    /**
     * Creates a new block state with a boolean property set.
     *
     * @param state the original block state
     * @param property the property to set
     * @param value the value to set
     * @return the new block state, or original if property doesn't exist
     */
    @NotNull
    public static BlockState withBoolean(@NotNull BlockState state,
            @NotNull BooleanProperty property, boolean value) {
        if (state.contains(property)) {
            return state.with(property, value);
        }
        return state;
    }

    /**
     * Creates a new block state with an integer property set.
     *
     * @param state the original block state
     * @param property the property to set
     * @param value the value to set
     * @return the new block state, or original if property doesn't exist
     */
    @NotNull
    public static BlockState withInt(@NotNull BlockState state, @NotNull IntProperty property,
            int value) {
        if (state.contains(property)) {
            // Get valid values and clamp
            java.util.Collection<Integer> values = property.getValues();
            if (!values.isEmpty()) {
                int min = values.stream().min((a, b) -> Integer.compare(a, b)).orElse(value);
                int max = values.stream().max((a, b) -> Integer.compare(a, b)).orElse(value);
                int clamped = Math.max(min, Math.min(max, value));
                return state.with(property, clamped);
            }
            return state.with(property, value);
        }
        return state;
    }

    /**
     * Cycles a property to its next value.
     *
     * @param state the block state
     * @param property the property to cycle
     * @param <T> the property type
     * @return the new block state with cycled property, or original if property doesn't exist
     */
    @NotNull
    public static <T extends Comparable<T>> BlockState cycle(@NotNull BlockState state,
            @NotNull Property<T> property) {
        if (state.contains(property)) {
            return state.cycle(property);
        }
        return state;
    }

    /**
     * Checks if a block state has a specific property.
     *
     * @param state the block state
     * @param property the property to check
     * @return true if the state has this property
     */
    public static boolean hasProperty(@NotNull BlockState state, @NotNull Property<?> property) {
        return state.contains(property);
    }

    /**
     * Gets the block from a block state.
     *
     * @param state the block state
     * @return the block
     */
    @NotNull
    public static Block getBlock(@NotNull BlockState state) {
        return state.getBlock();
    }

    /**
     * Checks if two block states are of the same block type (ignoring properties).
     *
     * @param state1 first block state
     * @param state2 second block state
     * @return true if both states are the same block
     */
    public static boolean isSameBlock(@NotNull BlockState state1, @NotNull BlockState state2) {
        return state1.getBlock() == state2.getBlock();
    }

    /**
     * Updates a block state in the world safely.
     *
     * @param world the world
     * @param pos the position
     * @param newState the new block state
     * @param flags the update flags
     * @return true if the update succeeded
     */
    public static boolean updateState(@NotNull World world, @NotNull BlockPos pos,
            @NotNull BlockState newState, int flags) {
        return world.setBlockState(pos, newState, flags);
    }

    /**
     * Updates a block state in the world with default flags (notify + update).
     *
     * @param world the world
     * @param pos the position
     * @param newState the new block state
     * @return true if the update succeeded
     */
    public static boolean updateState(@NotNull World world, @NotNull BlockPos pos,
            @NotNull BlockState newState) {
        return world.setBlockState(pos, newState, Block.NOTIFY_ALL);
    }
}
