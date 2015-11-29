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
 * @author Ariel Salazar
 *
 */
public class LoadingCaheImpl<K,V> implements LoadingCache<K, V> {
    
    private Object lck = new Object();
    
    private Map<K, Cacheable<K, V>>cacheMap = new ConcurrentHashMap<>();
    
    private CacheLoader<K, V>cacheLoader;
    
    private ScheduledExecutorService service;
    
    private long expireAfterWriteTime;
    
    private long expireAfterReadTime;
    
    public LoadingCaheImpl(int size, CacheLoader<K, V>cacheLoader) {
        this(size, cacheLoader, 0L, 0L);
    }
    
    public LoadingCaheImpl(int size, CacheLoader<K, V>cacheLoader, long expireAfterWriteTime, long expireAfterReadTime) {
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
        resetExpireAfterReadTask(key, expireAfterReadTime, value);
        
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
        Cacheable<K, V> cachedObj = cacheMap.get(key);
        return cachedObj == null ? null : cachedObj.get();
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
