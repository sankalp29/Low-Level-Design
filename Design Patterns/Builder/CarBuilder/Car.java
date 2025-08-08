import java.util.HashSet;
import java.util.Set;

public class Car {
    private final String engine;
    private final String wheels;
    private final Set<Feature> features;
    private final String color;

    private Car(String engine, String wheels, Set<Feature> features, String color) {
        this.engine = engine;
        this.wheels = wheels;
        this.features = features;
        this.color = color;
    }

    public static class CarBuilder {
        public interface EngineStep {
            WheelsStep selectEngine(String engine);
        }
    
        public interface WheelsStep {
            ColorsStep selectWheels(String wheels);
        }
    
        public interface ColorsStep {
            FeaturesStep selectColor(String color);
        }
    
        public interface FeaturesStep {
            FeaturesStep addFeature(Feature feature);
            BuildStep noMoreFeatures();
        }
    
        public interface BuildStep {
            Car build();
        }

        public static EngineStep builder() {
            return new Steps();
        }

        private static class Steps implements EngineStep, WheelsStep, ColorsStep, FeaturesStep, BuildStep {
            private String engine;
            private String wheels;
            private Set<Feature> features;
            private String color;

            private Steps() {
                this.features = new HashSet<>();
            }

            @Override
            public WheelsStep selectEngine(String engine) {
                this.engine = engine;
                return this;
            }

            @Override
            public ColorsStep selectWheels(String wheels) {
                this.wheels = wheels;
                return this;
            }

            @Override
            public FeaturesStep selectColor(String color) {
                this.color = color;
                return this;
            }

            @Override
            public FeaturesStep addFeature(Feature feature) {
                features.add(feature);
                return this;
            }

            @Override
            public BuildStep noMoreFeatures() {
                return this;
            }

            @Override
            public Car build() {
                if (engine == null) throw new IllegalArgumentException("You need to select an engine");
                if (wheels == null) throw new IllegalArgumentException("You need to select wheels");

                return new Car(engine, wheels, Set.copyOf(features), color);
            }   
        }
    }

    @Override
    public String toString() {
        return "Car [engine=" + engine + ", wheels=" + wheels + ", features=" + features + "]";
    }
}

interface Feature {
    String description();
}

class Airbags implements Feature {

    @Override
    public String description() {
        return "Airbags to protect you";
    }

    @Override
    public String toString() {
        return description();
    }
}

class ParkingSensor implements Feature {

    @Override
    public String description() {
        return "ParkingSensors to help you park";
    }

    @Override
    public String toString() {
        return description();
    }
}

class Defogger implements Feature {

    @Override
    public String description() {
        return "Defogger to remove fog from the glass";
    }

    @Override
    public String toString() {
        return description();
    }
}