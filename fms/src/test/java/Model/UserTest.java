package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 11/1/17.
 */

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void setUsernameTest() {
        user.setUsername("123");
        assertEquals("123",user.getUsername());
    }

    @Test
    public void setPasswordTest() {
        user.setPassword("123");
        assertEquals("123",user.getPassword());
    }
    @Test
    public void setEmailTest() {
        user.setEmail("123");
        assertEquals("123",user.getEmail());
    }

    @Test
    public void setFirstNameTest() {
        user.setFirstName("123");
        assertEquals("123",user.getFirstName());
    }

    @Test
    public void setLastNameTest() {
        user.setLastName("123");
        assertEquals("123",user.getLastName());
    }

    @Test
    public void setGenderTest() {
        user.setGender("123");
        assertEquals("123",user.getGender());
    }

    @Test
    public void setPersonIdTest() {
        user.setPersonId("123");
        assertEquals("123",user.getPersonId());
    }
}
