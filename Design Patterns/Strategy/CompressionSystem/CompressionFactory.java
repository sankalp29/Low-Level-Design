package CompressionSystem;

public class CompressionFactory {
    public static enum Type {
        ZIP,
        RAR,
        SEVEN_Z
    }

    public static CompressionStrategy getCompressionStrategy(Type type) {
        return switch (type) {
            case ZIP -> new ZIP();
            case RAR -> new RAR();
            case SEVEN_Z -> new SevenZ();
            default -> throw new IllegalArgumentException("Illegal compression type chosen.");
        };
    }
}
