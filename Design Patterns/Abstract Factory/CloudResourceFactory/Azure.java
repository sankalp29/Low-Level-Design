package CloudResourceFactory;

public class Azure implements CloudProvider {

    @Override
    public BlobService getBlobService() {
        return new AzureBlobService();
    }

    @Override
    public ComputingService getComputingService() {
        return new AzureComputingService();
    }
}
