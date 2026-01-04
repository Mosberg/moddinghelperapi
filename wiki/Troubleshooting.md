# Troubleshooting

Solutions to common issues with Modding Helper API.

## Installation Issues

### Issue: "Cannot find dk.mosberg.util"

**Symptoms:**

- IDE shows red underlines on `import dk.mosberg.util.*`
- Build fails: "Cannot find symbol"
- Gradle says dependency not found

**Causes:**

- `fabric.mod.json` doesn't include the dependency
- Gradle cache corrupted
- Internet connection issue
- IDE cache out of sync

**Solutions:**

1. **Verify `fabric.mod.json`:**

```json
"depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": "~1.21.11",
    "fabric-api": "*",
    "moddinghelperapi": "*"
}
```

2. **Clean and rebuild:**

```bash
./gradlew clean
./gradlew build
```

3. **Refresh IDE:**

   - **IntelliJ:** Right-click project → Gradle → Refresh Gradle Project
   - **Eclipse:** Right-click project → Gradle → Refresh Projects
   - **VS Code:** Reload window (Ctrl+Shift+P → Reload Window)

4. **Check internet:**
   - Gradle downloads dependencies from Maven
   - Ensure stable internet connection
   - Try building again

---

### Issue: "Version of moddinghelperapi not found"

**Symptoms:**

- Build fails: "Could not find moddinghelperapi"
- Error mentions "version 1.0.0 not found"

**Causes:**

- Using non-existent version number
- Using old version syntax
- Repository not configured

**Solutions:**

1. **Use valid version:**

```json
// ✅ Correct
"moddinghelperapi": "*"              // Any version
"moddinghelperapi": "1.0.0"          // Specific version
"moddinghelperapi": "1.+"            // Latest 1.x

// ❌ Wrong
"moddinghelperapi": "latest"         // Not a version
"moddinghelperapi": "2.0.0"          // Doesn't exist yet
```

2. **Check available versions:**
   - Visit [GitHub Releases](https://github.com/mosberg/moddinghelperapi/releases)
   - Use versions listed there

---

### Issue: Dependency Conflicts

**Symptoms:**

- Error: "Conflict between versions"
- Multiple mods depend on different versions

**Causes:**

- Your mod depends on old version
- Another dependency uses different version
- Version constraints incompatible

**Solutions:**

1. **Use compatible ranges:**

```json
// Flexible version range
"moddinghelperapi": "1.+"            // Any 1.x version

// Specific versions
"moddinghelperapi": ">=1.0.0,<2.0"   // 1.0.0 to 1.x.x
```

2. **Check dependencies:**

```bash
./gradlew dependencies
```

3. **Exclude conflicting versions:**

```gradle
modImplementation('com.example:other-mod:1.0') {
    exclude group: 'dk.mosberg', module: 'moddinghelperapi'
}
```

---

## Compilation Issues

### Issue: "Method doesn't exist"

**Symptoms:**

- Build fails: "Cannot find method"
- Red underlines on helper methods
- IDE autocomplete doesn't show method

**Causes:**

- Method doesn't exist in current version
- Using wrong helper class
- Typo in method name
- Method changed in 1.21.11

**Solutions:**

1. **Check method name:**

   - Verify in [API Reference](API-Reference.md)
   - Check JavaDoc in IDE (hover over class)
   - Search GitHub source code

2. **Check helper class:**

```java
// ✅ Correct
ItemStackHelper.of(...)
ItemStackHelper.isEmpty(...)

// ❌ Wrong
ItemStack.of(...)              // Not on ItemStack
ItemStackHelper.empty(...)     // Wrong method name
```

3. **Check 1.21.11 changes:**
   - See [Minecraft 1.21.11 Notes](Minecraft-API-Notes.md)
   - Some methods renamed or removed in 1.21.11

---

### Issue: Generic Type Errors

**Symptoms:**

- Build fails: "Cannot assign"
- Error: "Type mismatch"
- Generic type warnings

**Causes:**

- Type parameter mismatch
- Generic erasure issues
- Builder return type mismatch

**Solutions:**

1. **Use correct types:**

```java
// ✅ Correct
List<BlockPos> blocks = BlockSearchHelper.findInRadius(world, pos, 50, Blocks.DIAMOND_ORE);

// ❌ Wrong
var blocks = BlockSearchHelper.findInRadius(...);  // Type not inferred
BlockPos pos = BlockSearchHelper.findInRadius(...); // Wrong type
```

2. **Explicit type parameters:**

```java
// If inference fails, be explicit
List<BlockPos> blocks = BlockSearchHelper.<BlockPos>findInRadius(...);
```

---

### Issue: Import Conflicts

**Symptoms:**

- Error: "Conflicting imports"
- Autocomplete shows wrong class
- IDE can't resolve symbol

**Causes:**

- Multiple classes with same name
- Wildcard import conflict
- Wrong import statement

**Solutions:**

1. **Use specific imports:**

```java
// ✅ Specific (preferred)
import dk.mosberg.util.ItemStackHelper;
import dk.mosberg.util.TextHelper;

// ⚠️ Wildcard (can cause conflicts)
import dk.mosberg.util.*;
```

2. **Resolve conflicts:**

```java
// If conflict exists, use full name
dk.mosberg.util.ItemStackHelper.of(...)
```

3. **Check for duplicate names:**

```bash
# Grep for conflicting class names
grep -r "class ItemStackHelper" src/
```

---

## Runtime Issues

### Issue: "Mod won't load in game"

**Symptoms:**

- Game crashes on startup
- Mod doesn't appear in mod list
- Error in logs mentioning moddinghelperapi

**Causes:**

- JAR not in mods folder
- Dependency not found
- Version mismatch
- Corrupted JAR file

**Solutions:**

1. **Verify JAR location:**

   - Place JAR in: `run/mods/` or `~/.minecraft/mods/`
   - Check file exists and isn't corrupted

2. **Verify dependency:**

```json
"depends": {
    "moddinghelperapi": "*"
}
```

3. **Check game logs:**

   - Full log at: `logs/latest.log`
   - Search for "moddinghelperapi"
   - Look for version mismatches

4. **Rebuild JAR:**

```bash
./gradlew clean build
cp build/libs/yourmod-1.0.0.jar run/mods/
```

---

### Issue: "Code compiles but nothing happens"

**Symptoms:**

- No errors
- Code runs but no effect
- Features don't work as expected

**Causes:**

- Code not executing (wrong location/event)
- Silent exceptions
- Wrong environment (client vs server)
- Features not enabled

**Solutions:**

1. **Add logging:**

```java
var logger = LogHelper.getLogger("mymod", "Debug");
logger.info("Code is executing!");

ItemStack stack = ItemStackHelper.of("minecraft:diamond", 1);
logger.info("Created item: {}", stack);
```

2. **Check environment:**

```java
// ✅ Works on server
if (player instanceof ServerPlayerEntity) {
    // Safe to use helpers
}

// ❌ May not work on client
if (player instanceof AbstractClientPlayer) {
    // Different code path
}
```

3. **Check event registration:**

```java
// Make sure event is registered in ModInitializer
ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
    // This runs when players join
});
```

4. **Check game logs:**
   - Look for exceptions
   - Search for your mod ID
   - Check for "failed" messages

---

### Issue: "Exception in thread"

**Symptoms:**

- Stack trace in console/log
- Feature crashes game
- Error message about NullPointerException

**Causes:**

- Null pointer in your code
- Incorrect API usage
- Data not initialized
- Thread safety issue

**Solutions:**

1. **Read full stack trace:**

   - Find the line in your code
   - Look at the "caused by" section

2. **Add null checks:**

```java
// ✅ Safe
BlockPos pos = BlockSearchHelper.findNearest(world, center, 100, block);
if (pos != null) {
    // Do something with pos
}

// ❌ Risky
BlockPos pos = BlockSearchHelper.findNearest(world, center, 100, block);
world.setBlockState(pos, Blocks.STONE); // Crashes if pos is null!
```

3. **Use safe defaults:**

```java
// ✅ Safe with defaults
String name = NBTHelper.getString(nbt, "Name", "Unknown");

// ❌ Risky
String name = nbt.getString("Name"); // Crashes if missing
```

4. **Debug with logging:**

```java
var logger = LogHelper.getLogger("mymod", "Debug");
try {
    doSomething();
} catch (Exception e) {
    logger.error("Operation failed", e);
}
```

---

## Performance Issues

### Issue: "Game lags when using helpers"

**Symptoms:**

- FPS drops significantly
- World generation slows down
- Performance degrades over time

**Causes:**

- Searching too large area
- Expensive operation in frequent event
- Memory leak from caching
- Too many particles/effects

**Solutions:**

1. **Limit search radius:**

```java
// ❌ Too large (1000 block radius!)
BlockSearchHelper.findInRadius(world, pos, 1000, block);

// ✅ Reasonable (50 block radius)
BlockSearchHelper.findInRadius(world, pos, 50, block);
```

2. **Cache results:**

```java
// Cache frequently-accessed data
Map<String, ItemStack> cache = new HashMap<>();

ItemStack stack = cache.computeIfAbsent("diamond", k ->
    ItemStackHelper.of("minecraft:diamond", 1)
);
```

3. **Avoid frequent operations:**

```java
// ❌ Every tick (60+ times per second!)
if (tick % 20 == 0) {  // Every second
    var blocks = BlockSearchHelper.findInRadius(world, pos, 50, block);
}

// ✅ Throttled
```

4. **Limit particles:**

```java
// ❌ Too many particles
for (int i = 0; i < 1000; i++) {
    new ParticleBuilder(ParticleTypes.FLAME).spawn(world);
}

// ✅ Reasonable amount
new ParticleBuilder(ParticleTypes.FLAME)
    .count(20)
    .spawn(world);
```

---

## Getting More Help

### Check Documentation

1. **[Quick Start](Quick-Start.md)** - Basic usage
2. **[Code Examples](Examples.md)** - Working code
3. **[FAQ](FAQ.md)** - Common questions
4. **[API Reference](API-Reference.md)** - Method documentation

### Report Issues

If you found a bug:

1. **Check existing issues** on [GitHub](https://github.com/mosberg/moddinghelperapi/issues)
2. **Create new issue** with:
   - Clear description
   - Steps to reproduce
   - Error logs
   - Your code
   - Environment (MC version, Fabric version, Java version)

### Ask Questions

1. **Check [FAQ](FAQ.md)** first
2. **Search [GitHub Discussions](https://github.com/mosberg/mosberg/moddinghelperapi/discussions)**
3. **Create discussion** with details

---

**Still stuck?** → [FAQ](FAQ.md) | [Getting Help](Home.md#-getting-help)
