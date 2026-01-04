package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Thread-safe singleton for GSON instances. Provides both compact and pretty-print configurations
 * for JSON operations.
 */
public final class GsonInstance {
    private static final Gson COMPACT = new GsonBuilder().create();
    private static final Gson PRETTY = new GsonBuilder().setPrettyPrinting().create();

    private GsonInstance() {}

    /**
     * Gets the compact GSON instance (no extra whitespace). Configured for lenient parsing.
     *
     * @return the compact Gson instance
     */
    @NotNull
    public static Gson compact() {
        return COMPACT;
    }

    /**
     * Gets the pretty-print GSON instance (formatted with indentation). Configured for lenient
     * parsing.
     *
     * @return the pretty-print Gson instance
     */
    @NotNull
    public static Gson pretty() {
        return PRETTY;
    }
}
