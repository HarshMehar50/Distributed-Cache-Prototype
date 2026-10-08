package org.example;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DistributedCache {
    private final ConsistentHashingRing<CacheNode> ring;
    private final int replicationFactor;
    private final List<CacheNode> nodes = new CopyOnWriteArrayList<>();
    public DistributedCache(int virtualNodes , int replicationFactor){
        this.replicationFactor = replicationFactor;
        this.ring = new ConsistentHashingRing<>(virtualNodes);
    }
    public void addNode(CacheNode node){

    }
    public void removeNode(CacheNode node){

    }
    public String get(String key){

    }
    public void put(String key , String value , long ttlMillis){

    }
    public void delete(String key){

    }

}
