public class Main {
    public static void main(String[] args) {
        User user1 = new User("Alice");
        User user2 = new User("Bob");
        User user3 = new User("Charlie");
        
        user1.addFollower(user2);
        user1.addFollower(user3);

        user2.addFollower(user3);

        user3.addFollower(user1);

        user1.post("Hey everyone, it's Alice!");
        user2.post("Bob here, good morning!");
        user3.post("Charlie checking in.");

        user3.removeFollower(user1);
        user3.post("Charlie again, without Alice following me.");
    }
}