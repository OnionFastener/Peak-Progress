package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonWriter extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            new Projects();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyProjects() {
        try {
            Projects pr = new Projects();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyProjects.json");
            writer.open();
            writer.write(pr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyProjects.json");
            pr = reader.read();
            assertEquals(0, pr.getListOfAllClimbs().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralProjects() {
        try {
            Projects pr = new Projects();
            pr.addClimb(new Climb("boulder", "v0", "gym", "Boulder"));
            pr.addClimb(new Climb("top", "4a", "gym", "Top Rope"));
            pr.addClimb(new Climb("lead", "4a", "gym", "Lead Climb"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralProjects.json");
            writer.open();
            writer.write(pr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralProjects.json");
            pr = reader.read();
            List<Climb> climbs = pr.getListOfAllClimbs();
            assertEquals(3, climbs.size());
            checkClimb("boulder", "v0", "gym", "Boulder", climbs.get(0));
            checkClimb("top", "4a", "gym", "Top Rope", climbs.get(1));
            checkClimb("lead", "4a", "gym", "Lead Climb", climbs.get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}