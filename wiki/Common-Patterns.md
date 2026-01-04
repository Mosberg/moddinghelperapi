# Common Patterns

Reusable code patterns and best practices for Modding Helper API.

## Item Management Patterns

### Pattern: Safe Item Creation

**Problem:** Create items safely without errors.

```java
// ❌ Unsafe - can throw exception if not found
ItemStack unsafeStack = ItemStackHelper.of("invalid:item", 1);

// ✅ Safe - validate first
String itemId = "minecraft:diamond";
if (RegistryHelper.isItemRegistered(itemId)) {
    ItemStack safeStack = ItemStackHelper.of(itemId, 1);
} else {
    logError("Item not found: " + itemId);
}

// ✅ Safe - with fallback
ItemStack stack = RegistryHelper.getItem(itemId) != null
    ? ItemStackHelper.of(itemId, 1)
    : ItemStackHelper.of("minecraft:dirt", 1);
```

### Pattern: Item Stack Builder Fluent API

**Use Case:** Create complex items with multiple properties.

```java
ItemStack customItem = new ItemStackBuilder(Items.DIAMOND_SWORD)
    .quantity(1)
    .displayName(new TextBuilder("Legendary Sword")
        .bold()
        .color(Formatting.GOLD)
        .build())
    .unbreakable(true)
    .durability(50)
    .build();
```

### Pattern: Inventory Scanning

**Problem:** Find specific items in inventory.

```java
Inventory inventory = player.getInventory();

// Find first matching item
for (int i = 0; i < inventory.size(); i++) {
    ItemStack stack = inventory.getStack(i);
    if (ItemStackHelper.matches(stack, Items.DIAMOND)) {
        return i;
    }
}

// Count all matching items
int totalDiamonds = 0;
for (ItemStack stack : inventory) {
    if (ItemStackHelper.matches(stack, Items.DIAMOND)) {
        totalDiamonds += stack.getCount();
    }
}
```

---

## Entity and Player Patterns

### Pattern: Safe Player Access

**Problem:** Get player safely with null checks.

```java
// ❌ Unsafe - can be null
ServerPlayerEntity player = PlayerHelper.get(server, "Steve");
player.teleport(100, 100, 100);  // NullPointerException!

// ✅ Safe - null check
ServerPlayerEntity player = PlayerHelper.get(server, "Steve");
if (player != null && PlayerHelper.isAlive(player)) {
    player.teleport(100, 100, 100);
}
```

### Pattern: Entity Relationship Management

**Problem:** Handle entity mounting and passengers.

```java
// Mount entity on another
if (EntityHelper.canMount(horse, player)) {
    EntityHelper.mount(horse, player);
}

// Get all passengers
Set<Entity> passengers = EntityHelper.getPassengers(vehicle);
for (Entity passenger : passengers) {
    logInfo("Passenger: " + passenger.getName().getString());
}

// Safely dismount
if (EntityHelper.isRiding(player)) {
    EntityHelper.dismount(player);
}
```

### Pattern: Distance-Based Actions

**Problem:** Perform actions based on distance.

```java
Entity target = ...;
Entity observer = ...;

// Quick distance check (no sqrt)
if (EntityHelper.distanceSquared(observer, target) < 100 * 100) {
    // Target is within 100 blocks
    doSomething();
}

// Precise distance
double distance = EntityHelper.distance(observer, target);
if (distance < 50) {
    observer.sendMessage(Text.literal("Close!"));
}
```

---

## Block and World Patterns

### Pattern: Block Searching with Validation

**Problem:** Find blocks while validating conditions.

```java
BlockPos center = player.getBlockPos();

// Find specific block within radius
BlockSearchHelper.findInRadius(world, center, 50, block -> {
    BlockState state = world.getBlockState(block);

    // Only return blocks that are fully exposed to air above
    return state.getMaterial().isSolid() &&
           world.isAir(block.up());
});
```

### Pattern: Safe Block State Manipulation

**Problem:** Read and modify block states safely.

```java
BlockPos pos = ...;
BlockState state = world.getBlockState(pos);

// Read property safely
if (BlockStateHelper.hasProperty(state, Properties.LIT)) {
    boolean isLit = BlockStateHelper.getBoolean(state, Properties.LIT);

    // Toggle property
    BlockState newState = BlockStateHelper.setBoolean(
        state,
        Properties.LIT,
        !isLit
    );
    world.setBlockState(pos, newState);
}
```

### Pattern: Redstone Detection

**Problem:** Check redstone signals.

```java
BlockPos pos = ...;

// Check if powered from any direction
if (RedstoneHelper.hasAnyPower(world, pos)) {
    activateFeature();
}

// Get signal strength from specific direction
int power = RedstoneHelper.getSignalStrength(world, pos, Direction.UP);
if (power > 8) {
    maxPower();
}

// Find all powered neighbors
for (BlockPos powered : RedstoneHelper.getPoweredPositions(world, pos)) {
    logInfo("Powered: " + powered);
}
```

---

## Text and Display Patterns

### Pattern: Styled Message Builder

**Problem:** Create formatted messages easily.

```java
// ✅ Use TextBuilder
MutableText message = new TextBuilder("✓ ")
    .color(Formatting.GREEN)
    .append(new TextBuilder("Success!")
        .bold()
        .build())
    .build();
player.sendMessage(message);

// Alternatives by severity
MutableText error = new TextBuilder("✗ Error: ")
    .error()
    .append("Something failed")
    .build();

MutableText warning = new TextBuilder("⚠ Warning: ")
    .warning()
    .append("Check configuration")
    .build();

MutableText info = new TextBuilder("ℹ ")
    .info()
    .append("Information")
    .build();
```

### Pattern: Color Coding by Value

**Problem:** Show values with color coding.

```java
float health = player.getHealth();
float maxHealth = player.getMaxHealth();
float percent = health / maxHealth;

String color = percent > 0.75 ? "✓" :
               percent > 0.5 ? "◐" :
               percent > 0.25 ? "◑" : "✗";

Formatting format = percent > 0.5 ? Formatting.GREEN :
                    percent > 0.25 ? Formatting.YELLOW : Formatting.RED;

MutableText healthText = new TextBuilder(color)
    .color(format)
    .append(" " + String.format("%.1f", health))
    .build();
```

---

## Vector and Math Patterns

### Pattern: Vector Calculations

**Problem:** Perform vector math safely.

```java
Vec3d position = player.getPos();
Vec3d target = new Vec3d(100, 100, 100);

// Get direction (normalized)
Vec3d direction = VectorHelper.direction(position, target);

// Scale velocity
Vec3d velocity = VectorHelper.scale(direction, 10.0);

// Apply knockback
player.setVelocity(velocity);

// Distance check
if (VectorHelper.distance(position, target) < 1.0) {
    arrivedAtDestination();
}
```

### Pattern: 3D Positioning with Builder

**Problem:** Build complex positions with transformations.

```java
// Start from player position
Vec3d base = player.getPos();

// Build offset position
Vec3d offset = new Vec3dBuilder(base)
    .add(5, 0, 0)      // 5 blocks to the right
    .add(0, 2, 0)      // 2 blocks up
    .build();

// Rotate around player
Vec3d rotated = new Vec3dBuilder(offset)
    .subtract(base)    // Move to origin
    .rotateY(45)       // Rotate 45 degrees
    .add(base)         // Move back
    .build();
```

---

## NBT and Data Patterns

### Pattern: Safe NBT Access

**Problem:** Read NBT data without crashes.

```java
NbtCompound nbt = ...;

// ✅ Safe - use defaults
String name = NBTHelper.getString(nbt, "CustomName", "Unknown");
int level = NBTHelper.getInt(nbt, "Level", 0);
boolean enabled = NBTHelper.getBoolean(nbt, "Enabled", false);

// Never do this:
// ❌ String name = nbt.getString("CustomName");  // Crashes if missing!
```

### Pattern: Complex NBT Structures

**Problem:** Handle nested NBT data.

```java
NbtCompound data = player.getPersistentData();

// Create nested structure
NbtCompound stats = new NbtCompound();
NBTHelper.putInt(stats, "Kills", 10);
NBTHelper.putInt(stats, "Deaths", 2);
data.put("MyMod:Stats", stats);

// Read nested data
NbtCompound savedStats = data.getCompound("MyMod:Stats");
int kills = NBTHelper.getInt(savedStats, "Kills", 0);
int deaths = NBTHelper.getInt(savedStats, "Deaths", 0);
```

### Pattern: Lists in NBT

**Problem:** Store lists of values.

```java
NbtCompound data = ...;

// Write list
NbtList itemList = new NbtList();
for (String item : items) {
    NbtString tag = NbtString.of(item);
    itemList.add(tag);
}
data.put("Items", itemList);

// Read list
NbtList savedList = data.getList("Items", NbtElement.STRING_TYPE);
for (int i = 0; i < savedList.size(); i++) {
    String item = savedList.getString(i);
    logInfo("Item: " + item);
}
```

---

## Networking and Multiplayer Patterns

### Pattern: Synchronizing Player Data

**Problem:** Keep player data in sync.

```java
// On server - broadcast to all
NetworkHelper.broadcastPacket(
    server.getPlayerManager().getPlayerList(),
    new UpdatePlayerDataPayload(player.getUuid(), data)
);

// On client - receive and apply
PayloadHandler.register(UpdatePlayerDataPayload.ID, (payload, context) -> {
    Player player = Minecraft.getInstance().world.getPlayerByUuid(payload.uuid);
    if (player != null) {
        applyData(player, payload.data);
    }
});
```

### Pattern: Connection Event Handling

**Problem:** React to player joining/leaving.

```java
// Register connection handlers
NetworkHelper.registerConnectionHandler(
    Identifier.of("mymod", "on_join"),
    player -> {
        logInfo(player.getName().getString() + " joined!");
        // Send initial data to player
        NetworkHelper.sendToPlayer(player, welcomePayload);
    }
);

NetworkHelper.registerDisconnectionHandler(
    Identifier.of("mymod", "on_leave"),
    player -> {
        logInfo(player.getName().getString() + " left!");
        // Cleanup if needed
    }
);
```

---

## Caching and Performance Patterns

### Pattern: Item Registry Caching

**Problem:** Avoid repeated registry lookups.

```java
private static final Map<String, Item> ITEM_CACHE = new HashMap<>();

public static Item getCachedItem(String id) {
    return ITEM_CACHE.computeIfAbsent(id, k -> {
        Item item = RegistryHelper.getItem(k);
        return item != null ? item : Items.AIR;
    });
}

// Usage
ItemStack diamond = ItemStackHelper.of(getCachedItem("minecraft:diamond"), 1);
ItemStack gold = ItemStackHelper.of(getCachedItem("minecraft:gold_ingot"), 1);
```

### Pattern: Throttled Block Searching

**Problem:** Don't search every tick.

```java
private static long lastSearch = 0;
private static List<BlockPos> cachedResults = Collections.emptyList();

public static List<BlockPos> findBlocksCached(World world, BlockPos center, int radius) {
    long now = System.currentTimeMillis();

    // Only search every 1 second
    if (now - lastSearch > 1000) {
        cachedResults = BlockSearchHelper.findInRadius(world, center, radius, block);
        lastSearch = now;
    }

    return cachedResults;
}
```

---

## Error Handling Patterns

### Pattern: Graceful Error Recovery

**Problem:** Handle errors without crashing.

```java
try {
    // Attempt operation
    ItemStack stack = ItemStackHelper.of("custom:item", 1);
    // Use stack...
} catch (Exception e) {
    // Log error
    LogHelper.getLogger("mymod", "ItemCreation")
        .error("Failed to create item", e);

    // Fallback
    ItemStack fallback = ItemStackHelper.of("minecraft:diamond", 1);
    // Use fallback...
}
```

### Pattern: Validation Wrapper

**Problem:** Validate data before using.

```java
public static boolean safeOperation(Player player, String itemId) {
    // Validate input
    if (player == null || !player.isAlive()) {
        return false;
    }

    if (itemId == null || itemId.isEmpty()) {
        return false;
    }

    // Validate item exists
    if (!RegistryHelper.isItemRegistered(itemId)) {
        return false;
    }

    // Safe to proceed
    ItemStack stack = ItemStackHelper.of(itemId, 1);
    player.getInventory().insertStack(stack);
    return true;
}
```

---

## Testing Patterns

### Pattern: Unit Test Setup

**Problem:** Test helper methods.

```java
@Test
void testItemCreation() {
    // Arrange
    String itemId = "minecraft:diamond";
    int count = 5;

    // Act
    ItemStack stack = ItemStackHelper.of(itemId, count);

    // Assert
    assertNotNull(stack);
    assertEquals(count, stack.getCount());
    assertEquals(Items.DIAMOND, stack.getItem());
}
```

### Pattern: Testing with NBT

**Problem:** Test NBT operations.

```java
@Test
void testNBTOperations() {
    NbtCompound nbt = new NbtCompound();

    // Write
    NBTHelper.putString(nbt, "Name", "TestValue");
    NBTHelper.putInt(nbt, "Count", 42);

    // Read
    String name = NBTHelper.getString(nbt, "Name", "Default");
    int count = NBTHelper.getInt(nbt, "Count", 0);

    // Assert
    assertEquals("TestValue", name);
    assertEquals(42, count);
}
```

---

**Need more patterns?** → [Examples](Examples.md) | [API Reference](API-Reference.md)
