package Cross_Platform_UI_Factory;

public class MacSettingsComponent implements SettingsComponent {

    @Override
    public void change() {
        System.out.println("Mac Settings component changed...");
    }
}
