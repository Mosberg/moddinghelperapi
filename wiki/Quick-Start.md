# Quick Start Guide

Get up and running with Modding Helper API in 5 minutes. This guide assumes you have a Fabric mod project set up.

## Setup (1 minute)

### Add Dependency

In your `fabric.mod.json`, ensure moddinghelperapi is in the `depends` section:

```json
"depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": "~1.21.11",
    "java": ">=21",
    "fabric-api": "*",
    "moddinghelperapi": "*"
}
```

### Import Classes

```java
import dk.mosberg.util.*;
import dk.mosberg.util.builders.*;
```

## 5-Minute Tutorial

### Minute 1: Create Items

```java
// Create an item stack
ItemStack diamond = ItemStackHelper.of("minecraft:diamond", 1);
ItemStack dirt = ItemStackHelper.of("minecraft:dirt", 64);

// Check if empty
if (!ItemStackHelper.isEmpty(diamond)) {
    player.giveItemStack(diamond);
}
```

### Minute 2: Send Messages

```java
// Simple message
player.sendMessage(Text.literal("Hello!"));

// Styled message with builder
Text message = new TextBuilder("Item Received!")
    .success()      // Green color
    .bold()
    .build();
player.sendMessage(message, false);

// Different styles
Text error = new TextBuilder("Error!").error().build();      // Red
Text warning = new TextBuilder("Warning!").warning().build();  // Yellow
Text info = new TextBuilder("Info!").info().build();           // Cyan
```

### Minute 3: Work with Vectors

```java
// Create a vector
Vec3d vec = new Vec3dBuilder(10, 20, 30)
    .scale(2.0)        // Make it twice as big
    .normalize()       // Make it unit length
    .build();

// Vector math
double distance = VectorHelper.distance(pos1, pos2);
Vec3d direction = VectorHelper.direction(pos1, pos2);
double length = VectorHelper.length(vec);
```

### Minute 4: Search for Blocks

```java
// Find all diamond ore blocks in 50-block radius
List<BlockPos> diamonds = BlockSearchHelper.findInRadius(
    world,
    player.getBlockPos(),
    50,
    Blocks.DIAMOND_ORE
);

// Find nearest block
BlockPos nearest = BlockSearchHelper.findNearest(
    world,
    player.getBlockPos(),
    100,
    Blocks.GOLD_ORE
);

if (nearest != null) {
    System.out.println("Found gold at " + nearest);
}
```

### Minute 5: Manage Inventories

```java
// Add item to inventory
ItemStack toAdd = ItemStackHelper.of("minecraft:apple", 10);
ItemStack leftover = InventoryHelper.addItem(player.getInventory(), toAdd);

// If item didn't fit, handle leftover
if (!ItemStackHelper.isEmpty(leftover)) {
    player.dropItem(leftover, false);
}

// Count specific items
int appleCount = InventoryHelper.count(
    player.getInventory(),
    stack -> stack.getItem() == Items.APPLE
);

// Sort inventory
InventoryHelper.sortByType(player.getInventory());
```

## Your First Complete Mod

Create a simple mod that gives a player a diamond when they join:

```java
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import dk.mosberg.util.*;
import dk.mosberg.util.builders.*;

public class YourModInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        // Listen for player join event
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;

            // Create item
            var diamond = ItemStackHelper.of("minecraft:diamond", 1);

            // Add to player
            player.giveItemStack(diamond);

            // Send message
            Text msg = new TextBuilder("Welcome! You received a diamond!")
                .success()
                .bold()
                .build();
            player.sendMessage(msg, false);
        });
    }
}
```

## Common Patterns

### Check Item in Inventory

```java
if (InventoryHelper.count(player.getInventory(), Items.DIAMOND) > 0) {
    // Player has diamonds
}
```

### Format Numbers in Messages

```java
int count = 42;
Text msg = new TextBuilder("You have ")
    .append(String.valueOf(count))
    .color(Formatting.GOLD)
    .append(" items")
    .build();
```

### Get Player by Name

```java
ServerPlayerEntity player = PlayerHelper.get(server, "Steve");
if (player != null) {
    player.sendMessage(Text.literal("Hello, Steve!"));
}
```

### Check Entity Type

```java
if (EntityHelper.isLiving(entity)) {
    LivingEntity living = (LivingEntity) entity;
    System.out.println("Health: " + living.getHealth());
}
```

### Store Data with NBT

```java
NbtCompound nbt = new NbtCompound();

// Write data
NBTHelper.putString(nbt, "playerName", player.getName().getString());
NBTHelper.putInt(nbt, "level", 10);
NBTHelper.putBoolean(nbt, "hasQuest", true);

// Read data
String name = NBTHelper.getString(nbt, "playerName", "Unknown");
int level = NBTHelper.getInt(nbt, "level", 0);
boolean hasQuest = NBTHelper.getBoolean(nbt, "hasQuest", false);
```

## Next Steps

**You just used 5 different helpers!** Now:

1. **Explore more helpers** → [Helpers Overview](Helpers-Overview.md)
2. **See more examples** → [Code Examples](Examples.md)
3. **Learn all methods** → [API Reference](API-Reference.md)
4. **Review patterns** → [Common Patterns](Common-Patterns.md)

## Troubleshooting

**"Cannot find dk.mosberg.util"**

- Check `fabric.mod.json` has correct syntax
- Run `./gradlew clean` and refresh IDE
- Rebuild project

**"Method doesn't exist"**

- Check [API Reference](API-Reference.md) for correct method name
- Verify you're using Minecraft 1.21.11
- Check [Minecraft API Notes](Minecraft-API-Notes.md) for version differences

**Code compiles but nothing happens**

- Verify you're in a server environment (not client)
- Check mod initializer is called (add log statement)
- Check Minecraft logs for errors

## Help

- **Questions?** → [FAQ](FAQ.md)
- **Stuck?** → [Troubleshooting](Troubleshooting.md)
- **Want more examples?** → [Code Examples](Examples.md)

---

**Next:** [Helpers Overview →](Helpers-Overview.md)
