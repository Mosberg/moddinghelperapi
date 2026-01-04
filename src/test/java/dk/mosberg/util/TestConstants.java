package dk.mosberg.util;

import net.minecraft.util.Identifier;

/**
 * Test constants for use across test suites. Provides common test values and identifiers.
 *
 * @since 1.0.0
 */
public final class TestConstants {
    private TestConstants() {}

    // Identifiers
    public static final String TEST_NAMESPACE = "testmod";
    public static final String TEST_PATH = "test_item";
    public static final Identifier TEST_IDENTIFIER = Identifier.of(TEST_NAMESPACE, TEST_PATH);
    public static final Identifier MINECRAFT_IDENTIFIER = Identifier.of("minecraft", "diamond");

    // Strings
    public static final String TEST_STRING = "Test String Value";
    public static final String EMPTY_STRING = "";
    public static final String LONG_STRING = "a".repeat(256);

    // Numbers
    public static final int TEST_INT = 42;
    public static final int ZERO = 0;
    public static final int NEGATIVE_INT = -42;
    public static final double TEST_DOUBLE = 3.14159;
    public static final double TEST_DOUBLE_NEGATIVE = -3.14159;
    public static final float TEST_FLOAT = 1.5F;

    // NBT
    public static final String NBT_KEY = "test_key";
    public static final String NBT_KEY_MISSING = "missing_key";
    public static final String NBT_COMPOUND_KEY = "compound";

    // Coordinates
    public static final int TEST_X = 100;
    public static final int TEST_Y = 64;
    public static final int TEST_Z = -200;

    // Positions
    public static final double TEST_POS_X = 100.5;
    public static final double TEST_POS_Y = 64.0;
    public static final double TEST_POS_Z = -200.5;

    // JSON
    public static final String TEST_JSON_OBJECT = "{\"key\":\"value\",\"number\":42}";
    public static final String TEST_JSON_ARRAY = "[1,2,3,4,5]";
    public static final String TEST_JSON_PRETTY = "{\n  \"key\": \"value\"\n}";
}
