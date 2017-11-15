package Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import Dao.Database;
import Dao.DatabaseException;
import Model.AuthToken;
import Model.Event;
import Model.User;
import Result.EventResult;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 10/23/17.
 */

public class EventServiceTest {
    private Database db;
    private EventService es;

    @Before
    public void setUp() throws DatabaseException{
        es = new EventService();
        db = new Database();

        AuthToken auth = new AuthToken("username");
        Event e1 = new Event("username","1",1.1,1.1,"1","1","1","1");
        Event e2 = new Event("username","2",1.1,1.1,"2","2","2","2");
        Event e3 = new Event("2","3",1.1,1.1,"3","3","3","3");

        e1.setEventId("1");
        e2.setEventId("2");
        e3.setEventId("3");
        auth.setAuthToken("auth");

        db.openConnection();
        db.createTables();
        db.getAuthDao().createAuthToken(auth);
        db.getEventDao().createEvent(e1);
        db.getEventDao().createEvent(e2);
        db.getEventDao().createEvent(e3);
        db.closeConnection(true);
    }

    @Test
    public void testGetEvent() {
        Event myevent = es.getEvent("1", "username");

        assertEquals(myevent.getEventId(),"1");
        assertEquals(myevent.getDescendant(),"username");
        assertEquals(myevent.getPerson(),"1");
        assertEquals(Double.toString(myevent.getLatitude()),"1.1");
        assertEquals(Double.toString(myevent.getLongitude()),"1.1");
        assertEquals(myevent.getCountry(),"1");
        assertEquals(myevent.getCity(),"1");
        assertEquals(myevent.getEventType(),"1");
        assertEquals(myevent.getYear(),"1");
    }

    @Test
    public void testGetEventFail() {
        Event result = es.getEvent("5","1");
        assertEquals(result.getMessage(), "Event does not exist");
    }

    @Test
    public void testGetEvents() throws SQLException {
        Event myevent;
        EventResult events = es.getAllEvents("auth");
        ArrayList<Event> myevents = events.getData();
        myevent = myevents.get(0);
        assertEquals(myevent.getEventId(),"1");
        assertEquals(myevent.getDescendant(),"username");
        assertEquals(myevent.getPerson(),"1");
        assertEquals(Double.toString(myevent.getLatitude()),"1.1");
        assertEquals(Double.toString(myevent.getLongitude()),"1.1");
        assertEquals(myevent.getCountry(),"1");
        assertEquals(myevent.getCity(),"1");
        assertEquals(myevent.getEventType(),"1");
        assertEquals(myevent.getYear(),"1");

        myevent = myevents.get(1);
        assertEquals(myevent.getEventId(),"2");
        assertEquals(myevent.getDescendant(),"username");
        assertEquals(myevent.getPerson(),"2");
        assertEquals(Double.toString(myevent.getLatitude()),"1.1");
        assertEquals(Double.toString(myevent.getLongitude()),"1.1");
        assertEquals(myevent.getCountry(),"2");
        assertEquals(myevent.getCity(),"2");
        assertEquals(myevent.getEventType(),"2");
        assertEquals(myevent.getYear(),"2");
    }

    @Test
    public void testGetEventsFail() {
        EventResult result = es.getAllEvents("InvalidAuth");
        assertEquals("Invalid Authtoken", result.getMessage());
    }
}
