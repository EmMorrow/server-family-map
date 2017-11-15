package Service;

import java.sql.SQLException;

import Dao.*;
import Result.ClearResult;
/**
 * Created by emilychandler on 10/11/17.
 */

public class ClearService {
    private Database db;

    public ClearService() {
        db = new Database();
    }
    /**
     * Deletes all data from the database
     * @return a ClearResult object saying if it was successful
     */
    public ClearResult clear()  {
        try {
            db.openConnection();
            db.createTables();
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            return new ClearResult("Internal Service Error");
        }
        return new ClearResult("Clear succeeded");
    }

    public Database getDatabase() {
        return db;
    }
}
