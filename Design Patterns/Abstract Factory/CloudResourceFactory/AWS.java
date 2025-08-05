package CloudResourceFactory;

public class AWS implements CloudProvider {

    @Override
    public BlobService getBlobService() {
        return new AWSBlobService();
    }

    @Override
    public ComputingService getComputingService() {
        return new AWSComputingService();
    }
}
