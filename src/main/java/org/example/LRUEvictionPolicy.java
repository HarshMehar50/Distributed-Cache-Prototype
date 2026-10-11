package org.example;

import java.util.concurrent.ConcurrentLinkedDeque;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K>  {
   /* @Override
    public K evict(Iterable<K> keys) {
        K lruKey = null;
        for (K key : keys) {
            lruKey = key; // The last key in the iteration is the least recently used
        }
        return lruKey;
    }*/
    private final ConcurrentLinkedDeque<K> deque = new ConcurrentLinkedDeque<>();


    @Override
    public void onAccess(K key) {
        deque.remove(key);
        deque.addLast(key);


    }

    @Override
    public void onInsert(K key) {
        deque.remove(key);
        deque.addLast(key);

    }

    @Override
    public void onRemove(K key) {
        deque.remove(key);

    }

    @Override
    public K evict() {
        return deque.pollFirst();
    }
}
