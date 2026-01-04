package dk.mosberg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import net.minecraft.util.Formatting;

/**
 * Unit tests for {@link TextHelper} utilities.
 *
 * @since 1.0.0
 */
class TextHelperTest {

    @Test
    void testLiteral() {
        var text = TextHelper.literal("Hello");
        assertNotNull(text);
        assertEquals("Hello", text.getString());
    }

    @Test
    void testBold() {
        var text = TextHelper.bold("Bold Text");
        assertNotNull(text);
        assertTrue(text.getStyle().isBold());
        assertEquals("Bold Text", text.getString());
    }

    @Test
    void testItalic() {
        var text = TextHelper.italic("Italic Text");
        assertNotNull(text);
        assertTrue(text.getStyle().isItalic());
        assertEquals("Italic Text", text.getString());
    }

    @Test
    void testColored() {
        var text = TextHelper.colored("Gold Text", Formatting.GOLD);
        assertNotNull(text);
        assertNotNull(text.getStyle().getColor());
        assertEquals("Gold Text", text.getString());
    }

    @Test
    void testSuccess() {
        var text = TextHelper.success("Success!");
        assertNotNull(text);
        assertEquals("Success!", text.getString());
    }

    @Test
    void testError() {
        var text = TextHelper.error("Error!");
        assertNotNull(text);
        assertEquals("Error!", text.getString());
    }

    @Test
    void testWarning() {
        var text = TextHelper.warning("Warning!");
        assertNotNull(text);
        assertEquals("Warning!", text.getString());
    }

    @Test
    void testInfo() {
        var text = TextHelper.info("Info!");
        assertNotNull(text);
        assertEquals("Info!", text.getString());
    }

    @Test
    void testLiteralThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> TextHelper.literal(null));
    }

    @Test
    void testBoldThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> TextHelper.bold(null));
    }

    @Test
    void testItalicThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> TextHelper.italic(null));
    }

    @Test
    void testColoredThrowsOnNullText() {
        assertThrows(NullPointerException.class, () -> TextHelper.colored(null, Formatting.GOLD));
    }

    @Test
    void testColoredThrowsOnNullFormatting() {
        assertThrows(NullPointerException.class, () -> TextHelper.colored("Text", null));
    }

    @Test
    void testAppendText() {
        var main = TextHelper.literal("Hello ");
        var appended = TextHelper.literal("World");
        var result = main.append(appended);

        assertEquals("Hello World", result.getString());
    }

    @Test
    void testMultipleFormattingChain() {
        var text = TextHelper.success("Success! ").append(TextHelper.italic("formatted"));
        assertEquals("Success! formatted", text.getString());
    }
}
