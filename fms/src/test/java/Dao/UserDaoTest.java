package Dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import Model.User;

import static org.junit.Assert.assertEquals;

/**
 * Performs SQL queries on the User table
 */
public class UserDaoTest {
    private Database db;

    @Before
    public void setUp() throws DatabaseException{
        db = new Database();
        db.openConnection();
        db.createTables();
    }

    @After
    public void tearDown() throws DatabaseException {
        db.closeConnection(true);
    }

    @Test
    public void testCreateUser() throws SQLException {
        User u = new User("1","1","1","1","1","1","1");
        db.getUserDao().createUser(u);
    }

    @Test
    public void testGetUser() throws SQLException {
        User a = new User("1","1","1","1","1","1","1");
        db.getUserDao().createUser(a);

        User b = db.getUserDao().getUser("1");
        assertEquals(a.getUsername(),b.getUsername());
        assertEquals(a.getPassword(),b.getPassword());
        assertEquals(a.getEmail(),b.getEmail());
        assertEquals(a.getFirstName(),b.getFirstName());
        assertEquals(a.getLastName(),b.getLastName());
        assertEquals(a.getGender(),b.getGender());
        assertEquals(a.getPersonId(),b.getPersonId());
    }

    @Test
    public void testDeleteUser() throws SQLException {
        User a = new User("1","1","1","1","1","1","1");
        db.getUserDao().createUser(a);

        db.getUserDao().deleteUser("1");
    }
}
