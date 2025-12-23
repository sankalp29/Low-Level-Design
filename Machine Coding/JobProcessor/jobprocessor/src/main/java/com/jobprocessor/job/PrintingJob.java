package com.jobprocessor.job;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class PrintingJob implements IJob {
    private final String name;

    @Override
    public void execute() {
        System.out.println("Executing a simple printing job. Details: " + name);
    }
}
