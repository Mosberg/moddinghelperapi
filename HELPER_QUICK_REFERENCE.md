# Helper Classes Quick Reference

**Status:** ✅ All 9 Phase 1 helpers enhanced and verified
**Build:** ✅ SUCCESS (6s)
**Artifacts:** 3 JARs generated (407 KB total)

---

## Quick API Reference

### GsonInstance

```java
// Compact JSON: {"key":"value"}
Gson compact = GsonInstance.compact();

// Pretty JSON with indentation
Gson pretty = GsonInstance.pretty();
```

### IdentifierHelper

```java
// Create identifiers
Identifier id = IdentifierHelper.of("mymod", "item");
Identifier id = IdentifierHelper.of("mymod:item");

// Extract components
String ns = IdentifierHelper.getNamespace(id);     // "mymod"
String path = IdentifierHelper.getPath(id);        // "item"
boolean valid = IdentifierHelper.isValid("a:b");   // true
```

### ItemStackHelper

```java
// Create stacks
ItemStack stack = ItemStackHelper.of("minecraft:diamond", 64);
ItemStack stack = ItemStackHelper.of(Items.DIAMOND, 1);

// Query stacks
boolean empty = ItemStackHelper.isEmpty(stack);
boolean full = ItemStackHelper.isFull(stack);
int remaining = ItemStackHelper.getRemainingSpace(stack);
boolean match = ItemStackHelper.matches(stack, Items.DIAMOND);
```

### EntityHelper

```java
// Type checking
boolean isLiving = EntityHelper.isLiving(entity);
boolean isPlayer = EntityHelper.isPlayer(entity);

// Distance calculations
double dist = EntityHelper.distance(e1, e2);
double dist2 = EntityHelper.distanceSquared(e1, e2);  // Faster, no sqrt
double horz = EntityHelper.distanceHorizontal(e1, e2);

// Position & state
Vec3d pos = EntityHelper.getPos(entity);
boolean onGround = EntityHelper.isOnGround(entity);
boolean inLava = EntityHelper.isInLava(entity);
```

### PlayerHelper

```java
// Find player
ServerPlayerEntity player = PlayerHelper.get(server, "Steve");

// Send messages
PlayerHelper.message(player, "Hello!");
PlayerHelper.sendMessage(player, TextHelper.success("Success!"));

// Query player state
boolean creative = PlayerHelper.isCreative(player);
boolean alive = PlayerHelper.isAlive(player);
float health = PlayerHelper.getHealth(player);
float maxHealth = PlayerHelper.getMaxHealth(player);
```

### NBTHelper

```java
NbtCompound nbt = new NbtCompound();

// Read with defaults
String name = NBTHelper.getString(nbt, "Name", "Unknown");
int level = NBTHelper.getInt(nbt, "Level", 0);
double x = NBTHelper.getDouble(nbt, "X", 0.0);
boolean flag = NBTHelper.getBoolean(nbt, "Flag", false);

// Write
NBTHelper.putString(nbt, "Name", "Steve");
NBTHelper.putInt(nbt, "Level", 10);
NBTHelper.putDouble(nbt, "X", 100.5);
NBTHelper.putBoolean(nbt, "Flag", true);

// Check/remove
boolean has = NBTHelper.contains(nbt, "Name");
NBTHelper.remove(nbt, "Name");
```

### TextHelper

```java
// Basic text
MutableText text = TextHelper.literal("Hello");

// Formatting
MutableText bold = TextHelper.bold("Bold text");
MutableText italic = TextHelper.italic("Italic text");

// Colored text
MutableText colored = TextHelper.colored("Text", Formatting.GOLD);
MutableText success = TextHelper.success("Success!");     // Green
MutableText error = TextHelper.error("Error!");           // Red
MutableText warn = TextHelper.warning("Warning");         // Yellow
MutableText info = TextHelper.info("Info");              // Cyan

// Chain formatting
MutableText msg = TextHelper.success("✓ ")
    .append(TextHelper.italic("formatted message"));
```

### VectorHelper

```java
Vec3d v1 = new Vec3d(0, 0, 0);
Vec3d v2 = new Vec3d(10, 5, 3);

// Distance
double dist = VectorHelper.distance(v1, v2);
double dist2 = VectorHelper.squaredDistance(v1, v2);  // Faster

// Direction
Vec3d dir = VectorHelper.direction(v1, v2);  // Normalized

// Vector operations
Vec3d norm = VectorHelper.normalize(v1);
Vec3d scaled = VectorHelper.scale(v1, 2.0);
Vec3d mid = VectorHelper.midpoint(v1, v2);
double len = VectorHelper.length(v1);
Vec3d sum = VectorHelper.add(v1, v2);
Vec3d diff = VectorHelper.subtract(v1, v2);
double dot = VectorHelper.dotProduct(v1, v2);
```

### RegistryHelper

```java
// Item registry
Item diamond = RegistryHelper.getItem("minecraft:diamond");
boolean has = RegistryHelper.isItemRegistered("mymod:custom");
int count = RegistryHelper.getItemCount();

// Block registry
Block stone = RegistryHelper.getBlock("minecraft:stone");
boolean hasBlock = RegistryHelper.isBlockRegistered("mymod:custom");
int blockCount = RegistryHelper.getBlockCount();
```

---

## Design Patterns

### Null Safety

All methods use `@NotNull` and `@Nullable` annotations:

```java
// Method guarantees non-null return and null parameters forbidden
@NotNull
public static ItemStack of(@NotNull String itemId, int count)

// Method may return null if not found
@Nullable
public static ServerPlayerEntity get(@NotNull MinecraftServer server, @NotNull String name)
```

### Error Handling

Helpers use sensible defaults instead of throwing exceptions:

```java
// Returns default if missing instead of throwing
String name = NBTHelper.getString(nbt, "Name", "Unknown");
// Safe even if key doesn't exist
```

### Static-Only Utilities

All helpers are final utility classes:

```java
public final class MyHelper {
    private MyHelper() {}  // Prevent instantiation
    public static void method() { }  // All methods static
}
```

---

## Dependencies & Integration

**Provided by Modding Helper API:**

- GSON (bundled in JAR)
- Jetbrains annotations (@NotNull, @Nullable)
- Fabric API 0.138.3+1.21.11
- Minecraft 1.21.11 (via Fabric Loom)

**Java Version:** 21 (enforced)
**Minecraft Version:** 1.21.11 (with Yarn mappings)

---

## Files Modified

### Enhanced Phase 1 Helpers (9 total)

- ✅ GsonInstance.java
- ✅ IdentifierHelper.java
- ✅ ItemStackHelper.java
- ✅ EntityHelper.java
- ✅ PlayerHelper.java
- ✅ NBTHelper.java
- ✅ TextHelper.java
- ✅ VectorHelper.java
- ✅ RegistryHelper.java

### Documentation

- ✅ ENHANCEMENT_SUMMARY.md (full details)
- ✅ MINECRAFT_1.21.11_API_REFERENCE.md (API notes)
- ✅ .github/copilot-instructions.md (project guide)

---

## Getting Started

1. **Add as Dependency:** Include in `fabric.mod.json`:

```json
{
  "depends": {
    "fabricloader": ">=0.18.4",
    "moddinghelperapi": "*"
  }
}
```

2. **Use Helpers:** Import and use in your mod:

```java
import dk.mosberg.util.*;

public class MyMod {
    public void onServerStart() {
        ItemStack stack = ItemStackHelper.of("minecraft:diamond", 1);
        MutableText msg = TextHelper.success("Mod loaded!");
    }
}
```

3. **Refer to Examples:** Check this guide and JavaDoc for method signatures

---

## Compilation & Building

**Build the API:**

```bash
./gradlew.bat build
```

**Run Minecraft with API loaded:**

```bash
./gradlew.bat runClient
```

**Generate JavaDoc:**

```bash
./gradlew.bat javadoc
```

**Artifacts Location:**

- `build/libs/moddinghelperapi-*.jar` - Compiled mod (add to mods folder)
- `build/libs/moddinghelperapi-*-sources.jar` - Source code
- `build/libs/moddinghelperapi-*-javadoc.jar` - Documentation

---

## Version Info

- **API Version:** 1.0.0
- **Minecraft:** 1.21.11
- **Fabric API:** 0.138.3+1.21.11
- **Fabric Loader:** 0.18.4
- **Java:** 21
- **Last Updated:** January 2026

---

**For detailed documentation, see [ENHANCEMENT_SUMMARY.md](ENHANCEMENT_SUMMARY.md)**
