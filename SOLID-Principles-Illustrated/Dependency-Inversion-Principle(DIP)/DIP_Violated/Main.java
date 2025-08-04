package DIP_Violated;

class MySQLDatabase {
    public void connect() {
        System.out.println("MySQL database connected...");
    }
}

class MongoDBDatabase {
    public void connect() {
        System.out.println("MySQL database connected...");
    }
}

public class Main {
    public static void main(String[] args) {
        MySQLDatabase sqlDB = new MySQLDatabase(); // Tightly coupled
        sqlDB.connect();

        MongoDBDatabase mongo = new MongoDBDatabase();
        mongo.connect();
    }
}