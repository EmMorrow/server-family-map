package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 11/1/17.
 */

public class AuthTokenTest {
    private AuthToken auth;

    @Before
    public void setUp() {
        auth = new AuthToken();
    }

    @Test
    public void setAuthTokenTest() {
        auth.setAuthToken("123");
        assertEquals("123",auth.getAuthToken());
    }

    @Test
    public void setUsernameTest() {
        auth.setUsername("123");
        assertEquals("123",auth.getUsername());
    }
}
