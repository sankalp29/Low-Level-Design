public class Main {
    public static void main(String[] args) {
        LoggerRegistry loggerRegistry = new LoggerRegistry();
        Runnable task1 = () -> {
            LoggerScheduler loggerScheduler = LoggerScheduler.getInstance(loggerRegistry);
            for (int i = 1; i <= 100; i++) {
                loggerScheduler.log("json", "Message A : " + i);
            }
        };

        Runnable task2 = () -> {
            LoggerScheduler loggerScheduler = LoggerScheduler.getInstance(loggerRegistry);
            for (int i = 1; i <= 100; i++) {
                loggerScheduler.log("xml", "Message B : " + i);
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }   
    }
}