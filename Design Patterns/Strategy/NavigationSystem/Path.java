package NavigationSystem;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List<String> coordinates;

    public Path() {
        coordinates = new ArrayList<>();
    }

    public List<String> getCoordinates() {
        return List.copyOf(coordinates);
    }

    public void addNewCoordinate(String coordinate) {
        coordinates.add(coordinate);
    }
}
