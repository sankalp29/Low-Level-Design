package ShapeFactory;

public class Main {
    public static void main(String[] args) {
        Shape shape = ShapeFactory.getShape("circle");
        shape.show();
    }
}