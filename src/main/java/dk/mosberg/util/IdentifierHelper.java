package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import net.minecraft.util.Identifier;

/**
 * Utility for Minecraft Identifier operations. Provides convenient methods for creating and
 * validating identifiers in the "namespace:path" format.
 */
public final class IdentifierHelper {
    private IdentifierHelper() {}

    /**
     * Creates an Identifier from namespace and path.
     *
     * @param namespace the namespace (mod ID)
     * @param path the path component
     * @return a new Identifier
     * @throws IllegalArgumentException if either argument is invalid
     */
    @NotNull
    public static Identifier of(@NotNull String namespace, @NotNull String path) {
        return Identifier.of(namespace, path);
    }

    /**
     * Creates an Identifier from a "namespace:path" string. Defaults to minecraft namespace if no
     * colon is present.
     *
     * @param id the identifier string (e.g., "modname:item_name" or "item_name")
     * @return a new Identifier
     * @throws IllegalArgumentException if the identifier is invalid
     */
    @NotNull
    public static Identifier of(@NotNull String id) {
        if (id.contains(":")) {
            String[] parts = id.split(":", 2);
            return Identifier.of(parts[0], parts[1]);
        }
        return Identifier.of("minecraft", id);
    }

    /**
     * Gets the namespace portion of an Identifier.
     *
     * @param id the Identifier
     * @return the namespace
     */
    @NotNull
    public static String getNamespace(@NotNull Identifier id) {
        return id.getNamespace();
    }

    /**
     * Gets the path portion of an Identifier.
     *
     * @param id the Identifier
     * @return the path
     */
    @NotNull
    public static String getPath(@NotNull Identifier id) {
        return id.getPath();
    }

    /**
     * Checks if a string is a valid Identifier format.
     *
     * @param id the string to validate
     * @return true if the string is a valid identifier
     */
    public static boolean isValid(@NotNull String id) {
        try {
            of(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
