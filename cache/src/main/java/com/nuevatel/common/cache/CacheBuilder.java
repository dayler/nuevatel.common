/**
 * 
 */
package com.nuevatel.common.cache;

import java.util.concurrent.TimeUnit;

/**
 * Creates an instance of <code>LoadingCache</code>.
 * 
 * @author Ariel Salazar
 *
 */
public final class CacheBuilder {
    
    private int size = 8;
    
    private long expireAfterWriteTime = 0;
    
    private long expireAfterReadTime = 0;
    
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    CacheBuilder() {
        // to prevent instantiation
    }
    
    public CacheBuilder setSize(int size) {
        this.size = size;
        return this;
    }
    
    public CacheBuilder setExpireAfterReadTime(long expireAfterReadTime) {
        this.expireAfterReadTime = expireAfterReadTime;
        return this;
    }
    
    public CacheBuilder setExpireAfterWriteTime(long expireAfterWriteTime) {
        this.expireAfterWriteTime = expireAfterWriteTime;
        return this;
    }
    
    /**
     * 
     * @param timeUnit <code>TimeUnit</code> to use for <code>expireAfterReadTime</code> and <code>expireAfterWriteTime</code>.
     * @return
     */
    public CacheBuilder setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }
    
    /**
     * 
     * @param cacheLoader
     * @return
     */
    public <K, V> LoadingCache<K, V>buildSimpleLoadingCache(CacheLoader<K, V>cacheLoader) {
        return buildSimpleLoadingCache(cacheLoader, null);
    }
    
    /**
     * Creates <code>LoadingCache</code> based imput para meters.
     * 
     * @param cacheLoader
     * @param removalListener
     * @return
     */
    public <K, V> LoadingCache<K, V>buildSimpleLoadingCache(CacheLoader<K, V>cacheLoader, RemovalListener<K, V>removalListener) {
        if (cacheLoader == null) {
            throw new IllegalArgumentException("cacheLoader is null");
        }
        
        if (size < 1) {
            throw new IllegalArgumentException("size > 0 actual " + size);
        }
        
        if (expireAfterReadTime < 0) {
            throw new IllegalArgumentException("expireAfterReadTime >= 0 actual " + expireAfterReadTime);
        }
        
        if (expireAfterWriteTime < 0) {
            throw new IllegalArgumentException("expireAfterWriteTime >= 0 actual " + expireAfterWriteTime);
        }
        
        if (timeUnit == null) {
            throw new IllegalArgumentException("timeUnit != null actula " + timeUnit);
        }
        
        return new LoadingCaheImpl<>(size, cacheLoader, removalListener, expireAfterWriteTime, expireAfterReadTime);
    }
    
    public static CacheBuilder newCacheBuilder() {
        return new CacheBuilder();
    }
}
