package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

/**
 * Client-only HUD helpers for simple overlay messaging.
 */
@Environment(EnvType.CLIENT)
public final class ClientHudHelper {
    private ClientHudHelper() {}

    /**
     * Shows an overlay message near the crosshair (same area as action bar messages).
     *
     * @param message the text to display
     * @param tinted whether to tint the background (vanilla uses true for survival mode overlays)
     */
    public static void showOverlay(@NotNull Text message, boolean tinted) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.inGameHud != null) {
            client.inGameHud.setOverlayMessage(message, tinted);
        }
    }
}
