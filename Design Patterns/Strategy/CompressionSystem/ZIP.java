package CompressionSystem;

public class ZIP implements CompressionStrategy {

    @Override
    public void compress(String fileSource, String fileDestination) {
        System.out.println("Compressing via ZIP. File stored : " + fileSource + "_compressed.zip");
    }
}
