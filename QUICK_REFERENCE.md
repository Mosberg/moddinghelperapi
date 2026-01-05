# Quick Reference: Source Discovery Setup

## TL;DR

All your moddinghelperapi classes are now discoverable in VSCode when used as a dependency.

### For Your Dependent Mods

```gradle
repositories {
    mavenLocal()  // ← Add this line
}

dependencies {
    modImplementation "dk.mosberg:moddinghelperapi:1.0.0"
}
```

### Test It

```bash
# 1. Any mod that has above dependency
./gradlew.bat build

# 2. Open in VSCode
code .

# 3. Hover over any API class
import dk.mosberg.util.ItemStackHelper;  # Hover here ← Shows source

# 4. Ctrl+Click to open source file
```

## What's Published

```
~/.m2/repository/dk/mosberg/moddinghelperapi/1.0.0/
├── moddinghelperapi-1.0.0.jar           (270 KB - compiled lib)
├── moddinghelperapi-1.0.0-sources.jar   (933 KB - source code)  ← Key!
├── moddinghelperapi-1.0.0-javadoc.jar   (324 KB - docs)
├── moddinghelperapi-1.0.0.pom           (2 KB - metadata)
└── moddinghelperapi-1.0.0.module        (4 KB - metadata)
```

## Commands

```bash
# Publish moddinghelperapi to local Maven
./gradlew.bat publishMavenJavaPublicationToMavenLocal

# Refresh Gradle in VSCode
Ctrl+Shift+P → "Gradle: Refresh Gradle"

# Force refresh in dependent mod
./gradlew.bat clean build --refresh-dependencies

# Clean Java language server (if needed)
Ctrl+Shift+P → "Java: Clean Language Server Workspace"
```

## If Sources Don't Show

1. Verify published:

   ```bash
   dir "%USERPROFILE%\.m2\repository\dk\mosberg\moddinghelperapi\1.0.0\"
   ```

   Should show `-sources.jar`

2. Ensure `mavenLocal()` in dependent mod's repositories

3. Refresh Gradle and restart VSCode

## Features

✅ Hover to see source code preview
✅ Ctrl+Click to open source file
✅ Full IntelliSense/autocomplete
✅ JavaDoc hints on hover
✅ Works across all dependent mods

## Files Changed

- `build.gradle` - Updated Maven publishing config
- `docs/markdown/SETUP_SOURCE_DISCOVERY.md` - Full setup guide
- `example-mod/README.md` - Quick start instructions
- `SETUP_COMPLETE.md` - Implementation summary

---

See `SETUP_SOURCE_DISCOVERY.md` for detailed troubleshooting.
