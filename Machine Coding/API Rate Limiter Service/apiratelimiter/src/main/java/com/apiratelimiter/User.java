package com.apiratelimiter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class User {
    private UserType userType;
    private String name;
}
