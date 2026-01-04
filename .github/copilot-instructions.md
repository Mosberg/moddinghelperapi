# AI Agent Instructions: Modding Helper API

## Project Overview

A **production-ready Fabric Mod** for Minecraft 1.21.11 providing a comprehensive API library for mod development. This is a **library mod** (not a feature mod)—designed as a dependency for other mods, not as a standalone feature.

**Current Status**: Phase 2 Complete (10/15 improvements implemented)

- **29 utility helpers** across multiple categories
- **3 fluent builder classes** for common objects
- **72 unit tests** with 61 passing (11 registry-dependent)
- **2,600+ lines** of production code with full JavaDoc
- **Zero compilation errors** or null safety warnings

**Key Characteristics:**

- Pure utility/API library with no gameplay features
- Other mods declare this as a dependency in their `fabric.mod.json`
- Server-safe utilities (no client-side code in main source set)
- Comprehensive test coverage with JUnit 5
- Fluent builder pattern APIs for common operations
- Avoid adding items, blocks, or other game content

**Stack:**

- **Framework**: Fabric Modding Framework
- **Language**: Java 21 (toolchain enforced)
- **Build System**: Gradle 9.2.1 with Fabric Loom 1.14.10
- **Key Dependencies**: Fabric API 0.140.2+1.21.11, Fabric Loader 0.18.4, GSON 2.13.2
- **Testing**: JUnit 5 (Jupiter) with 72 tests

## Project Structure

```
src/main/java/dk/mosberg/
├── ModdingHelperAPI.java         # Main mod entry point (implements ModInitializer)
├── datagen/
│   └── ModdingHelperAPIDataGenerator.java  # Data generation entry point
└── util/                          # 29 utility helpers organized by category
    ├── builders/                  # Fluent builder pattern classes
    │   ├── TextBuilder.java       # Styled text component creation
    │   └── Vec3dBuilder.java      # 3D vector construction & transformation
    ├── [Core Helpers]             # Basic utilities
    │   ├── GsonInstance.java      # Singleton JSON serialization
    │   ├── IdentifierHelper.java  # Resource location handling
    │   ├── NBTHelper.java         # NBT data manipulation
    │   ├── TextHelper.java        # Text component creation
    │   └── VectorHelper.java      # Vector mathematics
    ├── [Block & World Helpers]    # World interaction
    │   ├── BlockEntityHelper.java # Block entity data access
    │   ├── BlockSearchHelper.java # Block searching & filtering
    │   ├── BlockStateHelper.java  # BlockState operations
    │   ├── DimensionHelper.java   # Dimension utilities
    │   └── RedstoneHelper.java    # Redstone power detection
    ├── [Entity & Player Helpers]  # Entity operations
    │   ├── EntityHelper.java      # Entity utilities
    │   ├── HealthHelper.java      # Health & damage operations
    │   └── PlayerHelper.java      # Player-specific utilities
    ├── [Item & Inventory]         # Item handling
    │   ├── ItemStackBuilder.java  # Fluent ItemStack creation
    │   ├── ItemStackHelper.java   # ItemStack operations (enhanced)
    │   └── InventoryHelper.java   # Inventory management
    ├── [Registry & Resources]     # Registry access
    │   ├── RegistryHelper.java    # Generic registry operations
    │   └── PersistentDataHelper.java # Persistent data storage
    ├── [Multiplayer & Network]    # Networking
    │   ├── NetworkHelper.java     # Packet & connection handling
    │   ├── ChatHelper.java        # Chat message utilities
    │   └── SoundHelper.java       # Sound playback
    ├── [Developer Tools]          # Development utilities
    │   ├── LogHelper.java         # Structured logging with categories
    │   ├── EventHelper.java       # Priority-based event system
    │   ├── ValidationHelper.java  # Input validation
    │   └── StatisticsHelper.java  # Statistics tracking
    └── [Miscellaneous]
        ├── ConfigHelper.java      # Configuration handling
        ├── FileHelper.java        # File I/O operations
        ├── MathHelper.java        # Mathematical utilities
        └── ParticleHelper.java    # Particle effect spawning

src/client/java/dk/mosberg/client/
└── ModdingHelperAPIClient.java   # Client-side entry point (implements ClientModInitializer)

src/test/java/dk/mosberg/util/
├── TestConstants.java             # Centralized test data
├── GsonInstanceTest.java          # 9 tests (all passing)
├── NBTHelperTest.java             # 14 tests (all passing)
├── TextHelperTest.java            # 13 tests (all passing)
├── VectorHelperTest.java          # 13 tests (all passing)
├── IdentifierHelperTest.java      # 10 tests (8 passing)
└── ItemStackHelperTest.java       # 13 tests (4 passing, 9 registry-dependent)

src/main/resources/
└── fabric.mod.json               # Mod metadata (properties expanded during build)
```

## Critical Build & Development Workflow

### Common Commands

- **`./gradlew.bat build`** – Compile, test, and package the mod (full build with tests)
- **`./gradlew.bat build -x test`** – Build without running tests (faster for development)
- **`./gradlew.bat compileJava`** – Quick compilation check (3-9s with config cache)
- **`./gradlew.bat test`** – Run JUnit 5 test suite (72 tests, 61 passing)
- **`./gradlew.bat runClient`** – Launch Minecraft client with mod loaded (dev mode)
- **`./gradlew.bat runServer`** – Launch dedicated Minecraft server with mod loaded
- **`./gradlew.bat runDatagen`** – Generate data files (recipes, loot tables, models, etc.)
- **`./gradlew.bat projectInfo`** – Display build configuration info

### Key Configuration Files

- **[gradle.properties](../gradle.properties)** – Centralized version management (Minecraft 1.21.11, Fabric, Java 21)
- **[build.gradle](../build.gradle)** – Gradle build script with Fabric Loom configuration
- **[src/main/resources/fabric.mod.json](../src/main/resources/fabric.mod.json)** – Mod metadata with templated properties

### Build Performance

- **Configuration cache enabled** – Typical compile time: 3-9 seconds
- **Parallel execution** – Enabled for faster multi-task builds
- **JVM tuning** – 4GB heap with G1GC for optimal performance
- **Incremental compilation** – Only changed files are recompiled

### Test Execution

- **Total tests**: 72 (across 6 test classes)
- **Passing**: 61 tests (non-registry dependent)
- **Expected failures**: 11 tests require Minecraft registry bootstrap
- **Test classes**: GsonInstanceTest, NBTHelperTest, TextHelperTest, VectorHelperTest, IdentifierHelperTest, ItemStackHelperTest
- **Run specific test**: `./gradlew.bat test --tests "dk.mosberg.util.NBTHelperTest"`

## Entry Points & Lifecycle

### Mod Initialization (`dk.mosberg.ModdingHelperAPI`)

- Implements `ModInitializer` (server + common code)
- Runs once during server startup in mod-load-ready state
- Resources may still be uninitialized—use caution
- Logging: Use static `LOGGER` field (SLF4J with mod ID "moddinghelperapi")

### Client Initialization (`dk.mosberg.client.ModdingHelperAPIClient`)

- Implements `ClientModInitializer`
- Runs on client startup; suitable for rendering, keybinds, screens
- Only loaded on physical client

### Data Generation (`dk.mosberg.datagen.ModdingHelperAPIDataGenerator`)

- Implements `DataGeneratorEntrypoint`
- Runs when `runDatagen` task executes
- Generates recipes, loot tables, tags, models, and other data files

## Important Conventions

### Environment Separation (Critical for Library Mods)

Fabric splits code into **main** (server + client shared) and **client** (client-only) source sets:

- **`src/main/java/dk/mosberg/`** – Server-safe utilities and APIs (loaded on both client and server)
- **`src/client/java/dk/mosberg/client/`** – Client-only rendering, keybinds, screens (never referenced from `src/main/`)

**Golden Rule**: Never import client classes from `src/client/` into `src/main/`. This breaks server installations.

### Package Naming

- Root package: `dk.mosberg` (corresponds to `maven_group` in gradle.properties)
- Subpackages follow standard conventions: `.client`, `.datagen`, `.util`

### Mod Metadata

All mod properties (name, version, author, etc.) are defined in [gradle.properties](../gradle.properties) and **expanded during resource processing**. Update there, not in `fabric.mod.json` (which is a template).

### Build Caching & Performance

- Gradle configuration cache enabled (`org.gradle.configuration-cache=true`)
- JVM configured with 4GB heap and G1GC for faster builds
- Properties captured in closures to remain configuration-cache compliant

### Compilation

- Java 21 required (enforce with toolchain)
- Warnings enabled: `-Xlint:deprecation -Xlint:unchecked`
- UTF-8 source encoding
- **Current status**: Zero compilation errors or warnings

### Testing

- Framework: **JUnit 5 (Jupiter)** with BOM dependency management
- Run via `./gradlew.bat test`
- Test logging configured to show pass/fail/skip with full exception details
- **Note**: 11 tests are expected to fail without registry bootstrap (requires Fabric test fixtures)
- All non-registry tests pass successfully (61/72)

## Minecraft 1.21.11 API Differences

**Critical API changes from earlier versions:**

1. **Identifier Construction**: Use `Identifier.of(namespace, path)` instead of `new Identifier()`
2. **NBT Methods Return Optional**: All NBT getter methods return `Optional<T>`, use `.orElse(defaultValue)`
3. **Formatting Import Path**: Use `net.minecraft.util.Formatting` not `net.minecraft.formatting.Formatting`
4. **ItemStack Component System**: 1.21.11 uses DataComponentTypes instead of direct NBT for certain data
5. **BlockEntity API**: Some NBT read/write methods have changed signatures

**When encountering compilation errors:**

- Check if method exists in Minecraft 1.21.11 sources
- Use Optional unwrapping for NBT operations: `.orElse()`, `.orElseGet()`, `.orElseThrow()`
- Simplify implementations if APIs are unavailable (document limitations in JavaDoc)

## Common Development Patterns

### Adding Utility Classes (Primary Expansion Point)

The `src/main/java/dk/mosberg/util/` directory has **29 helpers** and welcomes expansion:

1. Create new utility class in `src/main/java/dk/mosberg/util/YourUtilityName.java`
2. Keep utilities **server-safe** (no client-side rendering or screens)
3. Write comprehensive JavaDoc for public APIs—other mods depend on these
4. Add unit tests in `src/test/java/dk/mosberg/util/` for critical utilities
5. Follow existing patterns: static utility methods, private constructor, @NotNull/@Nullable annotations

**Example Utility Pattern:**

```java
package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public final class JsonHelper {
    private JsonHelper() {} // Prevent instantiation

    /**
     * Parses JSON string into an object of the specified type.
     *
     * @param json the JSON string to parse
     * @param type the target class type
     * @param <T> the type parameter
     * @return the parsed object
     * @throws NullPointerException if json or type is null
     */
    public static <T> @NotNull T parseJson(@NotNull String json, @NotNull Class<T> type) {
        Objects.requireNonNull(json);
        Objects.requireNonNull(type);
        return GsonInstance.GSON.fromJson(json, type);
    }
}
```

### Creating Builder Pattern Classes

For complex objects, create fluent builders in `util/builders/`:

```java
package dk.mosberg.util.builders;

import org.jetbrains.annotations.NotNull;

public final class MyBuilder {
    private final Object target;

    public MyBuilder() {
        this.target = new Object();
    }

    public @NotNull MyBuilder property(@NotNull String value) {
        // Configure property
        return this;
    }

    public @NotNull Object build() {
        return target.copy(); // Return immutable copy
    }
}
```

**Existing builders**: TextBuilder, Vec3dBuilder, ItemStackBuilder

### Writing Unit Tests

Create tests in `src/test/java/dk/mosberg/util/` following JUnit 5 patterns:

```java
package dk.mosberg.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class MyHelperTest {

    @BeforeEach
    void setUp() {
        // Initialize test data
    }

    @Test
    void testBasicOperation() {
        String result = MyHelper.doSomething("input");
        assertEquals("expected", result);
    }

    @Test
    void testNullHandling() {
        assertThrows(NullPointerException.class, () -> {
            MyHelper.doSomething(null);
        });
    }
}
```

**Use TestConstants** for shared test data (namespaces, identifiers, positions, etc.)

### Logging

Use **LogHelper** for structured logging with categories:

```java
import dk.mosberg.util.LogHelper;

// Get logger with category
var logger = LogHelper.getLogger("mymod", "ItemHandler");
logger.info("Processing item: {}", itemName);
logger.warn("Invalid configuration: {}", configValue);
logger.error("Failed to load data: {}", e.getMessage());
```

Or use raw SLF4J if preferred:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger LOGGER = LoggerFactory.getLogger("moddinghelperapi");
LOGGER.info("Message here");
```

### Client-Only Features

1. Add to `src/client/java/dk/mosberg/client/` or subpackages
2. Reference only from `ModdingHelperAPIClient.onInitializeClient()`
3. Use `@Environment(EnvType.CLIENT)` on classes if needed for clarity

### Network Operations

Use **NetworkHelper** for server-side packet handling:

```java
import dk.mosberg.util.NetworkHelper;
import net.minecraft.util.Identifier;

// Register connection handler
NetworkHelper.registerConnectionHandler(
    Identifier.of("mymod", "on_join"),
    player -> {
        // Send initial sync packet
        NetworkHelper.sendToPlayer(player, myPayload);
    }
);

// Broadcast to all players
NetworkHelper.broadcastPacket(server.getPlayerManager().getPlayerList(), packet);
```

### Event Management

Use **EventHelper** for custom events with priorities:

```java
import dk.mosberg.util.EventHelper;

EventHelper events = new EventHelper();

// Subscribe with priority (higher = executes first)
events.subscribe("player_join", event -> {
    // Handle event
}, 100);

// Dispatch event
events.dispatch("player_join", playerJoinEvent);

// Filter events with predicates
events.filterEvent("player_join", event -> shouldProcess(event));
```

## Dependencies & Integration

### Fabric API Patterns

- This mod **depends on Fabric API** (all versions)—reference its events and utilities liberally
- Fabric Loader handles mod loading and dependency resolution

### GSON (Bundled)

- GSON is included in the JAR via `include implementation(...)`
- Use for JSON serialization/deserialization as needed
- Access via `GsonInstance.GSON` or `GsonInstance.PRETTY_GSON`

### External Dependency Management

- Fabric Loom remaps Minecraft obfuscation automatically
- All dependencies pulled from Fabric Maven repository (fabric maven.net, Terraformers, Shedaniel)

## Key Helpers Reference

### Core Utilities

- **GsonInstance** - Singleton JSON serialization (compact and pretty)
- **IdentifierHelper** - Resource location creation and validation
- **NBTHelper** - NBT compound operations with Optional handling
- **TextHelper** - Text component creation and styling
- **VectorHelper** - Vector math operations (distance, direction, etc.)

### Block & World Operations

- **BlockEntityHelper** - Block entity NBT data access
- **BlockSearchHelper** - Block searching and filtering
- **BlockStateHelper** - BlockState property manipulation
- **RedstoneHelper** - Redstone power detection and queries
- **DimensionHelper** - Dimension-related utilities

### Item & Inventory

- **ItemStackBuilder** - Fluent ItemStack creation (use `new ItemStackBuilder(item).quantity(5).build()`)
- **ItemStackHelper** - ItemStack operations (durability, repair, damage, comparison)
- **InventoryHelper** - Inventory slot management and item transfers

### Entity & Player

- **EntityHelper** - Entity operations
- **HealthHelper** - Health and damage utilities
- **PlayerHelper** - Player-specific operations

### Network & Multiplayer

- **NetworkHelper** - Connection handlers, packet broadcasting
- **ChatHelper** - Chat message formatting
- **SoundHelper** - Sound playback utilities

### Developer Tools

- **LogHelper** - Structured logging with mod ID and category
- **EventHelper** - Priority-based event system
- **ValidationHelper** - Input validation
- **StatisticsHelper** - Statistics tracking

### Builders (in `util/builders/`)

- **TextBuilder** - Fluent text styling: `.bold().color(Formatting.GOLD).append("text").build()`
- **Vec3dBuilder** - Vector construction: `.x(1).y(2).z(3).normalize().build()`
- **ItemStackBuilder** - ItemStack creation: `.quantity(16).displayName("Custom").build()`

## Critical Pitfalls to Avoid

- **Don't add gameplay features** – This is a library mod. Game content (items, blocks, commands) belongs in separate feature mods that depend on this.
- **Don't break server compatibility** – Never import `net.minecraft.client.*` or `net.fabricmc.api.ClientModInitializer` code in `src/main/`. Use `@Environment` annotations if uncertain.
- **Don't update `fabric.mod.json` directly** – All metadata comes from `gradle.properties`. The JSON file is a template expanded during `processResources`.
- **Don't ignore the mod ID constant** – Always use `ModdingHelperAPI.MOD_ID` instead of hardcoding `"moddinghelperapi"` for consistency.
- **Don't forget to document public APIs** – Write JavaDoc on all public methods. This library will be used by other developers.
- **Don't assume methods exist** – Minecraft 1.21.11 has API changes. Check existing helpers for patterns before implementing.
- **Don't forget Optional handling** – NBT getters return Optional in 1.21.11. Always use `.orElse()` or similar.

## Useful Build Artifacts

- **`build/libs/moddinghelperapi-*.jar`** – Main compiled mod JAR
- **`build/libs/moddinghelperapi-*-sources.jar`** – Source code JAR (auto-generated)
- **`build/libs/moddinghelperapi-*-javadoc.jar`** – JavaDoc JAR (auto-generated)
- **`build/docs/javadoc/`** – Generated HTML documentation

## IDE Setup Tips

- Fabric Loom auto-generates run configurations for **runClient** and **runServer**
- Press F5 in most IDEs to launch client/server configurations
- Use Loom's Gradle refresh to sync IDE after updating dependencies

## Additional Resources

- **[IMPROVEMENTS_GUIDE.md](../IMPROVEMENTS_GUIDE.md)** – Complete guide to all Phase 2 improvements with usage examples
- **[PHASE_2_SUMMARY.md](../PHASE_2_SUMMARY.md)** – Detailed summary of Phase 2 implementation
- **[PROJECT_STATUS.md](../PROJECT_STATUS.md)** – Current project status and roadmap

---

**Last Updated**: January 4, 2026 | **Minecraft Version**: 1.21.11 | **Java**: 21 | **Phase**: 2 Complete (10/15)
