# Testing Guide

How to write and run tests for Modding Helper API.

## Overview

Modding Helper API includes a comprehensive test suite:

- **72 Total Tests** across 6 test classes
- **61 Passing Tests** (non-registry dependent)
- **11 Registry-dependent Tests** (require Minecraft game context)
- **Framework:** JUnit 5 (Jupiter)
- **Location:** `src/test/java/dk/mosberg/util/`

---

## Running Tests

### Run All Tests

```bash
./gradlew.bat test
```

Output:

```
BUILD SUCCESSFUL in 5s
72 tests completed, 61 passed, 11 skipped
```

### Run Specific Test Class

```bash
./gradlew.bat test --tests "dk.mosberg.util.NBTHelperTest"
./gradlew.bat test --tests "*Helper*"  # All *Helper tests
./gradlew.bat test --tests "*Stack*"   # All *Stack tests
```

### Run Specific Test Method

```bash
./gradlew.bat test --tests "dk.mosberg.util.TextHelperTest.testBoldFormatting"
```

### Re-run Failed Tests

```bash
./gradlew.bat test --rerun-tasks
```

---

## Test Structure

### Basic Test Class

```java
package dk.mosberg.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class MyHelperTest {

    // Setup before each test
    @BeforeEach
    void setUp() {
        // Initialize test data
    }

    // Individual test method
    @Test
    void testBasicFunctionality() {
        // Arrange - setup test data
        String input = "test";

        // Act - execute the code being tested
        String result = MyHelper.process(input);

        // Assert - verify results
        assertEquals("expected", result);
    }
}
```

### Test Naming Convention

```java
@Test
void test[FeatureName][Scenario][Expected]() {
    // testStringCreation() - basic feature test
    // testNullHandlingWithEmpty() - edge case
    // testErrorOnInvalidInput() - error condition
}
```

---

## Common Assertions

### String Assertions

```java
@Test
void testStrings() {
    String result = "Hello";

    assertEquals("Hello", result);
    assertNotEquals("Goodbye", result);
    assertTrue(result.startsWith("H"));
    assertFalse(result.isEmpty());
}
```

### Null Assertions

```java
@Test
void testNullHandling() {
    Object obj = null;

    assertNull(obj);
    assertNotNull(someValue);
}
```

### Collection Assertions

```java
@Test
void testCollections() {
    List<String> items = Arrays.asList("a", "b", "c");

    assertEquals(3, items.size());
    assertTrue(items.contains("a"));
    assertFalse(items.isEmpty());
}
```

### Exception Assertions

```java
@Test
void testExceptionHandling() {
    assertThrows(NullPointerException.class, () -> {
        MyHelper.unsafeMethod(null);
    });

    assertDoesNotThrow(() -> {
        MyHelper.safeMethod(validInput);
    });
}
```

---

## Writing Tests for Helpers

### Example: Testing NBTHelper

```java
class NBTHelperTest {

    private NbtCompound nbt;

    @BeforeEach
    void setUp() {
        nbt = new NbtCompound();
    }

    @Test
    void testWriteAndReadString() {
        // Arrange
        String key = "Name";
        String value = "TestValue";

        // Act
        NBTHelper.putString(nbt, key, value);
        String result = NBTHelper.getString(nbt, key, "Default");

        // Assert
        assertEquals(value, result);
    }

    @Test
    void testReadWithDefault() {
        // When key doesn't exist, should return default
        String result = NBTHelper.getString(nbt, "Missing", "Default");
        assertEquals("Default", result);
    }

    @Test
    void testContains() {
        NBTHelper.putString(nbt, "Key", "Value");

        assertTrue(NBTHelper.contains(nbt, "Key"));
        assertFalse(NBTHelper.contains(nbt, "Missing"));
    }

    @Test
    void testRemove() {
        NBTHelper.putString(nbt, "Key", "Value");
        NBTHelper.remove(nbt, "Key");

        assertFalse(NBTHelper.contains(nbt, "Key"));
    }
}
```

### Example: Testing Builders

```java
class TextBuilderTest {

    @Test
    void testTextCreation() {
        MutableText text = new TextBuilder("Hello")
            .bold()
            .color(Formatting.GOLD)
            .build();

        assertNotNull(text);
        assertEquals("Hello", text.getString());
    }

    @Test
    void testAppending() {
        MutableText text = new TextBuilder("Hello")
            .append(" World")
            .build();

        String content = text.getString();
        assertTrue(content.contains("Hello"));
        assertTrue(content.contains("World"));
    }

    @Test
    void testColorPresets() {
        MutableText success = new TextBuilder("Success")
            .success()
            .build();

        assertNotNull(success);
    }
}
```

### Example: Testing ItemStackHelper

```java
class ItemStackHelperTest {

    @Test
    void testCreation() {
        ItemStack stack = ItemStackHelper.of("minecraft:diamond", 5);

        assertNotNull(stack);
        assertEquals(5, stack.getCount());
    }

    @Test
    void testEmpty() {
        ItemStack empty = ItemStackHelper.of("minecraft:air", 0);

        assertTrue(ItemStackHelper.isEmpty(empty));
    }

    @Test
    void testFull() {
        ItemStack stack = ItemStackHelper.of("minecraft:diamond", 64);

        assertTrue(ItemStackHelper.isFull(stack));
    }
}
```

---

## Test Constants

Use `TestConstants` for shared test data:

```java
// In TestConstants.java
public class TestConstants {
    public static final String TEST_NAMESPACE = "testmod";
    public static final String TEST_PATH = "test_item";
    public static final BlockPos TEST_POS = new BlockPos(100, 64, 200);
}

// In test class
class MyTest {
    @Test
    void testWithConstants() {
        String id = TestConstants.TEST_NAMESPACE + ":" + TestConstants.TEST_PATH;
        BlockPos pos = TestConstants.TEST_POS;
    }
}
```

---

## Registry-Dependent Tests

Some tests fail without Minecraft registry bootstrap. These are marked:

```java
@Test
void testRequiresRegistry() {
    // Tests that need Items.DIAMOND, Blocks.STONE, etc.
    // Will fail in plain JVM test environment
    // Will pass in Minecraft test fixtures environment
}
```

To enable registry tests, use Fabric test fixtures:

```gradle
testImplementation 'net.fabricmc.fabric-api:fabric-api:${fabric_version}:test'
```

---

## Debugging Tests

### Run Tests with Debug Output

```bash
./gradlew.bat test --info
./gradlew.bat test --debug
```

### Print Debug Info in Tests

```java
@Test
void testWithDebug() {
    String value = "test";
    System.out.println("Value: " + value);  // Will print in test output

    assertEquals("test", value);
}
```

### Run in IDE with Debugger

- IntelliJ: Right-click test → Debug
- Eclipse: Right-click test → Debug As → JUnit Test
- VS Code: Click "Debug" above test method

---

## Best Practices

### 1. Arrange-Act-Assert Pattern

```java
@Test
void testFeature() {
    // Arrange - setup
    String input = "data";

    // Act - execute
    String result = process(input);

    // Assert - verify
    assertEquals("processed", result);
}
```

### 2. One Assertion Per Test

```java
// ❌ Multiple assertions make debugging harder
@Test
void testCreation() {
    ItemStack stack = ItemStackHelper.of("minecraft:diamond", 5);
    assertEquals(5, stack.getCount());
    assertNotNull(stack);
    assertTrue(stack.isStackable());
}

// ✅ Separate tests
@Test
void testCreationReturnsStack() {
    ItemStack stack = ItemStackHelper.of("minecraft:diamond", 5);
    assertNotNull(stack);
}

@Test
void testCreationSetsCount() {
    ItemStack stack = ItemStackHelper.of("minecraft:diamond", 5);
    assertEquals(5, stack.getCount());
}

@Test
void testCreatedStackIsStackable() {
    ItemStack stack = ItemStackHelper.of("minecraft:diamond", 5);
    assertTrue(stack.isStackable());
}
```

### 3. Test Edge Cases

```java
@Test
void testNullInput() {
    assertThrows(NullPointerException.class, () -> {
        MyHelper.process(null);
    });
}

@Test
void testEmptyString() {
    String result = MyHelper.process("");
    assertNotNull(result);
}

@Test
void testBoundaryValues() {
    // Test at 0, max value, etc.
    assertEquals(0, process(0));
    assertEquals(Integer.MAX_VALUE, process(Integer.MAX_VALUE));
}
```

### 4. Use Descriptive Names

```java
// ❌ Unclear
@Test
void test1() { }

// ✅ Clear
@Test
void testNBTHelperReturnsDefaultWhenKeyMissing() { }
```

### 5. Test Both Success and Failure

```java
@Test
void testSuccessPath() {
    // Should work
    ItemStack stack = ItemStackHelper.of("minecraft:diamond", 1);
    assertNotNull(stack);
}

@Test
void testFailurePath() {
    // Should handle gracefully
    ItemStack stack = ItemStackHelper.of("nonexistent:item", 1);
    assertNull(stack);  // Or check for default/error behavior
}
```

---

## Continuous Integration

Tests run automatically when:

1. **Building locally:** `./gradlew build`
2. **Pushing to GitHub:** Via GitHub Actions
3. **Creating pull requests:** Before merge
4. **Pre-commit:** If Git hooks configured

### GitHub Actions Example

```yaml
# .github/workflows/test.yml
name: Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
      - run: ./gradlew test
```

---

## Coverage Reports

Generate and view code coverage:

```bash
./gradlew.bat test
```

---

## Troubleshooting Tests

### Tests Don't Run

1. Check test class names end with `Test`
2. Check test methods annotated with `@Test`
3. Ensure `@BeforeEach` if setup needed
4. Try: `./gradlew.bat clean test`

### Tests Fail Unexpectedly

1. Read full error message in logs
2. Add `System.out.println()` for debugging
3. Check test data in `setUp()`
4. Verify assumptions about Minecraft behavior

### Registry Tests Fail

- Expected behavior without game context
- Use Fabric test fixtures to enable
- Or skip with `@Disabled` annotation

---

**Need help?** → [FAQ](FAQ.md) | [Troubleshooting](Troubleshooting.md)
