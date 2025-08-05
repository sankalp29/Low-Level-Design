package CloudResourceFactory;

public class Main {
    public static void main(String[] args) {
        CloudProvider cloudProvider = CloudProviderFactory.getCloudProvider("aws");
        BlobService blobService = cloudProvider.getBlobService();
        ComputingService computingService = cloudProvider.getComputingService();

        blobService.start();
        computingService.start();
    }
}