package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;

/**
 * Client-only helper for showing system toasts.
 */
@Environment(EnvType.CLIENT)
public final class ClientToastHelper {
    private ClientToastHelper() {}

    /**
     * Shows a toast with title and optional description using the TUTORIAL toast type.
     *
     * @param title title text
     * @param description optional description (null to omit)
     * @return true if toast was queued
     */
    public static boolean showToast(@NotNull Text title, @Nullable Text description) {
        MinecraftClient client = MinecraftClient.getInstance();
        ToastManager manager = client.getToastManager();
        if (manager == null) {
            return false;
        }
        // NARRATOR_TOGGLE is a safe generic toast type available in 1.21.11
        SystemToast toast =
                SystemToast.create(client, SystemToast.Type.NARRATOR_TOGGLE, title, description);
        manager.add(toast);
        return true;
    }
}
