package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkClimb(String name, String difficulty, String location, String type, Climb climb) {
        assertEquals(name, climb.getName());
        assertEquals(difficulty, climb.getDifficulty());
        assertEquals(location, climb.getLocation());
        assertEquals(type, climb.getType());
    }
}