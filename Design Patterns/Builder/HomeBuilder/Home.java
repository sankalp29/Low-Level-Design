import java.util.ArrayList;
import java.util.List;

public class Home {
    private final List<Room> rooms;
    private final Garage garage;
    private final Garden garden;

    public enum RoomType {
        BEDROOM,
        LIVING_ROOM,
        WASHROOM,
        KITCHEN,
        STUDY_ROOM
    }

    private Home(List<Room> rooms, Garage garage, Garden garden) {
        this.rooms = rooms;
        this.garage = garage;
        this.garden = garden;
    }

    static class HomeBuilder {
        public interface RoomStep {
            RoomStep addRoom(RoomType room);
            GardenStep noMoreRooms();
        }

        public interface GardenStep {
            GarageStep addGarden();
            GarageStep removeGarden();
        }

        public interface GarageStep {
            FeatureStep addGarage();
            FeatureStep removeGarage();
        }

        public interface FeatureStep {
            FeatureStep addSwimmingPool();
            FeatureStep addTerrace();
            FeatureStep addTennisCourt();
            BuildStep noMoreFeatures();
        }

        public interface BuildStep {
            Home build();
        }

        public static RoomStep builder() {
            return new Steps();
        }

        private static class Steps implements RoomStep, GardenStep, GarageStep, FeatureStep, BuildStep {
            
            private final List<Room> rooms;
            private Garage garage;
            private Garden garden;

            public Steps() {
                this.rooms = new ArrayList<>();
            }

            @Override
            public Home.HomeBuilder.RoomStep addRoom(RoomType room) {
                switch (room) {
                    case LIVING_ROOM:
                        rooms.add(new LivingRoom());
                        break;
                    case BEDROOM:
                        rooms.add(new Bedroom());
                        break;
                    case WASHROOM:
                        rooms.add(new Washroom());
                        break;
                    case KITCHEN:
                        rooms.add(new Kitchen());
                        break;
                    case STUDY_ROOM:
                        rooms.add(new Study());
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal Room type passed in");
                }
                return this;
            }

            @Override
            public Home.HomeBuilder.GardenStep noMoreRooms() {
                return this;
            }

            @Override
            public Home.HomeBuilder.GarageStep addGarden() {
                this.garden = new Garden();
                return this;   
            }

            @Override
            public Home.HomeBuilder.GarageStep removeGarden() {
                this.garden = null;
                return this;
            }

            @Override
            public Home.HomeBuilder.FeatureStep addGarage() {
                this.garage = new Garage();
                return this;
            }

            @Override
            public Home.HomeBuilder.FeatureStep removeGarage() {
                this.garage = null;
                return this;
            }

            @Override
            public Home.HomeBuilder.FeatureStep addSwimmingPool() {
                System.out.println("Swimming pool...");
                return this;
            }

            @Override
            public Home.HomeBuilder.FeatureStep addTennisCourt() {
                System.out.println("Tennis court...");
                return this;
            }

            @Override
            public Home.HomeBuilder.FeatureStep addTerrace() {
                System.out.println("Terrace...");
                return this;
            }

            @Override
            public Home.HomeBuilder.BuildStep noMoreFeatures() {
                return this;
            }

            @Override
            public Home build() {
                return new Home(rooms, garage, garden);
            }
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Garage getGarage() {
        return garage;
    }

    public Garden getGarden() {
        return garden;
    }

    @Override
    public String toString() {
        return "Home [rooms=" + rooms + ", garage=" + garage + ", garden=" + garden + "]";
    }

}

class Garage {
    public void garage() {
        System.out.println("Park your vehicles here...");
    }

    @Override
    public String toString() {
        return "Garage";
    }
}

class Garden {
    public void garden() {
        System.out.println("A nice green sit-out and BBQ area");
    }

    @Override
    public String toString() {
        return "Garden";
    }
}

interface Room {
    void description(String roomDescription);
}

class LivingRoom implements Room {

    @Override
    public void description(String roomDescription) {
        System.out.println("Living room...");
    }
}

class Bedroom implements Room {

    @Override
    public void description(String roomDescription) {
        System.out.println("Bedroom...");   
    }
}

class Kitchen implements Room {

    @Override
    public void description(String roomDescription) {
        System.out.println("Kitchen...");
    }   
}

class Washroom implements Room {

    @Override
    public void description(String roomDescription) {
        System.out.println("Washroom...");
    }
    
}

class Study implements Room {

    @Override
    public void description(String roomDescription) {
        System.out.println("Study room...");
    }
}