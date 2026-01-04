# Modding Helper API

Library mod for Fabric (Minecraft 1.21.11) providing utility helpers and fluent builders. Designed as a dependency for other mods; no gameplay content added.

**Snapshot (Jan 2026)**

- Version: 1.0.0 | Mod ID: moddinghelperapi
- Helpers: 28 utilities + 3 builders (31 total)
- Tests: 72 (61 pass without registry bootstrap; registry-dependent cases are disabled in plain JVM runs)
- Build: Java 21, Fabric Loader 0.18.4, Fabric API 0.140.2+1.21.11

## Quick Start

1. Declare dependency in your fabric.mod.json:

```json
{
  "depends": {
    "fabricloader": ">=0.18.4",
    "fabric-api": "*",
    "moddinghelperapi": "*"
  }
}
```

2. Import helpers in code:

```java
import dk.mosberg.util.*;
import dk.mosberg.util.builders.*;
```

3. Use a few helpers:

```java
var stack = ItemStackHelper.of("minecraft:diamond", 1);
var text = TextHelper.success("Loaded!");
var vec = new Vec3dBuilder(1, 2, 3).normalize().build();
```

## Build & Test

- Compile fast: ./gradlew.bat compileJava
- Full build (skip tests): ./gradlew.bat build -x test
- Run tests: ./gradlew.bat test (registry-dependent tests are disabled unless run with a bootstrapped game environment)
- Run client/server: ./gradlew.bat runClient / ./gradlew.bat runServer

## Documentation

- Quick examples: docs/markdown/HELPER_QUICK_REFERENCE.md
- Detailed enhancements: docs/markdown/ENHANCEMENT_SUMMARY.md
- Project status & metrics: docs/markdown/PROJECT_STATUS.md
- API notes for 1.21.11: docs/markdown/MINECRAFT_1.21.11_API_REFERENCE.md
- Builder/usage guides: docs/markdown/IMPROVEMENTS_GUIDE.md

## Features

- Server-safe utilities (no client imports in main source set)
- Helpers for identifiers, NBT, items, vectors, text, registries, blocks/world, networking, logging, events, validation, math, files, particles, sounds, and more
- Fluent builders: TextBuilder, Vec3dBuilder, ItemStackBuilder
- Comprehensive JavaDoc across public APIs

## Folder Map

- Source: src/main/java/dk/mosberg/ (helpers + entrypoints)
- Client-only: src/client/java/dk/mosberg/client/
- Tests: src/test/java/dk/mosberg/util/
- Docs: docs/markdown/

## Support

- Issues: https://github.com/mosberg/moddinghelperapi/issues
- Homepage: https://mosberg.github.io/moddinghelperapi

Last updated: January 2026
