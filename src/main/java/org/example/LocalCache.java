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
    public void put(K key , V value){
        put(key , value , 0);
    }

    public void put(K key, V value, long ttlMillis) {
        long expiresAt = ttlMillis <= 0 ? 0 : System.currentTimeMillis() + ttlMillis;
        CacheEntry<V> entry = new CacheEntry<>(value , expiresAt , System.nanoTime());
        map.put(key , entry);
        evictionPolicy.onInsert(key);
        stats.put();
        evictIfNecessary();

    }

    private void evictIfNecessary() {
        while(map.size() > capacity){
            K key = evictionPolicy.evict();
            if(key == null)
                return;
            if(map.remove(key) != null)
                stats.eviction();
        }
    }

    public void delete(V key) {
        CacheEntry<V> removed = map.remove(key);
        if(removed != null){
            evictionPolicy.onRemove((K) key);
            stats.delete();
        }
    }

    public V get(V key) {
        CacheEntry<V> entry = map.get(key);
        if(entry == null){
            stats.miss();
            return null;
        }
        if(entry.isExpired()){
            if(map.remove(key , entry))
                evictionPolicy.onRemove((K)key);
            stats.miss();
            return null;

        }
        evictionPolicy.onAccess((K) key);
        stats.hit();
        return entry.value;
    }

    public CacheStats stats(){
        return stats;
    }

    public int size(){
        return map.size();
    }

    public void ttl(K key , long ttlMillis){
        map.computeIfPresent(key , (l , oldEntry)->{
            long expiresAt = System.currentTimeMillis() + ttlMillis;
            return new CacheEntry<>(oldEntry.value , expiresAt , oldEntry.version);
        });

    }

}
