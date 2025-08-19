package com.apiratelimiter;

public class PremiumUser extends User {
    
    public PremiumUser(String name) {
        super(UserType.PREMIUM, name);
    }   
}
