package Model;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Stores information about the AuthToken
 */
public class AuthToken {
    private String authToken;
    private String username;

    public AuthToken() {
        this.authToken = generateAuthToken();
    }
    public AuthToken(String username) {
        this.username = username;
        this.authToken = generateAuthToken();
    }

    /**
     * Generates a unique AuthToken
     * @return return the unique authtoken
     */
    private String generateAuthToken(){
        UUID uuid = UUID.randomUUID();
        String auth = uuid.toString();

        return auth;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
