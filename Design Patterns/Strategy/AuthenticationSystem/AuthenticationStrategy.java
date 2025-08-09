package AuthenticationSystem;

public interface AuthenticationStrategy {
    public void authenticate(String userId, String credential);
}
