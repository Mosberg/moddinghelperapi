package dk.mosberg.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

/**
 * Utility for managing persistent data on entities using Fabric's attachment API. Provides methods
 * for storing and retrieving data that persists across server restarts.
 *
 * <p>
 * <b>Note:</b> You must create your own {@link AttachmentType} instances and register them. This
 * helper provides convenience methods for working with attachments.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * // First, create and register an attachment type in your mod initializer:
 * public static final AttachmentType&lt;Integer&gt; SCORE =
 *         AttachmentRegistry.createPersistent(new Identifier("mymod", "score"), Codec.INT);
 *
 * // Then use this helper:
 * PersistentDataHelper.set(entity, SCORE, 100);
 * int score = PersistentDataHelper.get(entity, SCORE, 0);
 * </pre>
 */
public final class PersistentDataHelper {
    private PersistentDataHelper() {}

    /**
     * Sets an attachment value on a target.
     *
     * @param target the attachment target (entity, block entity, etc.)
     * @param type the attachment type
     * @param value the value to set
     * @param <A> the attachment value type
     * @param <T> the target type
     */

    @SuppressWarnings("null")
    public static <A, T extends AttachmentTarget> void set(@NotNull T target,
            @NotNull AttachmentType<@NotNull A> type, @Nullable A value) {
        if (target != null) {
            target.setAttached(type, value);
        }
    }

    /**
     * Gets an attachment value from a target.
     *
     * @param target the attachment target
     * @param type the attachment type
     * @param <A> the attachment value type
     * @param <T> the target type
     * @return the value, or null if not present
     */
    @SuppressWarnings("null")
    @Nullable

    public static <A, T extends AttachmentTarget> A get(@NotNull T target,
            @NotNull AttachmentType<@Nullable A> type) {
        if (target != null) {
            return target.getAttached(type);
        }
        return null;
    }

    /**
     * Gets an attachment value with a default if not present.
     *
     * @param target the attachment target
     * @param type the attachment type
     * @param defaultValue the default value
     * @param <A> the attachment value type
     * @param <T> the target type
     * @return the value, or default if not present
     */
    @NotNull

    public static <A, T extends AttachmentTarget> A getOrDefault(@NotNull T target,
            @NotNull AttachmentType<@Nullable A> type, @NotNull A defaultValue) {
        if (target != null) {
            @SuppressWarnings("null")
            A value = target.getAttached(type);
            return value != null ? value : defaultValue;
        }
        return defaultValue;
    }

    /**
     * Checks if a target has an attachment.
     *
     * @param target the attachment target
     * @param type the attachment type
     * @param <A> the attachment value type
     * @param <T> the target type
     * @return true if the attachment is present
     */

    @SuppressWarnings("null")
    public static <A, T extends AttachmentTarget> boolean has(@NotNull T target,
            @NotNull AttachmentType<@Nullable A> type) {
        return target != null && target.getAttached(type) != null;
    }

    /**
     * Removes an attachment from a target.
     *
     * @param target the attachment target
     * @param type the attachment type
     * @param <A> the attachment value type
     * @param <T> the target type
     * @return the previous value, or null if not present
     */
    @SuppressWarnings("null")
    @Nullable

    public static <A, T extends AttachmentTarget> A remove(@NotNull T target,
            @NotNull AttachmentType<@Nullable A> type) {
        if (target == null) {
            return null;
        }
        A value = target.getAttached(type);
        target.setAttached(type, null);
        return value;
    }

    /**
     * Gets or creates a default attachment value.
     *
     * @param target the attachment target
     * @param type the attachment type
     * @param defaultSupplier supplier for the default value
     * @param <A> the attachment value type
     * @param <T> the target type
     * @return the existing or newly created value
     */
    @SuppressWarnings("null")
    @NotNull

    public static <A, T extends AttachmentTarget> A getOrCreate(@NotNull T target,
            @NotNull AttachmentType<@Nullable A> type,
            @NotNull java.util.function.Supplier<A> defaultSupplier) {
        if (target == null) {
            return defaultSupplier.get();
        }
        A value = target.getAttached(type);
        if (value == null) {
            value = defaultSupplier.get();
            target.setAttached(type, value);
        }
        return value;
    }

    /**
     * Modifies an attachment value if it exists.
     *
     * @param target the attachment target
     * @param type the attachment type
     * @param modifier function to modify the value
     * @param <A> the attachment value type
     * @param <T> the target type
     * @return true if the value was modified
     */

    @SuppressWarnings("null")
    public static <A, T extends AttachmentTarget> boolean modify(@NotNull T target,
            @NotNull AttachmentType<@Nullable A> type,
            @NotNull java.util.function.UnaryOperator<A> modifier) {
        if (target == null) {
            return false;
        }
        A value = target.getAttached(type);
        if (value != null) {
            target.setAttached(type, modifier.apply(value));
            return true;
        }
        return false;
    }

    /**
     * Modifies an attachment value or creates it with a default.
     *
     * @param target the attachment target
     * @param type the attachment type
     * @param defaultSupplier supplier for the default value
     * @param modifier function to modify the value
     * @param <A> the attachment value type
     * @param <T> the target type
     */

    @SuppressWarnings("null")
    public static <A, T extends AttachmentTarget> void modifyOrCreate(@NotNull T target,
            @NotNull AttachmentType<@Nullable A> type,
            @NotNull java.util.function.Supplier<A> defaultSupplier,
            @NotNull java.util.function.UnaryOperator<A> modifier) {
        if (target == null) {
            return;
        }
        A value = target.getAttached(type);
        if (value == null) {
            value = defaultSupplier.get();
        }
        target.setAttached(type, modifier.apply(value));
    }

    /**
     * Increments an integer attachment by 1.
     *
     * @param target the attachment target
     * @param type the attachment type (must be Integer)
     * @param <T> the target type
     * @return the new value
     */
    public static <T extends AttachmentTarget> int increment(@NotNull T target,
            @NotNull AttachmentType<Integer> type) {
        return incrementBy(target, type, 1);
    }

    /**
     * Increments an integer attachment by a specific amount.
     *
     * @param target the attachment target
     * @param type the attachment type (must be Integer)
     * @param amount the amount to increment
     * @param <T> the target type
     * @return the new value
     */

    @SuppressWarnings("null")
    public static <T extends AttachmentTarget> int incrementBy(@NotNull T target,
            @NotNull AttachmentType<@Nullable Integer> type, int amount) {
        if (target == null) {
            return amount;
        }
        Integer value = target.getAttached(type);
        int newValue = (value != null ? value : 0) + amount;
        target.setAttached(type, newValue);
        return newValue;
    }

    /**
     * Decrements an integer attachment by 1.
     *
     * @param target the attachment target
     * @param type the attachment type (must be Integer)
     * @param <T> the target type
     * @return the new value
     */
    public static <T extends AttachmentTarget> int decrement(@NotNull T target,
            @NotNull AttachmentType<Integer> type) {
        return incrementBy(target, type, -1);
    }
}
