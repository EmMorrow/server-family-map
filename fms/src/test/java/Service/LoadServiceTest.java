package Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import Dao.Database;
import Dao.DatabaseException;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;

/**
 * Created by emilychandler on 10/27/17.
 */

public class LoadServiceTest {
    private Database db;
    private LoadRequest request;
    private LoadService service;

    @Before
    public void setUp() throws DatabaseException{
        db = new Database();
        db.openConnection();
        db.createTables();
        db.closeConnection(true);
        request = new LoadRequest();
        service = new LoadService();

        Event e1 = new Event("1","1",1.1,1.1,"1","1","1","1");
        Event e2 = new Event("2","2",2.2,2.2,"2","2","2","2");

        Person p1 = new Person("1","1","1","1","1","1","1");
        Person p2 = new Person("2","2","2","2","2","2","2");

        User u1 = new User("1","1","1","1","1","1","1");
        User u2 = new User("2","2","2","2","2","2","2");

        ArrayList<Event> events = new ArrayList<Event>();
        ArrayList<Person> persons = new ArrayList<Person>();
        ArrayList<User> users = new ArrayList<User>();

        events.add(e1);
        events.add(e2);

        persons.add(p1);
        persons.add(p2);

        users.add(u1);
        users.add(u2);

        request.setEvents(events);
        request.setPersons(persons);
        request.setUsers(users);
    }

    @Test
    public void loadDataTest() {
        service.load(request);
    }
}
