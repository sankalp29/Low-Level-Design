public class Main {
    public static void main(String[] args) {
        Car car = Car.CarBuilder.builder()
                        .selectEngine("4.0 TSI")
                        .selectWheels("Bridgestone 18inch")
                        .selectColor("Navy")
                        .addFeature(new ParkingSensor())
                        .addFeature(new Defogger())
                        .noMoreFeatures()
                        .build();

        System.out.println(car);
    }
}
