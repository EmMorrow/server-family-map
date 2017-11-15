package Service;

import java.sql.SQLException;
import java.util.ArrayList;

import Dao.Database;
import Dao.DatabaseException;
import Model.AuthToken;
import Model.Event;
import Result.EventResult;

public class EventService {
    private Database db;

    public EventService () {
        db = new Database();
    }
    /**
     * Returns a single event object
     * @param eventId Id of event object to be returned
     * @return the event object
     */
    public Event getEvent(String eventId, String username) {
        Event myevent = null;
        try {
            db.openConnection();
            myevent = db.getEventDao().getEvent(eventId);
            db.closeConnection(true);
            if (myevent == null) {
                myevent = new Event();
                myevent.setMessage("Event does not exist");
            }
            else if (!myevent.getDescendant().equals(username)){
                myevent = new Event();
                myevent.setMessage("Event does not belong to user");
            }
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        return myevent;
    }

    /**
     * Returns all of the users and the users relatives event objects
     * @param authToken authToken of the user
     * @return an array of event objects
     */
    public EventResult getAllEvents(String authToken) {
        EventResult result = new EventResult();
        try {
            db.openConnection();
            AuthToken myauth = db.getAuthDao().getAuthToken(authToken);
            if (myauth == null ) {
                result.setMessage("Invalid Authtoken");
                db.closeConnection(false);
                return result;
            }

            result.setData(db.getEventDao().getEvents(myauth.getUsername()));
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            result.setMessage("Internal service error");
        }
        return result;
    }

    public Database getDatabase() {
        return db;
    }
}
