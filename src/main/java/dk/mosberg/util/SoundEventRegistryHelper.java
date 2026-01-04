package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;

/**
 * Utility for sound event registry operations. Provides methods for accessing sound events from the
 * registry.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * SoundEvent levelUp = SoundEventRegistryHelper.getSoundEvent("minecraft:entity.player.levelup");
 * if (SoundEventRegistryHelper.isSoundEventRegistered("minecraft:block.note_block.harp")) {
 *     // Sound exists
 * }
 * int count = SoundEventRegistryHelper.getSoundEventCount();
 * </pre>
 */
public final class SoundEventRegistryHelper {
    private SoundEventRegistryHelper() {}

    /**
     * Gets a sound event from the registry by ID.
     *
     * @param id the sound event identifier (e.g., "minecraft:entity.player.levelup")
     * @return the SoundEvent, or null if not found
     */
    public static SoundEvent getSoundEvent(@NotNull String id) {
        return Registries.SOUND_EVENT.get(IdentifierHelper.of(id));
    }

    /**
     * Checks if a sound event is registered.
     *
     * @param id the sound event identifier
     * @return true if the sound event exists
     */
    public static boolean isSoundEventRegistered(@NotNull String id) {
        return Registries.SOUND_EVENT.containsId(IdentifierHelper.of(id));
    }

    /**
     * Gets the total count of registered sound events.
     *
     * @return the number of registered sound events
     */
    public static int getSoundEventCount() {
        return Registries.SOUND_EVENT.size();
    }

    /**
     * Gets the registry ID for a sound event.
     *
     * @param soundEvent the sound event to look up
     * @return the registry ID as a string, or "unknown" if not found
     */
    @NotNull
    public static String getSoundEventId(@NotNull SoundEvent soundEvent) {
        var id = Registries.SOUND_EVENT.getId(soundEvent);
        return id != null ? id.toString() : "unknown";
    }
}
