package Service;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import Dao.Database;
import Dao.DatabaseException;
import Data.Location;
import Data.Locations;
import Data.Names;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 10/26/17.
 */

public class FillServiceTest {
    private FillService fs;
    private Database db;
    @Before
    public void setUp (){
        db = new Database();
        try {
            db.openConnection();
            db.createTables();
            db.closeConnection(true);
            fs = new FillService();

            Location location = new Location("1","1",1.1,1.1);

            ArrayList<Location> locations = new ArrayList<Location>();
            locations.add(location);

            Locations myLocations = new Locations();
            myLocations.setData(locations);

            fs.setLocations(myLocations);
            ArrayList<String> fdata = new ArrayList<String>();
            fdata.add("April");
            Names fn = new Names(fdata);

            ArrayList<String> sdata = new ArrayList<String>();
            sdata.add("idk");
            Names sn = new Names(sdata);

            ArrayList<String> mdata = new ArrayList<String>();
            mdata.add("Andy");
            Names mn = new  Names(mdata);

            fs.setFNames(fn);
            fs.setMNames(mn);
            fs.setSNames(sn);
            fs.setUsername("sup");
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fillTest() throws DatabaseException{
        FillRequest fr = new FillRequest("username");
        User user = new User ("username","password","email","fn","ln","gender","personId");

        db.openConnection();
        db.getUserDao().createUser(user);
        db.closeConnection(true);

        fr.setGenerations(3);
        FillResult result = fs.fill(fr);

        String expected = "Successfully added 15 persons and " + Integer.toString(fs.getEventCount()) + " events to the database";

        assertEquals(expected, result.getMessage());
        // Check DB to see if believable people and events were generated
    }

    @Test
    public void fillTestFail() throws DatabaseException {
        FillRequest fr = new FillRequest("username");
        User user = new User("username","password","email","fn","ln","gender","personId");

        db.openConnection();
        db.getUserDao().createUser(user);
        db.closeConnection(true);

        fr.setGenerations(-1);
        FillResult result = fs.fill(fr);

        assertEquals("Generations is invalid", result.getMessage());
    }


}
