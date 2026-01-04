package dk.mosberg.util;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

/**
 * Utility for sending chat messages and titles to players. Provides methods for broadcasting
 * messages, sending to specific players, and displaying titles and action bars.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * ChatHelper.broadcast(server, "Welcome to the server!");
 * ChatHelper.send(player, "You found a treasure!");
 * ChatHelper.sendTitle(player, "VICTORY", "You win!");
 * ChatHelper.sendActionBar(player, "Health: 20");
 * </pre>
 */
public final class ChatHelper {
    private ChatHelper() {}

    /**
     * Sends a message to a player.
     *
     * @param player the player
     * @param message the message text
     */
    public static void send(@NotNull ServerPlayerEntity player, @NotNull String message) {
        player.sendMessage(Text.literal(message), false);
    }

    /**
     * Sends a Text component to a player.
     *
     * @param player the player
     * @param text the text component
     */
    public static void send(@NotNull ServerPlayerEntity player, @NotNull Text text) {
        player.sendMessage(text, false);
    }

    /**
     * Sends a message to a player's action bar (above hotbar).
     *
     * @param player the player
     * @param message the message text
     */
    public static void sendActionBar(@NotNull ServerPlayerEntity player, @NotNull String message) {
        player.sendMessage(Text.literal(message), true);
    }

    /**
     * Sends a Text component to a player's action bar.
     *
     * @param player the player
     * @param text the text component
     */
    public static void sendActionBar(@NotNull ServerPlayerEntity player, @NotNull Text text) {
        player.sendMessage(text, true);
    }

    /**
     * Broadcasts a message to all players on the server.
     *
     * @param server the minecraft server
     * @param message the message text
     */
    public static void broadcast(@NotNull MinecraftServer server, @NotNull String message) {
        Text text = Text.literal(message);
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.sendMessage(text, false);
        }
    }

    /**
     * Broadcasts a Text component to all players on the server.
     *
     * @param server the minecraft server
     * @param text the text component
     */
    public static void broadcast(@NotNull MinecraftServer server, @NotNull Text text) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.sendMessage(text, false);
        }
    }

    /**
     * Sends a message to a collection of players.
     *
     * @param players the players
     * @param message the message text
     */
    public static void sendToPlayers(@NotNull Collection<ServerPlayerEntity> players,
            @NotNull String message) {
        Text text = Text.literal(message);
        for (ServerPlayerEntity player : players) {
            player.sendMessage(text, false);
        }
    }

    /**
     * Sends a title to a player.
     *
     * @param player the player
     * @param title the title text
     * @param subtitle the subtitle text
     */
    public static void sendTitle(@NotNull ServerPlayerEntity player, @NotNull String title,
            @NotNull String subtitle) {
        player.networkHandler.sendPacket(
                new net.minecraft.network.packet.s2c.play.TitleS2CPacket(Text.literal(title)));
        player.networkHandler
                .sendPacket(new net.minecraft.network.packet.s2c.play.SubtitleS2CPacket(
                        Text.literal(subtitle)));
    }

    /**
     * Sends a title with custom timing to a player.
     *
     * @param player the player
     * @param title the title text
     * @param subtitle the subtitle text
     * @param fadeIn fade in time in ticks
     * @param stay stay time in ticks
     * @param fadeOut fade out time in ticks
     */
    public static void sendTitle(@NotNull ServerPlayerEntity player, @NotNull String title,
            @NotNull String subtitle, int fadeIn, int stay, int fadeOut) {
        player.networkHandler
                .sendPacket(new net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket(fadeIn,
                        stay, fadeOut));
        player.networkHandler.sendPacket(
                new net.minecraft.network.packet.s2c.play.TitleS2CPacket(Text.literal(title)));
        player.networkHandler
                .sendPacket(new net.minecraft.network.packet.s2c.play.SubtitleS2CPacket(
                        Text.literal(subtitle)));
    }

    /**
     * Clears the current title for a player.
     *
     * @param player the player
     */
    public static void clearTitle(@NotNull ServerPlayerEntity player) {
        player.networkHandler
                .sendPacket(new net.minecraft.network.packet.s2c.play.ClearTitleS2CPacket(false));
    }

    /**
     * Sends a system message (chat message that appears as system/gray text).
     *
     * @param player the player
     * @param message the message text
     */
    public static void sendSystem(@NotNull ServerPlayerEntity player, @NotNull String message) {
        player.sendMessage(Text.literal(message).styled(style -> style.withColor(0x888888)), false);
    }

    /**
     * Broadcasts a message to all players on the server. Note: To check for operators specifically,
     * check the player's permission level separately.
     *
     * @param server the minecraft server
     * @param message the message text
     */
    public static void broadcastToAll(@NotNull MinecraftServer server, @NotNull String message) {
        Text text = Text.literal(message);
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.sendMessage(text, false);
        }
    }

    /**
     * Sends a formatted message using TextHelper.
     *
     * @param player the player
     * @param message the message text with formatting codes
     */
    public static void sendFormatted(@NotNull ServerPlayerEntity player, @NotNull String message) {
        player.sendMessage(TextHelper.literal(message), false);
    }

    /**
     * Broadcasts a formatted message using TextHelper.
     *
     * @param server the minecraft server
     * @param message the message text with formatting codes
     */
    public static void broadcastFormatted(@NotNull MinecraftServer server,
            @NotNull String message) {
        Text text = TextHelper.literal(message);
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.sendMessage(text, false);
        }
    }
}
