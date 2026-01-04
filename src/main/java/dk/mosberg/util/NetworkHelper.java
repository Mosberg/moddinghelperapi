package dk.mosberg.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Utility for network operations and packet handling in Fabric mods.
 *
 * <p>
 * Provides simplified packet registration, client-to-server (C2S) and server-to-client (S2C) packet
 * sending, and packet payload handling.
 *
 * <p>
 * <strong>Example:</strong>
 * </p>
 *
 * <pre>
 * // Register a custom payload packet
 * record MyPayload(String message) implements CustomPayload {
 *     public static final Identifier ID = Identifier.of("mymod", "my_packet");
 *     public static final PacketCodec<RegistryByteBuf, MyPayload> CODEC = ...;
 *
 *     public Id getId() { return new Id(ID); }
 * }
 *
 * // Send to server from client
 * ClientPlayNetworking.send(new MyPayload("Hello"));
 *
 * // Receive on server
 * ServerPlayNetworking.registerGlobalReceiver(MyPayload.ID,
 *     (payload, context) -> {
 *         context.server().execute(() -> {
 *             // Handle payload on main thread
 *         });
 *     }
 * );
 * </pre>
 */
public final class NetworkHelper {
    private static final Map<Identifier, Consumer<ServerPlayerEntity>> CONNECT_HANDLERS =
            new HashMap<>();
    private static final Map<Identifier, Consumer<ServerPlayerEntity>> DISCONNECT_HANDLERS =
            new HashMap<>();

    private NetworkHelper() {
        // Prevent instantiation
    }

    /**
     * Registers a handler to be called when a player connects to the server.
     *
     * <p>
     * The handler is called on the server thread and can be used to send initial sync packets or
     * perform connection-related operations.
     *
     * @param handlerId unique identifier for this handler
     * @param handler callback invoked with the connecting player
     * @throws NullPointerException if either parameter is null
     */
    public static void registerConnectionHandler(@NotNull Identifier handlerId,
            @NotNull Consumer<ServerPlayerEntity> handler) {
        Objects.requireNonNull(handlerId);
        Objects.requireNonNull(handler);
        CONNECT_HANDLERS.put(handlerId, handler);
    }

    /**
     * Unregisters a previously registered connection handler.
     *
     * @param handlerId the unique identifier of the handler to remove
     */
    public static void unregisterConnectionHandler(@NotNull Identifier handlerId) {
        CONNECT_HANDLERS.remove(handlerId);
    }

    /**
     * Registers a handler to be called when a player disconnects from the server.
     *
     * <p>
     * The handler is called on the server thread and can be used for cleanup operations.
     *
     * @param handlerId unique identifier for this handler
     * @param handler callback invoked with the disconnecting player
     * @throws NullPointerException if either parameter is null
     */
    public static void registerDisconnectionHandler(@NotNull Identifier handlerId,
            @NotNull Consumer<ServerPlayerEntity> handler) {
        Objects.requireNonNull(handlerId);
        Objects.requireNonNull(handler);
        DISCONNECT_HANDLERS.put(handlerId, handler);
    }

    /**
     * Unregisters a previously registered disconnection handler.
     *
     * @param handlerId the unique identifier of the handler to remove
     */
    public static void unregisterDisconnectionHandler(@NotNull Identifier handlerId) {
        DISCONNECT_HANDLERS.remove(handlerId);
    }

    /**
     * Broadcasts a server-to-client packet to all connected players.
     *
     * <p>
     * This method sends the packet to every player on the server.
     *
     * @param players the players to send to
     * @param payload the payload packet to send
     * @throws NullPointerException if either parameter is null
     */
    public static void broadcastPacket(@NotNull Iterable<ServerPlayerEntity> players,
            @NotNull CustomPayload payload) {
        Objects.requireNonNull(players);
        Objects.requireNonNull(payload);
        for (ServerPlayerEntity player : players) {
            sendToPlayer(player, payload);
        }
    }

    /**
     * Sends a server-to-client packet to a specific player.
     *
     * @param player the target player
     * @param payload the payload packet to send
     * @throws NullPointerException if either parameter is null
     */
    public static void sendToPlayer(@NotNull ServerPlayerEntity player,
            @NotNull CustomPayload payload) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(payload);
        ServerPlayNetworking.send(player, payload);
    }

    /**
     * Checks if a player connection is ready for packet sending.
     *
     * @param player the player to check
     * @return true if the connection is active and ready
     */
    public static boolean isPlayerConnected(@NotNull ServerPlayerEntity player) {
        return Objects.requireNonNull(player).networkHandler != null;
    }

    /**
     * Gets the number of registered connection handlers.
     *
     * @return the count of active handlers
     */
    public static int getConnectionHandlerCount() {
        return CONNECT_HANDLERS.size();
    }

    /**
     * Gets the number of registered disconnection handlers.
     *
     * @return the count of active handlers
     */
    public static int getDisconnectionHandlerCount() {
        return DISCONNECT_HANDLERS.size();
    }

    /**
     * Clears all registered connection and disconnection handlers.
     *
     * <p>
     * <strong>Warning:</strong> This should only be called during shutdown or testing.
     */
    public static void clearAllHandlers() {
        CONNECT_HANDLERS.clear();
        DISCONNECT_HANDLERS.clear();
    }

    /**
     * Initializes the network event listeners for connection/disconnection events.
     *
     * <p>
     * This should be called once during mod initialization to set up the event listeners.
     */
    public static void initializeEventListeners() {
        ServerPlayConnectionEvents.INIT.register((handler, server) -> {
            // Initialization on connection
        });

        ServerPlayConnectionEvents.JOIN.register((handler, payload, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            CONNECT_HANDLERS.forEach((id, handler_) -> {
                try {
                    handler_.accept(player);
                } catch (Exception e) {
                    LogHelper.getLogger("moddinghelperapi", "NetworkHelper")
                            .error("Error in connection handler {}: {}", id, e.getMessage());
                }
            });
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            DISCONNECT_HANDLERS.forEach((id, handler_) -> {
                try {
                    handler_.accept(player);
                } catch (Exception e) {
                    LogHelper.getLogger("moddinghelperapi", "NetworkHelper")
                            .error("Error in disconnection handler {}: {}", id, e.getMessage());
                }
            });
        });
    }
}
