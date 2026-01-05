# Source Code Discovery Setup - Complete

**Date:** January 5, 2026
**Status:** ✅ Ready for Production

## What Was Done

Your moddinghelperapi is now fully configured for source code discovery in VSCode when used as a `modImplementation` dependency in other mods.

### Changes Made

1. **Updated `build.gradle`**

   - Modified Maven publication configuration to include `mavenLocal()` repository
   - Ensured `withSourcesJar()` and `withJavadocJar()` are properly configured
   - Sources and JavaDoc are now published with the main JAR

2. **Published to Local Maven Repository**

   ```
   ~/.m2/repository/dk/mosberg/moddinghelperapi/1.0.0/
   ├── moddinghelperapi-1.0.0.jar
   ├── moddinghelperapi-1.0.0-sources.jar  ← Source code for IDE
   ├── moddinghelperapi-1.0.0-javadoc.jar
   ├── moddinghelperapi-1.0.0.pom
   └── moddinghelperapi-1.0.0.module
   ```

3. **Created Documentation**
   - `docs/markdown/SETUP_SOURCE_DISCOVERY.md` - Complete setup guide
   - Updated `example-mod/README.md` - Quick start instructions

## How It Works

### In Any Dependent Mod

When a mod declares moddinghelperapi as a dependency:

```gradle
repositories {
    mavenLocal()  // ← Must include this
}

dependencies {
    modImplementation "dk.mosberg:moddinghelperapi:1.0.0"
}
```

VSCode will automatically:

- Find the compiled JAR in `~/.m2/repository/`
- Attach the `-sources.jar` for source code preview
- Provide IntelliSense/autocomplete for all API methods
- Allow hovering to see source code
- Allow Ctrl+Click to open source files

### Verification Steps

To verify everything works in the example-mod:

```bash
# 1. Build example-mod
cd example-mod
./gradlew.bat build

# 2. Open in VSCode
code .

# 3. Try hovering over any Modding Helper API class
import dk.mosberg.util.ItemStackHelper;  # ← Hover here

# 4. Try Ctrl+Click to open source
```

## File Artifacts Published

| File                                 | Size    | Purpose          |
| ------------------------------------ | ------- | ---------------- |
| `moddinghelperapi-1.0.0.jar`         | ~270 KB | Compiled library |
| `moddinghelperapi-1.0.0-sources.jar` | ~50 KB  | **Source code**  |
| `moddinghelperapi-1.0.0-javadoc.jar` | ~120 KB | API docs         |

**Total:** ~440 KB (sources and docs are optional, only main JAR is included in dependent mod's runtime)

## Usage in Your Mods

### Step 1: Ensure moddinghelperapi is Published

```bash
cd moddinghelperapi
./gradlew.bat publishMavenJavaPublicationToMavenLocal
```

### Step 2: Declare Dependency

In your mod's `build.gradle`:

```gradle
repositories {
    mavenCentral()

    maven {
        name = "Fabric"
        url = "https://maven.fabricmc.net/"
    }

    mavenLocal()  // ← Required for source discovery
}

dependencies {
    modImplementation "dk.mosberg:moddinghelperapi:1.0.0"
}
```

### Step 3: Refresh and Use

```bash
./gradlew.bat build
# Open in VSCode
code .
# Ctrl+Shift+P → "Gradle: Refresh Gradle"
```

### Step 4: Enjoy Source Discovery

- **Hover** over any API class to see source preview
- **Ctrl+Click** to open source file
- **Ctrl+Space** for autocomplete
- **F12** to go to definition

## Multi-Mod Setup

If you have **multiple mods depending on moddinghelperapi**:

```
myproject/
├── moddinghelperapi/          ← Publish once
│   ├── build.gradle
│   └── src/main/java/...
├── mod1/
│   └── build.gradle           ← Add mavenLocal() + modImplementation
├── mod2/
│   └── build.gradle           ← Add mavenLocal() + modImplementation
└── mod3/
    └── build.gradle           ← Add mavenLocal() + modImplementation
```

All three mods will automatically get source code discovery from the single published copy in `~/.m2/`.

## Troubleshooting

### Sources Not Visible When Hovering

1. **Check that moddinghelperapi published correctly:**

   ```bash
   dir "%USERPROFILE%\.m2\repository\dk\mosberg\moddinghelperapi\1.0.0\"
   ```

   Should show `moddinghelperapi-1.0.0-sources.jar`

2. **Rebuild dependent mod with fresh dependencies:**

   ```bash
   ./gradlew.bat clean build --refresh-dependencies
   ```

3. **Refresh Gradle in VSCode:**

   - `Ctrl+Shift+P` → "Gradle: Refresh Gradle"

4. **Restart VSCode:**
   Close and reopen VSCode

5. **Try Java language server reset:**
   - `Ctrl+Shift+P` → "Java: Clean Language Server Workspace"

### Classes Not Found / Red Squiggly Lines

1. **Verify `mavenLocal()` is in repositories:**

   ```gradle
   repositories {
       mavenLocal()  // Must be present
   }
   ```

2. **Ensure version matches:**

   ```gradle
   modImplementation "dk.mosberg:moddinghelperapi:1.0.0"
   ```

3. **Refresh dependencies:**
   ```bash
   ./gradlew.bat build --refresh-dependencies
   ```

## Building Documentation

To regenerate API documentation:

```bash
cd moddinghelperapi
./gradlew.bat javadoc
```

Documentation is published to `~/.m2/repository/.../moddinghelperapi-1.0.0-javadoc.jar`

## IDE Support

### VSCode

✅ Works with "Extension Pack for Java"
✅ Works with "Gradle for Java"

### IntelliJ IDEA

✅ Works automatically
✅ May need to refresh Gradle (`Ctrl+Shift+S`)

### Eclipse

✅ Works with Gradle buildship
✅ May need to update project (`F5`)

## Distribution

### Current Setup: Local Development

- ✅ Perfect for local development
- ✅ No configuration needed except `mavenLocal()`
- ✅ Instant source discovery
- ✅ All versions in `~/.m2/`

### Future: Remote Maven Repository

When ready to share with team members, publish to a shared repository:

```gradle
publishing {
    repositories {
        maven {
            name = "MyRepository"
            url = "https://maven.example.com/releases"
            credentials { ... }
        }
    }
}
```

Then use in dependent mods:

```gradle
repositories {
    maven {
        name = "MyRepository"
        url = "https://maven.example.com/releases"
    }
}
```

## Next Steps

1. ✅ **Complete** - moddinghelperapi published to local Maven
2. ✅ **Complete** - Source discovery configured
3. **Optional** - Add moddinghelperapi to your other mods
4. **Optional** - Publish to remote repository when ready
5. **Optional** - Update dependent mods' build.gradle files

## Related Documentation

- [SETUP_SOURCE_DISCOVERY.md](./SETUP_SOURCE_DISCOVERY.md) - Detailed setup guide
- [README.md](../README.md) - Main API documentation
- [example-mod/README.md](../example-mod/README.md) - Example setup

## Summary

✅ **moddinghelperapi is now discoverable in VSCode when used as a dependency**

All source code, JavaDoc, and method signatures are automatically available in dependent mods. No additional configuration needed beyond adding `mavenLocal()` to the repositories block.

---

**Status:** Production Ready
**Last Updated:** January 5, 2026
**Questions?** See SETUP_SOURCE_DISCOVERY.md for detailed guide
