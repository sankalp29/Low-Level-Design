package CloudResourceFactory;

public class AzureComputingService implements ComputingService {

    @Override
    public void compute() {
        System.out.println("Computing using Azure compute");
    }

    @Override
    public void start() {
        compute();
    }
    
}
