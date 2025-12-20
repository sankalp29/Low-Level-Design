package com.inmemorycache;

import com.inmemorycache.strategies.LFUCache;
import com.inmemorycache.strategies.LRUCache;

public class Main {
    public static void main(String[] args) {
        testLFUEviction2();
    }

    private static void testLFUEviction2() {
        LFUCache<String, String> cache = new LFUCache<>(2);
        cache.put("A", "1", 100);  // freq=1, minFreq=1
        cache.get("A");             // freq=2, minFreq=2 (freq=1 list now empty)

        // frequencyLists: {2: [A]}
        // minFrequency = 2

        cache.put("B", "2", 100);   // size=2, capacity=2
        // Tries to evict from frequency=2, removes A
        // keys: {B}, size=1

        cache.put("C", "3", 100);   // size=1, capacity=2, no eviction
        // keys: {B, C}, size=2

        cache.put("D", "4", 100);   // size=2, capacity=2
        // Tries to get list at minFrequency=2
        // But that list might be empty or null!
        // If null/empty, doesn't evict but still adds D
        // keys: {B, C, D}, size=3 ❌ CAPACITY VIOLATED!

        System.out.println("Cache Size : " + cache.getCacheSize());
    }

    private static void testLFUEviction() {
        CacheStrategy<String, String> lfuCache = new LFUCache<>(3);
        String key1 = "A";
        String key2 = "B";
        String key3 = "C";

        lfuCache.put(key1, "A", 1);
        lfuCache.put(key2, "B", 1);
        lfuCache.put(key3, "C", 1);
        System.out.println();

        lfuCache.get("A"); lfuCache.get("A"); lfuCache.get("A");
        lfuCache.get("B"); lfuCache.get("B");
        lfuCache.get("C"); lfuCache.get("C");
        lfuCache.get("A");
        lfuCache.put("D", "D", 1);

        int present = (lfuCache.get("B") != null ? 1 : 0) + (lfuCache.get("C") != null ? 2 : 0);
        System.out.println(present);
        System.out.println("A is present ? " + lfuCache.get(key1));
        System.out.println("D is present ? " + lfuCache.get("D"));
    }

    private static void testLRUTTLExpiration() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(2);
        String key1 = "sankalp";
        String key2 = "jau";
        lruCache.put(key1, 1, 5);
        lruCache.put(key2, 2, 4);
        System.out.println(lruCache.get(key1));
        System.out.println(lruCache.get(key2));

        lruCache.put(key1, 1, 10);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(lruCache.get(key1));
        System.out.println(lruCache.get(key2));

        lruCache.shutdownScheduler();
    }

    private static void testLRUEviction() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(2);
        String key1 = "sankalp";
        String key2 = "jau";
        String key3 = "name3";
        lruCache.put(key1, 1, 1);
        System.out.println(lruCache.get(key1));

        lruCache.put(key2, 2, 1);
        System.out.println(lruCache.get(key2));

        lruCache.put(key3, 3, 1);
        System.out.println(lruCache.get(key3));

        System.out.println(lruCache.get(key1));

        lruCache.shutdownScheduler();
    }   
}