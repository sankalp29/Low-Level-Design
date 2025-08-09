package AuthenticationSystem;

public class AuthenticationFactory {
    public static enum AuthenticationMode {
        BIOMETRIC,
        PASSWORD,
        OAUTH2
    };

    public static AuthenticationStrategy getAuthenticationStrategy(AuthenticationMode authenticationMode) {
        return switch (authenticationMode) {
            case BIOMETRIC -> new BiometricAuth();
            case PASSWORD -> new PasswordAuth();
            case OAUTH2 -> new OAuth2Auth();
        };
    }
}
