package DataStream;

public class Compression extends DataStreamDecorator {

    public Compression(DataStream dataStream) {
        super(dataStream);
    }

    @Override
    public String readData(String data) {
        return super.readData(data) + " [Compressed Read]";
    }

    @Override
    public String writeData(String data) {
        return super.writeData(data) + " [Compressed Write]";
    }
}
