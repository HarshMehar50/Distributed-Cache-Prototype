package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class CacheStats {
    private final AtomicLong hits = new AtomicLong();
    private final AtomicLong misses = new AtomicLong();
    private final AtomicLong puts = new AtomicLong();
    private final AtomicLong deletes = new AtomicLong();
    private final AtomicLong evictions = new AtomicLong();

    void hit(){
        hits.incrementAndGet();
    }

    void miss(){
        misses.incrementAndGet();
    }

    void put(){
        puts.incrementAndGet();
    }

    void delete(){
        deletes.incrementAndGet();
    }

    void eviction(){
        evictions.incrementAndGet();
    }

    double hitRatio(){
        long h = hits.get();
        long m = misses.get();
        if(h+m == 0)
            return 0;
        else return (double) h/(h+m);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("hits : ").append(hits);
        sb.append("hit ratio : ").append(hitRatio());
        sb.append("misses : ").append(misses);
        sb.append("puts : ").append(puts);
        sb.append("deletes : ").append(deletes);
        sb.append("evictions : ").append(evictions);
        return sb.toString();
    }
}
