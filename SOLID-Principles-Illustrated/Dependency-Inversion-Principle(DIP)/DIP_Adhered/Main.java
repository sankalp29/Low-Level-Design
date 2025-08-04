package DIP_Adhered;

interface Database {
    void connect();
}

class MySQLDatabase implements Database {

    @Override
    public void connect() {
        System.out.println("MySQL database connected...");
    }
}

class MongoDBDatabase implements Database {

    @Override
    public void connect() {
        System.out.println("MongoDB database connected...");
    }
}

class DatabaseConnector {
    private Database db;
    public DatabaseConnector(Database db) {
        this.db = db;
    }

    public void start() {
        db.connect();
    }
}

public class Main {
    public static void main(String[] args) {        
        Database db = new MongoDBDatabase();
        DatabaseConnector dbConnector = new DatabaseConnector(db);
        dbConnector.start();

        db = new MySQLDatabase();
        dbConnector = new DatabaseConnector(db);
        dbConnector.start();
    }
}
