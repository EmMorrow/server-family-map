package Dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import Model.AuthToken;

import static org.junit.Assert.assertEquals;

/**
 * Performs SQL queries on the AuthToken table
 */
public class AuthTokenDaoTest {
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
    public void testAddAuthToken() throws SQLException {
        AuthToken a = new AuthToken("123");
        db.getAuthDao().createAuthToken(a);
    }

    @Test
    public void testGetAuthToken() throws SQLException {
        AuthToken a = new AuthToken("123");
        String auth = a.getAuthToken();
        db.getAuthDao().createAuthToken(a);
        AuthToken b = db.getAuthDao().getAuthToken(auth);

        assertEquals(b.getAuthToken(),a.getAuthToken());
        assertEquals(b.getUsername(),a.getUsername());
    }

    @Test
    public void testDeleteAuthToken() throws SQLException {
        AuthToken a = new AuthToken("123");
        AuthToken b = new AuthToken("126");
        String auth = a.getAuthToken();
        db.getAuthDao().createAuthToken(a);
        db.getAuthDao().createAuthToken(b);
        db.getAuthDao().deleteAuthToken(auth);
    }

}
