package com.apiratelimiter;

import com.apiratelimiter.strategies.SlidingWindow;

public class App 
{
    public static void main( String[] args ) {
        RateLimiter rateLimiter = RateLimiter.getInstance(new SlidingWindow());
        User sankalp = new NormalUser("Sankalp");
        User janvi = new PremiumUser("Janvi");
        User mockwise = new OrganizationUser("Mockwise");


        for (int i = 0; i < 51; i++) {
            System.out.println();
            System.out.println("Request Number : " + (i+1));
            try {
                System.out.println("Sankalp : " + rateLimiter.canCall(sankalp, "order-service"));
                Thread.sleep(5000);
            } catch (Exception e) {
            }
            
            // System.out.println("Janvi : " + rateLimiter.canCall(janvi, "order-service"));
            // System.out.println("Mockwise : " + rateLimiter.canCall(mockwise, "order-service"));
            System.out.println();
        }
    }
}
