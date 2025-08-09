package NavigationSystem;

public class Navigator {
    private NavigationStrategy navigationStrategy;

    public Navigator(NavigationStrategy navigationStrategy) {
        this.navigationStrategy = navigationStrategy;
    }

    public void setNavigationStrategy(NavigationStrategy navigationStrategy) {
        this.navigationStrategy = navigationStrategy;
    }

    public Path findPath(String source, String destination) {
        return navigationStrategy.navigate(source, destination);
    }
}
