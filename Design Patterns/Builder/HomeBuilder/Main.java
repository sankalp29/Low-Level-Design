public class Main {
    public static void main(String[] args) {
        Home home = Home.HomeBuilder.builder()
                    .addRoom(Home.RoomType.LIVING_ROOM)
                    .addRoom(Home.RoomType.BEDROOM)
                    .addRoom(Home.RoomType.KITCHEN)
                    .noMoreRooms()
                    .addGarden()
                    .addGarage()
                    .addSwimmingPool()
                    .addTerrace()
                    .noMoreFeatures()
                    .build();

        System.out.println(home);
    }
}