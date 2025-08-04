import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

class RateLimiter {
    private static final Integer TIME_WINDOW_MILLIS = 60000;
    private static final Integer MAX_REQUESTS_WINDOW = 100;
    private static RateLimiter rateLimiter;

    private static ConcurrentHashMap<String, LinkedList<Long>> userRequestMap;
    private static ConcurrentHashMap<String, Object> userIdLock;

    private RateLimiter() {
        userRequestMap = new ConcurrentHashMap<>();
        userIdLock = new ConcurrentHashMap<>();
    }

    public static RateLimiter getInstance() {
        if (rateLimiter == null) {
            synchronized (RateLimiter.class) {
                if (rateLimiter == null) rateLimiter = new RateLimiter();
            }
        }

        return rateLimiter;
    }

    public boolean isRateLimited(String userId) {
        userIdLock.putIfAbsent(userId, new Object());

        synchronized (userIdLock.get(userId)) {
            userRequestMap.putIfAbsent(userId, new LinkedList<>());
            LinkedList<Long> requestList = userRequestMap.get(userId);
            userRequestMap.putIfAbsent(userId, new LinkedList<>());

            long now = System.currentTimeMillis();
            while (!requestList.isEmpty() && now - requestList.peek() > TIME_WINDOW_MILLIS) {
                requestList.poll();
            }

            if (requestList.size() < MAX_REQUESTS_WINDOW) {
                requestList.offer(now);
                return true;
            }
            return false;
        }
    }
}
