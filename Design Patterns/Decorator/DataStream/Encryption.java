package DataStream;

public class Encryption extends DataStreamDecorator {

    public Encryption(DataStream dataStream) {
        super(dataStream);
    }

    @Override
    public String readData(String data) {
        return super.readData(data) + " + [Encrypted Read]";
    }

    @Override
    public String writeData(String data) {
        return super.writeData(data) + " + [Encrypted Write]";
    }
}
