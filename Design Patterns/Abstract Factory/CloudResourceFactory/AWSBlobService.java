package CloudResourceFactory;

public class AWSBlobService implements BlobService {

    @Override
    public void storeBlob() {
        System.out.println("Storing blob in AWS - S3");
    }

    @Override
    public void start() {
        storeBlob();
    }
    
}
