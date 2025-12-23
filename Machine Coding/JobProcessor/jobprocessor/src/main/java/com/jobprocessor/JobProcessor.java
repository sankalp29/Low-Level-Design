package com.jobprocessor;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import com.jobprocessor.exceptions.InvalidJobException;
import com.jobprocessor.job.IJob;

import lombok.Getter;

public class JobProcessor { 
    private final ExecutorService processorWorker;
    private final BlockingQueue<IJob> jobQueue;
    private static final Integer MAX_RETRIES = 3;
    @Getter
    private final AtomicInteger totalJobsEnqueued;

    /**
     * Enqueue a new job to the job queue
     * @param job
     */
    public void enqueue(IJob job) {
        checkJobValidity(job);
        if (jobQueue.offer(job)) {
            System.out.println("[ENQUEUED] : " + job);
            totalJobsEnqueued.incrementAndGet();
        }
    }
    
    private void processJobs() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                IJob job = jobQueue.take();
                process(job);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void process(IJob job) {
        Random random = new Random();
        int attempts = 0;

        while (attempts++ < MAX_RETRIES) {
            job.execute();
            boolean processedSuccesfully = random.nextBoolean();
            if (processedSuccesfully) break;
            System.out.println("Retry attempt " + attempts + " for " + job);
        }
        if (attempts == MAX_RETRIES) System.out.println("Job " + job + " failed permanently");
    }

    public void shutdown() throws InterruptedException {
        // Stop accepting new tasks but let submitted ones complete. But shutdown() does not interrupt the worker thread
        processorWorker.shutdown();
        
        // Wait for 40 secs for already submitted tasks to complete
        if (!processorWorker.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
            // Interrupt the worker threads now using shutdownNow()
            processorWorker.shutdownNow();
        }
    }

    private void checkJobValidity(IJob job) {
        if (job == null) throw new InvalidJobException("Invalid job. Cannot enqueue null job");
    }

    public JobProcessor() {
        this.totalJobsEnqueued = new AtomicInteger(0);
        this.jobQueue = new LinkedBlockingDeque<>();
        this.processorWorker = Executors.newFixedThreadPool(1, r -> {
            Thread thread = new Thread(r);
            thread.setName("Job Processor Thread");
            thread.setDaemon(false);
            // Daemon thread so that the Main thread does not end till all background jobs are processed
            return thread;
        });
        processorWorker.submit(this::processJobs);
    }
}