package com.jobprocessor;

import com.jobprocessor.job.IJob;
import com.jobprocessor.job.PrintingJob;
import com.jobprocessor.job.ProcessingJob;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        testHappyCase();
        // testConcurrentEnqueues();
    }

    private static void testHappyCase() throws InterruptedException {
        IJob printingJob1 = new PrintingJob("Printing A");
        IJob printingJob2 = new PrintingJob("Printing B");
        IJob printingJob3 = new PrintingJob("Printing C");

        IJob processingJob1 = new ProcessingJob("Processing 1");
        IJob processingJob2 = new ProcessingJob("Processing 2");
        IJob processingJob3 = new ProcessingJob("Processing 3");
        IJob processingJob4 = new ProcessingJob("Processing 4");

        JobProcessor jobProcessor = new JobProcessor();
        jobProcessor.enqueue(printingJob1);
        jobProcessor.enqueue(printingJob2);
        jobProcessor.enqueue(printingJob3);

        jobProcessor.enqueue(processingJob1);
        jobProcessor.enqueue(processingJob2);
        jobProcessor.enqueue(processingJob3);
        jobProcessor.enqueue(processingJob4);

        jobProcessor.shutdown();
    }

    private static void testConcurrentEnqueues() throws InterruptedException {
        
        IJob printingJob1 = new PrintingJob("Printing A");
        IJob printingJob2 = new PrintingJob("Printing B");
        IJob printingJob3 = new PrintingJob("Printing C");

        IJob processingJob1 = new ProcessingJob("Processing 1");
        IJob processingJob2 = new ProcessingJob("Processing 2");
        IJob processingJob3 = new ProcessingJob("Processing 3");
        IJob processingJob4 = new ProcessingJob("Processing 4");

        JobProcessor jobProcessor = new JobProcessor();
        Thread t1 = new Thread(() -> jobProcessor.enqueue(printingJob1));
        Thread t2 = new Thread(() -> jobProcessor.enqueue(printingJob2));
        Thread t3 = new Thread(() -> jobProcessor.enqueue(printingJob3));

        Thread t4 = new Thread(() -> jobProcessor.enqueue(processingJob1));
        Thread t5 = new Thread(() -> jobProcessor.enqueue(processingJob2));
        Thread t6 = new Thread(() -> jobProcessor.enqueue(processingJob3));
        Thread t7 = new Thread(() -> jobProcessor.enqueue(processingJob4));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();

        System.out.println("Total jobs enqueued : " + jobProcessor.getTotalJobsEnqueued().get());

        jobProcessor.shutdown();
    }
}