package Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import Dao.*;
import Model.AuthToken;
import Model.Person;
import Result.PersonResult;

import static org.junit.Assert.assertEquals;

public class PersonServiceTest {
    private Database db;
    private PersonService ps;

    @Before
    public void setUp() throws DatabaseException {
        ps = new PersonService();
        db = new Database();
        db.openConnection();
        db.createTables();

        Person p1 = new Person("username","1","1","1","1","1","1");
        Person p2 = new Person("username","2","2","2","2","2","2");
        Person p3 = new Person("2","3","3","3","3","3","3");
        AuthToken auth = new AuthToken("username");

        p1.setPersonId("1");
        p2.setPersonId("2");
        p3.setPersonId("3");
        auth.setAuthToken("auth");

        db.getAuthDao().createAuthToken(auth);
        db.getPersonDao().createPerson(p1);
        db.getPersonDao().createPerson(p2);
        db.getPersonDao().createPerson(p3);
        db.closeConnection(true);
    }

    @Test
    public void testGetPerson() throws DatabaseException {
        Person myperson = ps.getPerson("1", "username");
        assertEquals("1",myperson.getPersonId());
        assertEquals("username",myperson.getDescendant());
        assertEquals("1",myperson.getFirstName());
        assertEquals("1",myperson.getLastName());
        assertEquals("1",myperson.getGender());
        assertEquals("1",myperson.getFather());
        assertEquals("1",myperson.getMother());
        assertEquals("1",myperson.getSpouse());
    }

    @Test
    public void testGetPersonInvalid() {
        Person myperson = ps.getPerson("1","invalid");
        assertEquals("Person does not belong to user", myperson.getMessage());
    }

    @Test
    public void testGetPersons() throws DatabaseException {
        PersonResult result = ps.getPeople("auth");
        Person myperson = result.getData().get(0);
        assertEquals("1",myperson.getPersonId());
        assertEquals("username",myperson.getDescendant());
        assertEquals("1",myperson.getFirstName());
        assertEquals("1",myperson.getLastName());
        assertEquals("1",myperson.getGender());
        assertEquals("1",myperson.getFather());
        assertEquals("1",myperson.getMother());
        assertEquals("1",myperson.getSpouse());

        myperson = result.getData().get(1);
        assertEquals("2",myperson.getPersonId());
        assertEquals("username",myperson.getDescendant());
        assertEquals("2",myperson.getFirstName());
        assertEquals("2",myperson.getLastName());
        assertEquals("2",myperson.getGender());
        assertEquals("2",myperson.getFather());
        assertEquals("2",myperson.getMother());
        assertEquals("2",myperson.getSpouse());
    }
}
