package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        nodes.add(node);
        ring.addNode(node);
    }
    public void removeNode(CacheNode node){
        nodes.remove(node);
        ring.removeNode(node);

        //
    }
    public String get(String key){
        List<CacheNode> replicas = getReplicas(key);
        for(CacheNode node : replicas){
            if(!node.isAlive()) continue;
            String val = node.get(key);
            if(val != null)
                return val;
        }
        return null;
    }

    private List<CacheNode> getReplicas(String key) {
        List<CacheNode> ans = new ArrayList<>();
        Set<CacheNode> visited = new HashSet<>();
        CacheNode primary = ring.getNode(key);
        if(primary == null)
            return ans;
        int start = nodes.indexOf(primary);
        for(int i = 0; i < nodes.size() && ans.size() < replicationFactor; i++){
            CacheNode node = nodes.get((start+i)%nodes.size());
            if(visited.add(node))
                ans.add(node);
        }
        return ans;
    }

    public void put(String key , String value , long ttlMillis){
        List<CacheNode> replicas = getReplicas(key);
        int successfulWrites = 0;
        for(CacheNode node : replicas){
            if(!node.isAlive()) continue;
            node.put(key , value , ttlMillis);
            successfulWrites++;
        }
        if(successfulWrites == 0)
            throw new IllegalStateException("Failed to write value for key: " + key);
    }
    public void delete(String key){
        for(CacheNode node : getReplicas(key)){
            if(node.isAlive())
                node.delete(key);
        }
    }

}
