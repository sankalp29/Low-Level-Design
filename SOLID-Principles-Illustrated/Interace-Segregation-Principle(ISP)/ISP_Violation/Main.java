package ISP_Violation;

interface Worker {
    void eat();
    void work();
}

class HumanWorker implements Worker {

    @Override
    public void eat() {
        System.out.println("Human eating...");
    }

    @Override
    public void work() {
        System.out.println("Human working...");
    }
    
}

class RobotWorker implements Worker {

    @Override
    public void eat() {
        throw new UnsupportedOperationException("Robots don't eat");
    }

    @Override
    public void work() {
        System.out.println("Robot working...");
    }
    
}

public class Main {
    public static void main(String[] args) {
        Worker human = new HumanWorker();
        human.eat();
        
        Worker robot = new RobotWorker();
        robot.eat();
    }
}