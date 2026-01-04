# Getting Started with Modding Helper API

Learn how to add the Modding Helper API to your Fabric mod project and start using it.

## Prerequisites

Before you begin, ensure you have:

- **Java 21** or higher
- **Gradle 8.0+** (usually bundled with Fabric template)
- **Fabric Project Template** or existing Fabric mod
- Basic knowledge of Minecraft modding

## Installation Steps

### Step 1: Add Dependency to fabric.mod.json

In your mod's `src/main/resources/fabric.mod.json`, add moddinghelperapi to the `depends` section:

```json
{
  "schemaVersion": 1,
  "id": "yourmodid",
  "version": "1.0.0",
  "name": "Your Mod Name",
  "description": "Your mod description",
  "authors": ["Your Name"],
  "license": "MIT",
  "environment": "*",
  "entrypoints": {
    "main": ["com.example.YourModInitializer"]
  },
  "mixins": [],
  "depends": {
    "fabricloader": ">=0.18.4",
    "minecraft": "~1.21.11",
    "java": ">=21",
    "fabric-api": "*",
    "moddinghelperapi": "*"
  }
}
```

The important line is:

```json
"moddinghelperapi": "*"
```

This declares a dependency on any version of Modding Helper API.

### Step 2: Sync Your Project

After modifying `fabric.mod.json`, refresh your Gradle project:

**IntelliJ IDEA:**

- Right-click on the project → Gradle → Refresh Gradle Project
- Or press Ctrl+Shift+O

**Eclipse:**

- Right-click on the project → Gradle → Refresh Gradle Projects

**VS Code:**

- Reload window (Ctrl+Shift+P → Reload Window)

### Step 3: Start Using in Your Code

Create or modify your mod initializer:

```java
import net.fabricmc.api.ModInitializer;
import dk.mosberg.util.*;
import dk.mosberg.util.builders.*;

public class YourModInitializer implements ModInitializer {
    public static final String MOD_ID = "yourmodid";

    @Override
    public void onInitialize() {
        // Use helpers here
        LOGGER.info("Initializing with Modding Helper API!");

        // Example: Create an item stack
        var diamond = ItemStackHelper.of("minecraft:diamond", 1);

        // Example: Create styled text
        var msg = new TextBuilder("Mod Loaded!")
            .success()
            .bold()
            .build();
    }

    private static final org.slf4j.Logger LOGGER =
        org.slf4j.LoggerFactory.getLogger(MOD_ID);
}
```

### Step 4: Build and Test

Verify everything compiles:

```bash
./gradlew build
```

If successful, you'll see:

```
BUILD SUCCESSFUL in Xs
```

## Quick Verification

Create a simple test to verify the API is available:

```java
@Override
public void onInitialize() {
    // Test 1: Use ItemStackHelper
    var stack = ItemStackHelper.of("minecraft:stone", 64);
    assert !ItemStackHelper.isEmpty(stack);

    // Test 2: Use TextHelper
    var text = TextHelper.success("API Available!");
    assert text != null;

    // Test 3: Use VectorHelper
    var vec = VectorHelper.normalize(new Vec3d(1, 1, 1));
    assert vec != null;

    LOGGER.info("✓ Modding Helper API is working!");
}
```

## Project Structure

After installation, your mod structure looks like:

```
your-mod/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   └── YourModInitializer.java
│   │   └── resources/
│   │       └── fabric.mod.json
│   ├── client/
│   │   └── java/com/example/client/
│   └── test/
│       └── java/com/example/
├── build.gradle
├── gradle.properties
├── settings.gradle
└── README.md
```

## Importing Classes

To use the API, import from `dk.mosberg.util`:

```java
// Core helpers
import dk.mosberg.util.ItemStackHelper;
import dk.mosberg.util.TextHelper;
import dk.mosberg.util.VectorHelper;
import dk.mosberg.util.EntityHelper;
import dk.mosberg.util.NBTHelper;
import dk.mosberg.util.InventoryHelper;
import dk.mosberg.util.BlockSearchHelper;

// Builders
import dk.mosberg.util.builders.TextBuilder;
import dk.mosberg.util.builders.Vec3dBuilder;
import dk.mosberg.util.builders.ItemStackBuilder;
import dk.mosberg.util.builders.EntityBuilder;
import dk.mosberg.util.builders.ParticleBuilder;
import dk.mosberg.util.builders.StatusEffectBuilder;

// Wildcard import (all helpers)
import dk.mosberg.util.*;
import dk.mosberg.util.builders.*;
```

## Common Setup Issues

### Issue: "Cannot find dk.mosberg.util"

**Cause:** Dependency not resolved

**Solution:**

1. Verify `fabric.mod.json` has correct syntax
2. Run `./gradlew clean` to clear cache
3. Refresh Gradle project in IDE
4. Check internet connection (Gradle downloads dependencies)

### Issue: "Version of moddinghelperapi not found"

**Cause:** Using wildcard when specific version needed

**Solution:**

1. Use specific version: `"moddinghelperapi": "1.0.0"`
2. Or use compatible version range: `"moddinghelperapi": "1.+"`
3. Check [Releases](https://github.com/mosberg/moddinghelperapi/releases) for available versions

### Issue: "Cannot resolve symbol 'ItemStackHelper'"

**Cause:** Import missing

**Solution:**

```java
import dk.mosberg.util.ItemStackHelper;
// OR
import dk.mosberg.util.*;
```

## Next Steps

✅ **Installation complete!** Now:

1. **Read [Quick Start](Quick-Start.md)** - Create your first helper usage
2. **Browse [Code Examples](Examples.md)** - See working code
3. **Check [Helpers Overview](Helpers-Overview.md)** - Learn available utilities
4. **Reference [API Reference](API-Reference.md)** - Look up specific methods

## Server vs Client Code

**Important:** Modding Helper API is **server-safe**.

All helpers in `dk.mosberg.util` are safe to use on both server and client. Never import client classes (from `net.minecraft.client`) into your main mod code.

```java
// ✅ GOOD - Server safe
import dk.mosberg.util.ItemStackHelper;

// ❌ BAD - Client only
import net.minecraft.client.gui.screen.Screen;
```

## Troubleshooting

### Build Fails with "moddinghelperapi not found"

1. Check internet connection
2. Verify `fabric.mod.json` syntax (valid JSON)
3. Clear cache: `./gradlew clean`
4. Refresh Gradle: Right-click project → Gradle → Refresh

### JAR Won't Run

1. Ensure `depends` section in `fabric.mod.json` is correct
2. Verify no typos in `"moddinghelperapi": "*"`
3. Build with `./gradlew build`
4. Place JAR in mods folder: `run/mods/`

### IDE Shows Red Underlines

1. Rebuild: Ctrl+Shift+F9 (IntelliJ)
2. Refresh: Right-click project → Gradle → Refresh
3. Invalidate cache: File → Invalidate Caches → Invalidate and Restart (IntelliJ)

## Help

For more help:

- **[Quick Start](Quick-Start.md)** - First steps
- **[Code Examples](Examples.md)** - Working code
- **[FAQ](FAQ.md)** - Common questions
- **[Troubleshooting](Troubleshooting.md)** - More issues

---

**Next:** [Quick Start →](Quick-Start.md)
