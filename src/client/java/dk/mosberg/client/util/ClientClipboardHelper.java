package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

/**
 * Client-only clipboard utilities using the Minecraft keyboard handler.
 */
@Environment(EnvType.CLIENT)
public final class ClientClipboardHelper {
    private ClientClipboardHelper() {}

    /**
     * Copies the given text to the system clipboard.
     *
     * @param text text to copy
     */
    public static void copy(@NotNull String text) {
        var client = MinecraftClient.getInstance();
        if (client.keyboard != null) {
            client.keyboard.setClipboard(text);
        }
    }

    /**
     * Reads text from the system clipboard.
     *
     * @return clipboard contents or null if unavailable
     */
    public static @Nullable String paste() {
        var client = MinecraftClient.getInstance();
        if (client.keyboard != null) {
            return client.keyboard.getClipboard();
        }
        return null;
    }
}
