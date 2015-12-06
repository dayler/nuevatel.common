/**
 * 
 */
package com.nuevatel.common.cache;

import java.util.concurrent.TimeUnit;

/**
 * Define the service interface for the cache service.
 * 
 * @author Ariel Salazar
 *
 */
public interface LoadingCache<K,V> {
    
    /**
     * 
     * @param key Key to identify the cached object.
     * @return V Object associated with the key. <b>Load if not exists</b>
     */
    V get(K key);
    
    /**
     * 
     * @param key key Key to identify the cached object.
     * @param expireAfterWriteTime custom time to invalidate after last write.
     * @param expireAfterReadTime custom time to invalidate after last read, reset if is read before this time.
     * @return V Object associated with the key. <b>Load if not exists</b>
     */
    V get(K key, Long expireAfterWriteTime, Long expireAfterReadTime);
    
    /**
     * 
     * @param key key Key to identify the cached object.
     * @param expireAfterWriteTime custom time to invalidate after last write.
     * @param expireAfterReadTime custom time to invalidate after last read, reset if is read before this time.
     * @param timeUnit <code>TimeUnit</code> used for expireAfterReadTime and expireAfterWriteTime.
     * @return V Object associated with the key. <b>Load if not exists</b>
     */
    V get(K key, Long expireAfterWriteTime, Long expireAfterReadTime, TimeUnit timeUnit);
    
    /**
     * Put an element in the cache, with key. If previous element is existing with the same key, returns previous element.
     * 
     * @param key Key to identify the element.
     * @param value Value to insert in the cache.
     * @return Replaced element if it exists.
     */
    V put(K key, V value);
    
    /**
     * Put an element in the cache, with key. If previous element is existing with the same key, returns previous element.
     * 
     * @param key Key to identify the element.
     * @param value Value to insert in the cache.
     * @param expireAfterWriteTime custom time to invalidate after last write.
     * @param expireAfterReadTime custom time to invalidate after last read, reset if is read before this time.
     * @return Replaced element if it exists.
     */
    V put(K key, V value, Long expireAfterWriteTime, Long expireAfterReadTime);
    
    /**
     * Put an element in the cache, with key. If previous element is existing with the same key, returns previous element.
     * 
     * @param key Key to identify the element.
     * @param value Value to insert in the cache.
     * @param expireAfterWriteTime custom time to invalidate after last write.
     * @param expireAfterReadTime custom time to invalidate after last read, reset if is read before this time.
     * @param timeUnit <code>TimeUnit</code> used for expireAfterReadTime and expireAfterWriteTime.
     * @return Replaced element if it exists.
     */
    V put(K key, V value, Long expireAfterWriteTime, Long expireAfterReadTime, TimeUnit timeUnit);
    
    /**
     * 
     * @param key Key to identify the cached object.
     * @return V Object associated with the key. <b>null if the object not exists</b>
     */
    V getUnchecked(K key);
    
    /**
     * 
     * @param key Key to identify the cached object.
     * @return <code>true</code> if has an object associated with the key.
     */
    boolean contains(K key);
    
    /**
     * Remove an object associated with key from cache.
     * 
     * @param key Key to identify the cached object.
     * @return V cached object if it was invalidated.
     */
    V invalidate(K key);
    
    /**
     * Removes all objects from cache.
     */
    void invalidateAll();
    
    /**
     * Shutdown cache service.
     */
    void shutdown();
}
