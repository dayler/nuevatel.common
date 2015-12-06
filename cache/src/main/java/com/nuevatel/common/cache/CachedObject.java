/**
 * 
 */
package com.nuevatel.common.cache;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Cached object.
 * 
 * @author Ariel Salazar
 *
 */
public class CachedObject <K, V> implements Cacheable<K, V> {
    
    private Future<?> expireAfterWriteTask = null;
    
    private Future<?> expireAfterReadTask = null;
    
    private long expireAfterWriteTime;
    
    private long expireAfterReadTime;
    
    /**
     * <code>TimeUnit</code> for <code>expireAfterWriteTime</code> and <code>expireAfterReadTime</code>.
     */
    private TimeUnit timeUnit;
    
    private V obj;
    
    /**
     * 
     * @param obj cached object
     * @param expireAfterWriteTime Time in milliseconds to expire after it is write in the cache. Set 0 to indicate never.
     * @param expireAfterReadTime Time in milliseconds to expire if the object is no read from cache, for each access the timer is reset. Set 0 to
     * indicate never.
     */
    public CachedObject(V obj, long expireAfterWriteTime, long expireAfterReadTime, TimeUnit timeUnit) {
        this.obj = obj;
        this.expireAfterWriteTime = expireAfterWriteTime;
        this.expireAfterReadTime = expireAfterReadTime;
        this.timeUnit = timeUnit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get() {
        return obj;
    }
    
    @Override
    public long getExpireAfterReadTime() {
        return expireAfterReadTime;
    }
    
    @Override
    public long getExpireAfterWriteTime() {
        return expireAfterWriteTime;
    }

    @Override
    public void setExpireAfterWriteTask(Future<?> expireAfterWriteTask) {
        this.expireAfterWriteTask = expireAfterWriteTask;
    }

    @Override
    public void cancelExpireAfterWriteTask() {
        if (this.expireAfterWriteTask != null && !this.expireAfterWriteTask.isCancelled()) {
            expireAfterWriteTask.cancel(false);
            // reset task
            this.expireAfterWriteTask = null;
        }
    }

    @Override
    public void setExpireAfterReadTask(Future<?> expireAfterReadTask) {
        this.expireAfterReadTask = expireAfterReadTask;
    }

    @Override
    public TimeUnit getExpireAfterReadTimeUnit() {
        return timeUnit;
    }

    @Override
    public void cancelExpireAfterReadTask() {
        if (expireAfterReadTask != null && !expireAfterReadTask.isCancelled()) {
            expireAfterReadTask.cancel(false);
            // reset task
            expireAfterReadTask = null;
        }
    }

    @Override
    public TimeUnit getExpireAfterWriteTimeUnit() {
        return timeUnit;
    }

}
