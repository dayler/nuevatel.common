/**
 * 
 */
package com.nuevatel.common.cache;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Cacheable object, only this kind of object can be stored in the cache.
 * 
 * @author Ariel Salazar
 *
 */
public interface Cacheable <K, V> {
    
    /**
     * 
     * @return Cached Object value
     */
    V get();
    
    void setExpireAfterWriteTask(Future<?> expireAfterWriteTask);
    
    void cancelExpireAfterWriteTask();
    
    void setExpireAfterReadTask(Future<?> expireAfterReadTask);
    
    void cancelExpireAfterReadTask();
    
    long getExpireAfterReadTime();
    
    TimeUnit getExpireAfterReadTimeUnit();
    
    long getExpireAfterWriteTime();
    
    TimeUnit getExpireAfterWriteTimeUnit();
}
