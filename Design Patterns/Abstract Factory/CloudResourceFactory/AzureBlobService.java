package CloudResourceFactory;

public class AzureBlobService implements BlobService {

    @Override
    public void storeBlob() {
        System.out.println("Storing in Azure Blob");
    }

    @Override
    public void start() {
        storeBlob();
    }
    
}
