package ConfigurationManager;

class Configurations {
    private static volatile Configurations configurations;
    
    private Configurations() {
        loadConfigurations();
    }

    public static Configurations getInstance() {
        if (configurations == null) {
            synchronized (Configurations.class) {
                if (configurations == null) configurations = new Configurations();
            }
        }

        return configurations;
    }

    private void loadConfigurations() {
        System.out.println("Loading configurations....");
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Loaded configurations....");
    }

    public String getConfigValue(String key) {
        return "value : " + key;
    }
}
