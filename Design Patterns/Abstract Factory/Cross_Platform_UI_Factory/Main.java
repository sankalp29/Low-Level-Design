package Cross_Platform_UI_Factory;

public class Main {
    public static void main(String args[]) {
        OperatingSystem os = OperatingSystemFactory.getOperatingSystem();
        HomeComponent homeComponent = os.createHomeComponent();
        SettingsComponent settingsComponent = os.createSettingsComponent();

        homeComponent.show();
        settingsComponent.change();
    }
}