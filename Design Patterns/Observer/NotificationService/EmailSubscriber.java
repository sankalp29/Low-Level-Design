public class EmailSubscriber implements ISubscriber {
    private final String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void receive(IPublisher publisher, String message) {
        System.out.println("📧 Sending Email to " + email);
        display(publisher, message);
    }
}
