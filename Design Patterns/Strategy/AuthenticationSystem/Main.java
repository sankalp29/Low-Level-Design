package AuthenticationSystem;

public class Main {
    public static void main(String[] args) {

        AuthenticationStrategy strategy = AuthenticationFactory.getAuthenticationStrategy(AuthenticationFactory.AuthenticationMode.BIOMETRIC);
        strategy.authenticate("id1", "thumb1");
    }
}
