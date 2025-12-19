package com.inmemorycache;

import com.inmemorycache.strategies.LRUCache;

public class Main {
    public static void main(String[] args) {
        testTTLExpiration();
        // testLRUEviction();
    }

    private static void testTTLExpiration() {
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