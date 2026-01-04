package dk.mosberg.client.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

/**
 * Simple client helper for building tooltip line lists.
 */
@Environment(EnvType.CLIENT)
public final class ClientTooltipHelper {
    private ClientTooltipHelper() {}

    /**
     * Builds an immutable list of tooltip lines from raw strings (literal text).
     *
     * @param lines strings to convert to literal text
     * @return immutable list of {@link Text} lines
     */
    public static @NotNull List<Text> fromStrings(@NotNull List<String> lines) {
        List<Text> result = new ArrayList<>(lines.size());
        for (String line : lines) {
            result.add(Text.literal(line));
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Convenience overload for varargs strings.
     *
     * @param lines strings to convert to literal text
     * @return immutable list of {@link Text} lines
     */
    public static @NotNull List<Text> fromStrings(@NotNull String... lines) {
        List<Text> result = new ArrayList<>(lines.length);
        for (String line : lines) {
            result.add(Text.literal(line));
        }
        return Collections.unmodifiableList(result);
    }
}
