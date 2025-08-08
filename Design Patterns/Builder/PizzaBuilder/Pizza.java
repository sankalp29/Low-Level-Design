import java.util.HashSet;
import java.util.Set;

public class Pizza {

    public enum Crust {THICK, THIN, PAN};
    public enum Topping {OLIVES, ONIONS, JALAPENOS, TOMATO, PANEER, CAPSICUM};
    public enum Size {REGULAR, MEDIUM, LARGE};

    private final Crust crust;
    private final Set<Topping> toppings;
    private final Size size;

    private Pizza(Crust crust, Set<Topping> toppings, Size size) {
        this.crust = crust;
        this.toppings = toppings;
        this.size = size;
    }

    static class PizzaBuilder {
        private Crust crust;
        private Set<Topping> toppings;
        private Size size;
        
        public PizzaBuilder() {
            toppings = new HashSet<>();
        }

        public PizzaBuilder setCrust(Crust crust) {
            this.crust = crust;
            return this;
        }

        public PizzaBuilder addTopping(Topping topping) {
            toppings.add(topping);
            return this;
        }

        public PizzaBuilder removeTopping(Topping topping) {
            toppings.remove(topping);
            return this;
        }

        public PizzaBuilder setSize(Size size) {
            this.size = size;
            return this;
        }

        public Crust getCrust() {
            return crust;
        }

        public Set<Topping> getToppings() {
            return toppings;
        }

        public Size getSize() {
            return size;
        }

        public Pizza build() {
            Pizza pizza = new Pizza(crust, toppings, size);
            return pizza;
        }
    }

    @Override
    public String toString() {
        return "Pizza [crust=" + crust + ", toppings=" + toppings + ", size=" + size + "]";
    }
}
