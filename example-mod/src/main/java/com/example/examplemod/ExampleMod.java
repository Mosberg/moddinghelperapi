package com.example.examplemod;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import dk.mosberg.util.BlockSearchHelper;
import dk.mosberg.util.EntityHelper;
import dk.mosberg.util.EventHelper;
import dk.mosberg.util.GsonInstance;
import dk.mosberg.util.IdentifierHelper;
import dk.mosberg.util.InventoryHelper;
import dk.mosberg.util.ItemStackHelper;
import dk.mosberg.util.LogHelper;
import dk.mosberg.util.NBTHelper;
import dk.mosberg.util.ParticleHelper;
import dk.mosberg.util.PlayerHelper;
import dk.mosberg.util.RedstoneHelper;
import dk.mosberg.util.SoundHelper;
import dk.mosberg.util.StatisticsHelper;
import dk.mosberg.util.TextHelper;
import dk.mosberg.util.VectorHelper;
import dk.mosberg.util.builders.ItemStackBuilder;
import dk.mosberg.util.builders.TextBuilder;
import dk.mosberg.util.builders.Vec3dBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Example mod demonstrating comprehensive usage of Modding Helper API utilities.
 *
 * Features: - Multiple demonstration commands - Configuration system - Event handling - Particle
 * effects - Sound playback - Block and world interactions - Advanced item manipulation - Statistics
 * tracking
 */
public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "examplemod";
    private static final Logger LOGGER = LogHelper.getLogger(MOD_ID, "Main");

    // Configuration
    private static ModConfig config;
    private static final Path CONFIG_PATH = Path.of("config", MOD_ID + ".json");

    // Player statistics tracking
    private static final Map<UUID, PlayerStats> playerStats = new ConcurrentHashMap<>();

    // Event handler
    private static final EventHelper eventBus = new EventHelper();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Example Mod - Demonstrating Modding Helper API");

        // Load configuration
        loadConfig();

        // Register commands
        registerCommands();

        // Register event listeners
        registerEvents();

        LOGGER.info("Example Mod initialized successfully!");
        LOGGER.info("Loaded config: enabled={}, particleCount={}", config.featuresEnabled,
                config.particleCount);
    }

    private void loadConfig() {
        config = new ModConfig(); // Simple default config for demonstration
        LOGGER.info("Using default configuration");
        // Note: ConfigHelper.load(Path) returns JsonObject, not type-safe config
        // For production use, parse JsonObject manually or use GSON
    }

    private void registerCommands() {
        // Register all commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            // /demo - Main demonstration
            dispatcher.register(CommandManager.literal("demo").executes(context -> {
                try {
                    ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
                    ServerWorld world = context.getSource().getWorld();
                    demonstrateHelpers(player, world);
                    return Command.SINGLE_SUCCESS;
                } catch (Exception e) {
                    context.getSource().sendError(TextHelper.error("Must be run by a player!"));
                    return 0;
                }
            }));

            // /exitem - Give custom item
            dispatcher.register(CommandManager.literal("exitem").then(CommandManager
                    .argument("count", IntegerArgumentType.integer(1, 64)).executes(context -> {
                        try {
                            var player = context.getSource().getPlayerOrThrow();
                            int count = IntegerArgumentType.getInteger(context, "count");

                            var item =
                                    new ItemStackBuilder(Items.DIAMOND_SWORD).quantity(1)
                                            .displayName(new TextBuilder("Epic Sword")
                                                    .color(Formatting.LIGHT_PURPLE).bold().build())
                                            .build();

                            InventoryHelper.addItem(player.getInventory(), item);
                            player.sendMessage(TextHelper.success("Given Epic Sword!"));
                            var world = context.getSource().getWorld();
                            SoundHelper.playAt(world, EntityHelper.getPos(player),
                                    SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

                            return Command.SINGLE_SUCCESS;
                        } catch (Exception e) {
                            return 0;
                        }
                    })));

            // /exstats - Show stats
            dispatcher.register(CommandManager.literal("exstats").executes(context -> {
                try {
                    var player = context.getSource().getPlayerOrThrow();
                    var stats = getPlayerStats(player.getUuid());

                    player.sendMessage(new TextBuilder("=== Player Statistics ===")
                            .color(Formatting.GOLD).bold().build());

                    player.sendMessage(TextHelper.literal("Blocks Broken: " + stats.blocksBreak));
                    player.sendMessage(TextHelper.literal("Commands Used: " + stats.commandsUsed));

                    int deaths = StatisticsHelper.getDeaths(player);
                    int jumps = StatisticsHelper.getJumps(player);

                    player.sendMessage(TextHelper.literal("Deaths: " + deaths));
                    player.sendMessage(TextHelper.literal("Jumps: " + jumps));

                    stats.commandsUsed++;
                    return Command.SINGLE_SUCCESS;
                } catch (Exception e) {
                    return 0;
                }
            }));

            // /exblock - Block search demo
            dispatcher.register(CommandManager.literal("exblock").executes(context -> {
                try {
                    var player = context.getSource().getPlayerOrThrow();
                    var pos = player.getBlockPos();
                    var world = context.getSource().getWorld();
                    var diamondOres =
                            BlockSearchHelper.findInRadius(world, pos, 32, Blocks.DIAMOND_ORE);

                    player.sendMessage(
                            TextHelper.info("Found " + diamondOres.size() + " diamond ore nearby"));

                    if (!diamondOres.isEmpty()) {
                        var nearest = diamondOres.get(0);
                        var distance =
                                VectorHelper.distance(Vec3d.ofCenter(pos), Vec3d.ofCenter(nearest));
                        player.sendMessage(TextHelper
                                .literal(String.format("Nearest at [%d, %d, %d] (%.1f blocks)",
                                        nearest.getX(), nearest.getY(), nearest.getZ(), distance)));
                    }

                    int power = RedstoneHelper.getRedstoneSignal(world, pos.down());
                    if (power > 0) {
                        player.sendMessage(
                                TextHelper.warning("Standing on powered block: " + power));
                    }

                    return Command.SINGLE_SUCCESS;
                } catch (Exception e) {
                    return 0;
                }
            }));

            // /exparticle - Particle effects
            dispatcher.register(CommandManager.literal("exparticle").then(CommandManager
                    .argument("type", StringArgumentType.string()).executes(context -> {
                        try {
                            var player = context.getSource().getPlayerOrThrow();
                            String type = StringArgumentType.getString(context, "type");
                            var pos = EntityHelper.getPos(player);
                            var world = context.getSource().getWorld();

                            switch (type.toLowerCase()) {
                                case "circle" -> ParticleHelper.spawnCircle(world,
                                        ParticleTypes.FLAME, pos, 2.0, 50);
                                case "spiral" -> ParticleHelper.spawnSpiral(world,
                                        ParticleTypes.ENCHANT, pos, 3.0, 5.0, 80);
                                case "sphere" -> ParticleHelper.spawnSphere(world,
                                        ParticleTypes.END_ROD, pos, 2.0, 100);
                                case "line" -> {
                                    var target = pos.add(
                                            VectorHelper.scale(player.getRotationVector(), 10));
                                    ParticleHelper.spawnLine(world, ParticleTypes.END_ROD, pos,
                                            target, 0.5);
                                }
                                default -> {
                                    player.sendMessage(TextHelper.error("Unknown type: " + type));
                                    return 0;
                                }
                            }

                            player.sendMessage(
                                    TextHelper.success("Spawned " + type + " particles!"));
                            return Command.SINGLE_SUCCESS;
                        } catch (Exception e) {
                            return 0;
                        }
                    })));
        });

        LOGGER.info("Registered {} commands", 5);
    }

    private void registerEvents() {
        // Server lifecycle events
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);

        // Player block break event
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (world instanceof ServerWorld && config.trackBlockBreaks) {
                var stats = getPlayerStats(player.getUuid());
                stats.blocksBreak++;

                eventBus.dispatch("block_break", new BlockBreakData(player.getName().getString(),
                        pos, Registries.BLOCK.getId(state.getBlock()).toString()));
            }
        });

        // Custom event listeners
        eventBus.subscribe("block_break", event -> {
            var data = (BlockBreakData) event;
            LOGGER.debug("Player {} broke block {} at {}", data.playerName, data.blockId,
                    data.position);
        }, 100); // High priority

        LOGGER.info("Registered event listeners");
    }

    private void onServerStarted(MinecraftServer server) {
        LOGGER.info("Server started - Example mod is active");
        if (config.featuresEnabled) {
            LOGGER.info("All features enabled");
        }
    }

    private void onServerStopping(MinecraftServer server) {
        LOGGER.info("Server stopping - Saving player statistics");
        LOGGER.info("Tracked stats for {} players", playerStats.size());
    }

    private static PlayerStats getPlayerStats(UUID uuid) {
        return playerStats.computeIfAbsent(uuid, k -> new PlayerStats());
    }

    /**
     * Demonstrates comprehensive helper utilities.
     */
    private void demonstrateHelpers(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new TextBuilder("=== Modding Helper API Demo ===").color(Formatting.GOLD)
                .bold().build());

        // Track usage
        var stats = getPlayerStats(player.getUuid());
        stats.commandsUsed++;

        // 1. Player & Entity helpers
        demoPlayerHelpers(player);

        // 2. Item & Inventory helpers
        demoItemHelpers(player);

        // 3. Vector & Math helpers
        demoVectorHelpers(player);

        // 4. Block & World helpers
        demoBlockHelpers(player, world);

        // 5. Visual effects
        if (config.showParticles) {
            demoVisualEffects(player, world);
        }

        // 6. Data helpers
        demoDataHelpers(player);

        player.sendMessage(TextHelper.success("Demo complete! Try other commands:"));
        player.sendMessage(TextHelper.info("/exitem 1, /exstats, /exblock, /exparticle circle"));
    }

    private void demoPlayerHelpers(ServerPlayerEntity player) {
        String name = player.getName().getString();
        float health = PlayerHelper.getHealth(player);
        float maxHealth = PlayerHelper.getMaxHealth(player);
        boolean alive = PlayerHelper.isAlive(player);
        boolean creative = PlayerHelper.isCreative(player);

        player.sendMessage(
                TextHelper.literal("Player: ").append(TextHelper.colored(name, Formatting.AQUA)));
        player.sendMessage(TextHelper.literal(
                String.format("Health: %.1f/%.1f %s", health, maxHealth, alive ? "✓" : "✗")));
        player.sendMessage(TextHelper.literal("Mode: " + (creative ? "Creative" : "Survival")));
    }

    private void demoItemHelpers(ServerPlayerEntity player) {
        var magicItem = new ItemStackBuilder(Items.GOLDEN_APPLE).quantity(3)
                .displayName(new TextBuilder("Magic Apple").color(Formatting.LIGHT_PURPLE).build())
                .build();

        player.sendMessage(TextHelper.literal("Created: ")
                .append(TextHelper.colored("3x Magic Apple", Formatting.GOLD)));

        // Calculate empty slots manually: total size - occupied slots
        int emptySlots = player.getInventory().size()
                - InventoryHelper.getOccupiedSlotCount(player.getInventory());
        player.sendMessage(TextHelper.literal("Inventory: " + emptySlots + " empty slots"));

        boolean stackable = !ItemStackHelper.isFull(magicItem);
        player.sendMessage(TextHelper.literal("Stackable: " + stackable));
    }

    private void demoVectorHelpers(ServerPlayerEntity player) {
        Vec3d pos = EntityHelper.getPos(player);
        Vec3d spawn = new Vec3d(0, 64, 0);

        double distance = VectorHelper.distance(pos, spawn);
        // Calculate horizontal distance manually (distanceHorizontal doesn't exist)
        double distance2d = Math.sqrt(Math.pow(pos.x - spawn.x, 2) + Math.pow(pos.z - spawn.z, 2));

        Vec3d direction = VectorHelper.direction(pos, spawn);
        Vec3d midpoint = VectorHelper.midpoint(pos, spawn);

        Vec3d scaled = new Vec3dBuilder(direction).scale(10.0).build();

        player.sendMessage(TextHelper
                .literal(String.format("Distance: %.1f blocks (2D: %.1f)", distance, distance2d)));
        player.sendMessage(TextHelper.literal(
                String.format("Midpoint: [%.1f, %.1f, %.1f]", midpoint.x, midpoint.y, midpoint.z)));
    }

    private void demoBlockHelpers(ServerPlayerEntity player, ServerWorld world) {
        var pos = player.getBlockPos();

        var state = world.getBlockState(pos.down());
        var blockId = Registries.BLOCK.getId(state.getBlock());

        player.sendMessage(TextHelper.literal("Block below: ")
                .append(TextHelper.colored(blockId.toString(), Formatting.YELLOW)));

        int power = RedstoneHelper.getRedstoneSignal(world, pos.down());
        if (power > 0) {
            player.sendMessage(TextHelper.warning("Redstone power: " + power));
        }

        var nearby = BlockSearchHelper.findInRadius(world, pos, 10, Blocks.STONE);
        player.sendMessage(TextHelper.literal("Nearby stone blocks: " + nearby.size()));
    }

    private void demoVisualEffects(ServerPlayerEntity player, ServerWorld world) {
        var pos = EntityHelper.getPos(player);

        ParticleHelper.spawnCircle(world, ParticleTypes.ENCHANT, pos.add(0, 0.5, 0), 1.5, 30);

        SoundHelper.playAt(world, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 0.5f, 1.2f);

        player.sendMessage(TextHelper.success("✨ Visual effects displayed!"));
    }

    private void demoDataHelpers(ServerPlayerEntity player) {
        var nbt = new NbtCompound();
        NBTHelper.putString(nbt, "demo", "value");
        NBTHelper.putInt(nbt, "counter", 42);
        NBTHelper.putBoolean(nbt, "enabled", true);

        String value = NBTHelper.getString(nbt, "demo", "default");
        player.sendMessage(TextHelper.literal("NBT test: " + value));

        var data = new DemoData(player.getName().getString(), (int) PlayerHelper.getHealth(player),
                PlayerHelper.isCreative(player));
        String json = GsonInstance.compact().toJson(data);
        player.sendMessage(TextHelper.literal("JSON: " + json));

        var id = IdentifierHelper.of("minecraft:diamond");
        player.sendMessage(TextHelper.literal(String.format("Parsed ID: %s:%s",
                IdentifierHelper.getNamespace(id), IdentifierHelper.getPath(id))));
    }

    // ==================== Data Classes ====================

    /**
     * Configuration class for JSON persistence.
     */
    private static class ModConfig {
        public boolean featuresEnabled = true;
        public boolean showParticles = true;
        public boolean trackBlockBreaks = true;
        public int particleCount = 50;
        public double teleportRange = 1000.0;
    }

    /**
     * Player statistics tracker.
     */
    private static class PlayerStats {
        public int blocksBreak = 0;
        public int commandsUsed = 0;
        public long lastLogin = System.currentTimeMillis();
    }

    /**
     * Event data for block breaks.
     */
    private record BlockBreakData(String playerName, BlockPos position, String blockId) {
    }

    /**
     * Simple data class for JSON serialization demonstration.
     */
    private static class DemoData {
        private final String playerName;
        private final int health;
        private final boolean creative;
        private final long timestamp;

        public DemoData(String playerName, int health, boolean creative) {
            this.playerName = playerName;
            this.health = health;
            this.creative = creative;
            this.timestamp = System.currentTimeMillis();
        }
    }
}
