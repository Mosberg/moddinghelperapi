# API Reference

Complete reference for all methods in Modding Helper API.

**Quick Jump:** [Core](#core-utilities) | [Builders](#builders) | [Items](#items--inventory) | [Entities](#entities--players) | [Blocks](#blocks--world) | [Text](#text--display) | [Network](#network--multiplayer) | [Misc](#miscellaneous)

---

## Core Utilities

### GsonInstance

Singleton JSON serialization with compact and pretty printing.

```java
// Get GSON instances
Gson compact = GsonInstance.compact();        // Compact JSON
Gson pretty = GsonInstance.pretty();          // Pretty-printed JSON

// Usage
String json = compact.toJson(object);
MyObject obj = pretty.fromJson(json, MyObject.class);
```

| Method      | Returns | Purpose                           |
| ----------- | ------- | --------------------------------- |
| `compact()` | `Gson`  | Get compact GSON instance         |
| `pretty()`  | `Gson`  | Get pretty-printing GSON instance |

---

### IdentifierHelper

Resource location creation and validation.

```java
// Create identifiers
Identifier id = IdentifierHelper.of("mymod", "item");
Identifier id2 = IdentifierHelper.of("mymod:item");

// Extract components
String namespace = IdentifierHelper.getNamespace(id);
String path = IdentifierHelper.getPath(id);

// Validate
boolean valid = IdentifierHelper.isValid("a:b");
```

| Method                     | Returns      | Purpose                                        |
| -------------------------- | ------------ | ---------------------------------------------- |
| `of(namespace, path)`      | `Identifier` | Create identifier from components              |
| `of(String)`               | `Identifier` | Create identifier from "namespace:path" string |
| `getNamespace(Identifier)` | `String`     | Get namespace component                        |
| `getPath(Identifier)`      | `String`     | Get path component                             |
| `isValid(String)`          | `boolean`    | Validate identifier format                     |

---

### NBTHelper

Safe NBT data access and modification.

```java
NbtCompound nbt = new NbtCompound();

// Read with defaults
String name = NBTHelper.getString(nbt, "Name", "Default");
int level = NBTHelper.getInt(nbt, "Level", 0);
double x = NBTHelper.getDouble(nbt, "X", 0.0);
boolean flag = NBTHelper.getBoolean(nbt, "Flag", false);

// Write
NBTHelper.putString(nbt, "Name", "Steve");
NBTHelper.putInt(nbt, "Level", 10);
NBTHelper.putDouble(nbt, "X", 100.5);
NBTHelper.putBoolean(nbt, "Flag", true);

// Check/remove
if (NBTHelper.contains(nbt, "Name")) {
    NBTHelper.remove(nbt, "Name");
}
```

| Method                                  | Returns   | Purpose                  |
| --------------------------------------- | --------- | ------------------------ |
| `getString(NbtCompound, key, default)`  | `String`  | Get string with default  |
| `getInt(NbtCompound, key, default)`     | `int`     | Get integer with default |
| `getDouble(NbtCompound, key, default)`  | `double`  | Get double with default  |
| `getBoolean(NbtCompound, key, default)` | `boolean` | Get boolean with default |
| `putString(NbtCompound, key, value)`    | `void`    | Write string             |
| `putInt(NbtCompound, key, value)`       | `void`    | Write integer            |
| `putDouble(NbtCompound, key, value)`    | `void`    | Write double             |
| `putBoolean(NbtCompound, key, value)`   | `void`    | Write boolean            |
| `contains(NbtCompound, key)`            | `boolean` | Check key exists         |
| `remove(NbtCompound, key)`              | `void`    | Remove key               |

---

## Builders

Fluent APIs for complex object creation.

### TextBuilder

Create styled text with method chaining.

```java
MutableText text = new TextBuilder("Hello")
    .bold()
    .color(Formatting.GOLD)
    .append(" World")
    .italic()
    .build();

// Presets
MutableText success = new TextBuilder("✓ Success").success().build();
MutableText error = new TextBuilder("✗ Error").error().build();
MutableText warn = new TextBuilder("⚠ Warning").warning().build();
MutableText info = new TextBuilder("ℹ Info").info().build();
```

| Method              | Returns       | Purpose               |
| ------------------- | ------------- | --------------------- |
| `bold()`            | `TextBuilder` | Make text bold        |
| `italic()`          | `TextBuilder` | Make text italic      |
| `underline()`       | `TextBuilder` | Underline text        |
| `strikethrough()`   | `TextBuilder` | Strike through text   |
| `color(Formatting)` | `TextBuilder` | Set text color        |
| `success()`         | `TextBuilder` | Green color (preset)  |
| `error()`           | `TextBuilder` | Red color (preset)    |
| `warning()`         | `TextBuilder` | Yellow color (preset) |
| `info()`            | `TextBuilder` | Cyan color (preset)   |
| `append(String)`    | `TextBuilder` | Append text           |
| `append(Text)`      | `TextBuilder` | Append styled text    |
| `build()`           | `MutableText` | Build final text      |

---

### Vec3dBuilder

Create and transform 3D vectors.

```java
Vec3d vec = new Vec3dBuilder(10, 5, 3)
    .add(1, 0, 0)
    .scale(2.0)
    .normalize()
    .build();

// Copy and modify
Vec3d newVec = new Vec3dBuilder(oldVec)
    .y(100)
    .scale(0.5)
    .round()
    .build();
```

| Method                                | Returns        | Purpose                 |
| ------------------------------------- | -------------- | ----------------------- |
| `constructor(double, double, double)` | -              | Create from coordinates |
| `constructor(Vec3d)`                  | -              | Copy vector             |
| `x(double)`                           | `Vec3dBuilder` | Set X coordinate        |
| `y(double)`                           | `Vec3dBuilder` | Set Y coordinate        |
| `z(double)`                           | `Vec3dBuilder` | Set Z coordinate        |
| `add(double, double, double)`         | `Vec3dBuilder` | Add offset              |
| `subtract(double, double, double)`    | `Vec3dBuilder` | Subtract offset         |
| `scale(double)`                       | `Vec3dBuilder` | Multiply by factor      |
| `normalize()`                         | `Vec3dBuilder` | Make unit vector        |
| `round()`                             | `Vec3dBuilder` | Round to integers       |
| `floor()`                             | `Vec3dBuilder` | Floor to integers       |
| `invert()`                            | `Vec3dBuilder` | Negate all components   |
| `build()`                             | `Vec3d`        | Build final vector      |

---

### ItemStackBuilder

Create items with fluent API.

```java
ItemStack sword = new ItemStackBuilder(Items.DIAMOND_SWORD)
    .quantity(1)
    .displayName(Text.literal("Legendary"))
    .unbreakable(true)
    .durability(50)
    .build();
```

| Method                 | Returns            | Purpose               |
| ---------------------- | ------------------ | --------------------- |
| `constructor(Item)`    | -                  | Create with item type |
| `quantity(int)`        | `ItemStackBuilder` | Set stack size        |
| `displayName(Text)`    | `ItemStackBuilder` | Set display name      |
| `unbreakable(boolean)` | `ItemStackBuilder` | Set unbreakable flag  |
| `durability(int)`      | `ItemStackBuilder` | Set durability        |
| `build()`              | `ItemStack`        | Build final stack     |

---

## Items & Inventory

### ItemStackHelper

ItemStack creation and operations.

```java
ItemStack stack = ItemStackHelper.of("minecraft:diamond", 64);
ItemStack stack2 = ItemStackHelper.of(Items.DIAMOND, 1);

if (ItemStackHelper.isEmpty(stack)) {
    logInfo("Stack is empty");
}

int space = ItemStackHelper.getRemainingSpace(stack);
if (space > 0) {
    stack.increment(1);
}
```

| Method                           | Returns     | Purpose                 |
| -------------------------------- | ----------- | ----------------------- |
| `of(String, int)`                | `ItemStack` | Create from item ID     |
| `of(Item, int)`                  | `ItemStack` | Create from item object |
| `isEmpty(ItemStack)`             | `boolean`   | Check if stack empty    |
| `isFull(ItemStack)`              | `boolean`   | Check if stack full     |
| `getRemainingSpace(ItemStack)`   | `int`       | Get available capacity  |
| `matches(ItemStack, Item)`       | `boolean`   | Check item type         |
| `canStack(ItemStack, ItemStack)` | `boolean`   | Can stacks combine      |

---

### InventoryHelper

Inventory management and item transfers.

```java
Inventory inv = player.getInventory();

int diamondCount = InventoryHelper.countItem(inv, Items.DIAMOND);
InventoryHelper.addItem(inv, new ItemStack(Items.DIAMOND, 64));
InventoryHelper.removeItem(inv, Items.DIAMOND, 10);
InventoryHelper.clearInventory(inv);
```

| Method                             | Returns   | Purpose                |
| ---------------------------------- | --------- | ---------------------- |
| `countItem(Inventory, Item)`       | `int`     | Count item occurrences |
| `addItem(Inventory, ItemStack)`    | `boolean` | Add item to inventory  |
| `removeItem(Inventory, Item, int)` | `boolean` | Remove items           |
| `clearInventory(Inventory)`        | `void`    | Empty entire inventory |
| `isFull(Inventory)`                | `boolean` | Check if full          |
| `isEmpty(Inventory)`               | `boolean` | Check if empty         |

---

## Entities & Players

### EntityHelper

Entity utilities and queries.

```java
Entity target = ...;
Entity observer = ...;

if (EntityHelper.isLiving(target)) {
    double dist = EntityHelper.distance(observer, target);
    Vec3d dir = EntityHelper.getDirection(observer, target);
}

Set<Entity> passengers = EntityHelper.getPassengers(target);
EntityHelper.mount(target, observer);
```

| Method                                     | Returns       | Purpose                  |
| ------------------------------------------ | ------------- | ------------------------ |
| `isLiving(Entity)`                         | `boolean`     | Check if living entity   |
| `isPlayer(Entity)`                         | `boolean`     | Check if player          |
| `distance(Entity, Entity)`                 | `double`      | Get distance between     |
| `distanceSquared(Entity, Entity)`          | `double`      | Get distance² (faster)   |
| `distanceHorizontal(Entity, Entity)`       | `double`      | Horizontal distance only |
| `getPos(Entity)`                           | `Vec3d`       | Get entity position      |
| `isOnGround(Entity)`                       | `boolean`     | Check if on ground       |
| `isInLava(Entity)`                         | `boolean`     | Check if in lava         |
| `isInWater(Entity)`                        | `boolean`     | Check if in water        |
| `getDirection(Entity, Entity)`             | `Vec3d`       | Get direction between    |
| `getPassengers(Entity)`                    | `Set<Entity>` | Get riding entities      |
| `getVehicle(Entity)`                       | `Entity`      | Get what entity rides    |
| `mount(Entity, Entity)`                    | `void`        | Ride another entity      |
| `dismount(Entity)`                         | `void`        | Stop riding              |
| `isRiding(Entity)`                         | `boolean`     | Check if riding          |
| `teleport(Entity, double, double, double)` | `void`        | Teleport entity          |
| `damage(Entity, float)`                    | `void`        | Damage entity            |
| `heal(LivingEntity, float)`                | `void`        | Heal entity              |

---

### PlayerHelper

Player-specific utilities.

```java
ServerPlayerEntity player = PlayerHelper.get(server, "Steve");
if (player != null && PlayerHelper.isAlive(player)) {
    PlayerHelper.message(player, "Hello!");
    float health = PlayerHelper.getHealth(player);
}
```

| Method                                                 | Returns              | Purpose             |
| ------------------------------------------------------ | -------------------- | ------------------- |
| `get(MinecraftServer, String)`                         | `ServerPlayerEntity` | Get player by name  |
| `message(ServerPlayerEntity, String)`                  | `void`               | Send chat message   |
| `sendMessage(ServerPlayerEntity, Text)`                | `void`               | Send styled message |
| `isCreative(ServerPlayerEntity)`                       | `boolean`            | Check creative mode |
| `isAlive(ServerPlayerEntity)`                          | `boolean`            | Check if alive      |
| `getHealth(ServerPlayerEntity)`                        | `float`              | Get current health  |
| `getMaxHealth(ServerPlayerEntity)`                     | `float`              | Get max health      |
| `damage(ServerPlayerEntity, float)`                    | `void`               | Damage player       |
| `heal(ServerPlayerEntity, float)`                      | `void`               | Heal player         |
| `teleport(ServerPlayerEntity, double, double, double)` | `void`               | Teleport player     |

---

## Blocks & World

### BlockSearchHelper

Find blocks by criteria.

```java
List<BlockPos> blocks = BlockSearchHelper.findInRadius(
    world, playerPos, 50, Blocks.DIAMOND_ORE
);

List<BlockPos> boxes = BlockSearchHelper.findInBox(
    world, minPos, maxPos, state -> state.getMaterial().isSolid()
);
```

| Method                                            | Returns          | Purpose                |
| ------------------------------------------------- | ---------------- | ---------------------- |
| `findInRadius(World, BlockPos, int, Block)`       | `List<BlockPos>` | Find blocks in sphere  |
| `findInBox(World, BlockPos, BlockPos, Predicate)` | `List<BlockPos>` | Find blocks in box     |
| `findNearest(World, BlockPos, int, Block)`        | `BlockPos`       | Find nearest block     |
| `countInRadius(World, BlockPos, int, Block)`      | `int`            | Count blocks in sphere |

---

### BlockStateHelper

Block state property manipulation.

```java
BlockState state = world.getBlockState(pos);

if (BlockStateHelper.hasProperty(state, Properties.LIT)) {
    boolean lit = BlockStateHelper.getBoolean(state, Properties.LIT);
    BlockState newState = BlockStateHelper.setBoolean(state, Properties.LIT, !lit);
    world.setBlockState(pos, newState);
}
```

| Method                                      | Returns      | Purpose               |
| ------------------------------------------- | ------------ | --------------------- |
| `hasProperty(BlockState, Property)`         | `boolean`    | Check property exists |
| `getBoolean(BlockState, Property)`          | `boolean`    | Get boolean property  |
| `getInteger(BlockState, Property)`          | `int`        | Get integer property  |
| `setBoolean(BlockState, Property, boolean)` | `BlockState` | Set boolean property  |
| `setInteger(BlockState, Property, int)`     | `BlockState` | Set integer property  |

---

### RedstoneHelper

Redstone signal detection.

```java
if (RedstoneHelper.isPowered(world, pos)) {
    activateFeature();
}

int signal = RedstoneHelper.getSignalStrength(world, pos, Direction.UP);
```

| Method                                          | Returns         | Purpose                   |
| ----------------------------------------------- | --------------- | ------------------------- |
| `isPowered(World, BlockPos)`                    | `boolean`       | Check if powered          |
| `hasAnyPower(World, BlockPos)`                  | `boolean`       | Check any power           |
| `getSignalStrength(World, BlockPos, Direction)` | `int`           | Get signal from direction |
| `getPoweredPositions(World, BlockPos)`          | `Set<BlockPos>` | Find powered neighbors    |

---

### BlockEntityHelper

Block entity data access.

```java
BlockEntity be = world.getBlockEntity(pos);
if (be != null) {
    String name = BlockEntityHelper.getString(be, "CustomName", "Unnamed");
    int level = BlockEntityHelper.getInt(be, "Level", 0);
}
```

| Method                                   | Returns       | Purpose             |
| ---------------------------------------- | ------------- | ------------------- |
| `getBlockEntity(World, BlockPos)`        | `BlockEntity` | Get block entity    |
| `getNBT(BlockEntity)`                    | `NbtCompound` | Get entity NBT      |
| `setNBT(BlockEntity, NbtCompound)`       | `void`        | Set entity NBT      |
| `getString(BlockEntity, String, String)` | `String`      | Get string property |
| `getInt(BlockEntity, String, int)`       | `int`         | Get int property    |

---

## Text & Display

### TextHelper

Text component creation.

```java
MutableText msg = TextHelper.literal("Hello");
MutableText bold = TextHelper.bold("Bold");
MutableText colored = TextHelper.colored("Gold", Formatting.GOLD);
MutableText success = TextHelper.success("✓ Success");
```

| Method                        | Returns       | Purpose           |
| ----------------------------- | ------------- | ----------------- |
| `literal(String)`             | `MutableText` | Create basic text |
| `bold(String)`                | `MutableText` | Bold text         |
| `italic(String)`              | `MutableText` | Italic text       |
| `colored(String, Formatting)` | `MutableText` | Colored text      |
| `success(String)`             | `MutableText` | Green text        |
| `error(String)`               | `MutableText` | Red text          |
| `warning(String)`             | `MutableText` | Yellow text       |
| `info(String)`                | `MutableText` | Cyan text         |

---

## Network & Multiplayer

### NetworkHelper

Networking and packet operations.

```java
NetworkHelper.registerConnectionHandler(
    Identifier.of("mymod", "join"),
    player -> sendWelcomeMessage(player)
);

NetworkHelper.broadcastPacket(
    playerList,
    new MyPayload(...)
);
```

| Method                                               | Returns   | Purpose                  |
| ---------------------------------------------------- | --------- | ------------------------ |
| `registerConnectionHandler(Identifier, Consumer)`    | `void`    | Handle player join       |
| `registerDisconnectionHandler(Identifier, Consumer)` | `void`    | Handle player leave      |
| `broadcastPacket(List, Packet)`                      | `void`    | Send to multiple players |
| `sendToPlayer(ServerPlayerEntity, Packet)`           | `void`    | Send to single player    |
| `isPlayerConnected(MinecraftServer, UUID)`           | `boolean` | Check if player online   |

---

### ChatHelper

Chat message utilities.

```java
ChatHelper.broadcastMessage(server, "Server message");
ChatHelper.sendPrivateMessage(player1, player2, "Secret");
```

| Method                                                               | Returns | Purpose                  |
| -------------------------------------------------------------------- | ------- | ------------------------ |
| `broadcastMessage(MinecraftServer, String)`                          | `void`  | Send to all players      |
| `sendPrivateMessage(ServerPlayerEntity, ServerPlayerEntity, String)` | `void`  | Send private message     |
| `formatMessage(String, Formatting)`                                  | `Text`  | Create formatted message |

---

## Miscellaneous

### VectorHelper

Vector mathematics.

```java
Vec3d v1 = new Vec3d(0, 0, 0);
Vec3d v2 = new Vec3d(10, 5, 3);

double dist = VectorHelper.distance(v1, v2);
Vec3d normalized = VectorHelper.normalize(v2);
Vec3d midpoint = VectorHelper.midpoint(v1, v2);
```

| Method                          | Returns  | Purpose                  |
| ------------------------------- | -------- | ------------------------ |
| `distance(Vec3d, Vec3d)`        | `double` | Distance between vectors |
| `squaredDistance(Vec3d, Vec3d)` | `double` | Distance² (faster)       |
| `normalize(Vec3d)`              | `Vec3d`  | Unit vector              |
| `scale(Vec3d, double)`          | `Vec3d`  | Multiply vector          |
| `midpoint(Vec3d, Vec3d)`        | `Vec3d`  | Midpoint between         |
| `length(Vec3d)`                 | `double` | Vector length            |
| `add(Vec3d, Vec3d)`             | `Vec3d`  | Vector addition          |
| `subtract(Vec3d, Vec3d)`        | `Vec3d`  | Vector subtraction       |
| `dotProduct(Vec3d, Vec3d)`      | `double` | Dot product              |

---

### RegistryHelper

Registry access.

```java
Item diamond = RegistryHelper.getItem("minecraft:diamond");
Block stone = RegistryHelper.getBlock("minecraft:stone");

if (RegistryHelper.isItemRegistered("mymod:custom")) {
    // Item exists
}
```

| Method                      | Returns   | Purpose                 |
| --------------------------- | --------- | ----------------------- |
| `getItem(String)`           | `Item`    | Get item from registry  |
| `getBlock(String)`          | `Block`   | Get block from registry |
| `isItemRegistered(String)`  | `boolean` | Check if item exists    |
| `isBlockRegistered(String)` | `boolean` | Check if block exists   |
| `getItemCount()`            | `int`     | Total registered items  |
| `getBlockCount()`           | `int`     | Total registered blocks |

---

### ParticleHelper

Particle effects.

```java
new ParticleHelper(ParticleTypes.FLAME)
    .count(10)
    .speed(1.0)
    .spread(2.0)
    .spawn(world, pos);
```

| Method                | Returns          | Purpose             |
| --------------------- | ---------------- | ------------------- |
| `count(int)`          | `ParticleHelper` | Number of particles |
| `speed(double)`       | `ParticleHelper` | Particle speed      |
| `spread(double)`      | `ParticleHelper` | Spread radius       |
| `spawn(World, Vec3d)` | `void`           | Spawn particles     |

---

**Want examples?** → [Examples](Examples.md) | [Patterns](Common-Patterns.md)
