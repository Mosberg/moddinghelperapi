# Builders Guide

Complete guide to the 6 fluent builder classes in Modding Helper API.

## What are Builders?

Builders use the **fluent interface pattern** to create complex objects with readable, chainable method calls.

Instead of:

```java
ItemStack stack = new ItemStack(Items.DIAMOND);
stack.setCount(10);
// ... 10 more lines to configure
```

Write:

```java
ItemStack stack = new ItemStackBuilder(Items.DIAMOND)
    .quantity(10)
    .displayName("Special Diamond")
    .build();
```

## The 6 Builders

### 1. TextBuilder

Create styled Minecraft text components fluently.

#### Basic Usage

```java
// Simple styled text
Text message = new TextBuilder("Hello World")
    .bold()
    .color(Formatting.GOLD)
    .build();

// With appending
Text complex = new TextBuilder("Status: ")
    .color(Formatting.WHITE)
    .append("Active")
    .color(Formatting.GREEN)
    .build();
```

#### Styling Methods

```java
new TextBuilder("Text")
    .bold()              // Make bold
    .italic()            // Make italic
    .underline()         // Add underline
    .strikethrough()     // Strike through
    .color(Formatting.RED)  // Set color
    .append(Text)        // Append another text
    .build();
```

#### Preset Colors

```java
// Success (Green)
Text success = new TextBuilder("Success!")
    .success()
    .build();

// Error (Red)
Text error = new TextBuilder("Error!")
    .error()
    .build();

// Warning (Yellow)
Text warning = new TextBuilder("Warning!")
    .warning()
    .build();

// Info (Cyan)
Text info = new TextBuilder("Info!")
    .info()
    .build();
```

#### Complete Example

```java
// Multi-part message with different styles
Text message = new TextBuilder("Achievement Unlocked: ")
    .success()
    .bold()
    .append("First Night Survived")
    .color(Formatting.AQUA)
    .italic()
    .build();

player.sendMessage(message, false);
```

---

### 2. Vec3dBuilder

Build and transform 3D vectors fluently.

#### Basic Creation

```java
// Create from coordinates
Vec3d vec = new Vec3dBuilder(10, 20, 30)
    .build();

// Copy and modify
Vec3d modified = new Vec3dBuilder(original)
    .scale(2.0)
    .normalize()
    .build();

// From entity position
Vec3d entityPos = EntityHelper.getPos(entity);
Vec3d adjusted = new Vec3dBuilder(entityPos)
    .add(0, 1, 0)  // Move up 1 block
    .build();
```

#### Transformation Methods

```java
new Vec3dBuilder(1, 2, 3)
    .x(5)              // Set X coordinate
    .y(10)             // Set Y coordinate
    .z(15)             // Set Z coordinate
    .add(1, 2, 3)      // Add to coordinates
    .subtract(1, 1, 1) // Subtract from coordinates
    .scale(2.0)        // Multiply all by value
    .normalize()       // Make unit vector
    .round()           // Round to nearest integer
    .floor()           // Floor coordinates
    .invert()          // Negate all coordinates
    .build();
```

#### Direction Calculation

```java
// Get direction from one point to another
Vec3d from = player.getPos();
Vec3d to = new Vec3d(100, 64, 200);

Vec3d direction = VectorHelper.direction(from, to);
Vec3d normalized = new Vec3dBuilder(direction)
    .normalize()
    .scale(speed)  // Apply speed
    .build();
```

#### Complete Example

```java
// Create launch velocity
Vec3d launchVec = new Vec3dBuilder(1, 0, 0)
    .normalize()
    .scale(2.0)        // Speed
    .add(0, 1, 0)      // Add upward component
    .build();

entity.setVelocity(launchVec);
```

---

### 3. ItemStackBuilder

Create ItemStacks fluently with custom properties.

#### Basic Creation

```java
// Create item
ItemStack diamond = new ItemStackBuilder(Items.DIAMOND)
    .quantity(10)
    .build();

// From item ID
ItemStack dirt = ItemStackBuilder
    .createFromId("minecraft:dirt")
    .quantity(64)
    .build();
```

#### Configuration Methods

```java
new ItemStackBuilder(Items.DIAMOND)
    .quantity(10)           // Set stack size
    .displayName("Custom Name")
    .unbreakable()          // Make unbreakable
    .durability(100)        // Set durability
    .enchant(Enchantments.UNBREAKING, 3)
    .hideAttributes()       // Hide attribute modifiers
    .build();
```

#### Adding NBT Data

```java
ItemStack stored = new ItemStackBuilder(Items.DIAMOND)
    .quantity(1)
    .displayName("Stored Data")
    .nbt(nbt -> {
        NBTHelper.putString(nbt, "owner", "Steve");
        NBTHelper.putInt(nbt, "value", 1000);
    })
    .build();
```

#### Complete Example

```java
// Create a special tool
ItemStack tool = new ItemStackBuilder(Items.DIAMOND_PICKAXE)
    .displayName("Legendary Pickaxe")
    .unbreakable()
    .enchant(Enchantments.UNBREAKING, 3)
    .enchant(Enchantments.EFFICIENCY, 5)
    .nbt(nbt -> {
        NBTHelper.putString(nbt, "rarity", "LEGENDARY");
        NBTHelper.putInt(nbt, "level", 50);
    })
    .build();

player.giveItemStack(tool);
```

---

### 4. EntityBuilder

Spawn and configure entities fluently.

#### Basic Spawning

```java
// Spawn a zombie
Zombie zombie = new EntityBuilder<>(EntityTypes.ZOMBIE)
    .pos(100, 64, 200)      // Position
    .yaw(90)                // Rotation
    .pitch(0)
    .velocity(1, 0, 0)      // Initial velocity
    .build()
    .spawn(world);

// Spawn with properties
LivingEntity mob = new EntityBuilder<>(EntityTypes.CREEPER)
    .pos(100, 64, 200)
    .health(20)
    .maxHealth(20)
    .build()
    .spawn(world);
```

#### Configuration

```java
new EntityBuilder<>(EntityTypes.HORSE)
    .pos(player.getPos())       // Position
    .yaw(player.getYaw())       // Copy rotation
    .pitch(player.getPitch())
    .velocity(1, 1, 1)          // Movement
    .health(30)                 // Starting health
    .maxHealth(30)
    .build()
    .spawn(world);
```

#### Advanced Example

```java
// Create and spawn a boss
Wither wither = new EntityBuilder<>(EntityTypes.WITHER)
    .pos(x, y, z)
    .maxHealth(300)
    .health(300)
    .yaw(45)
    .velocity(0.5, 0, 0.5)
    .customName("Realm Boss")
    .build()
    .spawn(world);

// Wither is now spawned with all settings
```

---

### 5. ParticleBuilder

Create particle effects fluently.

#### Basic Particles

```java
// Spawn particles at position
new ParticleBuilder(ParticleTypes.FLAME)
    .pos(100, 64, 200)
    .count(10)
    .offset(0.5, 0.5, 0.5)
    .speed(1.0)
    .spawn(world);

// Particles in circle pattern
new ParticleBuilder(ParticleTypes.SOUL_FIRE_FLAME)
    .circle(center, radius)
    .count(20)
    .spawn(world);
```

#### Effect Patterns

```java
// Spiral effect
new ParticleBuilder(ParticleTypes.END_ROD)
    .spiral(center, height, radius)
    .spawn(world);

// Ring effect
new ParticleBuilder(ParticleTypes.ENCHANT)
    .ring(center, radius)
    .count(30)
    .spawn(world);

// Explosion effect
new ParticleBuilder(ParticleTypes.EXPLOSION)
    .pos(center)
    .count(50)
    .offset(1, 1, 1)
    .speed(2.0)
    .spawn(world);
```

#### Complete Example

```java
// Create an impressive effect
new ParticleBuilder(ParticleTypes.END_ROD)
    .pos(player.getPos())
    .count(10)
    .offset(1, 1, 1)
    .speed(0.5)
    .build()
    .spawn(world);

// Add sound
SoundHelper.playAtPos(world, player.getBlockPos(),
    SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
```

---

### 6. StatusEffectBuilder

Apply potion effects fluently.

#### Basic Effects

```java
// Apply strength effect
StatusEffect strength = new StatusEffectBuilder(StatusEffects.STRENGTH)
    .duration(200)      // 10 seconds (20 ticks/second)
    .amplifier(0)       // Level I
    .ambient(false)     // Don't show as ambient
    .showParticles(true) // Show particles
    .build()
    .apply(player);

// Apply poison
StatusEffect poison = new StatusEffectBuilder(StatusEffects.POISON)
    .duration(100)
    .amplifier(1)       // Level II
    .build()
    .apply(entity);
```

#### Effect Configuration

```java
new StatusEffectBuilder(StatusEffects.REGENERATION)
    .duration(600)           // 30 seconds
    .amplifier(2)            // Level III
    .ambient(true)           // Show as ambient
    .showParticles(true)     // Show visual effect
    .showIcon(true)          // Show effect icon
    .build()
    .apply(player);
```

#### Complete Example

```java
// Create multi-effect buff
StatusEffect buff1 = new StatusEffectBuilder(StatusEffects.STRENGTH)
    .duration(1200)          // 60 seconds
    .amplifier(1)
    .build()
    .apply(player);

StatusEffect buff2 = new StatusEffectBuilder(StatusEffects.SPEED)
    .duration(1200)
    .amplifier(1)
    .build()
    .apply(player);

// Notify player
ChatHelper.sendActionBar(player, "✓ Power boost activated!");
```

---

## Builder Patterns

### Chaining Pattern

All builders return `this` from methods, allowing chaining:

```java
Text message = new TextBuilder("A")
    .bold()           // Returns TextBuilder
    .color(GOLD)      // Returns TextBuilder
    .append("B")      // Returns TextBuilder
    .italic()         // Returns TextBuilder
    .build();         // Build and return final object
```

### Terminal Operation

All builders have a `.build()` method that:

- Creates the final object
- Returns it (never null)
- Is terminal (can't chain after)

```java
// ✅ Correct
Text text = new TextBuilder("Hello")
    .bold()
    .build();        // Terminal - returns Text

// ❌ Wrong
Text text = new TextBuilder("Hello")
    .build()
    .bold();         // Can't call methods on Text!
```

### Immutability

Builders create immutable objects:

```java
Text text = new TextBuilder("Original")
    .bold()
    .build();

// text is immutable - won't change
Text text2 = text;  // Same reference, same content
```

---

## Comparison: With/Without Builders

### Before (Without Builders)

```java
// Create item the hard way
ItemStack pickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
pickaxe.setCount(1);

var nbt = pickaxe.getOrCreateNbt();
nbt.putString("display", "Legendary Pickaxe");

// Add enchantments
EnchantmentHelper.addEnchantment(pickaxe, Enchantments.UNBREAKING, 3);
EnchantmentHelper.addEnchantment(pickaxe, Enchantments.EFFICIENCY, 5);

// Give to player (10 lines total)
player.giveItemStack(pickaxe);
```

### After (With Builders)

```java
// Create item the easy way (4 lines)
ItemStack pickaxe = new ItemStackBuilder(Items.DIAMOND_PICKAXE)
    .displayName("Legendary Pickaxe")
    .enchant(Enchantments.UNBREAKING, 3)
    .enchant(Enchantments.EFFICIENCY, 5)
    .build();

player.giveItemStack(pickaxe);
```

---

## Builder Best Practices

### ✅ Do

```java
// Group related configurations
Text msg = new TextBuilder("Success!")
    .success()      // Related: color
    .bold()         // Related: style
    .append(" ✓")
    .build();

// Use descriptive variable names
Vec3d launchVelocity = new Vec3dBuilder(1, 2, 3)
    .normalize()
    .scale(speed)
    .build();
```

### ❌ Don't

```java
// Don't forget .build()
Text msg = new TextBuilder("Text");  // Missing .build()!

// Don't break chains unnecessarily
Text msg = new TextBuilder("Text");
msg = msg.bold();              // Can't do this - already built
```

---

## See Also

- **[Code Examples](Examples.md)** - More builder examples
- **[API Reference](API-Reference.md)** - All methods
- **[Helpers Overview](Helpers-Overview.md)** - Other utilities

---

**Next:** [Code Examples →](Examples.md)
