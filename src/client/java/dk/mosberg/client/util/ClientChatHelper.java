package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

/**
 * Client-side chat helpers. Adds messages directly to the local chat HUD without sending to the
 * server.
 */
@Environment(EnvType.CLIENT)
public final class ClientChatHelper {
    private ClientChatHelper() {}

    /**
     * Adds a chat message to the local HUD.
     *
     * @param message text to display
     * @return true if shown, false if chat HUD unavailable
     */
    public static boolean addMessage(@NotNull Text message) {
        var client = MinecraftClient.getInstance();
        if (client.inGameHud == null || client.inGameHud.getChatHud() == null) {
            return false;
        }
        client.inGameHud.getChatHud().addMessage(message);
        return true;
    }

    /**
     * Adds a plain string message to the local HUD.
     *
     * @param message text to display
     * @return true if shown
     */
    public static boolean addMessage(@NotNull String message) {
        return addMessage(Text.literal(message));
    }
}
