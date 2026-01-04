# FAQ - Frequently Asked Questions

Common questions about Modding Helper API.

## Installation & Setup

### Q: How do I add the API to my mod?

**A:** Add this to your `fabric.mod.json` in the `depends` section:

```json
"moddinghelperapi": "*"
```

Then refresh your Gradle project and you're ready to use it.

[Full setup guide →](Getting-Started.md)

---

### Q: Will it work with my existing mod?

**A:** Yes! The API is backward compatible and doesn't interfere with existing code. Just add the dependency and import the helpers you need.

---

### Q: What Minecraft version does it support?

**A:** Currently **Minecraft 1.21.11** with Fabric Loader 0.18.4+.

---

## Using the Helpers

### Q: Which helper should I use for [task]?

**A:** Check [Helpers Overview](Helpers-Overview.md) which groups all 28 helpers by function. Or use Ctrl+F to search for keywords.

Common tasks:

- **Create items:** ItemStackHelper, ItemStackBuilder
- **Send messages:** TextHelper, TextBuilder, ChatHelper
- **Work with vectors:** VectorHelper, Vec3dBuilder
- **Find blocks:** BlockSearchHelper
- **Manage inventory:** InventoryHelper
- **Store data:** NBTHelper
- **Check entities:** EntityHelper

---

### Q: What's the difference between a helper and a builder?

**A:**

- **Helpers** are static utility methods (e.g., `ItemStackHelper.of(...)`)
- **Builders** are fluent interfaces for complex objects (e.g., `new TextBuilder(...)`)

Both are designed to reduce boilerplate code.

---

### Q: Can I use helpers in client-side code?

**A:** Yes! The API is server-safe, meaning you can use helpers on both server and client. Just avoid importing client-only Minecraft classes in your server code.

---

## Method Usage

### Q: How do I create an ItemStack?

**A:** Two ways:

```java
// Simple way
ItemStack stack = ItemStackHelper.of("minecraft:diamond", 64);

// Complex way with builder
ItemStack stack = new ItemStackBuilder(Items.DIAMOND)
    .quantity(64)
    .displayName("Special Diamond")
    .enchant(Enchantments.UNBREAKING, 3)
    .build();
```

---

### Q: How do I get data from NBT?

**A:** Use `NBTHelper` with a default fallback:

```java
// Returns "Unknown" if key doesn't exist
String name = NBTHelper.getString(nbt, "Name", "Unknown");

// Returns 0 if key doesn't exist
int level = NBTHelper.getInt(nbt, "Level", 0);

// Returns false if key doesn't exist
boolean flag = NBTHelper.getBoolean(nbt, "Flag", false);
```

---

### Q: How do I send a styled message to a player?

**A:** Use `TextBuilder` with styling:

```java
Text message = new TextBuilder("Welcome!")
    .success()      // Green
    .bold()
    .build();

player.sendMessage(message, false);
```

---

### Q: How do I find blocks in the world?

**A:** Use `BlockSearchHelper`:

```java
// Find all diamond ore in 50-block radius
List<BlockPos> diamonds = BlockSearchHelper.findInRadius(
    world,
    player.getBlockPos(),
    50,
    Blocks.DIAMOND_ORE
);

// Find nearest gold ore
BlockPos nearest = BlockSearchHelper.findNearest(
    world,
    player.getBlockPos(),
    100,
    Blocks.GOLD_ORE
);
```

---

## Troubleshooting

### Q: "Cannot find dk.mosberg.util"

**A:** The dependency isn't resolved. Try:

1. Verify `fabric.mod.json` has correct syntax
2. Run `./gradlew clean` to clear cache
3. Refresh Gradle in your IDE
4. Rebuild your project

[Full troubleshooting →](Troubleshooting.md)

---

### Q: "Method doesn't exist"

**A:** Check:

1. [API Reference](API-Reference.md) for correct method name
2. [Minecraft 1.21.11 Notes](Minecraft-API-Notes.md) for version-specific changes
3. You're importing from `dk.mosberg.util.*`

---

### Q: My code compiles but nothing happens

**A:** Check:

1. Are you in a server environment? (Not client-only)
2. Is your mod initializer being called? (Add log statement)
3. Are there exceptions in the Minecraft log?
4. Did you build with `./gradlew build`?

---

### Q: JARs won't load in game

**A:** Ensure:

1. `fabric.mod.json` has correct `moddinghelperapi` dependency
2. The API JAR is in the `mods` folder
3. You used `./gradlew build` to build
4. No typos in the mod ID

---

## API Design

### Q: Why does NBT return with a default value?

**A:** NBT operations can fail if the key doesn't exist. Instead of throwing exceptions, helpers return sensible defaults:

```java
// If "Name" doesn't exist, returns "Unknown"
String name = NBTHelper.getString(nbt, "Name", "Unknown");

// Safer than:
// String name = nbt.getString("Name"); // Throws if missing
```

---

### Q: Why use builders instead of constructors?

**A:** Builders are more readable and flexible:

```java
// Constructor (unclear intent)
new ItemStack(Items.DIAMOND, 10, null, null);

// Builder (very clear)
new ItemStackBuilder(Items.DIAMOND)
    .quantity(10)
    .displayName("Special")
    .build();
```

---

### Q: Do helpers have overhead?

**A:** No! Helpers are thin wrappers around vanilla Minecraft code. They add no performance overhead—they just make code cleaner and safer.

---

## Integration

### Q: Can I use this with other mod libraries?

**A:** Yes! The API doesn't conflict with other libraries. It's purely additive.

---

### Q: How do I make my API depend on this?

**A:** Add to your `fabric.mod.json`:

```json
{
  "depends": {
    "moddinghelperapi": "*"
  }
}
```

Now other mods can depend on your mod and use the API through it.

---

### Q: Can I use this with custom datapacks?

**A:** Yes! The API helps you work with vanilla structures, which datapacks can modify.

---

## Performance

### Q: Will using helpers slow down my code?

**A:** No. Helpers are optimized static methods—there's no performance penalty.

---

### Q: What's the best way to search for blocks?

**A:** `BlockSearchHelper.findInRadius()` is optimized for chunk iteration. For large searches, consider caching results or using predicates to filter efficiently.

---

### Q: Can I do expensive operations in events?

**A:** Be careful with expensive operations in frequently-called events. Consider:

- Caching results
- Running async with `CompletableFuture`
- Limiting search radius/frequency

---

## Troubleshooting Advanced

### Q: How do I debug helper usage?

**A:** Add logging:

```java
var logger = LogHelper.getLogger("mymod", "Debug");

ItemStack stack = ItemStackHelper.of("minecraft:diamond", 1);
logger.info("Created stack: {}", stack);

List<BlockPos> blocks = BlockSearchHelper.findInRadius(...);
logger.info("Found {} blocks", blocks.size());
```

---

### Q: How do I report a bug?

**A:** Check [GitHub Issues](https://github.com/mosberg/moddinghelperapi/issues) and create a new issue with:

1. What you did
2. What happened
3. What you expected
4. Your code
5. Full error log

---

## Documentation

### Q: Where can I find more examples?

**A:**

- **[Code Examples](Examples.md)** - 50+ working examples
- **[Common Patterns](Common-Patterns.md)** - Reusable patterns
- **[API Reference](API-Reference.md)** - All methods

---

### Q: Where's the JavaDoc?

**A:** Available in:

- Your IDE (hover over methods)
- `build/libs/moddinghelperapi-*-javadoc.jar`
- Generated from source code

---

## Getting Help

### I still have questions!

Check:

1. **[Quick Start](Quick-Start.md)** - Basic usage
2. **[Code Examples](Examples.md)** - Working examples
3. **[Helpers Overview](Helpers-Overview.md)** - All utilities
4. **[Common Patterns](Common-Patterns.md)** - Best practices
5. **[Troubleshooting](Troubleshooting.md)** - Common issues

Still stuck? Create an [issue on GitHub](https://github.com/mosberg/moddinghelperapi/issues).

---

**See also:** [Troubleshooting](Troubleshooting.md) | [Common Patterns](Common-Patterns.md) | [Code Examples](Examples.md)
