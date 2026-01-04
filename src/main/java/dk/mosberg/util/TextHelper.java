package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Utility for Text component creation and formatting. Provides convenient methods for building
 * colored and formatted text messages for players.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * MutableText message = TextHelper.success("Success! ").append(TextHelper.italic("formatted"));
 * player.sendMessage(message);
 * </pre>
 */
public final class TextHelper {
    private TextHelper() {}

    /**
     * Creates a literal text component.
     *
     * @param text the text content
     * @return a new MutableText component
     */
    @NotNull
    public static MutableText literal(@NotNull String text) {
        return Text.literal(text);
    }

    /**
     * Creates bold text.
     *
     * @param text the text content
     * @return bold formatted text
     */
    @NotNull
    public static MutableText bold(@NotNull String text) {
        return literal(text).formatted(Formatting.BOLD);
    }

    /**
     * Creates italic text.
     *
     * @param text the text content
     * @return italic formatted text
     */
    @NotNull
    public static MutableText italic(@NotNull String text) {
        return literal(text).formatted(Formatting.ITALIC);
    }

    /**
     * Creates text with a custom color.
     *
     * @param text the text content
     * @param color the text color
     * @return colored text
     */
    @NotNull
    public static MutableText colored(@NotNull String text, @NotNull Formatting color) {
        return literal(text).formatted(color);
    }

    /**
     * Creates a success (green) message.
     *
     * @param message the message text
     * @return green formatted text
     */
    @NotNull
    public static MutableText success(@NotNull String message) {
        return colored(message, Formatting.GREEN);
    }

    /**
     * Creates an error (red) message.
     *
     * @param message the message text
     * @return red formatted text
     */
    @NotNull
    public static MutableText error(@NotNull String message) {
        return colored(message, Formatting.RED);
    }

    /**
     * Creates a warning (yellow) message.
     *
     * @param message the message text
     * @return yellow formatted text
     */
    @NotNull
    public static MutableText warning(@NotNull String message) {
        return colored(message, Formatting.YELLOW);
    }

    /**
     * Creates an info (blue) message.
     *
     * @param message the message text
     * @return blue formatted text
     */
    @NotNull
    public static MutableText info(@NotNull String message) {
        return colored(message, Formatting.BLUE);
    }
}
