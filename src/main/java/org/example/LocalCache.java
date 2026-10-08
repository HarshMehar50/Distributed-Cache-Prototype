package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class LocalCache<K , V>{
    private final ConcurrentHashMap<K , CacheEntry<V>> map = new ConcurrentHashMap<>();
    private final CacheStats stats = new CacheStats();
    private final int capacity;
    private final EvictionPolicy<K> evictionPolicy;
    public LocalCache(int capacity , EvictionPolicy<K> evictionPolicy){
        this.capacity = capacity;
        this.evictionPolicy = evictionPolicy;
    }

    public void put(V key, V value, long ttlMillis) {
    }

    public void delete(V key) {
    }
}
