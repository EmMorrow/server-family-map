package Service;

import java.sql.SQLException;
import java.util.ArrayList;

import Dao.Database;
import Dao.DatabaseException;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Result.PersonResult;

/**
 * Created by emilychandler on 10/13/17.
 */

public class PersonService {
    private Database db;

    public PersonService () {
        db = new Database();
    }
    /**
     * Returns the single Person object with the specified ID
     * @param personId the persons ID
     * @return Person object
     */
    public Person getPerson(String personId, String username) {
        Person myperson = null;
        try {
            db.openConnection();
            myperson = db.getPersonDao().getPerson(personId);
            db.closeConnection(true);
            if (myperson == null) {
                myperson = new Person();
                myperson.setMessage("Person does not exist");
            }
            else if (!myperson.getDescendant().equals(username)) {
                myperson = new Person();
                myperson.setMessage("Person does not belong to user");
            }
        }
        catch(DatabaseException e) {
            e.printStackTrace();
        }
        return myperson;
    }

    /**
     * Returns all family members of the current user
     * @param authToken authToken of the current user
     * @return array of persons
     */
    public PersonResult getPeople(String authToken) {
        ArrayList<Person> people = null;
        PersonResult result = new PersonResult();
        try {
            db.openConnection();
            AuthToken myauth = db.getAuthDao().getAuthToken(authToken);
            if (myauth == null) {
                result.setMessage("Invalid Authtoken");
                db.closeConnection(false);
                return result;
            }

            people = db.getPersonDao().getPeople(myauth.getUsername());

            result.setData(people);
            db.closeConnection(true);
        }
        catch(DatabaseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Database getDatabase() {
        return db;
    }
}
