package ISP_Adhered;

interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements Workable, Eatable {

    @Override
    public void work() {
        System.out.println("Human working...");
    }

    @Override
    public void eat() {
        System.out.println("Human eating...");
    }
}

class Robot implements Workable {

    @Override
    public void work() {
        System.out.println("Robot working...");   
    }
}

public class Main {
    public static void main(String[] args) {
        Workable human = new Human();
        Workable robot = new Robot();
        human.work();
        robot.work();

        Eatable eater = new Human();
        eater.eat();
    }
}
