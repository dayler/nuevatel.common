/**
 * 
 */
package com.nuevatel.common.cache;

/**
 * Define the strategy use to load an cached object. For each kind of <code>LoadingCahe</code> must to implement it.
 * 
 * @author Ariel Salazar
 *
 */
public interface CacheLoader<K, V> {
    
    /**
     * 
     * @param key Key to identify the object to store in the cache.
     * @return Object to store in the cache
     */
    V load(K key) throws Exception;
    
}
