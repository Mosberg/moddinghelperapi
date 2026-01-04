package dk.mosberg.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility for simple caching operations. Provides methods for caching values with time-based
 * expiration.
 *
 * <p>
 * Useful for expensive operations that need to be called frequently but don't change often.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * var cache = new CacheHelper.TimedCache<String, Integer>(5000); // 5 second expiry
 * cache.set("count", 42);
 * Integer value = cache.get("count"); // Returns 42
 *
 * // After 5 seconds, value expires
 * </pre>
 */
public final class CacheHelper {
    private CacheHelper() {}

    /**
     * A simple timed cache that expires entries after a specified duration.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    public static class TimedCache<K, V> {
        private static class CacheEntry<V> {
            @Nullable
            final V value;
            final long expiryTime;

            CacheEntry(@Nullable V value, long expiryTime) {
                this.value = value;
                this.expiryTime = expiryTime;
            }

            boolean isExpired() {
                return System.currentTimeMillis() > expiryTime;
            }
        }

        private final Map<K, CacheEntry<V>> cache = new HashMap<>();
        private final long expirationMs;

        /**
         * Creates a new timed cache with the specified expiration duration.
         *
         * @param expirationMs the expiration time in milliseconds
         */
        public TimedCache(long expirationMs) {
            this.expirationMs = expirationMs;
        }

        /**
         * Sets a value in the cache.
         *
         * @param key the cache key
         * @param value the value to cache
         */
        public void set(@NotNull K key, @Nullable V value) {
            long expiryTime = System.currentTimeMillis() + expirationMs;
            cache.put(key, new CacheEntry<>(value, expiryTime));
        }

        /**
         * Gets a value from the cache if it exists and hasn't expired.
         *
         * @param key the cache key
         * @return the cached value, or null if expired or not found
         */
        @SuppressWarnings("null")
        @Nullable
        public V get(@NotNull K key) {
            var entry = cache.get(key);
            if (entry == null) {
                return null;
            }
            if (entry.isExpired()) {
                cache.remove(key);
                return null;
            }
            return entry.value;
        }

        /**
         * Gets a value from the cache, computing it if missing or expired.
         *
         * @param key the cache key
         * @param supplier the supplier to compute the value if needed
         * @return the cached or computed value
         */
        @Nullable
        public V getOrCompute(@NotNull K key, @NotNull Supplier<@Nullable V> supplier) {
            var cached = get(key);
            if (cached != null) {
                return cached;
            }
            var computed = supplier.get();
            set(key, computed);
            return computed;
        }

        /**
         * Removes an entry from the cache.
         *
         * @param key the cache key
         */
        public void remove(@NotNull K key) {
            cache.remove(key);
        }

        /**
         * Clears all entries from the cache.
         */
        public void clear() {
            cache.clear();
        }

        /**
         * Gets the number of entries in the cache (including expired ones).
         *
         * @return the cache size
         */
        public int size() {
            return cache.size();
        }

        /**
         * Checks if a key exists in the cache and is not expired.
         *
         * @param key the cache key
         * @return true if the key has a valid cached value
         */
        public boolean has(@NotNull K key) {
            return get(key) != null;
        }
    }

    /**
     * A simple value cache that holds a single value with optional expiration.
     *
     * @param <T> the value type
     */
    public static class ValueCache<T> {
        @Nullable
        private T value;
        @Nullable
        private Long expiryTime;
        private final long expirationMs;
        private boolean isSet = false;

        /**
         * Creates a value cache with the specified expiration duration.
         *
         * @param expirationMs the expiration time in milliseconds (-1 for no expiration)
         */
        @SuppressWarnings("null")
        public ValueCache(long expirationMs) {
            this.expirationMs = expirationMs;
        }

        /**
         * Sets the cached value.
         *
         * @param value the value to cache
         */
        public void set(@Nullable T value) {
            this.value = value;
            this.isSet = true;
            if (expirationMs > 0) {
                this.expiryTime = System.currentTimeMillis() + expirationMs;
            }
        }

        /**
         * Gets the cached value if it hasn't expired.
         *
         * @return the cached value, or null if not set or expired
         */
        @SuppressWarnings("null")
        @Nullable
        public T get() {
            if (!isSet) {
                return null;
            }
            if (expiryTime != null && System.currentTimeMillis() > expiryTime) {
                clear();
                return null;
            }
            return value;
        }

        /**
         * Gets the cached value, computing it if missing or expired.
         *
         * @param supplier the supplier to compute the value if needed
         * @return the cached or computed value
         */
        @Nullable
        public T getOrCompute(@NotNull Supplier<@Nullable T> supplier) {
            var cached = get();
            if (cached != null || (isSet && value == null)) {
                return cached;
            }
            var computed = supplier.get();
            set(computed);
            return computed;
        }

        /**
         * Clears the cached value.
         */
        @SuppressWarnings("null")
        public void clear() {
            value = null;
            expiryTime = null;
            isSet = false;
        }

        /**
         * Checks if a value is cached and valid.
         *
         * @return true if a valid value is cached
         */
        public boolean isValid() {
            return get() != null;
        }
    }
}
