package dk.mosberg.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

/**
 * Event management utility for registering and managing event listeners with priorities.
 *
 * <p>
 * Provides simplified event registration with support for callback filtering and priority-based
 * execution.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * var eventHelper = new EventHelper();
 * eventHelper.subscribe("player_damage", event -> {
 *     if (eventHelper.filterEvent(event, e -> e instanceof PlayerDamageEvent)) {
 *         handleDamage((PlayerDamageEvent) event);
 *     }
 * });
 * </pre>
 *
 * @since 1.0.0
 */
public final class EventHelper {
    private static final int DEFAULT_PRIORITY = 0;
    private final Map<String, List<PrioritizedListener<?>>> listeners = new HashMap<>();

    /**
     * Registers a listener for an event type with default priority.
     *
     * @param eventName the name/identifier of the event
     * @param listener the listener to register
     * @param <T> the event type
     * @throws NullPointerException if eventName or listener is null
     */
    public <T> void subscribe(@NotNull String eventName, @NotNull Consumer<T> listener) {
        subscribe(eventName, listener, DEFAULT_PRIORITY);
    }

    /**
     * Registers a listener with a specific priority. Higher priority listeners are called first.
     *
     * @param eventName the name/identifier of the event
     * @param listener the listener to register
     * @param priority the listener priority (higher = earlier execution)
     * @param <T> the event type
     * @throws NullPointerException if eventName or listener is null
     */
    public <T> void subscribe(@NotNull String eventName, @NotNull Consumer<T> listener,
            int priority) {
        listeners.computeIfAbsent(eventName, k -> new ArrayList<>())
                .add(new PrioritizedListener<>(listener, priority));

        // Re-sort by priority (descending)
        var list = listeners.get(eventName);
        list.sort((a, b) -> Integer.compare(b.priority, a.priority));
    }

    /**
     * Unregisters a listener from an event type.
     *
     * @param eventName the name/identifier of the event
     * @param listener the listener to unregister
     * @param <T> the event type
     * @throws NullPointerException if eventName or listener is null
     */
    public <T> void unsubscribe(@NotNull String eventName, @NotNull Consumer<T> listener) {
        var list = listeners.get(eventName);
        if (list != null) {
            list.removeIf(pl -> pl.listener.equals(listener));
            if (list.isEmpty()) {
                listeners.remove(eventName);
            }
        }
    }

    /**
     * Dispatches an event to all registered listeners.
     *
     * @param eventName the name/identifier of the event
     * @param event the event object to dispatch
     * @param <T> the event type
     * @throws NullPointerException if eventName or event is null
     */
    @SuppressWarnings("unchecked")
    public <T> void dispatch(@NotNull String eventName, @NotNull T event) {
        var list = listeners.get(eventName);
        if (list != null) {
            for (var pl : list) {
                ((Consumer<T>) pl.listener).accept(event);
            }
        }
    }

    /**
     * Applies a filter to an event, returning true if the event matches the predicate.
     *
     * @param event the event to test
     * @param filter the filter predicate
     * @param <T> the event type
     * @return true if the event passes the filter
     * @throws NullPointerException if event or filter is null
     */
    public <T> boolean filterEvent(@NotNull T event, @NotNull Predicate<T> filter) {
        return filter.test(event);
    }

    /**
     * Creates a filtered listener that only calls the callback when the filter matches.
     *
     * @param eventName the name/identifier of the event
     * @param filter the filter predicate
     * @param callback the callback to invoke when filter passes
     * @param <T> the event type
     * @return a consumer that can be passed to subscribe()
     * @throws NullPointerException if eventName, filter, or callback is null
     */
    @NotNull
    public <T> Consumer<T> createFilteredListener(@NotNull String eventName,
            @NotNull Predicate<T> filter, @NotNull Consumer<T> callback) {
        return event -> {
            if (filter.test(event)) {
                callback.accept(event);
            }
        };
    }

    /**
     * Checks if an event type has any registered listeners.
     *
     * @param eventName the name/identifier of the event
     * @return true if the event has listeners
     * @throws NullPointerException if eventName is null
     */
    public boolean hasListeners(@NotNull String eventName) {
        var list = listeners.get(eventName);
        return list != null && !list.isEmpty();
    }

    /**
     * Gets the number of listeners registered for an event type.
     *
     * @param eventName the name/identifier of the event
     * @return the number of registered listeners
     * @throws NullPointerException if eventName is null
     */
    public int getListenerCount(@NotNull String eventName) {
        var list = listeners.get(eventName);
        return list != null ? list.size() : 0;
    }

    /**
     * Clears all listeners for a specific event type.
     *
     * @param eventName the name/identifier of the event
     * @throws NullPointerException if eventName is null
     */
    public void clearListeners(@NotNull String eventName) {
        listeners.remove(eventName);
    }

    /**
     * Clears all registered listeners.
     */
    public void clearAllListeners() {
        listeners.clear();
    }

    /**
     * Internal class for storing listeners with their priority.
     */
    private static final class PrioritizedListener<T> {
        private final Consumer<T> listener;
        private final int priority;

        PrioritizedListener(Consumer<T> listener, int priority) {
            this.listener = listener;
            this.priority = priority;
        }
    }
}
