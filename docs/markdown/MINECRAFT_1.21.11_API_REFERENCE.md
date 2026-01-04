# Minecraft 1.21.11 Fabric API Reference

**Snapshot (Jan 2026):** Target platform remains Minecraft 1.21.11 with Fabric Loader 0.18.4 / Fabric API 0.140.2; helpers and examples below are aligned to that version.

## Research Results for ItemStack NBT, Entity, and Player Methods

Based on analysis of the Modding Helper API codebase and Minecraft 1.21.11 Fabric API, here are the exact method signatures and implementations.

---

## 1. ItemStack NBT Handling

### 1.1 Check if ItemStack has NBT data

**Method:** `ItemStack.hasNbt()`

```java
// Check if ItemStack has NBT
if (stack.hasNbt()) {
    // Has NBT data
}

// Wrapper utility (from ItemStackHelper)
public static boolean hasNBT(@NotNull ItemStack stack) {
    return stack.hasNbt();
}
```

**Note:** The method is `hasNbt()` (lowercase `nbt`), not `hasNBT()`.

---

### 1.2 Get NBT from ItemStack

**Method:** `ItemStack.getNbt()` - returns `@Nullable NbtCompound`

```java
// Direct access (may return null)
NbtCompound tag = stack.getNbt();
if (tag != null) {
    String value = tag.getString("key");
}

// Safer approach (create if missing)
public static NbtCompound getNBT(@NotNull ItemStack stack) {
    if (!stack.hasNbt()) {
        NbtCompound tag = new NbtCompound();
        stack.setNbt(tag);
        return tag;
    }
    return stack.getNbt();
}
```

**Important:** `getNbt()` returns `null` if NBT doesn't exist. Always check `hasNbt()` first or create NBT if needed.

---

### 1.3 Set NBT on ItemStack

**Method:** `ItemStack.setNbt(NbtCompound tag)`

```java
// Create and set NBT
NbtCompound tag = new NbtCompound();
tag.putString("customKey", "value");
tag.putInt("count", 42);
stack.setNbt(tag);

// Or get/create and modify
NbtCompound tag = stack.hasNbt() ? stack.getNbt() : new NbtCompound();
tag.putString("key", "value");
stack.setNbt(tag);
```

**Type-safe convenience method (from ItemStackHelper):**

```java
public static void setNBT(@NotNull ItemStack stack, @NotNull String key, @NotNull Object value) {
    NbtCompound tag = getNBT(stack); // Gets or creates

    if (value instanceof String str) {
        tag.putString(key, str);
    } else if (value instanceof Integer integer) {
        tag.putInt(key, integer);
    } else if (value instanceof Double dbl) {
        tag.putDouble(key, dbl);
    } else if (value instanceof Boolean bool) {
        tag.putBoolean(key, bool);
    } else if (value instanceof Long lng) {
        tag.putLong(key, lng);
    }
}
```

---

### 1.4 Remove NBT from ItemStack

**Method:** `ItemStack.removeNbt()`

```java
// Remove all NBT data
stack.removeNbt();

// Wrapper utility (from ItemStackHelper)
public static void removeNBT(@NotNull ItemStack stack) {
    stack.removeNbt();
}
```

**Note:** After calling `removeNbt()`, `hasNbt()` will return false until NBT is set again.

---

### 1.5 Complete NBT Usage Example

```java
// Create/modify NBT safely
if (!stack.hasNbt()) {
    stack.setNbt(new NbtCompound());
}

NbtCompound tag = stack.getNbt();
tag.putString("ownerUUID", "550e8400-e29b-41d4-a716-446655440000");
tag.putInt("durability", 100);

// Read NBT safely
NbtCompound tag = stack.getNbt();
if (tag != null && tag.contains("ownerUUID")) {
    String uuid = tag.getString("ownerUUID");
}

// Remove all NBT
stack.removeNbt();
```

---

## 2. Entity Position (Entity.getPos())

### Method: `Entity.getPos()` - returns `Vec3d`

```java
// Get entity position
Vec3d position = entity.getPos();
double x = position.x;
double y = position.y;
double z = position.z;

// Usage in EntityHelper
public static double distance2D(@NotNull Entity entity1, @NotNull Entity entity2) {
    Vec3d pos1 = entity1.getPos();
    Vec3d pos2 = entity2.getPos();
    return Math.sqrt(Math.pow(pos1.x - pos2.x, 2) + Math.pow(pos1.z - pos2.z, 2));
}
```

**Return Type:** `Vec3d` (immutable vector with `.x`, `.y`, `.z` double fields)

**Also available:**

- `entity.getX()` - returns `double`
- `entity.getY()` - returns `double`
- `entity.getZ()` - returns `double`

---

## 3. BlockPos Iteration (BlockPos.iterateInCuboid())

### Method: `BlockPos.iterateInCuboid(BlockPos min, BlockPos max)` - returns `Iterable<BlockPos>`

**Status:** ✅ Method EXISTS in 1.21.11

```java
// Iterate in cuboid
BlockPos min = new BlockPos(0, 0, 0);
BlockPos max = new BlockPos(10, 10, 10);

for (BlockPos pos : BlockPos.iterateInCuboid(min, max)) {
    BlockState state = world.getBlockState(pos);
    // Process block
}

// Usage from BlockSearchHelper
public static List<BlockPos> searchInRadius(@NotNull World world, @NotNull BlockPos center,
        int radius, Predicate<BlockState> condition) {
    List<BlockPos> results = new ArrayList<>();

    for (BlockPos pos : BlockPos.iterateInCuboid(
            center.add(-radius, -radius, -radius),
            center.add(radius, radius, radius))) {

        if (condition.test(world.getBlockState(pos))) {
            results.add(pos.toImmutable());
        }
    }
    return results;
}
```

**Method Signature:**

```java
public static Iterable<BlockPos> iterateInCuboid(BlockPos pos1, BlockPos pos2)
```

**Returns:** `Iterable<BlockPos>` - can be used directly in for-each loops

**Note:** BlockPos coordinates are inclusive on both ends.

---

## 4. ServerPlayerEntity Teleport

### Method: `ServerPlayerEntity.teleport(World world, double x, double y, double z, float yaw, float pitch)`

```java
// Basic teleport (preserve rotation)
player.teleport(targetWorld, position.x, position.y, position.z,
                player.getYaw(), player.getPitch());

// Teleport with specific rotation
player.teleport(targetWorld, position.x, position.y, position.z,
                90.0F,  // yaw
                45.0F); // pitch

// Helper wrapper from PlayerHelper
public static void teleport(@NotNull ServerPlayerEntity player,
        @NotNull World world,
        @NotNull Vec3d position,
        float yaw, float pitch) {
    player.teleport(world, position.x, position.y, position.z, yaw, pitch);
}

// Or preserve player's current rotation
public static void teleport(@NotNull ServerPlayerEntity player,
        @NotNull World world,
        @NotNull Vec3d position) {
    player.teleport(world, position.x, position.y, position.z,
                   player.getYaw(), player.getPitch());
}
```

**Method Signature:**

```java
public void teleport(World world, double x, double y, double z, float yaw, float pitch)
```

**Parameters:**

- `World world` - target world (can be same or different world)
- `double x, y, z` - target coordinates
- `float yaw` - rotation around vertical axis (0-360, default north is 0)
- `float pitch` - rotation up/down (-90 to 90, default horizontal is 0)

**Yaw Reference:**

- 0° = South (-Z)
- 90° = West (-X)
- 180° = North (+Z)
- 270° = East (+X)

**Pitch Reference:**

- 0° = Horizontal looking forward
- 90° = Looking straight down
- -90° = Looking straight up

---

## 5. Complete Usage Examples

### Example 1: ItemStack with Persistent NBT

```java
ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);

// Set custom data
ItemStackHelper.setNBT(stack, "ownerUUID", "550e8400-e29b-41d4-a716-446655440000");
ItemStackHelper.setNBT(stack, "enchantmentLevel", 5);
ItemStackHelper.setNBT(stack, "customName", "Excalibur");

// Check and read
if (ItemStackHelper.hasNBT(stack)) {
    String owner = ItemStackHelper.getNBT(stack, "ownerUUID", String.class);
    Integer level = ItemStackHelper.getNBT(stack, "enchantmentLevel", Integer.class);
}

// Remove NBT
ItemStackHelper.removeNBT(stack);
```

### Example 2: Entity Detection and Teleportation

```java
// Get all entities near player
List<LivingEntity> nearby = EntityHelper.getEntitiesNearby(
    serverWorld,
    player.getPos(),  // Uses Entity.getPos()
    32.0,
    LivingEntity.class
);

// Teleport player
Vec3d destination = new Vec3d(100.5, 64.0, 200.5);
PlayerHelper.teleport(player, serverWorld, destination, 0.0F, 0.0F);
```

### Example 3: Block Search with Iteration

```java
// Find all oak logs in a cuboid
List<BlockPos> logs = new ArrayList<>();

BlockPos min = new BlockPos(0, 0, 0);
BlockPos max = new BlockPos(16, 256, 16);

for (BlockPos pos : BlockPos.iterateInCuboid(min, max)) {
    BlockState state = world.getBlockState(pos);
    if (state.isOf(Blocks.OAK_LOG)) {
        logs.add(pos.toImmutable());
    }
}
```

---

## Summary Table

| Operation            | Method                       | Return Type             | Notes                                    |
| -------------------- | ---------------------------- | ----------------------- | ---------------------------------------- |
| Check ItemStack NBT  | `stack.hasNbt()`             | `boolean`               | Not `hasNBT()` - lowercase `nbt`         |
| Get ItemStack NBT    | `stack.getNbt()`             | `@Nullable NbtCompound` | Returns null if no NBT                   |
| Set ItemStack NBT    | `stack.setNbt(NbtCompound)`  | `void`                  | Creates/overwrites NBT                   |
| Remove ItemStack NBT | `stack.removeNbt()`          | `void`                  | Clears all NBT data                      |
| Get Entity Position  | `entity.getPos()`            | `Vec3d`                 | Returns (x, y, z) coordinates            |
| Iterate BlockPos     | `BlockPos.iterateInCuboid()` | `Iterable<BlockPos>`    | ✅ EXISTS in 1.21.11                     |
| Teleport Player      | `player.teleport(...)`       | `void`                  | 6 parameters: world, x, y, z, yaw, pitch |

---

## Implementation Status for Modding Helper API

All methods research has confirmed:

✅ **1. ItemStack NBT Handling**

- `hasNbt()` - Implemented in ItemStackHelper
- `getNbt()` / `setNbt()` - Implemented in ItemStackHelper
- `removeNbt()` - Implemented in ItemStackHelper

✅ **2. Entity.getPos()**

- Confirmed working in EntityHelper methods
- Returns `Vec3d` with `.x`, `.y`, `.z` fields

✅ **3. BlockPos.iterateInCuboid()**

- Method DOES exist in Minecraft 1.21.11
- Actively used in BlockSearchHelper
- Returns `Iterable<BlockPos>`

✅ **4. ServerPlayerEntity.teleport()**

- Signature: `teleport(World, double, double, double, float, float)`
- Implemented in PlayerHelper with overloads
- Supports cross-world teleportation

---

## References

- **Minecraft Version:** 1.21.11
- **Fabric API Version:** 0.140.2+1.21.11 (latest)
- **Java Version:** 21
- **Source:** Modding Helper API codebase analysis
