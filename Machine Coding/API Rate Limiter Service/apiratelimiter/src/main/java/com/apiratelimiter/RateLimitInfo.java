package com.apiratelimiter;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RateLimitInfo {
    private final Integer numOfRequests;
    private final Long numOfSeconds;
}
