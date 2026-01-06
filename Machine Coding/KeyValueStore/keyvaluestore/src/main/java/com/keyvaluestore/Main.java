package com.keyvaluestore;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        threadSafetyTest();
    }

    private void happyCaseTest() throws InterruptedException {
        KeyValueStore<Integer, String> keyValueStore = new KeyValueStore<>();
        keyValueStore.put(1, "a");
        System.out.println(keyValueStore.get(1));
        keyValueStore.put(2, "b", 5000);
        System.out.println(keyValueStore.get(2));
        Thread.sleep(5000);
        System.out.println(keyValueStore.get(2));
    }

    private static void threadSafetyTest() throws InterruptedException {
        KeyValueStore<Integer, String> keyValueStore = new KeyValueStore<>();
        
        Thread t1 = new Thread(() -> keyValueStore.put(1, "a"));
        Thread t2 = new Thread(() -> keyValueStore.put(2, "b", 2000));
        Thread t3 = new Thread(() -> keyValueStore.put(3, "c"));
        
        Thread c1 = new Thread(() -> System.out.println("1 : " + keyValueStore.get(1)));
        Thread c2 = new Thread(() -> System.out.println("2 : " + keyValueStore.get(2)));
        Thread c3 = new Thread(() -> System.out.println("2 : " + keyValueStore.get(2)));

        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(1000);
        c1.start();
        c2.start();
        Thread.sleep(2000);
        c3.start();

        t1.join();
        t2.join();
        t3.join();
        c1.join();
        c2.join();
        c3.join();
    }
}