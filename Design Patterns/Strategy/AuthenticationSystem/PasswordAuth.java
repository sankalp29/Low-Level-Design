package AuthenticationSystem;

public class PasswordAuth implements AuthenticationStrategy {

    @Override
    public void authenticate(String userId, String password) {
        System.out.println("Authenticating " + userId + " with password : " + password);
    }
}
