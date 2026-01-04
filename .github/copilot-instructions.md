# AI Agent Instructions: Modding Helper API

## Project Overview

A **Fabric Mod** for Minecraft 1.21.11 that provides a foundational API library to assist with mod development. This is a **library mod** (not a feature mod)—designed as a dependency for other mods, not as a standalone feature.

**Key Characteristics:**

- Pure utility/API library with no gameplay features
- Other mods will declare this as a dependency in their `fabric.mod.json`
- Always expand the `util/` directory with reusable tools, not feature-specific logic
- Avoid adding items, blocks, or other game content

**Stack:**

- **Framework**: Fabric Modding Framework
- **Language**: Java 21
- **Build System**: Gradle with Fabric Loom plugin
- **Key Dependencies**: Fabric API, Fabric Loader, GSON

## Project Structure

```
src/main/java/dk/mosberg/
├── ModdingHelperAPI.java         # Main mod entry point (implements ModInitializer)
├── datagen/
│   └── ModdingHelperAPIDataGenerator.java  # Data generation entry point
└── util/                          # Utility classes for mod development (expand here)

src/client/java/dk/mosberg/client/
└── ModdingHelperAPIClient.java   # Client-side entry point (implements ClientModInitializer)

src/main/resources/
└── fabric.mod.json               # Mod metadata (properties expanded during build)
```

## Critical Build & Development Workflow

### Common Commands

- **`./gradlew.bat build`** – Compile, test, and package the mod
- **`./gradlew.bat runClient`** – Launch Minecraft client with mod loaded (dev mode)
- **`./gradlew.bat runServer`** – Launch dedicated Minecraft server with mod loaded
- **`./gradlew.bat runDatagen`** – Generate data files (recipes, loot tables, models, etc.)
- **`./gradlew.bat test`** – Run JUnit 5 tests
- **`./gradlew.bat projectInfo`** – Display build configuration info

### Key Configuration Files

- **[gradle.properties](../gradle.properties)** – Centralized version management (Minecraft, Fabric, Java 21)
- **[build.gradle](../build.gradle)** – Gradle build script with Fabric Loom configuration
- **[src/main/resources/fabric.mod.json](../src/main/resources/fabric.mod.json)** – Mod metadata with templated properties

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

### Testing

- Framework: **JUnit 5 (Jupiter)** with BOM dependency management
- Run via `./gradlew.bat test`
- Test logging configured to show pass/fail/skip with full exception details

## Common Development Patterns

### Adding Utility Classes (Primary Expansion Point)

The `src/main/java/dk/mosberg/util/` directory is **empty and waiting for expansion**. This is where reusable tools belong:

1. Create new utility class in `src/main/java/dk/mosberg/util/YourUtilityName.java`
2. Keep utilities **server-safe** (no client-side rendering or screens)
3. Write comprehensive JavaDoc for public APIs—other mods depend on these
4. Add unit tests in `src/test/java/dk/mosberg/util/` for critical utilities

**Example Utility Pattern:**

```java
package dk.mosberg.util;

public final class JsonHelper {
    private JsonHelper() {} // Prevent instantiation

    /**
     * Utility method description for library consumers.
     */
    public static <T> T parseJson(String json, Class<T> type) {
        return GsonInstance.GSON.fromJson(json, type);
    }
}
```

### Logging

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

## Dependencies & Integration

### Fabric API Patterns

- This mod **depends on Fabric API** (all versions)—reference its events and utilities liberally
- Fabric Loader handles mod loading and dependency resolution

### GSON (Bundled)

- GSON is included in the JAR via `include implementation(...)`
- Use for JSON serialization/deserialization as needed

### External Dependency Management

- Fabric Loom remaps Minecraft obfuscation automatically
- All dependencies pulled from Fabric Maven repository (fabric maven.net, Terraformers, Shedaniel)

## Critical Pitfalls to Avoid

- **Don't add gameplay features** – This is a library mod. Game content (items, blocks, commands) belongs in separate feature mods that depend on this.
- **Don't break server compatibility** – Never import `net.minecraft.client.*` or `net.fabricmc.api.ClientModInitializer` code in `src/main/`. Use `@Environment` annotations if uncertain.
- **Don't update `fabric.mod.json` directly** – All metadata comes from `gradle.properties`. The JSON file is a template expanded during `processResources`.
- **Don't ignore the mod ID constant** – Always use `ModdingHelperAPI.MOD_ID` instead of hardcoding `"moddinghelperapi"` for consistency.
- **Don't forget to document public APIs** – Write JavaDoc on all public methods. This library will be used by other developers.

## Useful Build Artifacts

- **`build/libs/moddinghelperapi-*.jar`** – Main compiled mod JAR
- **`build/libs/moddinghelperapi-*-sources.jar`** – Source code JAR (auto-generated)
- **`build/libs/moddinghelperapi-*-javadoc.jar`** – JavaDoc JAR (auto-generated)
- **`build/docs/javadoc/`** – Generated HTML documentation

## IDE Setup Tips

- Fabric Loom auto-generates run configurations for **runClient** and **runServer**
- Press F5 in most IDEs to launch client/server configurations
- Use Loom's Gradle refresh to sync IDE after updating dependencies

---

**Last Updated**: January 2026 | **Minecraft Version**: 1.21.11 | **Java**: 21
