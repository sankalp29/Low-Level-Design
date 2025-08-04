public class Main {
    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.getInstance();
        for (int i = 1; i <= 101; i++) {
            System.out.println(rateLimiter.isRateLimited("userId1"));
        }
        for (int i = 1; i <= 101; i++) {
            System.out.println(rateLimiter.isRateLimited("userId2"));
        }
    }
}
