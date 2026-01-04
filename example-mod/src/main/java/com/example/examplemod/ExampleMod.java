package com.example.examplemod;

import org.slf4j.Logger;
import com.mojang.brigadier.Command;
import dk.mosberg.util.CommandHelper;
import dk.mosberg.util.EntityHelper;
import dk.mosberg.util.GsonInstance;
import dk.mosberg.util.ItemStackHelper;
import dk.mosberg.util.LogHelper;
import dk.mosberg.util.PlayerHelper;
import dk.mosberg.util.TextHelper;
import dk.mosberg.util.VectorHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

/**
 * Example mod demonstrating usage of Modding Helper API utilities.
 *
 * This mod showcases: - Command registration with CommandHelper - Text formatting with TextHelper -
 * Player interactions with PlayerHelper - ItemStack creation with ItemStackHelper - Vector
 * calculations with VectorHelper - NBT data operations with NBTHelper - Structured logging with
 * LogHelper
 */
public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "examplemod";
    private static final Logger LOGGER = LogHelper.getLogger(MOD_ID, "Main");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Example Mod - Demonstrating Modding Helper API");

        // Register a command that demonstrates various helpers
        registerDemoCommand();

        LOGGER.info("Example Mod initialized successfully!");
    }

    /**
     * Registers a /demo command that showcases helper utilities.
     */
    private void registerDemoCommand() {
        CommandHelper.registerLiteral("demo", 0, context -> {
            var source = context.getSource();

            try {
                ServerPlayerEntity player = source.getPlayerOrThrow();
                demonstrateHelpers(player);
                return Command.SINGLE_SUCCESS;
            } catch (Exception e) {
                source.sendError(TextHelper.error("This command must be run by a player!"));
                return 0;
            }
        });

        LOGGER.info("Registered /demo command");
    }

    /**
     * Demonstrates various helper utilities with a player.
     */
    private void demonstrateHelpers(ServerPlayerEntity player) {
        // 1. Text formatting with TextHelper
        player.sendMessage(TextHelper.success("=== Modding Helper API Demo ==="));
        player.sendMessage(TextHelper.info("Demonstrating various utilities..."));

        // 2. Player information with PlayerHelper
        String playerName = player.getName().getString();
        float health = PlayerHelper.getHealth(player);
        float maxHealth = PlayerHelper.getMaxHealth(player);
        boolean creative = PlayerHelper.isCreative(player);

        player.sendMessage(TextHelper.literal("Player: ")
                .append(TextHelper.colored(playerName, net.minecraft.util.Formatting.GOLD)));
        player.sendMessage(TextHelper.literal("Health: " + health + "/" + maxHealth));
        player.sendMessage(TextHelper.literal("Mode: " + (creative ? "Creative" : "Survival")));

        // 3. ItemStack creation with ItemStackHelper
        var diamondStack = ItemStackHelper.of(Items.DIAMOND, 5);
        player.sendMessage(TextHelper.literal("Created: ")
                .append(TextHelper.colored("5x Diamond", net.minecraft.util.Formatting.AQUA)));

        if (!ItemStackHelper.isFull(diamondStack)) {
            int remaining = ItemStackHelper.getRemainingSpace(diamondStack);
            player.sendMessage(TextHelper.literal("Can stack " + remaining + " more"));
        }

        // 4. Vector calculations with VectorHelper
        Vec3d playerPos = EntityHelper.getPos(player);
        Vec3d spawnPos = new Vec3d(0, 64, 0);
        double distance = VectorHelper.distance(playerPos, spawnPos);
        Vec3d direction = VectorHelper.direction(playerPos, spawnPos);

        player.sendMessage(TextHelper.literal("Distance to spawn: ").append(TextHelper.colored(
                String.format("%.2f blocks", distance), net.minecraft.util.Formatting.YELLOW)));
        player.sendMessage(TextHelper.literal(String.format("Direction: [%.2f, %.2f, %.2f]",
                direction.x, direction.y, direction.z)));

        // 5. Entity operations with EntityHelper
        boolean onGround = EntityHelper.isOnGround(player);
        boolean isLiving = EntityHelper.isLiving(player);

        player.sendMessage(TextHelper.literal("On ground: " + onGround));
        player.sendMessage(TextHelper.literal("Is living entity: " + isLiving));

        // 6. JSON serialization with GsonInstance
        var testData = new DemoData(playerName, (int) health, creative);
        String json = GsonInstance.compact().toJson(testData);
        player.sendMessage(TextHelper.literal("JSON: " + json));

        // Final success message
        player.sendMessage(TextHelper.success("Demo complete! Check server logs for details."));

        // Log to console
        LOGGER.info("Demo command executed for player: {}", playerName);
        LOGGER.debug("Player position: [{}, {}, {}]", playerPos.x, playerPos.y, playerPos.z);
    }

    /**
     * Simple data class for JSON serialization demonstration.
     */
    private static class DemoData {
        private final String playerName;
        private final int health;
        private final boolean creative;

        public DemoData(String playerName, int health, boolean creative) {
            this.playerName = playerName;
            this.health = health;
            this.creative = creative;
        }
    }
}
