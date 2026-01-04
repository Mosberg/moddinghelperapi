# Minecraft 1.21.11 API Notes

Important API differences and considerations for Minecraft 1.21.11.

## Version Overview

- **Version:** 1.21.11
- **Fabric API:** 0.140.2+1.21.11
- **Fabric Loader:** 0.18.4
- **Java:** 21 (enforced)
- **YARP:** 1.21.11+build.3

---

## Major API Changes

### 1. Identifier Construction

**1.20.x (Old):**

```java
Identifier id = new Identifier("mymod", "item");
```

**1.21.11 (New):**

```java
Identifier id = Identifier.of("mymod", "item");
```

**Modding Helper API Support:**

```java
Identifier id = IdentifierHelper.of("mymod", "item");
```

---

### 2. NBT Optional Returns

**1.20.x (Old):**

```java
String name = nbt.getString("Name");  // Throws if missing
```

**1.21.11 (New):**

```java
Optional<String> name = nbt.getOptional(NbtElement.STRING_TYPE, "Name");
String value = name.orElse("Default");
```

**Modding Helper API Support:**

```java
String name = NBTHelper.getString(nbt, "Name", "Default");
```

---

### 3. Formatting Import Path

**Changed location:**

```java
// ✅ Correct (1.21.11)
import net.minecraft.util.Formatting;

// ❌ Wrong (older versions)
import net.minecraft.formatting.Formatting;
```

---

### 4. Component System

**1.21.11 uses DataComponentType instead of direct NBT for:**

- Item enchantments
- Display names
- Custom model data
- Attributes

**Example - Custom Display Name:**

```java
ItemStack stack = new ItemStack(Items.DIAMOND);
Text displayName = Text.literal("Legendary Diamond")
    .withColor(0xFFD700);  // Gold color
stack.set(DataComponentTypes.CUSTOM_NAME, displayName);
```

**Modding Helper API Support:**

```java
ItemStack stack = new ItemStackBuilder(Items.DIAMOND)
    .displayName(new TextBuilder("Legendary Diamond")
        .color(Formatting.GOLD)
        .build())
    .build();
```

---

### 5. BlockEntity Changes

**getNbt() now returns Optional:**

```java
// 1.20.x
NbtCompound nbt = blockEntity.createNbt();

// 1.21.11
Optional<NbtCompound> optionalNbt = blockEntity.saveToNbt(new NbtCompound());
NbtCompound nbt = optionalNbt.orElse(new NbtCompound());
```

**Modding Helper API Support:**

```java
NbtCompound nbt = BlockEntityHelper.getNBT(blockEntity);
```

---

### 6. Text Component Styling

**1.21.11 Improved Style API:**

```java
// Old way (still works)
Text text = Text.literal("Hello")
    .setStyle(Style.EMPTY.withBold(true));

// New way (preferred)
Text text = Text.literal("Hello").bold();
```

**Modding Helper API Support:**

```java
MutableText text = new TextBuilder("Hello")
    .bold()
    .build();
```

---

### 7. Entity Attributes

**Component-based system:**

```java
// Apply knockback to entity
LivingEntity entity = (LivingEntity) entityInQuestion;
entity.addVelocity(1, 0.5, 1);  // x, y, z velocity

// Apply status effect
entity.addStatusEffect(new StatusEffectInstance(
    StatusEffects.SPEED,
    200,  // duration in ticks
    1,    // amplifier (level - 1)
    false, // ambient
    false  // show particles
));
```

---

### 8. Client/Server Environment

**Environment detection:**

```java
// Check if client
if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
    // Client-only code
}

// Check if server
if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
    // Server-only code
}

// Thread-safe check
if (Thread.currentThread().getName().contains("Render")) {
    // Rendering thread
}
```

---

## Removed/Deprecated Methods

### Items/Blocks

| Method            | Replacement              | Notes        |
| ----------------- | ------------------------ | ------------ |
| `Item.byRawId()`  | `Registries.ITEM.get()`  | Use registry |
| `Block.byRawId()` | `Registries.BLOCK.get()` | Use registry |

### Entity

| Method               | Replacement       | Notes         |
| -------------------- | ----------------- | ------------- |
| `Entity.isInWater()` | Check fluid state | More complex  |
| `Entity.age`         | Custom tracking   | Removed field |

### NBT

| Method            | Replacement         | Notes        |
| ----------------- | ------------------- | ------------ |
| `NBT.getString()` | `NBT.getOptional()` | Now Optional |
| `NBT.getInt()`    | `NBT.getOptional()` | Now Optional |

---

## New Features in 1.21.11

### 1. Data Components

Items can have components (replacing some NBT):

```java
ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);

// Set unbreakable
stack.set(DataComponentTypes.UNBREAKABLE, new Unbreakable(false, true));

// Set enchantments
EnchantmentLevelEntry ench = new EnchantmentLevelEntry(
    Enchantments.SHARPNESS,
    5
);
stack.set(DataComponentTypes.ENCHANTMENTS,
    new EnchantmentsComponent(List.of(ench), false));

// Set custom model data
stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(42));
```

### 2. Structured NBT

More structured NBT access:

```java
// Safe path navigation
NbtElement element = nbt.get("path.to.element");
if (element instanceof NbtCompound compound) {
    // Handle as compound
}
```

### 3. Networking

Improved networking with payloads:

```java
// Send packet
ServerPlayNetworking.send(player, new MyPayload(...));

// Register payload handler
ServerPlayNetworking.registerGlobalReceiver(MyPayload.ID, (payload, context) -> {
    // Handle on server
});
```

---

## Compatibility Notes

### Registry Access

Always use registries for lookups:

```java
// ✅ Correct
Item item = Registries.ITEM.get(id);

// ❌ Avoid
Item item = Registry.ITEM.get(id);  // May be null
```

### Optional Handling

Always unwrap Optionals:

```java
// ✅ Correct
String value = optional.orElse("default");
String value = optional.orElseThrow();
String value = optional.orElseGet(() -> computeDefault());

// ❌ Wrong
String value = optional.get();  // Throws if empty, avoid
```

### Type Checks

Use instanceof patterns:

```java
// ✅ Correct (pattern matching)
if (entity instanceof LivingEntity living) {
    living.damage(source, damage);
}

// ✅ Also correct
if (entity instanceof LivingEntity) {
    LivingEntity living = (LivingEntity) entity;
    living.damage(source, damage);
}
```

---

## Common Pitfalls

### Pitfall 1: Assuming NBT Keys Exist

```java
// ❌ Crashes if key missing
String name = nbt.getString("Name");

// ✅ Safe
String name = NBTHelper.getString(nbt, "Name", "Default");
```

### Pitfall 2: Client Code in Server Context

```java
// ❌ Crashes on server (no client screen manager)
Screen screen = MinecraftClient.getInstance().currentScreen;

// ✅ Check environment first
if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
    Screen screen = MinecraftClient.getInstance().currentScreen;
}
```

### Pitfall 3: Using @Environment Incorrectly

```java
// ✅ Correct - marks class as client-only
@Environment(EnvType.CLIENT)
public class ClientOnlyClass { }

// ❌ Wrong - doesn't actually restrict access
public class ServerOnlyClass {
    @Environment(EnvType.SERVER)
    void serverMethod() { }  // Doesn't work on class
}
```

### Pitfall 4: Registry Lookups During Init

```java
// ❌ May fail if registry not initialized
public class MyMod implements ModInitializer {
    private static final Item ITEM = Registries.ITEM.get(id);  // Too early!
}

// ✅ Look up in event or later
ServerTickEvents.END_SERVER_TICK.register(server -> {
    Item item = Registries.ITEM.get(id);
});
```

---

## Migration Guide

### From Minecraft 1.20.x to 1.21.11

1. **Update Identifiers:**

   - Change `new Identifier(...)` to `Identifier.of(...)`

2. **Handle NBT Optionals:**

   - Wrap all NBT reads in `Optional` unwrapping
   - Use defaults with `.orElse()`

3. **Update Imports:**

   - Check Formatting import path
   - Update any removed imports

4. **Component System:**

   - Learn DataComponentType for item properties
   - Migrate from NBT-based data

5. **Test Thoroughly:**
   - Registry-dependent features may behave differently
   - Test with actual game context

---

## Resources

- [Fabric Documentation](https://docs.fabricmc.net/)
- [Minecraft Wiki - 1.21.11](https://minecraft.wiki/)
- [Yarn Mappings](https://github.com/FabricMC/yarn)
- [Fabric API](https://maven.fabricmc.net/)

---

## Getting Help

1. **Check this guide first**
2. **Search [Fabric Discord](https://discord.gg/v6v4pMv)**
3. **Check [GitHub Issues](https://github.com/mosberg/moddinghelperapi/issues)**
4. **Ask in [Discussions](https://github.com/mosberg/moddinghelperapi/discussions)**

---

**Questions about API?** → [API Reference](API-Reference.md) | [FAQ](FAQ.md)
