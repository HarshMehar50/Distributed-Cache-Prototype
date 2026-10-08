package org.example;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K>  {
   /* @Override
    public K evict(Iterable<K> keys) {
        K lruKey = null;
        for (K key : keys) {
            lruKey = key; // The last key in the iteration is the least recently used
        }
        return lruKey;
    }*/

    @Override
    public void onAccess(K key) {

    }

    @Override
    public void onInsert(K key) {

    }

    @Override
    public void onRemove(K key) {

    }

    @Override
    public K evict() {
        return null;
    }
}
