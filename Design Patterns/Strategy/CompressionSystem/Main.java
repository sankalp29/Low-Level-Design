package CompressionSystem;

public class Main {
    public static void main(String[] args) {
        CompressionTool compressionTool = new CompressionTool(CompressionFactory.getCompressionStrategy(CompressionFactory.Type.RAR));
        compressionTool.compress("source", "dest");
    }
}