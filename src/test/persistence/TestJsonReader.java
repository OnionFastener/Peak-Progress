package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonReader extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyProjects() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProjects.json");
        try {
            Projects pr = reader.read();
            assertEquals(0, pr.getListOfAllClimbs().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralProjects.json");
        try {
            Projects pr = reader.read();
            List<Climb> climbs = pr.getListOfAllClimbs();
            assertEquals(3, climbs.size());
            checkClimb("boulder", "v0", "gym", "Boulder", climbs.get(0));
            checkClimb("top", "4a", "gym", "Top Rope", climbs.get(1));
            checkClimb("lead", "4a", "gym", "Lead Climb", climbs.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
