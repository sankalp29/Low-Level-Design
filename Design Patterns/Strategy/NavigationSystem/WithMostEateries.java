package NavigationSystem;

public class WithMostEateries implements NavigationStrategy {

    @Override
    public Path navigate(String sourceLocation, String destLocation) {
        System.out.println("Fetching a path with most eateries");
        return new Path();
    }
}
