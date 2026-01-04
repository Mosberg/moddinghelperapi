package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

/**
 * Client-only title and subtitle helpers.
 */
@Environment(EnvType.CLIENT)
public final class ClientTitleHelper {
    private ClientTitleHelper() {}

    /**
     * Shows a title with optional subtitle using default timing.
     *
     * @param title main title text
     * @param subtitle optional subtitle (null to skip)
     */
    public static void showTitle(@NotNull Text title, @Nullable Text subtitle) {
        var client = MinecraftClient.getInstance();
        if (client.inGameHud == null) {
            return;
        }
        client.inGameHud.setTitle(title);
        if (subtitle != null) {
            client.inGameHud.setSubtitle(subtitle);
        }
    }

    /**
     * Clears any active title/subtitle.
     */
    public static void clear() {
        var client = MinecraftClient.getInstance();
        if (client.inGameHud != null) {
            client.inGameHud.clear();
        }
    }
}
