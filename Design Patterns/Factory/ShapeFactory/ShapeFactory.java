package ShapeFactory;

public class ShapeFactory {
    public static Shape getShape(String type) {
        if (type.equalsIgnoreCase("circle")) return new Circle();
        if (type.equalsIgnoreCase("square")) return new Square();
        if (type.equalsIgnoreCase("rectangle")) return new Rectangle();

        return null;
    }
}
