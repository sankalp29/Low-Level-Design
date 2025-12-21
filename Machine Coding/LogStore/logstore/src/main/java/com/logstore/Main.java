package com.logstore;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        testConcurrentLogs();
        // testSingleThreadedMode();
    }

    private static void testSingleThreadedMode() {
        LogStore logStore = LogStore.getInstance();
        logStore.log(10, "First message");
        logStore.log(20, "Third message");
        logStore.log(20, "Fourth message");
        logStore.log(20, "Second message");

        logStore.getLogs(10, 30).forEach(System.out::println);
    }

    private static void testConcurrentLogs() throws InterruptedException {
        LogStore logStore = LogStore.getInstance();
        Runnable log1 = () -> logStore.log(10, "First message");
        Runnable log3 = () -> logStore.log(30, "Third message");
        Runnable log2 = () -> logStore.log(20, "Second message");
        Runnable log4 = () -> logStore.log(20, "Fourth message");
        Thread t1 = new Thread(log1);
        Thread t2 = new Thread(log2);
        Thread t3 = new Thread(log3);
        Thread t4 = new Thread(log4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        logStore.getLogs(0, 30).forEach(System.out::println);
    }
}