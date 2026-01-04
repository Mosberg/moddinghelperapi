package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;

/**
 * Client-only particle spawning helpers. Uses the local client world and safely no-ops when the
 * world is unavailable (e.g., main menu).
 */
@Environment(EnvType.CLIENT)
public final class ClientParticleHelper {
    private ClientParticleHelper() {}

    /**
     * Spawns a single particle at the given position with velocity.
     *
     * @param effect the particle type
     * @param x x position
     * @param y y position
     * @param z z position
     * @param velX x velocity
     * @param velY y velocity
     * @param velZ z velocity
     * @return true if a particle was spawned, false if no client world is present
     */
    public static boolean spawn(@NotNull ParticleEffect effect, double x, double y, double z,
            double velX, double velY, double velZ) {
        var client = MinecraftClient.getInstance();
        ParticleManager manager = client.particleManager;
        if (manager == null) {
            return false;
        }
        return manager.addParticle(effect, x, y, z, velX, velY, velZ) != null;
    }

    /**
     * Spawns multiple particles with the same parameters.
     *
     * @param effect the particle type
     * @param count number of particles to spawn
     * @param x x position
     * @param y y position
     * @param z z position
     * @param velX x velocity
     * @param velY y velocity
     * @param velZ z velocity
     * @return number of particles successfully spawned
     */
    public static int spawnMany(@NotNull ParticleEffect effect, int count, double x, double y,
            double z, double velX, double velY, double velZ) {
        var client = MinecraftClient.getInstance();
        ParticleManager manager = client.particleManager;
        if (manager == null || count <= 0) {
            return 0;
        }
        int spawned = 0;
        for (int i = 0; i < count; i++) {
            if (manager.addParticle(effect, x, y, z, velX, velY, velZ) != null) {
                spawned++;
            }
        }
        return spawned;
    }
}
