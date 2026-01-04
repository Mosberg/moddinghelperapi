package dk.mosberg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link IdentifierHelper} utilities.
 *
 * @since 1.0.0
 */
class IdentifierHelperTest {

    @Test
    void testOfWithNamespaceAndPath() {
        var id = IdentifierHelper.of("mymod", "myitem");
        assertNotNull(id);
        assertEquals("mymod", id.getNamespace());
        assertEquals("myitem", id.getPath());
    }

    @Test
    void testOfWithFullString() {
        var id = IdentifierHelper.of("mymod:myitem");
        assertNotNull(id);
        assertEquals("mymod", id.getNamespace());
        assertEquals("myitem", id.getPath());
    }

    @Test
    void testGetNamespace() {
        var id = IdentifierHelper.of("mymod:myitem");
        assertEquals("mymod", IdentifierHelper.getNamespace(id));
    }

    @Test
    void testGetPath() {
        var id = IdentifierHelper.of("mymod:myitem");
        assertEquals("myitem", IdentifierHelper.getPath(id));
    }

    @Test
    void testIsValidWithValidIdentifier() {
        assertTrue(IdentifierHelper.isValid("mymod:myitem"));
        assertTrue(IdentifierHelper.isValid("minecraft:diamond"));
        assertTrue(IdentifierHelper.isValid("test:test_name"));
    }

    @Test
    void testIsValidWithInvalidIdentifier() {
        assertFalse(IdentifierHelper.isValid("invalid"));
        assertFalse(IdentifierHelper.isValid("invalid:"));
        assertFalse(IdentifierHelper.isValid(":invalid"));
        assertFalse(IdentifierHelper.isValid("in valid:name"));
    }

    @Test
    void testOfThrowsOnNullNamespace() {
        assertThrows(NullPointerException.class, () -> IdentifierHelper.of(null, "path"));
    }

    @Test
    void testOfThrowsOnNullPath() {
        assertThrows(NullPointerException.class, () -> IdentifierHelper.of("namespace", null));
    }

    @Test
    void testOfThrowsOnNullFullString() {
        assertThrows(NullPointerException.class, () -> IdentifierHelper.of((String) null));
    }

    @Test
    void testMinecraftIdentifier() {
        var id = IdentifierHelper.of("minecraft:stone");
        assertEquals("minecraft", IdentifierHelper.getNamespace(id));
        assertEquals("stone", IdentifierHelper.getPath(id));
    }
}
