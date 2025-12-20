package com.inmemorycache;

import com.inmemorycache.strategies.LFUCache;
import com.inmemorycache.strategies.LRUCache;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        
    }

    private static void testSimpleLFUConcurrency() throws InterruptedException {
        LFUCache<String, String> lfuCache = new LFUCache<>(2);
        Thread thread1 = new Thread(() -> lfuCache.put("A", "A", 1));
        Thread thread2 = new Thread(() -> lfuCache.put("B", "B", 1));
        Thread thread3 = new Thread(() -> lfuCache.put("C", "C", 1));
        
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        Thread thread4 = new Thread(() -> System.out.println("Key A : " + lfuCache.get("A")));
        Thread thread5 = new Thread(() -> System.out.println("Key B : " + lfuCache.get("B")));
        Thread thread6 = new Thread(() -> System.out.println("Key C : " + lfuCache.get("C")));

        thread4.start();
        thread5.start();
        thread6.start();

        thread4.join();
        thread5.join();
        thread6.join();

        System.out.println(lfuCache.getCacheSize()); // Should be 2
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