package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event event;
    private Date date;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
    @BeforeEach
	public void runBefore() {
        event = new Event("Sensor open at door");   // (1)
        date = Calendar.getInstance().getTime();   // (2)
    }
	
    @Test
	public void testEvent() {
        assertEquals("Sensor open at door", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @Test
	public void testToString() {
        assertEquals(date.toString() + "\n" + "Sensor open at door", event.toString());
    }

    @Test
    public void testEquals() {
        Event newEvent = new Event("Sensor open at door");
        assertTrue(event.equals(newEvent));

        Event wrongEvent = new Event("not a sensor");
        assertFalse(event.equals(wrongEvent));

        assertFalse(event.equals(10));
        assertFalse(event.equals(null));
    }
}
