package Service;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import Dao.Database;
import Dao.DatabaseException;
import Model.User;
import Result.ClearResult;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 10/20/17.
 */

public class ClearServiceTest {
    private Database db;
    private ClearService cs;

    @Before
    public void setUp() throws DatabaseException {
        cs = new ClearService();
        db = new Database();
        db.openConnection();
        db.createTables();
        db.getUserDao().createUser(new User("1", "1", "1", "1", "1", "1", "1"));
        db.closeConnection(true);

    }

    @Test
    public void testClear() {
        ClearResult result= cs.clear();
        assertEquals("Clear succeeded",result.getMessage());
    }

}
