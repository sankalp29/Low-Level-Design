package CloudResourceFactory;

public class AWSComputingService implements ComputingService {

    @Override
    public void compute() {
        System.out.println("Computing using AWS - EC2");
    }

    @Override
    public void start() {
        compute();
    }
    
}
