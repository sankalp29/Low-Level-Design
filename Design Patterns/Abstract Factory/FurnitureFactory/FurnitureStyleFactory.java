package FurnitureFactory;

public class FurnitureStyleFactory {
    public static FurnitureStyle getFurnitureStyle(String style) {
        if (style.isEmpty()) throw new IllegalArgumentException("Furniture style cannot be null");

        if (style.equalsIgnoreCase("modern")) return new ModernFurnitureStyle();
        if (style.equalsIgnoreCase("victorian")) return new VictorianFurnitureStyle();

        throw new IllegalArgumentException("Illegal furniture style");
    }
}
