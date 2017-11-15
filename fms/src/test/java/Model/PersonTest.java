package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 11/1/17.
 */

public class PersonTest {
    private Person person;

    @Before
    public void setUp() {
        person = new Person();
    }

    @Test
    public void setPersonIdTest() {
        person.setPersonId("123");
        assertEquals("123",person.getPersonId());
    }

    @Test
    public void setDescendantTest() {
        person.setDescendant("123");
        assertEquals("123",person.getDescendant());
    }

    @Test
    public void setFirstNameTest() {
        person.setFirstName("123");
        assertEquals("123",person.getFirstName());
    }

    @Test
    public void setLastNameTest() {
        person.setLastName("123");
        assertEquals("123",person.getLastName());
    }

    @Test
    public void setFatherTest() {
        person.setFather("123");
        assertEquals("123",person.getFather());
    }

    @Test
    public void setMotherTest() {
        person.setMother("123");
        assertEquals("123",person.getMother());
    }

    @Test
    public void setSpouseTest() {
        person.setSpouse("123");
        assertEquals("123",person.getSpouse());
    }
}
