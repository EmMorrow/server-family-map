package Dao;

import Model.Event;
import java.sql.*;
import java.util.ArrayList;

/**
 * Performs SQL queries on the Event table
 */
public class EventDao {
    private Connection conn;

    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new row in the event SQL table using the information provided
     * from the event object.
     * @param myEvent The event object to be added to the table
     */
    public void createEvent(Event myEvent) {
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "insert into Event values (?,?,?,?,?,?,?,?,?)";

                stmt = conn.prepareStatement(sql);
                stmt.setString(1, myEvent.getEventId());
                stmt.setString(2, myEvent.getDescendant());
                stmt.setString(3, myEvent.getPerson());
                stmt.setDouble(4, myEvent.getLatitude());
                stmt.setDouble(5, myEvent.getLongitude());
                stmt.setString(6, myEvent.getCountry());
                stmt.setString(7, myEvent.getCity());
                stmt.setString(8, myEvent.getEventType());
                stmt.setString(9, myEvent.getYear());

                if (stmt.executeUpdate() != 1) {
                    System.out.println("createEvent failed: could not insert event");
                }
            }
            finally {
                if (stmt != null) stmt.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects and returns a row from the event SQL table
     * @param eventId the ID of the event to be selected
     * @return returns the event
     */
    public Event getEvent(String eventId) {
        Event myevent = null;
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Event where event_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, eventId);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    myevent = new Event();
                    myevent.setEventId(rs.getString(1));
                    myevent.setDescendant(rs.getString(2));
                    myevent.setPerson(rs.getString(3));
                    myevent.setLatitude(rs.getDouble(4));
                    myevent.setLongitude(rs.getDouble(5));
                    myevent.setCountry(rs.getString(6));
                    myevent.setCity(rs.getString(7));
                    myevent.setEventType(rs.getString(8));
                    myevent.setYear(rs.getString(9));
                }
            }
            finally {
                if (stmt != null) stmt.close();
                if(rs != null) rs.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return myevent;
    }



    /**
     * Selects and returns all rows with the give authToken
     * @param username the username of the events to be selected
     * @return
     */
    public ArrayList<Event> getEvents(String username) {
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Event where descendant = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Event myevent = new Event();
                    myevent.setEventId(rs.getString(1));
                    myevent.setDescendant(rs.getString(2));
                    myevent.setPerson(rs.getString(3));
                    myevent.setLatitude(rs.getDouble(4));
                    myevent.setLongitude(rs.getDouble(5));
                    myevent.setCountry(rs.getString(6));
                    myevent.setCity(rs.getString(7));
                    myevent.setEventType(rs.getString(8));
                    myevent.setYear(rs.getString(9));
                    events.add(myevent);
                }
            }
            finally {
                if (stmt != null) stmt.close();
                if(rs != null) rs.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }



    /**
     * Deletes an event in the SQL table
     * @param descendant the username of the event to be deleted
     */
    public void deleteEvent(String descendant) {
        try {
            String sql = "delete from Event where descendant = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, descendant);

            if (stmt.executeUpdate() != 1) {
                System.out.println("deleteEvent failed: couldn't delete event");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
