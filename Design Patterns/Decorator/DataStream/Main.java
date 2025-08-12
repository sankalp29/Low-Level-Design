package DataStream;

public class Main {
    public static void main(String[] args) {
        String data = "This is some data";
        
        DataStream dataStream = new FileDataStream();
        System.out.println(dataStream.readData(data));

        dataStream = new Compression(dataStream);
        System.out.println(dataStream.readData(data));

        dataStream = new Encryption(dataStream);
        System.out.println(dataStream.writeData(data));
    }
}
