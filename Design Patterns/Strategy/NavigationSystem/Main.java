package NavigationSystem;

public class Main {
    public static void main(String[] args) {
        Navigator navigator = new Navigator(NavigationFactory.getNavigationStrategy(NavigationFactory.NavigationType.SHORTEST_PATH));
        Path path1 = navigator.findPath("Source1", "Destination1");

        navigator.setNavigationStrategy(NavigationFactory.getNavigationStrategy(NavigationFactory.NavigationType.MOST_EATERIES));
        Path path2 = navigator.findPath("Source2", "Destination2");
    }
}
