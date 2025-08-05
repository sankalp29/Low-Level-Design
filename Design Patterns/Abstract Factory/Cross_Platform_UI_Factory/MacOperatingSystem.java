package Cross_Platform_UI_Factory;

public class MacOperatingSystem implements OperatingSystem {

    @Override
    public HomeComponent createHomeComponent() {
        return new MacHomeComponent();
    }

    @Override
    public SettingsComponent createSettingsComponent() {
        return new MacSettingsComponent();
    }
}
