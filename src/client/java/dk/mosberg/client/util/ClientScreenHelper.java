package dk.mosberg.client.util;

import org.jetbrains.annotations.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

/**
 * Client-only screen helpers for opening and closing GUIs.
 */
@Environment(EnvType.CLIENT)
public final class ClientScreenHelper {
    private ClientScreenHelper() {}

    /**
     * Opens the given screen on the client.
     *
     * @param screen screen to open (null closes current screen)
     */
    public static void open(@Nullable Screen screen) {
        MinecraftClient.getInstance().setScreen(screen);
    }

    /**
     * Closes the current screen (returns to game HUD).
     */
    public static void close() {
        MinecraftClient.getInstance().setScreen(null);
    }

    /**
     * @return the currently open screen, or null if in-game
     */
    public static @Nullable Screen getCurrentScreen() {
        return MinecraftClient.getInstance().currentScreen;
    }
}
