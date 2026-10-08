package org.example;

public class CacheEntry<V> {
    final V value;
    final long expiresAt;
    final long version;
    public CacheEntry(V value , long expiresAt , long version){
        this.value = value;
        this.expiresAt = expiresAt;
        this.version = version;
    }
    boolean isExpired(){
        return expiresAt != 0 && System.currentTimeMillis() >= expiresAt;
    }
}
