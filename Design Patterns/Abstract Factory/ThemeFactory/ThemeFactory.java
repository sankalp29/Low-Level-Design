package ThemeFactory;

public class ThemeFactory {
    public static Theme getTheme(String choice) {
        if (choice == null) {
            throw new IllegalArgumentException("Theme choice cannot be null");
        }

        if (choice.equalsIgnoreCase("dark")) return new DarkTheme();
        if (choice.equalsIgnoreCase("light")) return new LightTheme();

        throw new IllegalArgumentException("Theme choice is invalid");
    }
}
