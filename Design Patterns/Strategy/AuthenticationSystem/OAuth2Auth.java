package AuthenticationSystem;

public class OAuth2Auth implements AuthenticationStrategy {

    @Override
    public void authenticate(String userId, String token) {
        System.out.println("Authenticating " + userId + " with token : " + token);
    }
}
