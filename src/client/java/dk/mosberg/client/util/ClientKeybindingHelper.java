package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.KeyBinding.Category;
import net.minecraft.client.util.InputUtil;

/**
 * Convenience methods for creating and working with client key bindings.
 */
@Environment(EnvType.CLIENT)
public final class ClientKeybindingHelper {
    private ClientKeybindingHelper() {}

    /**
     * Registers a new key binding with the Fabric key binding helper.
     *
     * @param translationKey the translation key for the binding
     * @param defaultKeyCode the default key code (GLFW constant)
     * @param category the category translation key shown in controls menu
     * @return the registered {@link KeyBinding}
     */
    public static @NotNull KeyBinding registerKeybinding(@NotNull String translationKey,
            int defaultKeyCode, @NotNull Category category) {
        KeyBinding binding =
                new KeyBinding(translationKey, InputUtil.Type.KEYSYM, defaultKeyCode, category);
        return KeyBindingHelper.registerKeyBinding(binding);
    }

    /**
     * Checks whether the key binding is currently pressed.
     *
     * @param keyBinding the key binding to query
     * @return true if pressed
     */
    public static boolean isPressed(@NotNull KeyBinding keyBinding) {
        return keyBinding.isPressed();
    }

    /**
     * Clears the pressed state of the key binding.
     *
     * @param keyBinding the key binding to update
     */
    public static void reset(@NotNull KeyBinding keyBinding) {
        keyBinding.setPressed(false);
    }
}
