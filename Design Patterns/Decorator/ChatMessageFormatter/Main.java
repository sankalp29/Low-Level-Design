package ChatMessageFormatter;

public class Main {
    public static void main(String[] args) {
        Formatter formatter = new ChatMessageFormatter();
        System.out.println(formatter.getDescription());

        formatter = new Emoji(formatter);
        System.out.println(formatter.getDescription());

        formatter = new Bold(formatter);
        System.out.println(formatter.getDescription());
    }
}