package com.jason.jasonimageutil;

import java.util.concurrent.ConcurrentHashMap;

public class LockMap {
    static ConcurrentHashMap<String, Lock> lockConcurrentHashMap = new ConcurrentHashMap<>();

    public static synchronized Lock getLock(String path) {
        if (lockConcurrentHashMap.get(path) == null) {
            Lock lock = new Lock();
            lockConcurrentHashMap.put(path, lock);
            return lock;
        } else {
            return lockConcurrentHashMap.get(path);
        }
    }

    public static class Lock {
        public volatile int mark;

    }
}
