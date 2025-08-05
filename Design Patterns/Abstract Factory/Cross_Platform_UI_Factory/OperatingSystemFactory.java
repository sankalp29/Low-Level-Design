package Cross_Platform_UI_Factory;

public class OperatingSystemFactory {
    public static OperatingSystem getOperatingSystem() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.equals("mac")) return new MacOperatingSystem();
        return new WindowsOperatingSystem();
    }
}
