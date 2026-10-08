package org.example;

import java.util.Map;
import java.util.TreeMap;

public class ConsistentHashingRing<N> {
    private final TreeMap<Long , N> ring = new TreeMap<>();
    private final int virtualNodes;
    public ConsistentHashingRing(int virtualNodes){
        this.virtualNodes = virtualNodes;
    }
    public synchronized void addNode(N node){
        for(int i = 0; i < virtualNodes; i++){
            long hash = hash(node.toString()+"#"+i);
            ring.put(hash , node);
        }
    }
    public synchronized void removeNode(N node){
        for(int i = 0; i < virtualNodes; i++){
            long hash = hash(node.toString()+"#"+i);
            ring.remove(hash);
        }
    }
    public synchronized N getNode(String key){
        if(ring.isEmpty())
            return null;
        long hash = hash(key);
        Map.Entry<Long , N> entry = ring.ceilingEntry(hash);
        if(entry == null)
            return ring.firstEntry().getValue();
        return entry.getValue();
    }
    private long hash(String s){
        long h = 1125899906842597L;
        for(char c : s.toCharArray()){
            h = 31*h + c;
        }
        return h&Long.MAX_VALUE;
    }
}
