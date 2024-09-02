package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Climb;

public class TestClimb {
    private Climb testBoulder;
    private Climb testTopRope;
    private Climb testLeadClimb;
    
    @BeforeEach
    void runBefore() {
        testBoulder = new Climb("boulder", "v0", "gym", "Boulder");
        testTopRope = new Climb("top rope", "4a", "gym", "Top Rope");
        testLeadClimb = new Climb("lead climb", "4a", "gym", "Lead Climb");
    }

    @Test
    void testConstructor() {
        assertEquals("boulder", testBoulder.getName());
        assertEquals("v0", testBoulder.getDifficulty());
        assertEquals("gym", testBoulder.getLocation());
        assertEquals("Boulder", testBoulder.getType());
        assertEquals("Top Rope", testTopRope.getType());
        assertEquals("Lead Climb", testLeadClimb.getType());
    }

    @Test
    void testSetName() {
        testBoulder.setName("Burden");
        assertEquals("Burden", testBoulder.getName());

        testTopRope.setName("of");
        assertEquals("of", testTopRope.getName());

        testLeadClimb.setName("Dreams");
        testLeadClimb.setName("Dreams");
        assertEquals("Dreams", testLeadClimb.getName());
    }
    
    @Test
    void testSetDifficulty() {
        testBoulder.setDifficulty("v10");
        assertEquals("v10", testBoulder.getDifficulty());

        testTopRope.setDifficulty("6a");
        testTopRope.setDifficulty("6a");
        assertEquals("6a", testTopRope.getDifficulty());

        testLeadClimb.setDifficulty("6a");
        testLeadClimb.setDifficulty("3a");
        assertEquals("3a", testLeadClimb.getDifficulty());
    }

    @Test
    void testSetLocation() {
        testLeadClimb.setLocation("Yosemite");
        assertEquals("Yosemite", testLeadClimb.getLocation());

        testTopRope.setLocation("B-Pump");
        testTopRope.setLocation("B-Pump");
        assertEquals("B-Pump", testTopRope.getLocation());

        testBoulder.setLocation("CCC");
        testBoulder.setLocation("Hive");
        assertEquals("Hive", testBoulder.getLocation());
    }
}
