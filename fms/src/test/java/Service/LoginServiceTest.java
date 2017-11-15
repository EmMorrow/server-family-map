package Service;

import org.junit.Before;
import org.junit.Test;

import Dao.Database;
import Dao.DatabaseException;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 10/27/17.
 */

public class LoginServiceTest {
    private Database db;
    private LoginService service;

    @Before
    public void setUp() throws DatabaseException {
        db = new Database();
        service = new LoginService();

        db.openConnection();
        db.createTables();

        User user = new User("username","password","","","","","1");
        db.getUserDao().createUser(user);

        db.closeConnection(true);
    }

    @Test
    public void loginTest() {
        LoginRequest request = new LoginRequest("username","password");
        LoginResult result = service.login(request);

        assertEquals("username",result.getUsername());
        assertEquals("1",result.getPersonId());
    }
}
