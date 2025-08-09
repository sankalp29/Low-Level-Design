package CompressionSystem;

public class SevenZ implements CompressionStrategy {

    @Override
    public void compress(String fileSource, String fileDestination) {
        System.out.println("Compressing via 7z. File stored : " + fileSource + "_compressed.7z");
    }
}
