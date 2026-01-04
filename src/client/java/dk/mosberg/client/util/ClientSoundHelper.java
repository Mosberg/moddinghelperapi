package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

/**
 * Client-only sound playback helpers.
 */
@Environment(EnvType.CLIENT)
public final class ClientSoundHelper {
    private ClientSoundHelper() {}

    /**
     * Plays a UI-style sound at full volume.
     *
     * @param sound the sound event to play
     */
    public static void playUi(@NotNull SoundEvent sound) {
        play(sound, SoundCategory.MASTER, 1.0f, 1.0f);
    }

    /**
     * Plays a sound with volume/pitch on the MASTER category.
     *
     * @param sound the sound event
     * @param volume volume (1.0 = default)
     * @param pitch pitch (1.0 = default)
     */
    public static void play(@NotNull SoundEvent sound, float volume, float pitch) {
        play(sound, SoundCategory.MASTER, volume, pitch);
    }

    /**
     * Plays a sound in the given category.
     *
     * @param sound the sound event
     * @param category the sound category
     * @param volume volume (1.0 = default)
     * @param pitch pitch (1.0 = default)
     */
    public static void play(@NotNull SoundEvent sound, @NotNull SoundCategory category,
            float volume, float pitch) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() == null) {
            return;
        }
        client.getSoundManager().play(PositionedSoundInstance.master(sound, volume, pitch));
    }
}
