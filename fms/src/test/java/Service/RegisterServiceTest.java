package Service;

import org.junit.Before;
import org.junit.Test;

import Dao.Database;
import Dao.DatabaseException;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 10/28/17.
 */

public class RegisterServiceTest {
    private RegisterRequest request;
    private RegisterService service;
    private Database db;

    @Before
    public void SetUp() throws DatabaseException {
        request = new RegisterRequest("username","password","email","fn","ln","m");
        service = new RegisterService();

        db = new Database();
        db.openConnection();
        db.createTables();
        db.closeConnection(true);
    }

    @Test
    public void ValidRegisterTest() {
        RegisterResult result = service.register(request);
        assertEquals("username",result.getUserName());

    }

    @Test
    public void UsernameTakenRegisterTest() throws DatabaseException{
        db.openConnection();
        db.getUserDao().createUser(new User("username","password","email","fn","ln","m","123"));
        db.closeConnection(true);
        RegisterResult result = service.register(request);
        assertEquals("Username already taken by another user", result.getMessage());
    }

    @Test
    public void InvalidRegisterTest() {
        request = new RegisterRequest();
        RegisterResult result = service.register(request);
        assertEquals("Request property missing or has invalid value", result.getMessage());
    }

}
