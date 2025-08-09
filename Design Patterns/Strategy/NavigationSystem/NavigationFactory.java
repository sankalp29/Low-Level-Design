package NavigationSystem;

public class NavigationFactory {
    public static enum NavigationType {
        FASTEST_ROUTE,
        SHORTEST_PATH,
        MOST_EATERIES
    };

    private NavigationFactory() {}

    public static NavigationStrategy getNavigationStrategy(NavigationType navigationType) {
        return switch (navigationType) {
            case FASTEST_ROUTE -> new FastestRoute();
            case SHORTEST_PATH -> new ShortestPath();
            case MOST_EATERIES -> new WithMostEateries();
            default -> throw new IllegalArgumentException("Invalid navigation type selected.");
        };
    }
}
