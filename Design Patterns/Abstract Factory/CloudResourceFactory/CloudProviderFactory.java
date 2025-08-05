package CloudResourceFactory;

public class CloudProviderFactory {
    public static CloudProvider getCloudProvider(String cloudProvider) {
        if (cloudProvider == null) throw new IllegalArgumentException("Cloud provider cannot be null");

        if (cloudProvider.equalsIgnoreCase("aws")) return new AWS();
        if (cloudProvider.equalsIgnoreCase("azure")) return new Azure();

        throw new IllegalArgumentException("Illegal cloud provider requested");
    }
}
