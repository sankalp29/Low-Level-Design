package CompressionSystem;

public class RAR implements CompressionStrategy {

    @Override
    public void compress(String fileSource, String fileDestination) {
        System.out.println("Compressing via RAR. File stored : " + fileSource + "_compressed.rar");
    }
}
