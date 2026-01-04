package dk.mosberg.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * Utility for working with redstone power and signal strength.
 *
 * <p>
 * Provides methods to query and manipulate redstone power levels, emit redstone signals, and detect
 * redstone wire states. Simplifies redstone power calculations and directional checks.
 *
 * <p>
 * <strong>Example:</strong>
 * </p>
 *
 * <pre>
 * World world = ...;
 * BlockPos pos = ...;
 *
 * // Get redstone power at a position
 * int power = RedstoneHelper.getRedstoneSignal(world, pos);
 *
 * // Check if redstone power is strong enough
 * if (RedstoneHelper.isPowered(world, pos, 8)) {
 *     // Do something
 * }
 *
 * // Check adjacent power from a specific direction
 * int westPower = RedstoneHelper.getAdjacentPower(world, pos, Direction.WEST);
 * </pre>
 */
public final class RedstoneHelper {
    private static final int MAX_POWER = 15;
    private static final int MIN_POWER = 0;

    private RedstoneHelper() {
        // Prevent instantiation
    }

    /**
     * Gets the redstone power level at a block position.
     *
     * <p>
     * This value represents the signal strength of redstone at that position, ranging from 0-15.
     *
     * @param world the world to query
     * @param pos the block position
     * @return the redstone power level (0-15), or 0 if no power
     * @throws NullPointerException if world or pos is null
     */
    public static int getRedstoneSignal(@NotNull World world, @NotNull BlockPos pos) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(pos);
        return Math.clamp(world.getEmittedRedstonePower(pos, null), MIN_POWER, MAX_POWER);
    }

    /**
     * Gets the maximum redstone power from all adjacent blocks.
     *
     * <p>
     * Checks redstone power from all six cardinal directions and returns the highest.
     *
     * @param world the world to query
     * @param pos the block position
     * @return the maximum redstone power from adjacent blocks (0-15)
     * @throws NullPointerException if world or pos is null
     */
    public static int getMaxAdjacentPower(@NotNull World world, @NotNull BlockPos pos) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(pos);
        int max = 0;
        for (Direction direction : Direction.values()) {
            max = Math.max(max, getAdjacentPower(world, pos, direction));
        }
        return max;
    }

    /**
     * Gets the redstone power from a specific adjacent block.
     *
     * @param world the world to query
     * @param pos the block position to check adjacent to
     * @param direction the direction to check
     * @return the redstone power from that direction (0-15)
     * @throws NullPointerException if any parameter is null
     */
    public static int getAdjacentPower(@NotNull World world, @NotNull BlockPos pos,
            @NotNull Direction direction) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(pos);
        Objects.requireNonNull(direction);
        BlockPos adjacent = pos.offset(direction);
        return Math.clamp(world.getEmittedRedstonePower(adjacent, direction), MIN_POWER, MAX_POWER);
    }

    /**
     * Checks if a position has redstone power above a minimum threshold.
     *
     * @param world the world to query
     * @param pos the block position
     * @param minimumPower the minimum power level required (0-15)
     * @return true if redstone power at pos is >= minimumPower
     * @throws NullPointerException if world or pos is null
     */
    public static boolean isPowered(@NotNull World world, @NotNull BlockPos pos, int minimumPower) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(pos);
        return getRedstoneSignal(world, pos) >= Math.clamp(minimumPower, MIN_POWER, MAX_POWER);
    }

    /**
     * Checks if a position is powered at all (power > 0).
     *
     * @param world the world to query
     * @param pos the block position
     * @return true if any redstone power is present
     * @throws NullPointerException if world or pos is null
     */
    public static boolean hasAnyPower(@NotNull World world, @NotNull BlockPos pos) {
        return getRedstoneSignal(world, pos) > 0;
    }

    /**
     * Checks if redstone wire is present at a position.
     *
     * @param world the world to query
     * @param pos the block position
     * @return true if the block is redstone wire
     * @throws NullPointerException if world or pos is null
     */
    public static boolean isRedstoneWire(@NotNull World world, @NotNull BlockPos pos) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(pos);
        return world.getBlockState(pos).getBlock() instanceof RedstoneWireBlock;
    }

    /**
     * Gets the power level of redstone wire at a position.
     *
     * @param world the world to query
     * @param pos the block position
     * @return the wire power (0-15), or 0 if not redstone wire
     * @throws NullPointerException if world or pos is null
     */
    public static int getWirePower(@NotNull World world, @NotNull BlockPos pos) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(pos);
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof RedstoneWireBlock) {
            Integer power = state.get(RedstoneWireBlock.POWER);
            return power != null ? power : 0;
        }
        return 0;
    }

    /**
     * Gets all positions with redstone power within a certain distance.
     *
     * @param world the world to search
     * @param center the center position
     * @param radiusXZ the horizontal search radius
     * @param radiusY the vertical search radius
     * @return a list of powered positions
     * @throws NullPointerException if world or center is null
     */
    public static @NotNull List<BlockPos> getPoweredPositions(@NotNull World world,
            @NotNull BlockPos center, int radiusXZ, int radiusY) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(center);
        List<BlockPos> powered = new ArrayList<>();
        int minX = center.getX() - radiusXZ;
        int maxX = center.getX() + radiusXZ;
        int minY = center.getY() - radiusY;
        int maxY = center.getY() + radiusY;
        int minZ = center.getZ() - radiusXZ;
        int maxZ = center.getZ() + radiusXZ;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (hasAnyPower(world, pos)) {
                        powered.add(pos);
                    }
                }
            }
        }
        return powered;
    }

    /**
     * Gets all redstone wire positions within a certain distance.
     *
     * @param world the world to search
     * @param center the center position
     * @param radiusXZ the horizontal search radius
     * @param radiusY the vertical search radius
     * @return a list of redstone wire positions
     * @throws NullPointerException if world or center is null
     */
    public static @NotNull List<BlockPos> getRedstoneWires(@NotNull World world,
            @NotNull BlockPos center, int radiusXZ, int radiusY) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(center);
        List<BlockPos> wires = new ArrayList<>();
        int minX = center.getX() - radiusXZ;
        int maxX = center.getX() + radiusXZ;
        int minY = center.getY() - radiusY;
        int maxY = center.getY() + radiusY;
        int minZ = center.getZ() - radiusXZ;
        int maxZ = center.getZ() + radiusXZ;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (isRedstoneWire(world, pos)) {
                        wires.add(pos);
                    }
                }
            }
        }
        return wires;
    }

    /**
     * Checks if a block can emit redstone power.
     *
     * <p>
     * This checks if the block is capable of producing redstone signals.
     *
     * @param world the world to query
     * @param pos the block position
     * @return true if the block can emit redstone power
     * @throws NullPointerException if world or pos is null
     */
    public static boolean canEmitPower(@NotNull World world, @NotNull BlockPos pos) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(pos);
        Block block = world.getBlockState(pos).getBlock();
        // Redstone wire and most power emitters have predictable behavior
        return getRedstoneSignal(world, pos) > 0 || block instanceof RedstoneWireBlock;
    }

    /**
     * Gets the maximum power level constant.
     *
     * @return 15 (maximum redstone power level)
     */
    public static int getMaxPower() {
        return MAX_POWER;
    }
}
