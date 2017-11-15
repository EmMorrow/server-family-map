package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by emilychandler on 11/1/17.
 */

public class EventTest {
    private Event event;

    @Before
    public void setUp() {
        event = new Event();
    }

    @Test
    public void setEventIdTest() {
        event.setEventId("123");
        assertEquals("123",event.getEventId());
    }

    @Test
    public void setDescendantTest() {
        event.setDescendant("123");
        assertEquals("123",event.getDescendant());
    }

    @Test
    public void setPersonTest() {
        event.setPerson("123");
        assertEquals("123",event.getPerson());
    }

    @Test
    public void setYearTest() {
        event.setYear("123");
        assertEquals("123",event.getYear());
    }

    @Test
    public void setLatitudeTest() {
        event.setLatitude(1.1);
        assertEquals("1.1",Double.toString(event.getLatitude()));
    }

    @Test
    public void setCountryTest() {
        event.setCountry("123");
        assertEquals("123",event.getCountry());
    }

}
