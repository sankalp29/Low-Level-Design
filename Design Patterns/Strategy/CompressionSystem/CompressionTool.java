package CompressionSystem;

public class CompressionTool {
    private CompressionStrategy compressionStrategy;

    public CompressionTool(CompressionStrategy compressionStrategy) {
        this.compressionStrategy = compressionStrategy;
    }

    public void compress(String source, String destination) {
        compressionStrategy.compress(source, destination);
    }
}
