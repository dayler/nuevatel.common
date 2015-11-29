/**
 * 
 */
package com.nuevatel.common.cache;

/**
 * @author Ariel Salazar
 *
 */
public final class CacheBuilder {
    
    private int size = 8;
    
    private long expireAfterWriteTime = 0;
    
    private long expireAfterReadTime = 0;

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
    
    public <K, V> LoadingCache<K, V>buildSimpleLoadingCache(CacheLoader<K, V>cacheLoader) {
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
        return new LoadingCaheImpl<>(size, cacheLoader, expireAfterWriteTime, expireAfterReadTime);
    }
    
    public static CacheBuilder newCacheBuilder() {
        return new CacheBuilder();
    }
}
