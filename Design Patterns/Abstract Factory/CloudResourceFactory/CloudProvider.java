package CloudResourceFactory;

public interface CloudProvider {
    BlobService getBlobService();
    ComputingService getComputingService();
}
