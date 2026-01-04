package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;

/**
 * Utility for accessing and modifying player statistics. Provides methods for reading and updating
 * various game statistics.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * int deaths = StatisticsHelper.getValue(player, Stats.DEATHS);
 * StatisticsHelper.increment(player, Stats.CUSTOM.getOrCreateStat(Stats.JUMP));
 * StatisticsHelper.set(player, Stats.DEATHS, 0);
 * </pre>
 */
public final class StatisticsHelper {
    private StatisticsHelper() {}

    /**
     * Gets the value of a statistic for a player.
     *
     * @param player the player
     * @param stat the statistic
     * @param <T> the statistic type
     * @return the statistic value
     */
    public static <T> int getValue(@NotNull ServerPlayerEntity player, @NotNull Stat<T> stat) {
        return player.getStatHandler().getStat(stat);
    }

    /**
     * Sets the value of a statistic for a player.
     *
     * @param player the player
     * @param stat the statistic
     * @param value the new value
     * @param <T> the statistic type
     */
    public static <T> void set(@NotNull ServerPlayerEntity player, @NotNull Stat<T> stat,
            int value) {
        player.getStatHandler().setStat(player, stat, value);
    }

    /**
     * Increments a statistic by 1.
     *
     * @param player the player
     * @param stat the statistic
     * @param <T> the statistic type
     */
    public static <T> void increment(@NotNull ServerPlayerEntity player, @NotNull Stat<T> stat) {
        incrementBy(player, stat, 1);
    }

    /**
     * Increments a statistic by a specific amount.
     *
     * @param player the player
     * @param stat the statistic
     * @param amount the amount to increment
     * @param <T> the statistic type
     */
    public static <T> void incrementBy(@NotNull ServerPlayerEntity player, @NotNull Stat<T> stat,
            int amount) {
        int current = getValue(player, stat);
        set(player, stat, current + amount);
    }

    /**
     * Decrements a statistic by 1.
     *
     * @param player the player
     * @param stat the statistic
     * @param <T> the statistic type
     */
    public static <T> void decrement(@NotNull ServerPlayerEntity player, @NotNull Stat<T> stat) {
        decrementBy(player, stat, 1);
    }

    /**
     * Decrements a statistic by a specific amount.
     *
     * @param player the player
     * @param stat the statistic
     * @param amount the amount to decrement
     * @param <T> the statistic type
     */
    public static <T> void decrementBy(@NotNull ServerPlayerEntity player, @NotNull Stat<T> stat,
            int amount) {
        int current = getValue(player, stat);
        set(player, stat, Math.max(0, current - amount));
    }

    /**
     * Resets a statistic to zero.
     *
     * @param player the player
     * @param stat the statistic
     * @param <T> the statistic type
     */
    public static <T> void reset(@NotNull ServerPlayerEntity player, @NotNull Stat<T> stat) {
        set(player, stat, 0);
    }

    /**
     * Gets the number of times a player has died.
     *
     * @param player the player
     * @return the death count
     */
    public static int getDeaths(@NotNull ServerPlayerEntity player) {
        return getValue(player, Stats.CUSTOM.getOrCreateStat(Stats.DEATHS));
    }

    /**
     * Gets the distance a player has walked (in cm).
     *
     * @param player the player
     * @return the walk distance in cm
     */
    public static int getWalkDistance(@NotNull ServerPlayerEntity player) {
        return getValue(player, Stats.CUSTOM.getOrCreateStat(Stats.WALK_ONE_CM));
    }

    /**
     * Gets the distance a player has flown (in cm).
     *
     * @param player the player
     * @return the fly distance in cm
     */
    public static int getFlyDistance(@NotNull ServerPlayerEntity player) {
        return getValue(player, Stats.CUSTOM.getOrCreateStat(Stats.FLY_ONE_CM));
    }

    /**
     * Gets the number of jumps a player has performed.
     *
     * @param player the player
     * @return the jump count
     */
    public static int getJumps(@NotNull ServerPlayerEntity player) {
        return getValue(player, Stats.CUSTOM.getOrCreateStat(Stats.JUMP));
    }

    /**
     * Gets the time played in ticks.
     *
     * @param player the player
     * @return the time played in ticks
     */
    public static int getTimePlayed(@NotNull ServerPlayerEntity player) {
        return getValue(player, Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME));
    }

    /**
     * Gets the time played in seconds.
     *
     * @param player the player
     * @return the time played in seconds
     */
    public static int getTimePlayedSeconds(@NotNull ServerPlayerEntity player) {
        return getTimePlayed(player) / 20;
    }

    /**
     * Gets the total mob kills.
     *
     * @param player the player
     * @return the number of mobs killed
     */
    public static int getMobKills(@NotNull ServerPlayerEntity player) {
        return getValue(player, Stats.CUSTOM.getOrCreateStat(Stats.MOB_KILLS));
    }

    /**
     * Gets the total damage dealt.
     *
     * @param player the player
     * @return the total damage dealt (in half-hearts * 10)
     */
    public static int getDamageDealt(@NotNull ServerPlayerEntity player) {
        return getValue(player, Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_DEALT));
    }

    /**
     * Gets the total damage taken.
     *
     * @param player the player
     * @return the total damage taken (in half-hearts * 10)
     */
    public static int getDamageTaken(@NotNull ServerPlayerEntity player) {
        return getValue(player, Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_TAKEN));
    }
}
