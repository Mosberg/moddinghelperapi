# Contributing

How to contribute to Modding Helper API.

## Welcome Contributors! 👋

Modding Helper API welcomes contributions from the community:

- 🐛 **Bug Reports** - Found an issue? Report it!
- ✨ **Feature Requests** - Have an idea? Share it!
- 📚 **Documentation** - Help improve the guides
- 💻 **Code Contributions** - Submit pull requests
- 🧪 **Tests** - Add test cases
- 📝 **Examples** - Share working code examples

---

## Getting Started

### 1. Fork and Clone

```bash
# Fork on GitHub, then clone your fork
git clone https://github.com/YOUR_USERNAME/moddinghelperapi.git
cd moddinghelperapi

# Add upstream remote
git remote add upstream https://github.com/mosberg/moddinghelperapi.git
```

### 2. Create a Branch

```bash
git checkout -b feature/your-feature-name
# or
git checkout -b fix/your-bug-fix
```

### 3. Set Up Development Environment

```bash
# Build the project
./gradlew.bat build

# Run tests
./gradlew.bat test

# Load in game
./gradlew.bat runClient
```

---

## Making Changes

### Code Style

Follow existing conventions:

```java
// ✅ Good
public final class MyHelper {
    private MyHelper() {}  // Prevent instantiation

    /**
     * Does something useful.
     *
     * @param input the input value
     * @return the result
     * @throws NullPointerException if input is null
     */
    public static @NotNull String process(@NotNull String input) {
        Objects.requireNonNull(input);
        // Implementation
        return result;
    }
}

// ❌ Avoid
public class MyHelper {
    public static String process(String input) {
        if (input == null) return null;  // Silent failure
    }
}
```

### Guidelines

1. **Null Safety**

   - Use `@NotNull` and `@Nullable` annotations
   - Validate parameters with `Objects.requireNonNull()`

2. **Documentation**

   - Write JavaDoc for all public methods
   - Include `@param`, `@return`, `@throws`
   - Add usage examples in JavaDoc

3. **Testing**

   - Add unit tests for new methods
   - Test edge cases and error conditions
   - Ensure tests pass: `./gradlew.bat test`

4. **Code Quality**

   - Run: `./gradlew.bat build` (includes quality checks)
   - Fix any style violations
   - No warnings on compilation

5. **Commits**
   - Clear commit messages
   - One feature per commit
   - Reference issues: "Fixes #123"

### Example Contribution

**Adding a new helper method:**

```java
// 1. Add method to helper class
public final class MyHelper {
    // ... existing methods ...

    /**
     * Performs a new operation on the given value.
     *
     * @param input the input value (not null)
     * @return the processed result
     * @throws NullPointerException if input is null
     */
    public static @NotNull String newOperation(@NotNull String input) {
        Objects.requireNonNull(input);
        // Implementation
        return result;
    }
}

// 2. Add test in MyHelperTest
@Test
void testNewOperation() {
    // Arrange
    String input = "test";

    // Act
    String result = MyHelper.newOperation(input);

    // Assert
    assertEquals("expected", result);
}

// 3. Test null handling
@Test
void testNewOperationNullInput() {
    assertThrows(NullPointerException.class, () -> {
        MyHelper.newOperation(null);
    });
}

// 4. Update JavaDoc index if needed
```

---

## Submitting Changes

### Before Submitting

1. **Run full build:**

   ```bash
   ./gradlew.bat clean build
   ```

2. **Run tests:**

   ```bash
   ./gradlew.bat test
   ```

3. **Verify no warnings:**

   - Check compiler output
   - Fix any issues

4. **Update documentation:**
   - Update README if needed
   - Update CHANGELOG
   - Update API reference

### Creating a Pull Request

1. **Push to your fork:**

   ```bash
   git push origin feature/your-feature-name
   ```

2. **Create PR on GitHub:**

   - Title: Clear description
   - Description: Explain what and why
   - Reference issues: "Fixes #123"
   - List changes made

3. **PR Template:**

   ```markdown
   ## Description

   Brief description of changes.

   ## Type of Change

   - [ ] Bug fix
   - [ ] New feature
   - [ ] Documentation update
   - [ ] Code quality improvement

   ## Checklist

   - [ ] I've tested the changes
   - [ ] Tests pass: `./gradlew.bat test`
   - [ ] Code builds cleanly: `./gradlew.bat build`
   - [ ] I've added/updated documentation
   - [ ] I've added/updated tests

   ## Related Issues

   Fixes #(issue number)

   ## Additional Context

   Any additional information helpful for review.
   ```

---

## Types of Contributions

### 🐛 Bug Reports

**Create an issue with:**

1. Clear title describing the bug
2. Steps to reproduce
3. Expected vs actual behavior
4. Your environment:
   - Minecraft version
   - Fabric version
   - Java version
   - Modding Helper API version

**Example:**

```
Title: ItemStackHelper.of() returns null for valid items

Steps to reproduce:
1. Create item with ItemStackHelper.of("minecraft:diamond", 1)
2. Item is null
3. Expected: ItemStack with diamond

Environment:
- Minecraft 1.21.11
- Fabric API 0.140.2+1.21.11
- Modding Helper API 1.0.0
- Java 21
```

### ✨ Feature Requests

**Create an issue with:**

1. Clear title
2. Use case / problem it solves
3. Proposed solution
4. Alternative approaches

**Example:**

```
Title: Add method to search blocks by name pattern

Use case: Find all blocks matching a pattern like "redstone_*"

Proposed solution:
BlockSearchHelper.findByPattern(world, center, radius, "redstone_*")

Benefits:
- Flexible block searching
- Useful for multi-block structures
```

### 📚 Documentation

**Contribute by:**

1. Improving existing guides
2. Adding examples
3. Fixing typos
4. Clarifying confusing sections
5. Adding new guides for missing topics

**Submit as:**

- Pull requests with markdown files
- Issue suggestions

### 💻 Code Contributions

**Guidelines:**

1. **New helpers:** Add to `src/main/java/dk/mosberg/util/`
2. **New builders:** Add to `src/main/java/dk/mosberg/util/builders/`
3. **Client code:** Add to `src/client/java/dk/mosberg/client/`
4. **Never:** Add gameplay features (this is a library mod)

**Size expectations:**

- Small changes: 1-3 methods
- Medium changes: New helper class
- Large changes: Multiple helpers + tests

### 🧪 Tests

**Help with testing by:**

1. Adding test cases for existing helpers
2. Improving test coverage
3. Creating test utilities
4. Testing in different environments

**Test file location:** `src/test/java/dk/mosberg/util/`

### 📝 Examples

**Share examples by:**

1. Creating example mods that use the API
2. Adding code examples to documentation
3. Sharing in discussions
4. Creating tutorial posts

---

## Review Process

### What Happens After PR Submission

1. **Automated Checks**

   - Tests run automatically
   - Code quality tools run
   - Coverage checked

2. **Manual Review**

   - Maintainer reviews code
   - Feedback provided if needed
   - Questions asked if unclear

3. **Revisions**

   - Make requested changes
   - Push updates to same branch
   - PR updates automatically

4. **Approval & Merge**
   - Approved when ready
   - Merged into main
   - Added to next release

### Review Criteria

- ✅ Code quality (no warnings)
- ✅ Tests pass and cover changes
- ✅ Documentation complete
- ✅ Follows project conventions
- ✅ No breaking changes without discussion
- ✅ Clear commit messages

---

## Development Workflow

### Common Commands

```bash
# Build everything
./gradlew.bat build

# Run specific test
./gradlew.bat test --tests "dk.mosberg.util.TextHelperTest"

# Run game with mod loaded
./gradlew.bat runClient

# Generate documentation
./gradlew.bat javadoc
```

### Project Structure

```
src/
  main/java/dk/mosberg/
    ├── ModdingHelperAPI.java        # Main entry point
    ├── util/
    │   ├── *Helper.java             # Utility helpers
    │   └── builders/                # Builder classes
    └── datagen/                     # Data generation

  client/java/dk/mosberg/client/
    └── ModdingHelperAPIClient.java # Client entry point

  test/java/dk/mosberg/util/
    └── *Test.java                  # Unit tests
```

---

## Getting Help

### Resources

- **Discussions:** [GitHub Discussions](https://github.com/mosberg/moddinghelperapi/discussions)
- **Issues:** [GitHub Issues](https://github.com/mosberg/moddinghelperapi/issues)
- **Discord:** [Fabric Discord](https://discord.gg/v6v4pMv)
- **Wiki:** [Documentation](Home.md)

### Before Contributing

1. Check [existing issues](https://github.com/mosberg/moddinghelperapi/issues)
2. Search [discussions](https://github.com/mosberg/moddinghelperapi/discussions)
3. Read [contributing guidelines](CONTRIBUTING.md)
4. Review [code of conduct](#code-of-conduct)

---

## Code of Conduct

### Our Pledge

We are committed to providing a welcoming and inspiring community for all. We expect all participants to adhere to the code of conduct.

### Expected Behavior

- Be respectful and inclusive
- Welcome feedback and criticism constructively
- Focus on what is best for the community
- Show empathy towards other community members

### Unacceptable Behavior

- Harassment or discrimination
- Disrespectful language
- Unwelcome sexual advances
- Spam or abuse
- Privacy violations

### Reporting Issues

Report violations to: [maintainer email]

---

## Recognition

Contributors are recognized in:

1. **README.md** - Contributors section
2. **CHANGELOG.md** - Credited for contributions
3. **GitHub** - Contribution graph
4. **Releases** - Credited in release notes

---

## Questions?

- 📧 **Email:** [contact info]
- 💬 **Discussions:** [Link to discussions]
- 🐛 **Issues:** [Link to issues]
- 💬 **Discord:** [Link to Discord]

---

**Thank you for contributing!** ❤️

Your contributions make Modding Helper API better for everyone.

---

**Need help?** → [FAQ](FAQ.md) | [Getting Started](Getting-Started.md)
