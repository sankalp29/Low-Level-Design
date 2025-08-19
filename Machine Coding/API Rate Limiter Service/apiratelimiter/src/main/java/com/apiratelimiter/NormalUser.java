package com.apiratelimiter;

public class NormalUser extends User {
    
    public NormalUser(String name) {
        super(UserType.NORMAL, name);
    }
}
