package dk.mosberg.util;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Utility for searching blocks in the world. Provides methods for finding blocks within specific
 * areas and ranges.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * List&lt;BlockPos&gt; diamonds = BlockSearchHelper.findInRadius(world, center, 16, Blocks.DIAMOND_ORE);
 * BlockPos nearest = BlockSearchHelper.findNearest(world, center, 32, Blocks.DIAMOND_ORE);
 * </pre>
 */
public final class BlockSearchHelper {
    private BlockSearchHelper() {}

    /**
     * Finds all blocks of a specific type within a spherical radius.
     *
     * @param world the world to search in
     * @param center the center position
     * @param radius the search radius
     * @param block the block type to find
     * @return list of matching block positions
     */
    @NotNull
    public static List<BlockPos> findInRadius(@NotNull World world, @NotNull BlockPos center,
            int radius, @NotNull Block block) {
        List<BlockPos> results = new ArrayList<>();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        BlockPos pos = center.add(x, y, z);
                        if (world.getBlockState(pos).getBlock() == block) {
                            results.add(pos);
                        }
                    }
                }
            }
        }

        return results;
    }

    /**
     * Finds all blocks within a cubic area.
     *
     * @param world the world to search in
     * @param from the starting corner
     * @param to the ending corner
     * @param block the block type to find
     * @return list of matching block positions
     */
    @NotNull
    public static List<BlockPos> findInBox(@NotNull World world, @NotNull BlockPos from,
            @NotNull BlockPos to, @NotNull Block block) {
        List<BlockPos> results = new ArrayList<>();

        int minX = Math.min(from.getX(), to.getX());
        int maxX = Math.max(from.getX(), to.getX());
        int minY = Math.min(from.getY(), to.getY());
        int maxY = Math.max(from.getY(), to.getY());
        int minZ = Math.min(from.getZ(), to.getZ());
        int maxZ = Math.max(from.getZ(), to.getZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (world.getBlockState(pos).getBlock() == block) {
                        results.add(pos);
                    }
                }
            }
        }

        return results;
    }

    /**
     * Finds the nearest block of a specific type within a radius.
     *
     * @param world the world to search in
     * @param center the center position
     * @param radius the search radius
     * @param block the block type to find
     * @return the nearest matching position, or null if none found
     */
    @NotNull
    public static BlockPos findNearest(@NotNull World world, @NotNull BlockPos center, int radius,
            @NotNull Block block) {
        BlockPos nearest = null;
        double nearestDistSq = Double.MAX_VALUE;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    int distSq = x * x + y * y + z * z;
                    if (distSq <= radius * radius && distSq < nearestDistSq) {
                        BlockPos pos = center.add(x, y, z);
                        if (world.getBlockState(pos).getBlock() == block) {
                            nearest = pos;
                            nearestDistSq = distSq;
                        }
                    }
                }
            }
        }

        return nearest;
    }

    /**
     * Counts blocks of a specific type within a radius.
     *
     * @param world the world to search in
     * @param center the center position
     * @param radius the search radius
     * @param block the block type to count
     * @return the number of matching blocks
     */
    public static int countInRadius(@NotNull World world, @NotNull BlockPos center, int radius,
            @NotNull Block block) {
        int count = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        BlockPos pos = center.add(x, y, z);
                        if (world.getBlockState(pos).getBlock() == block) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    /**
     * Checks if any block of a specific type exists within a radius.
     *
     * @param world the world to search in
     * @param center the center position
     * @param radius the search radius
     * @param block the block type to find
     * @return true if at least one matching block is found
     */
    public static boolean existsInRadius(@NotNull World world, @NotNull BlockPos center, int radius,
            @NotNull Block block) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        BlockPos pos = center.add(x, y, z);
                        if (world.getBlockState(pos).getBlock() == block) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Replaces all blocks of one type with another within a radius.
     *
     * @param world the world to modify
     * @param center the center position
     * @param radius the search radius
     * @param from the block type to replace
     * @param to the block type to place
     * @return the number of blocks replaced
     */
    public static int replaceInRadius(@NotNull World world, @NotNull BlockPos center, int radius,
            @NotNull Block from, @NotNull Block to) {
        int count = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        BlockPos pos = center.add(x, y, z);
                        if (world.getBlockState(pos).getBlock() == from) {
                            world.setBlockState(pos, to.getDefaultState());
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }
}
