package com.apiratelimiter.user;

public class OrganizationUser extends User {
    
    public OrganizationUser(String name) {
        super(UserType.ORGANIZATION, name);
    }
}
