
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PrinterSpooler {
    private static PrinterSpooler spooler;
    private static BlockingQueue<PrintJob> printJobs;

    private PrinterSpooler() {
        printJobs = new LinkedBlockingDeque<>();
        new Thread(this::processJobs, "PrintProcessor").start();
    }

    public static PrinterSpooler getInstance() {
        if (spooler == null) {
            synchronized (PrinterSpooler.class) {
                if (spooler == null) spooler = new PrinterSpooler();
            }
        }

        return spooler;
    }

    public void addPrintJob(PrintJob job) {
        printJobs.offer(job);
    }

    private void processJobs() {
        while (true) { 
            try {
                PrintJob job = printJobs.take();
                Thread.sleep(1000);
                System.out.println(job);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
