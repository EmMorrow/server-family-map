
package Dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import Model.AuthToken;
import Model.Event;
import java.sql.SQLException;

import org.junit.* ;

import java.util.Set;

import static org.junit.Assert.* ;
/**
 * Provides a connection for the DAO's to work on the database
 */
public class DatabaseTest {
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
    public void testCreateTables() throws DatabaseException {
        db.createTables();
    }
}
