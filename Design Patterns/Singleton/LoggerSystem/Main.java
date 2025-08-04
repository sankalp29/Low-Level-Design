package LoggerSystem;

public class Main {
    static Logger logger1, logger2;
    public static void main(String[] args) {
        
        Runnable runnable1 = () -> logger1 = Logger.getInstance();
        Runnable runnable2 = () -> logger2 = Logger.getInstance();

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(logger1 == logger2); // Returns true
    }
}
