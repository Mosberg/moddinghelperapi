package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

/**
 * Utility for server player operations. Provides methods for finding players and sending messages.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * ServerPlayerEntity player = PlayerHelper.get(server, "Steve");
 * if (player != null) {
 *     PlayerHelper.message(player, "Hello, Steve!");
 * }
 * </pre>
 */
public final class PlayerHelper {
    private PlayerHelper() {}

    /**
     * Gets a player by name from the server.
     *
     * @param server the Minecraft server instance
     * @param name the player's username
     * @return the ServerPlayerEntity, or null if not found
     */
    @Nullable
    public static ServerPlayerEntity get(@NotNull MinecraftServer server, @NotNull String name) {
        return server.getPlayerManager().getPlayer(name);
    }

    /**
     * Sends a message to a player.
     *
     * @param player the player to message
     * @param text the message text
     */
    public static void message(@NotNull ServerPlayerEntity player, @NotNull String text) {
        player.sendMessage(Text.literal(text));
    }

    /**
     * Sends a text component message to a player.
     *
     * @param player the player to message
     * @param message the Text component to send
     */
    public static void sendMessage(@NotNull ServerPlayerEntity player, @NotNull Text message) {
        player.sendMessage(message);
    }

    /**
     * Checks if a player is in creative mode.
     *
     * @param player the player to check
     * @return true if in creative mode
     */
    public static boolean isCreative(@NotNull ServerPlayerEntity player) {
        return player.isCreative();
    }

    /**
     * Checks if a player is alive.
     *
     * @param player the player to check
     * @return true if alive
     */
    public static boolean isAlive(@NotNull ServerPlayerEntity player) {
        return player.isAlive();
    }

    /**
     * Gets the player's current health.
     *
     * @param player the player
     * @return current health value
     */
    public static float getHealth(@NotNull ServerPlayerEntity player) {
        return player.getHealth();
    }

    /**
     * Gets the player's maximum health.
     *
     * @param player the player
     * @return maximum health value
     */
    public static float getMaxHealth(@NotNull ServerPlayerEntity player) {
        return player.getMaxHealth();
    }
}
