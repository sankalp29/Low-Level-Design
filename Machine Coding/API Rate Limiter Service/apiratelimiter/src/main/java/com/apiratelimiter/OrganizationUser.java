package com.apiratelimiter;

public class OrganizationUser extends User {
    
    public OrganizationUser(String name) {
        super(UserType.ORGANIZATION, name);
    }
}
