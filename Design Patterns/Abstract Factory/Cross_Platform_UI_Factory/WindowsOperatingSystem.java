package Cross_Platform_UI_Factory;

public class WindowsOperatingSystem implements OperatingSystem {

    @Override
    public HomeComponent createHomeComponent() {
        return new WindowsHomeComponent();
    }

    @Override
    public SettingsComponent createSettingsComponent() {
        return new WindowsSettingsComponent();
    }
}
