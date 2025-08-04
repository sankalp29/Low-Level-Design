public class Main {
    public static void main(String[] args) {
        PrinterSpooler printerSpooler = PrinterSpooler.getInstance();
        printerSpooler.addPrintJob(new PrintJob("id1", "title1", "desc1"));
        printerSpooler.addPrintJob(new PrintJob("id2", "title2", "desc2"));
        printerSpooler.addPrintJob(new PrintJob("id3", "title3", "desc3"));
    }
}
