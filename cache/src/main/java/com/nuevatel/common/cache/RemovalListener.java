/**
 * 
 */
package com.nuevatel.common.cache;

/**
 * Listener called in a moment after remove an cached object from cache.
 * 
 * @author Ariel Salazar
 *
 */
public interface RemovalListener <K, V> {

    void onRemoval(K key, V value);
}
