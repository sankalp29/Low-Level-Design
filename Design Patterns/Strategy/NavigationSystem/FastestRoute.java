package NavigationSystem;

public class FastestRoute implements NavigationStrategy {

    @Override
    public Path navigate(String sourceLocation, String destLocation) {
        System.out.println("Fetching the fastest route for you!");
        return new Path();
    }
    
}
