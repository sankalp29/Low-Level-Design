package Cross_Platform_UI_Factory;

public class WindowsSettingsComponent implements SettingsComponent {

    @Override
    public void change() {
        System.out.println("Windows Settings component changing...");
    }
}
