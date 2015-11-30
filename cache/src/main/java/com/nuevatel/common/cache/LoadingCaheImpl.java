/**
 * 
 */
package com.nuevatel.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Implements lazy load acache.
 * 
 * @author Ariel Salazar
 *
 */
public class LoadingCaheImpl<K,V> implements LoadingCache<K, V> {
    
    /**
     * Lock object, used to controls critical sections.
     */
    private Object lck = new Object();
    
    /**
     * Map all cached objects.
     */
    private Map<K, Cacheable<K, V>>cacheMap = new ConcurrentHashMap<>();
    
    /**
     * Define the strategy to load an cachable object.
     */
    private CacheLoader<K, V>cacheLoader;
    
    /**
     * Service used to clean cache map, used scheduled tasks.
     */
    private ScheduledExecutorService service;
    
    /**
     * Time spend before remove cached object. <b>Always is removed after this time.</b>
     */
    private long expireAfterWriteTime;
    
    /**
     * Time spend before remove cached object if the object was not read during this period.
     */
    private long expireAfterReadTime;
    
    public LoadingCaheImpl(int size, CacheLoader<K, V>cacheLoader) {
        this(size, cacheLoader, 0L, 0L);
    }
    
    public LoadingCaheImpl(int size, CacheLoader<K, V>cacheLoader, long expireAfterWriteTime, long expireAfterReadTime) {
        if (cacheLoader == null) {
            throw new IllegalArgumentException("cacheLoader is null");
        }
        
        this.cacheLoader = cacheLoader;
        this.expireAfterWriteTime = expireAfterWriteTime;
        this.expireAfterReadTime = expireAfterReadTime;
        // create service to invalidate keys
        service = Executors.newScheduledThreadPool(size, new MinPriorityThreadFactory());
    }
    
    @Override
    public V get(K key) {
        return get(key, expireAfterWriteTime, expireAfterReadTime);
    }
    
    @Override
    public V get(K key, Long expireAfterWriteTime, Long expireAfterReadTime) {
        Cacheable<K, V>value = cacheMap.get(key);
        if (value == null) {
            synchronized (lck) {
                // Check again
                if ((value = cacheMap.get(key)) != null) {
                    return value.get();
                }
                // if value == null
                try {
                    // load it to cache
                    V tmp = cacheLoader.load(key);
                    value = new CachedObject<>(tmp, expireAfterWriteTime, expireAfterReadTime);
                    cacheMap.put(key, value);
                    // initialize tasks
                    Future<?>expireAfterWriteTask = scheduleTask(()->cacheMap.remove(key), expireAfterWriteTime);
                    value.setExpireAfterWriteTask(expireAfterWriteTask);
                    Future<?>expireAfterReadTask = scheduleTask(()->cacheMap.remove(key), expireAfterReadTime);
                    value.setExpireAfterReadTask(expireAfterReadTask);
                    return value.get();
                } catch (Exception ex) {
                    return null;
                }
            }
        }
        
        // reset expire after read task
        resetExpireAfterReadTask(key, value.getExpireAfterReadTime(), value);
        return value.get();
    }

    @Override
    public V getUnchecked(K key) {
        Cacheable<K, V>cachedObj = cacheMap.get(key);
        return cachedObj == null ? null : cachedObj.get();
    }

    @Override
    public V put(K key, V value) {
        return put(key, value, expireAfterWriteTime, expireAfterReadTime);
    }

    @Override
    public synchronized V put(K key, V value, Long expireAfterWriteTime, Long expireAfterReadTime) {
        Cacheable<K, V> newCachedObj = new CachedObject<>(value, expireAfterWriteTime, expireAfterReadTime);
        cacheMap.put(key, newCachedObj);
        // initialize tasks
        Future<?>expireAfterWriteTask = scheduleTask(()->cacheMap.remove(key), expireAfterWriteTime);
        newCachedObj.setExpireAfterWriteTask(expireAfterWriteTask);
        Future<?>expireAfterReadTask = scheduleTask(()->cacheMap.remove(key), expireAfterReadTime);
        // get previous if it exists
        newCachedObj.setExpireAfterReadTask(expireAfterReadTask);
        Cacheable<K, V> oldCachedObj = cacheMap.get(key);
        if (oldCachedObj == null) {
            return null;
        }
        oldCachedObj.cancelExpireAfterReadTask();
        oldCachedObj.cancelExpireAfterWriteTask();
        return oldCachedObj.get();
    }

    @Override
    public boolean contains(K key) {
        Cacheable<K, V>value = cacheMap.get(key);
        if (value != null) {
            resetExpireAfterReadTask(key, value.getExpireAfterReadTime(), value);
        }
        return cacheMap.containsKey(key);
    }

    @Override
    public synchronized V invalidate(K key) {
        Cacheable<K, V>cahedObj = cacheMap.remove(key);
        return cahedObj == null ? null : cahedObj.get();
    }

    @Override
    public synchronized void invalidateAll() {
        cacheMap.clear();
    }

    @Override
    public synchronized void shutdown() {
        try {
            if (service != null) {
                service.shutdown();
                service.awaitTermination(60, TimeUnit.SECONDS);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void resetExpireAfterReadTask(K key, Long expireAfterReadTime, Cacheable<K, V> value) {
        synchronized (lck) {
            value.cancelExpireAfterReadTask();
            Future<?>expireAfterReadTask = scheduleTask(()->cacheMap.remove(key), expireAfterReadTime);
            value.setExpireAfterReadTask(expireAfterReadTask);
        }
    }

    private Future<?> scheduleTask(Runnable target, long scheduleTime) {
        if (scheduleTime == 0) {
            return null;
        }
        
        return service.schedule(target, scheduleTime, TimeUnit.MILLISECONDS);
    }

    private static final class MinPriorityThreadFactory implements ThreadFactory {
    
        /**
         * {@inheritDoc}
         */
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setPriority(Thread.MIN_PRIORITY);
            return thread;
        }
    }

}
