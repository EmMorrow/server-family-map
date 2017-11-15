package Dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Event;

import static org.junit.Assert.assertEquals;

/**
 * Performs SQL queries on the Event table
 */
public class EventDaoTest {
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
    public void testAddEvent() throws SQLException{
        Event e = new Event("1","1",1.1,1.1,"USA","Provo","Anevent","1997");
        db.getEventDao().createEvent(e);
    }

    @Test
    public void testGetEvent() throws SQLException {
        Event a = new Event("1","1",1.1,1.1,"1","1","1","1");
        a.setEventId("1");
        db.getEventDao().createEvent(a);

        Event b = db.getEventDao().getEvent("1");
        assertEquals(b.getEventId(),a.getEventId());
        assertEquals(b.getDescendant(),a.getDescendant());
        assertEquals(b.getPerson(),a.getPerson());
        assertEquals(b.getLatitude(),a.getLatitude());
        assertEquals(b.getLongitude(),a.getLongitude());
        assertEquals(b.getCountry(),a.getCountry());
        assertEquals(b.getCity(),a.getCity());
        assertEquals(b.getEventType(),a.getEventType());
        assertEquals(b.getYear(),a.getYear());
    }

    @Test
    public void testDeleteEvent() throws SQLException {
        Event a = new Event("1","1",1.1,1.1,"1","1","1","1");
        db.getEventDao().createEvent(a);

        db.getEventDao().deleteEvent("1");
    }
}
