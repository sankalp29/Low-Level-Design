package FurnitureFactory;

public class Main {
    public static void main(String[] args) {
        FurnitureStyle style = FurnitureStyleFactory.getFurnitureStyle("victorian");
        Chair chair = style.getChair();
        Table table = style.getTable();

        chair.desc();
        table.desc();
    }
}
