package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

/** Client-side GUI helpers for opening and querying screens. */
@Environment(EnvType.CLIENT)
public final class GuiHelper {
    private GuiHelper() {}

    /** Opens the given screen on the client. */
    public static void openScreen(@NotNull Screen screen) {
        MinecraftClient.getInstance().setScreen(screen);
    }

    /** Returns true if the current screen matches the given class. */
    public static boolean isCurrentScreen(@NotNull Class<? extends Screen> screenClass) {
        var current = MinecraftClient.getInstance().currentScreen;
        return current != null && screenClass.isInstance(current);
    }

    /** Closes the current screen, returning to the game. */
    public static void closeScreen() {
        MinecraftClient.getInstance().setScreen(null);
    }
}
