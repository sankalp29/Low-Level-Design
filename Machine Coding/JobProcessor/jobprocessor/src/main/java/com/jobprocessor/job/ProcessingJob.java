package com.jobprocessor.job;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProcessingJob implements IJob {
    private final String name;

    @Override
    public void execute() {
        System.out.println("Started processing job. Details: " + name);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished processing job. " + name);
    }
}
