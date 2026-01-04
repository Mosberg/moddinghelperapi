package dk.mosberg.util.builders;

import org.jetbrains.annotations.NotNull;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Fluent builder for creating styled text components with method chaining.
 *
 * <p>
 * Provides a convenient way to construct complex text with multiple formatting options.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * var text =
 *         new TextBuilder("Hello").bold().color(Formatting.GOLD).append(" World").italic().build();
 * </pre>
 *
 * @since 1.0.0
 */
public final class TextBuilder {
    private MutableText text;

    /**
     * Creates a new TextBuilder with initial text content.
     *
     * @param initialText the starting text content
     * @throws NullPointerException if initialText is null
     */
    public TextBuilder(@NotNull String initialText) {
        this.text = Text.literal(initialText).copy();
    }

    /**
     * Applies bold formatting to the current text.
     *
     * @return this builder for chaining
     */
    @NotNull
    public TextBuilder bold() {
        this.text.setStyle(text.getStyle().withBold(true));
        return this;
    }

    /**
     * Applies italic formatting to the current text.
     *
     * @return this builder for chaining
     */
    @NotNull
    public TextBuilder italic() {
        this.text.setStyle(text.getStyle().withItalic(true));
        return this;
    }

    /**
     * Applies strikethrough formatting to the current text.
     *
     * @return this builder for chaining
     */
    @NotNull
    public TextBuilder strikethrough() {
        this.text.setStyle(text.getStyle().withStrikethrough(true));
        return this;
    }

    /**
     * Applies underline formatting to the current text.
     *
     * @return this builder for chaining
     */
    @NotNull
    public TextBuilder underline() {
        this.text.setStyle(text.getStyle().withUnderline(true));
        return this;
    }

    /**
     * Sets the color of the text.
     *
     * @param color the formatting color
     * @return this builder for chaining
     * @throws NullPointerException if color is null
     */
    @NotNull
    public TextBuilder color(@NotNull Formatting color) {
        this.text.setStyle(text.getStyle().withColor(color));
        return this;
    }

    /**
     * Appends additional text to this builder.
     *
     * @param appendText the text to append
     * @return this builder for chaining
     * @throws NullPointerException if appendText is null
     */
    @NotNull
    public TextBuilder append(@NotNull String appendText) {
        this.text.append(Text.literal(appendText));
        return this;
    }

    /**
     * Appends a styled text component to this builder.
     *
     * @param textComponent the text component to append
     * @return this builder for chaining
     * @throws NullPointerException if textComponent is null
     */
    @NotNull
    public TextBuilder append(@NotNull Text textComponent) {
        this.text.append(textComponent);
        return this;
    }

    /**
     * Applies success formatting (green color).
     *
     * @return this builder for chaining
     */
    @NotNull
    public TextBuilder success() {
        return color(Formatting.GREEN);
    }

    /**
     * Applies error formatting (red color).
     *
     * @return this builder for chaining
     */
    @NotNull
    public TextBuilder error() {
        return color(Formatting.RED);
    }

    /**
     * Applies warning formatting (yellow/gold color).
     *
     * @return this builder for chaining
     */
    @NotNull
    public TextBuilder warning() {
        return color(Formatting.GOLD);
    }

    /**
     * Applies info formatting (cyan/aqua color).
     *
     * @return this builder for chaining
     */
    @NotNull
    public TextBuilder info() {
        return color(Formatting.AQUA);
    }

    /**
     * Builds and returns the final styled text component.
     *
     * @return the constructed MutableText
     */
    @NotNull
    public MutableText build() {
        return text.copy();
    }

    /**
     * Returns the built text (alias for build() for convenience).
     *
     * @return the constructed MutableText
     */
    @NotNull
    public MutableText get() {
        return build();
    }
}
