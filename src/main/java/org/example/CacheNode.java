package org.example;

public class CacheNode {
    private final String id;
    private final LocalCache<String , String> cache;
    private volatile boolean alive = true;
    public CacheNode(String id , int capacity){
        this.id = id;
        this.cache = new LocalCache<>(capacity , new LRUEvictionPolicy<>());
    }
    public String getId(){
        return id;
    }
    public void put(String key , String value , long ttlMillis){
        cache.put(key , value , ttlMillis);

    }
    public void delete(String key){
        cache.delete(key);
    }
    public boolean isAlive(){
        return alive;
    }
    public void shutdown(){
        alive = false;
    }
    public void start(){
        alive = true;
    }

    public String get(String key) {
        return cache.get(key);
    }
}
