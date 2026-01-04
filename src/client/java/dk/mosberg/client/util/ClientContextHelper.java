package dk.mosberg.client.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

/**
 * Client-side context utilities for safely accessing {@link MinecraftClient} state.
 *
 * <p>
 * All methods are client-only and annotated with {@link Environment} to avoid accidental
 * server-side use.
 * </p>
 */
@Environment(EnvType.CLIENT)
public final class ClientContextHelper {
    private ClientContextHelper() {}

    /**
     * Gets the singleton Minecraft client instance.
     *
     * @return the active {@link MinecraftClient}
     */
    public static @NotNull MinecraftClient getClient() {
        return MinecraftClient.getInstance();
    }

    /**
     * Gets the current client player, if present.
     *
     * @return the client player or {@code null} if not in a world yet
     */
    public static @Nullable ClientPlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }

    /**
     * Gets the current client world, if present.
     *
     * @return the client world or {@code null} if not in a world yet
     */
    public static @Nullable ClientWorld getWorld() {
        return MinecraftClient.getInstance().world;
    }

    /**
     * Checks if the client is fully ready (both player and world available).
     *
     * @return true if player and world are non-null
     */
    public static boolean isClientReady() {
        MinecraftClient client = MinecraftClient.getInstance();
        return client.player != null && client.world != null;
    }

    /**
     * Schedules a task on the client render thread.
     *
     * @param task the work to run on the client thread
     */
    public static void runOnClientThread(@NotNull Runnable task) {
        MinecraftClient.getInstance().execute(task);
    }
}
