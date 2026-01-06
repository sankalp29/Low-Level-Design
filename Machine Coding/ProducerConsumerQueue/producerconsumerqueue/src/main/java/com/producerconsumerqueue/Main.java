package com.producerconsumerqueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        IBlockingQueue<Integer> blockingQueue = new CustomBlockingQueue(2);
        
        Thread producer1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) 
            try {
                blockingQueue.put(i);
            } catch (InterruptedException ex) {
                System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
        Thread producer2 = new Thread(() -> {
            for (int i = 11; i <= 20; i++) 
            try {
                blockingQueue.put(i);
            } catch (InterruptedException ex) {
                System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
        Thread producer3 = new Thread(() -> {
            for (int i = 21; i <= 30; i++) 
            try {
                blockingQueue.put(i);
            } catch (InterruptedException ex) {
                System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        Runnable consumerTask = () -> {
            try {
                for (int i = 0; i < 10; i++) System.out.println(blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread consumer1 = new Thread(consumerTask);
        Thread consumer2 = new Thread(consumerTask);
        Thread consumer3 = new Thread(consumerTask);

        producer1.start();
        producer2.start();
        producer3.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();

        producer1.join();
        producer2.join();
        producer3.join();

        consumer1.join();
        consumer2.join();
        consumer3.join();
    }
}