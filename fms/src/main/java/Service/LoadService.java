package Service;

import java.sql.SQLException;
import java.util.ArrayList;

import Dao.Database;
import Dao.DatabaseException;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

/**
 * THis is hard come back to it
 */

public class LoadService {
    private Database db;

    public LoadService() {
        db = new Database();
    }

    /**
     * Load new data into the database
     * @param l the data to be loaded into the database
     */
    public LoadResult load(LoadRequest l) {
        String message;
        try {
            db.openConnection();
            db.createTables();
            ArrayList<User> users = l.getUsers();
            ArrayList<Person> people = l.getPersons();
            ArrayList<Event> events = l.getEvents();

            for (int i = 0; i < users.size(); i++) {
                User myUser = users.get(i);
                db.getUserDao().createUser(myUser);
            }

            for (int i = 0; i < people.size(); i++) {
                Person myPerson = people.get(i);
                db.getPersonDao().createPerson(myPerson);
            }

            for (int i = 0; i < events.size(); i++) {
                Event myEvent = events.get(i);
                db.getEventDao().createEvent(myEvent);
            }
            db.closeConnection(true);
            message = "Successfully added " + users.size() + " users, " + people.size() + " persons, and " + events.size() + " Events to the database.";
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            message = "Internal server error";
        }
        return new LoadResult(message);
    }
}
