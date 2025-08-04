package ConfigurationManager;

public class Main {
    public static void main(String[] args) {
        Configurations configurations = Configurations.getInstance();
        System.out.println(configurations.getConfigValue("db.url"));
    }
}
