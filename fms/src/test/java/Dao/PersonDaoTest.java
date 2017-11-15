package Dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import Model.Person;

import static org.junit.Assert.assertEquals;

/**
 * Performs SQL queries on the Person table
 */
public class PersonDaoTest {
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
    public void testCreatePerson() throws SQLException {
        Person p = new Person("1","1","1","1","1","1","1");
        p.generatePersonId();
        db.getPersonDao().createPerson(p);

        Person np = new Person();
        np.generatePersonId();
        np.setDescendant("1");
        np.setLastName("1");
        np.setGender("1");
        np.setFirstName("1");
        db.getPersonDao().createPerson(np);
    }

    @Test
    public void testGetPerson() throws SQLException {
        Person a = new Person("1","1","1","1","1","1","1");
        a.setPersonId("1");
        db.getPersonDao().createPerson(a);

        Person b = db.getPersonDao().getPerson("1");
        assertEquals(b.getPersonId(),a.getPersonId());
        assertEquals(b.getDescendant(),a.getDescendant());
        assertEquals(b.getFirstName(),a.getFirstName());
        assertEquals(b.getLastName(),a.getLastName());
        assertEquals(b.getGender(),a.getGender());
        assertEquals(b.getFather(),a.getFather());
        assertEquals(b.getMother(),a.getMother());
        assertEquals(b.getSpouse(),a.getSpouse());
    }

    @Test
    public void testDeletePerson() throws SQLException {
        Person a = new Person("1","1","1","1","1","1","1");
        db.getPersonDao().createPerson(a);

        db.getPersonDao().deletePerson("1");
    }
}
