package DataStream;

public class FileDataStream implements DataStream {

    @Override
    public String readData(String data) {
        return data;
    }

    @Override
    public String writeData(String data) {
        return data;
    }
}
