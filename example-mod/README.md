# Example Mod - Modding Helper API Demo

This is a complete example mod demonstrating the usage of the Modding Helper API library.

## Features

The mod adds a single `/demo` command that showcases all major helper utilities:

- **TextHelper** - Formatted and colored chat messages
- **PlayerHelper** - Player information queries (health, game mode, etc.)
- **ItemStackHelper** - ItemStack creation and manipulation
- **VectorHelper** - 3D vector mathematics and calculations
- **NBTHelper** - NBT data reading and writing
- **EntityHelper** - Entity state queries
- **IdentifierHelper** - Resource location handling
- **GsonInstance** - JSON serialization
- **CommandHelper** - Command registration
- **LogHelper** - Structured logging

## Installation

### Prerequisites

1. Build the Modding Helper API first:

   ```bash
   cd ../
   ./gradlew.bat build publishToMavenLocal
   ```

2. This publishes the helper API to your local Maven repository.

### Building the Example Mod

```bash
cd example-mod
./gradlew.bat build
```

The compiled JAR will be in `build/libs/examplemod-1.0.0.jar`.

### Running

1. Copy both JARs to your Minecraft `mods` folder:

   - `moddinghelperapi-1.0.0.jar` (from the main project)
   - `examplemod-1.0.0.jar` (from this example)

2. Launch Minecraft 1.21.11 with Fabric Loader

3. In-game, run `/demo` to see all helpers in action

## Usage Example

```java
// Text formatting
player.sendMessage(TextHelper.success("Operation successful!"));
player.sendMessage(TextHelper.error("Something went wrong!"));

// Player operations
float health = PlayerHelper.getHealth(player);
boolean creative = PlayerHelper.isCreative(player);

// ItemStack creation
var stack = ItemStackHelper.of(Items.DIAMOND, 5);
int remaining = ItemStackHelper.getRemainingSpace(stack);

// Vector math
Vec3d pos = EntityHelper.getPos(player);
double distance = VectorHelper.distance(pos, new Vec3d(0, 64, 0));

// NBT operations
var nbt = new NbtCompound();
NBTHelper.putString(nbt, "key", "value");
String value = NBTHelper.getString(nbt, "key", "default");

// Logging
var logger = LogHelper.getLogger("mymod", "Category");
logger.info("Message with arg: {}", value);
```

## Project Structure

```
example-mod/
├── src/main/java/com/example/examplemod/
│   └── ExampleMod.java              # Main mod class with /demo command
├── src/main/resources/
│   └── fabric.mod.json              # Mod metadata (depends on moddinghelperapi)
├── build.gradle                     # Build configuration
├── gradle.properties                # Gradle settings
└── settings.gradle                  # Project settings
```

## Code Walkthrough

### Main Mod Class

The `ExampleMod` class implements `ModInitializer` and demonstrates:

1. **Command Registration**

   ```java
   CommandHelper.registerLiteral("demo", 0, context -> {
       // Command logic here
       return Command.SINGLE_SUCCESS;
   });
   ```

2. **Text Formatting**

   ```java
   player.sendMessage(TextHelper.success("Success message"));
   player.sendMessage(TextHelper.info("Info message"));
   player.sendMessage(TextHelper.error("Error message"));
   ```

3. **Player Queries**

   ```java
   float health = PlayerHelper.getHealth(player);
   boolean creative = PlayerHelper.isCreative(player);
   ```

4. **Vector Calculations**

   ```java
   Vec3d direction = VectorHelper.direction(from, to);
   double distance = VectorHelper.distance(pos1, pos2);
   ```

5. **NBT Data Access**
   ```java
   String value = NBTHelper.getString(nbt, "key", "default");
   int number = NBTHelper.getInt(nbt, "key", 0);
   ```

## Learning Resources

- **API Documentation**: See the main project's JavaDoc
- **Source Code**: Examine `ExampleMod.java` for practical usage patterns
- **Main Project**: Check the helper class implementations for advanced features

## License

MIT License - See main project for details
