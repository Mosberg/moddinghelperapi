package dk.mosberg.util;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.permission.Permission;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

/**
 * Utilities for registering simple Brigadier commands via Fabric callbacks.
 */
public final class CommandHelper {
    private CommandHelper() {}

    /**
     * Registers a literal command at root with an optional permission level gate.
     *
     * @param name the command literal (e.g., "ping")
     * @param permissionLevel minimum permission level required (0 for all)
     * @param handler execution handler returning Brigadier command result
     */
    public static void registerLiteral(@NotNull String name, int permissionLevel,
            @NotNull Command<ServerCommandSource> handler) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(handler);
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerLiteral(dispatcher, name, permissionLevel, handler);
        });
    }

    /**
     * Registers a literal on an existing dispatcher (e.g., for nested registration flows).
     */
    public static void registerLiteral(@NotNull CommandDispatcher<ServerCommandSource> dispatcher,
            @NotNull String name, int permissionLevel,
            @NotNull Command<ServerCommandSource> handler) {
        var permission = new Permission.Level(PermissionLevel.fromLevel(permissionLevel));
        dispatcher.register(CommandManager.literal(name).requires(
                source -> permissionLevel <= 0 || source.getPermissions().hasPermission(permission))
                .executes(handler));
    }
}
