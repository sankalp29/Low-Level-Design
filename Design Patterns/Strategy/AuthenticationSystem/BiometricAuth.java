package AuthenticationSystem;

public class BiometricAuth implements AuthenticationStrategy {

    @Override
    public void authenticate(String userId, String biometric) {
        System.out.println("Authenticating " + userId + " with token : " + biometric);
    }
}
