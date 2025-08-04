package DBConnectionPool;

public class Main {
    private static DBConnectionPool poolInstance1, poolInstance2;
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable1 = () -> poolInstance1 = DBConnectionPool.getInstance();
        Runnable runnable2 = () -> poolInstance2 = DBConnectionPool.getInstance();

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();   
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(poolInstance1 == poolInstance2); // returns true
    }
}
