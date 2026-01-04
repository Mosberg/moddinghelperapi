# Helpers Overview

Complete overview of all 28 utility helpers in the Modding Helper API.

## Quick Navigation

- [Core Utilities](#core-utilities) (9)
- [Block & World](#block--world) (6)
- [Item & Inventory](#item--inventory) (2)
- [Entity & Living](#entity--living) (4)
- [Network & Communication](#network--communication) (3)
- [Developer Tools](#developer-tools) (5)
- [Configuration & Data](#configuration--data) (3)
- [Specialized Utilities](#specialized-utilities) (8)

---

## Core Utilities

### 1. GsonInstance

**JSON serialization singleton**

```java
// Access pre-configured GSON instances
Gson compact = GsonInstance.compact();  // Compact JSON
Gson pretty = GsonInstance.pretty();    // Pretty-printed JSON

// Use for parsing
MyClass obj = compact.fromJson(json, MyClass.class);
String jsonString = pretty.toJson(obj);
```

**Use when:** Working with JSON data, config files, API responses

---

### 2. IdentifierHelper

**Minecraft resource location handling**

```java
// Create identifiers
Identifier id = IdentifierHelper.of("mymod", "item");
Identifier id2 = IdentifierHelper.of("mymod:item");

// Extract components
String namespace = IdentifierHelper.getNamespace(id);  // "mymod"
String path = IdentifierHelper.getPath(id);             // "item"

// Validate format
if (IdentifierHelper.isValid("valid:id")) {
    // Valid identifier
}
```

**Use when:** Creating resource locations, validating identifier format

---

### 3. NBTHelper

**Safe NBT compound operations**

```java
NbtCompound nbt = new NbtCompound();

// Write data
NBTHelper.putString(nbt, "name", "value");
NBTHelper.putInt(nbt, "count", 10);

// Read with defaults
String name = NBTHelper.getString(nbt, "name", "default");
int count = NBTHelper.getInt(nbt, "count", 0);

// Check and remove
if (NBTHelper.contains(nbt, "key")) {
    NBTHelper.remove(nbt, "key");
}
```

**Use when:** Storing/retrieving NBT data, handling item/entity data

---

### 4. TextHelper

**Text component creation and styling**

```java
// Create styled text
Text bold = TextHelper.bold("Bold");
Text italic = TextHelper.italic("Italic");
Text colored = TextHelper.colored("Text", Formatting.GOLD);

// Preset colors
Text success = TextHelper.success("Success!");    // Green
Text error = TextHelper.error("Error!");          // Red
Text warn = TextHelper.warning("Warning!");       // Yellow
Text info = TextHelper.info("Info!");             // Cyan
```

**Use when:** Creating chat messages, formatting text output

---

### 5. VectorHelper

**Vector mathematics utilities**

```java
Vec3d v1 = new Vec3d(0, 0, 0);
Vec3d v2 = new Vec3d(10, 20, 30);

// Distance calculations
double dist = VectorHelper.distance(v1, v2);
double distSq = VectorHelper.squaredDistance(v1, v2);  // Faster

// Vector operations
Vec3d normalized = VectorHelper.normalize(v2);
Vec3d scaled = VectorHelper.scale(v2, 2.0);
Vec3d direction = VectorHelper.direction(v1, v2);

// Math operations
double length = VectorHelper.length(v2);
double dot = VectorHelper.dotProduct(v1, v2);
Vec3d sum = VectorHelper.add(v1, v2);
```

**Use when:** Working with positions, directions, distances

---

### 6. EntityHelper

**Entity queries and operations**

```java
// Type checking
if (EntityHelper.isLiving(entity)) { }
if (EntityHelper.isPlayer(entity)) { }

// Distance/position
double dist = EntityHelper.distance(e1, e2);
Vec3d pos = EntityHelper.getPos(entity);

// State
if (EntityHelper.isOnGround(entity)) { }
if (EntityHelper.isInLava(entity)) { }
if (EntityHelper.isBurning(entity)) { }

// Passenger management
EntityHelper.mount(player, horse);
List<Entity> passengers = EntityHelper.getPassengers(boat);

// Teleport
EntityHelper.teleport(entity, 100, 64, 200);
```

**Use when:** Querying entities, managing relationships, moving entities

---

### 7. PlayerHelper

**Player-specific utilities**

```java
// Find players
ServerPlayerEntity player = PlayerHelper.get(server, "Steve");

// Check status
if (PlayerHelper.isAlive(player)) { }
if (PlayerHelper.isCreative(player)) { }

// Health
float health = PlayerHelper.getHealth(player);
float maxHealth = PlayerHelper.getMaxHealth(player);

// Messages
PlayerHelper.sendMessage(player, message);
```

**Use when:** Finding players, checking status, sending messages

---

### 8. ItemStackHelper

**ItemStack creation and operations**

```java
// Create items
ItemStack diamond = ItemStackHelper.of("minecraft:diamond", 1);
ItemStack stack = ItemStackHelper.of(Items.GOLD_ORE, 64);

// Check properties
if (ItemStackHelper.isEmpty(stack)) { }
if (ItemStackHelper.isFull(stack)) { }
if (ItemStackHelper.matches(stack, Items.DIAMOND)) { }

// Get info
int space = ItemStackHelper.getRemainingSpace(stack);
int count = stack.getCount();
```

**Use when:** Creating items, checking properties, comparing stacks

---

### 9. RegistryHelper

**Minecraft registry access**

```java
// Get from registry
Item diamond = RegistryHelper.getItem("minecraft:diamond");
Block stone = RegistryHelper.getBlock("minecraft:stone");

// Check if registered
if (RegistryHelper.isItemRegistered("mymod:custom")) { }
if (RegistryHelper.isBlockRegistered("mymod:custom")) { }

// Get counts
int itemCount = RegistryHelper.getItemCount();
int blockCount = RegistryHelper.getBlockCount();
```

**Use when:** Looking up items/blocks, checking registration

---

## Block & World

### 10. BlockSearchHelper

**Find blocks in the world**

```java
// Find in radius
List<BlockPos> diamonds = BlockSearchHelper.findInRadius(
    world, player.getBlockPos(), 50, Blocks.DIAMOND_ORE);

// Find nearest
BlockPos nearest = BlockSearchHelper.findNearest(
    world, pos, 100, Blocks.GOLD_ORE);

// Find in box
List<BlockPos> blocks = BlockSearchHelper.findInBox(
    world, box, Blocks.IRON_ORE);

// Find with predicate
List<BlockPos> ores = BlockSearchHelper.findInRadius(
    world, pos, 50, state -> state.getBlock() instanceof OreBlock);
```

**Use when:** Locating specific blocks, searching areas

---

### 11. BlockStateHelper

**Block state property manipulation**

```java
BlockState state = world.getBlockState(pos);

// Get properties
if (state.getProperties().contains(Properties.LIT)) {
    boolean lit = state.get(Properties.LIT);
}

// Set properties
BlockState updated = state.with(Properties.LIT, true);
world.setBlockState(pos, updated);

// Check block type
if (state.getBlock() == Blocks.DIAMOND_ORE) { }
```

**Use when:** Modifying block state, checking properties

---

### 12. BlockEntityHelper

**Block entity data access**

```java
BlockEntity be = world.getBlockEntity(pos);

// Get NBT
var nbt = be.createNbt();
String owner = NBTHelper.getString(nbt, "Owner", "Unknown");

// Modify entity
if (be instanceof ChestBlockEntity chest) {
    chest.setStack(0, ItemStackHelper.of("minecraft:diamond", 1));
}

// Get/set data
var data = BlockEntityHelper.getNBT(world, pos);
BlockEntityHelper.setNBT(world, pos, data);
```

**Use when:** Reading/writing block entity data

---

### 13. DimensionHelper

**Dimension and world utilities**

```java
// Get dimension
RegistryKey<World> dim = DimensionHelper.getDimension(player);

// Get time
long time = DimensionHelper.getTime(world);

// Check time
if (DimensionHelper.isDaytime(world)) {
    System.out.println("It's daytime!");
}

// Get all dimensions
Set<RegistryKey<World>> dims = DimensionHelper.getAllDimensions(server);
```

**Use when:** Working with dimensions, checking time

---

### 14. RedstoneHelper

**Redstone power detection**

```java
// Get signal strength (0-15)
int signal = RedstoneHelper.getRedstoneSignal(world, pos);

// Check if powered
if (RedstoneHelper.isPowered(world, pos)) {
    // Block is powered
}

// Get adjacent power
int power = RedstoneHelper.getAdjacentPower(world, pos);

// Find powered blocks
Set<BlockPos> powered = RedstoneHelper.getPoweredPositions(
    world, center, radius);
```

**Use when:** Detecting redstone signals, creating pulse detectors

---

### 15. WorldGenHelper

**World generation utilities**

```java
// Check if chunk loaded
if (WorldGenHelper.isChunkLoaded(world, pos)) {
    // Can access chunk
}

// Get biome
Biome biome = WorldGenHelper.getBiomeAt(world, pos);

// Find biome
BlockPos biomePos = WorldGenHelper.findBiome(
    world, pos, "minecraft:forest", 1000);
```

**Use when:** Working with chunks, finding biomes

---

## Item & Inventory

### 16. InventoryHelper

**Inventory management**

```java
// Add items
ItemStack leftover = InventoryHelper.addItem(
    inventory, ItemStackHelper.of("minecraft:apple", 32));

// Remove items
InventoryHelper.removeItemCount(inventory, Items.APPLE, 5);

// Count items
int appleCount = InventoryHelper.count(inventory, Items.APPLE);
int toolCount = InventoryHelper.count(inventory,
    stack -> stack.getItem() instanceof PickaxeItem);

// Sort
InventoryHelper.sortByType(inventory);
InventoryHelper.sortByName(inventory);

// Bulk operations
InventoryHelper.moveAllItems(sourceInv, targetInv);
InventoryHelper.clearMatching(inventory,
    stack -> stack.getItem() == Items.DIRT);
```

**Use when:** Managing player/storage inventories

---

### 17. ItemStackBuilder (see Builders Guide)

**Fluent ItemStack creation**

---

## Entity & Living

### 18. HealthHelper

**Health and damage operations**

```java
// Get/set health
float health = HealthHelper.getHealth(living);
HealthHelper.setHealth(living, 20.0f);

// Get max health
float max = HealthHelper.getMaxHealth(living);

// Apply damage
HealthHelper.damage(living, 5.0f);

// Heal
HealthHelper.heal(living, 10.0f);

// Check conditions
if (HealthHelper.isDead(living)) { }
if (HealthHelper.isFullHealth(living)) { }

// Status effects
HealthHelper.addEffect(living, StatusEffects.STRENGTH, 200, 0);
HealthHelper.removeEffect(living, StatusEffects.POISON);
```

**Use when:** Managing entity health, applying effects

---

### 19. EntityAIHelper

**Entity behavior utilities**

```java
// Control behavior
EntityAIHelper.setAttackTarget(mob, player);
EntityAIHelper.stopAttacking(mob);

// Navigation
EntityAIHelper.moveTo(mob, targetPos);
EntityAIHelper.lookAt(mob, player);
```

**Use when:** Controlling mob behavior

---

### 20. StatisticsHelper

**Player statistics access**

```java
// Get statistic
int blocksWalked = StatisticsHelper.getStatistic(
    player, Stats.WALK_ONE_CM);

// Get values
int itemsUsed = StatisticsHelper.getItemUsed(player, Items.APPLE);
int blocksBroken = StatisticsHelper.getBlockBroken(player, Blocks.STONE);

// Check achievements
if (StatisticsHelper.hasAchievement(player, "minecraft:story/obtain_wooden_pickaxe")) {
    // Player has achievement
}
```

**Use when:** Checking player stats/achievements

---

### 21. EnchantmentHelper

**Enchantment operations**

```java
// Add enchantment
EnchantmentHelper.addEnchantment(stack, Enchantments.UNBREAKING, 3);

// Get level
int level = EnchantmentHelper.getEnchantmentLevel(
    stack, Enchantments.SHARPNESS);

// Remove enchantment
EnchantmentHelper.removeEnchantment(stack, Enchantments.CURSE_OF_VANISHING);

// Check if enchanted
if (EnchantmentHelper.isEnchanted(stack)) { }
```

**Use when:** Managing item enchantments

---

## Network & Communication

### 22. NetworkHelper

**Networking and connection handling**

```java
// Register handlers
NetworkHelper.registerConnectionHandler(
    Identifier.of("mymod", "on_join"),
    player -> {
        // Player joined
        NetworkHelper.sendToPlayer(player, payload);
    }
);

// Broadcast
NetworkHelper.broadcastPacket(
    server.getPlayerManager().getPlayerList(),
    packet
);

// Send to player
NetworkHelper.sendToPlayer(player, packet);
```

**Use when:** Handling player connections, sending network packets

---

### 23. ChatHelper

**Chat message utilities**

```java
// Send message
ChatHelper.send(player, "Hello!");

// Send formatted
Text msg = new TextBuilder("Welcome!").success().build();
ChatHelper.sendFormatted(player, msg);

// Send title
ChatHelper.sendTitle(player, "Welcome!", "To the server", 10, 70, 10);

// Send action bar
ChatHelper.sendActionBar(player, "Health: 20");

// Broadcast
ChatHelper.broadcast(server, "Server event!");
```

**Use when:** Sending messages to players

---

### 24. SoundHelper

**Sound effect playback**

```java
// Play to player
SoundHelper.playToPlayer(player,
    SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

// Play at position
SoundHelper.playAtPos(world, pos,
    SoundEvents.BLOCK_DIAMOND_ORE_BREAK, 1.0f, 1.0f);

// Play to nearby players
SoundHelper.playNearby(world, pos,
    SoundEvents.ENTITY_PLAYER_HURT, 1.0f, 1.0f, 50);
```

**Use when:** Playing sound effects

---

## Developer Tools

### 25. LogHelper

**Structured logging**

```java
var logger = LogHelper.getLogger("mymod", "ItemHandler");

logger.debug("Debug message");
logger.info("Info message");
logger.warn("Warning message");
logger.error("Error message", exception);
```

**Use when:** Logging events and debugging

---

### 26. EventHelper

**Custom event system**

```java
EventHelper events = new EventHelper();

// Subscribe
events.subscribe("player_join", event -> {
    // Handle event
});

// Subscribe with priority
events.subscribe("damage", event -> {
    // Higher priority = runs first
}, 100);

// Dispatch
events.dispatch("player_join", playerJoinEvent);

// Check listeners
if (events.hasListeners("damage")) {
    // Listeners exist
}
```

**Use when:** Creating custom event systems

---

### 27. ValidationHelper

**Input validation and sanitization**

```java
// Validate formats
if (ValidationHelper.isValidEmail(email)) { }
if (ValidationHelper.isValidUsername(username)) { }

// Validate values
if (ValidationHelper.isInRange(value, 0, 100)) { }
if (ValidationHelper.isNotEmpty(string)) { }

// Sanitize
String clean = ValidationHelper.sanitizeString(userInput);
String url = ValidationHelper.sanitizeUrl(input);

// Pattern matching
if (ValidationHelper.matches(text, "pattern")) { }
```

**Use when:** Validating user input

---

### 28. FileHelper

**File I/O operations**

```java
// Read/write text
String content = FileHelper.readFile(path);
FileHelper.writeFile(path, content);

// JSON operations
var obj = FileHelper.readJson(path, MyClass.class);
FileHelper.writeJson(path, obj);

// Directory operations
FileHelper.createDirectories(dir);
boolean exists = FileHelper.exists(path);

// List files
List<Path> files = FileHelper.listFiles(dir);
```

**Use when:** File I/O, config management

---

## Configuration & Data

### Additional Utilities

**ConfigHelper** - Configuration file management
**PersistentDataHelper** - Persistent data storage (Fabric attachments)
**CacheHelper** - LRU caching with expiry

---

## Specialized Utilities

**MathHelper** - Advanced math (interpolation, angles)
**TimeHelper** - Time calculations and conversions
**ParticleHelper** - Particle effect spawning
**PotionHelper** - Potion effect utilities
**LootTableHelper** - Loot table queries
**Registry Helpers** - Biome, SoundEvent, StatusEffect registries

---

## Summary Table

| Helper            | Category | Use Case             |
| ----------------- | -------- | -------------------- |
| GsonInstance      | Core     | JSON serialization   |
| IdentifierHelper  | Core     | Resource locations   |
| NBTHelper         | Core     | NBT data             |
| TextHelper        | Core     | Text components      |
| VectorHelper      | Core     | Vector math          |
| EntityHelper      | Core     | Entity queries       |
| PlayerHelper      | Core     | Player operations    |
| ItemStackHelper   | Core     | Item creation        |
| RegistryHelper    | Core     | Registry access      |
| BlockSearchHelper | Block    | Find blocks          |
| BlockStateHelper  | Block    | Block properties     |
| BlockEntityHelper | Block    | Block entity data    |
| DimensionHelper   | Block    | World utilities      |
| RedstoneHelper    | Block    | Redstone signals     |
| WorldGenHelper    | Block    | World generation     |
| InventoryHelper   | Item     | Inventory management |
| HealthHelper      | Entity   | Health/damage        |
| EntityAIHelper    | Entity   | Mob behavior         |
| StatisticsHelper  | Entity   | Player stats         |
| EnchantmentHelper | Entity   | Enchantments         |
| NetworkHelper     | Network  | Connections          |
| ChatHelper        | Network  | Messages             |
| SoundHelper       | Network  | Sound effects        |
| LogHelper         | Tools    | Logging              |
| EventHelper       | Tools    | Custom events        |
| ValidationHelper  | Tools    | Input validation     |
| FileHelper        | Tools    | File I/O             |
| + 2 more          | Config   | Configuration        |

---

**See also:** [Builders Guide](Builders-Guide.md) | [Code Examples](Examples.md) | [API Reference](API-Reference.md)
