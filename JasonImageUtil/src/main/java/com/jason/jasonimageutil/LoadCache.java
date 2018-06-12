package com.jason.jasonimageutil;

import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public abstract class LoadCache<T> {

    private LruCache<String, T> cache = new LruCache<String, T>((int) (Runtime.getRuntime().maxMemory() / 8 / 1024)) {
        @Override
        protected int sizeOf(String key, T value) {

            return LoadCache.this.sizeOf(key, value);
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, T oldValue, T newValue) {
            if (oldValue != null) {
                concurrentHashMap.put(key, new SoftReference<T>(oldValue));
            }


        }
    };
    private ConcurrentHashMap<String, SoftReference<T>> concurrentHashMap = new ConcurrentHashMap<>();

    public abstract int sizeOf(String key, T value);

    public void putInCache(String key, T value) {
        if (key != null && value != null) {
            cache.put(key, value);
        }


    }

    public T getCache(String key) {
        if (key == null) {
            return null;
        }
        T value = concurrentHashMap.get(key).get();

        return value == null ? cache.get(key) : value;
    }

    public void clear() {
        cache.evictAll();
        concurrentHashMap.clear();

    }
}
