# Example Mod - Modding Helper API Demonstration

A comprehensive demonstration mod showcasing the full capabilities of the **Modding Helper API** for Minecraft 1.21.11 using Fabric.

## 🎯 Features

### Commands

- **`/demo`** - Main demonstration command showing all helpers
- **`/exitem <count>`** - Give yourself custom enchanted items
- **`/exstats`** - View player statistics and tracked data
- **`/exblock`** - Search for blocks and analyze redstone
- **`/exparticle <type>`** - Spawn particle effects (circle, spiral, sphere, line)

### Systems

✅ **Configuration System** - JSON-based config with runtime loading
✅ **Event Handling** - Server lifecycle and player block break events
✅ **Statistics Tracking** - Per-player stats (blocks broken, commands used)
✅ **Particle Effects** - Circle, spiral, sphere, and line patterns
✅ **Sound Effects** - Context-aware sound playback
✅ **Block Search** - Find nearby blocks with filters
✅ **Redstone Analysis** - Detect and measure power levels

## 📖 Command Usage

### /demo

Main demonstration command showing comprehensive helper usage:

- Player information (health, mode, position)
- Item creation with builders
- Inventory analysis
- Vector calculations
- Block state queries
- Particle and sound effects
- NBT and JSON operations

**Example:**

```
/demo
```

### /exitem <count>

Create and give custom items with display names and enchantments.

**Example:**

```
/exitem 1
```

Creates an "Epic Sword" with custom name and formatting.

### /exstats

Display player statistics including:

- Blocks broken (tracked by mod)
- Commands used
- Deaths (Minecraft stat)
- Jumps (Minecraft stat)

**Example:**

```
/exstats
```

### /exblock

Search for nearby blocks and analyze the environment:

- Find diamond ore within 50 blocks
- Show distance to nearest match
- Check redstone power under player

**Example:**

```
/exblock
```

### /exparticle <type>

Spawn visual particle effects around the player.

**Types:**

- `circle` - Ring of flame particles
- `spiral` - Enchanting spiral effect
- `sphere` - End rod sphere
- `line` - Dragon breath line forward

**Example:**

```
/exparticle circle
/exparticle spiral
```

## 🔧 Helper Classes Demonstrated

### Core Helpers

- **TextBuilder** - Fluent text styling with colors and formatting
- **ItemStackBuilder** - Complex item creation with metadata
- **Vec3dBuilder** - Vector construction and manipulation
- **EntityHelper** - Entity position and state queries
- **PlayerHelper** - Player info, health, and mode checks

### World & Block Helpers

- **BlockSearchHelper** - Find blocks by type and condition
- **RedstoneHelper** - Analyze redstone signals and power
- **ParticleHelper** - Spawn complex particle patterns
- **SoundHelper** - Play positioned sound effects

### Data Helpers

- **NBTHelper** - Safe NBT compound operations
- **GsonInstance** - JSON serialization
- **IdentifierHelper** - Resource location parsing
- **StatisticsHelper** - Access player statistics
- **InventoryHelper** - Inventory management and queries

### Utilities

- **LogHelper** - Structured logging with categories
- **EventHelper** - Priority-based event system
- **VectorHelper** - Distance and direction calculations
- **ConfigHelper** - Configuration file management

## 🎨 Event System

The mod demonstrates the EventHelper API:

```java
// Register custom event
eventBus.subscribe("block_break", event -> {
    var data = (BlockBreakData) event;
    LOGGER.debug("Block broken: {}", data.blockId);
}, 100); // Priority 100

// Dispatch events
eventBus.dispatch("block_break", new BlockBreakData(...));
```

## ⚙️ Configuration

Configuration stored in `config/examplemod.json`:

```json
{
  "featuresEnabled": true,
  "showParticles": true,
  "trackBlockBreaks": true,
  "particleCount": 50,
  "teleportRange": 1000.0
}
```

## 📊 Statistics Tracking

Tracks per-player statistics:

- `blocksBreak` - Total blocks broken
- `commandsUsed` - Commands executed
- `lastLogin` - Last login timestamp

Uses `ConcurrentHashMap` for thread-safe access.

## 🔍 Code Examples

### Creating Custom Items

```java
var item = new ItemStackBuilder(Items.DIAMOND_SWORD)
    .quantity(1)
    .displayName(new TextBuilder("Epic Sword")
        .color(Formatting.LIGHT_PURPLE)
        .bold()
        .build())
    .build();
```

### Particle Effects

```java
ParticleHelper.spawnCircle(world, pos,
    ParticleTypes.FLAME, 2.0, 50);

ParticleHelper.spawnSpiral(world, pos,
    ParticleTypes.ENCHANT, 3.0, 5.0, 80);
```

### Block Searching

```java
var diamondOres = BlockSearchHelper.findNearbyBlocks(world, pos, 50,
    state -> state.isOf(Blocks.DIAMOND_ORE));
```

### Vector Calculations

```java
Vec3d scaled = new Vec3dBuilder(direction)
    .scale(10.0)
    .build();

double distance = VectorHelper.distance(pos1, pos2);
```

### Event Handling

```java
PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
    if (world instanceof ServerWorld && config.trackBlockBreaks) {
        var stats = getPlayerStats(player.getUuid());
        stats.blocksBreak++;
    }
});
```

## 🚀 Building

Build the mod:

```bash
./gradlew.bat build -x test
```

Output JAR location:

```
build/libs/example-mod-1.0.0.jar
```

## 📦 Dependencies

- **Minecraft**: 1.21.11
- **Fabric Loader**: 0.18.4
- **Fabric API**: 0.140.2+1.21.11
- **Modding Helper API**: 1.0.0 (local Maven)

## 📝 License

This example mod is provided under the MIT license for educational purposes.

## 🔗 Related

- [Modding Helper API Documentation](../README.md)
- [Helper Quick Reference](../docs/markdown/HELPER_QUICK_REFERENCE.md)
- [Improvements Guide](../docs/markdown/IMPROVEMENTS_GUIDE.md)

---

**Version**: 1.0.0 | **Minecraft**: 1.21.11 | **Last Updated**: January 2026
