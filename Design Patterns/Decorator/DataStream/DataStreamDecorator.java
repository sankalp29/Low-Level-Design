package DataStream;

public abstract class DataStreamDecorator implements DataStream {

    private DataStream dataStream;

    public DataStreamDecorator(DataStream dataStream) {
        this.dataStream = dataStream;
    }

    @Override
    public String readData(String data) {
        return dataStream.readData(data);
    }

    @Override
    public String writeData(String data) {
        return dataStream.writeData(data);
    }
    
}
