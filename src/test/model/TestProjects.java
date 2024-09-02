package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Climb;
import model.Event;
import model.EventLog;
import model.Projects;

import java.util.ArrayList;

public class TestProjects {
    private Projects testProjects;
    private Projects testProjects2;
    private Projects testProjects3;
    private Climb testBoulder;
    private Climb testTopRope;
    private Climb testLeadClimb;
    private ArrayList<Climb> validClimbs;
    private ArrayList<Climb> validClimbs2;

    @BeforeEach
    void runBefore() {
        testProjects = new Projects();
        testProjects2 = new Projects();
        testProjects3 = new Projects();
        testBoulder = new Climb("Boulder", "v0", "gym", "Boulder");
        testTopRope = new Climb("Top Rope", "4a", "gym", "Top Rope");
        testLeadClimb = new Climb("Lead Climb", "4a", "gym", "Lead Climb");
        validClimbs = new ArrayList<>();
        validClimbs2 = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(testProjects.getListOfBoulders().size(), 0);
        assertEquals(testProjects.getListOfTopRopes().size(), 0);
        assertEquals(testProjects.getListOfLeadClimbs().size(), 0);
        assertEquals(testProjects.getListOfAllClimbs().size(), 0);
    }

    @Test
    void testAddClimb() {
        testProjects.addClimb(testBoulder);
        assertEquals(testProjects.getListOfBoulders().size(), 1);

        testProjects.addClimb(testLeadClimb);
        testProjects.addClimb(testLeadClimb);
        assertEquals(testProjects.getListOfLeadClimbs().size(), 1);

        testProjects3.addClimb(testTopRope);
        assertEquals(testProjects3.getListOfTopRopes().size(), 1);

        testProjects2.addClimb(testTopRope);
        testProjects2.addClimb(testBoulder);
        testProjects2.addClimb(testLeadClimb);
        assertEquals(3, testProjects2.getListOfAllClimbs().size());
    }

    @Test
    void testRemoveClimb() {
        testProjects.addClimb(testBoulder);
        testProjects.removeClimb(testBoulder);
        assertEquals(testProjects.getListOfBoulders().size(), 0);
        
        testProjects3.addClimb(testLeadClimb);
        testProjects3.removeClimb(testLeadClimb);
        testProjects3.removeClimb(testLeadClimb);
        assertEquals(testProjects3.getListOfLeadClimbs().size(), 0);

        testProjects2.addClimb(testTopRope);
        testProjects2.addClimb(testBoulder);
        testProjects2.removeClimb(testTopRope);
        assertEquals(1, testProjects2.getListOfAllClimbs().size());
    }

    @Test
    void testGetProjectByType() {
        assertEquals(0, testProjects.getProjectsByType("Boulder").size());
        testProjects2.addClimb(testBoulder);
        assertEquals(testProjects2.getListOfBoulders(), testProjects2.getProjectsByType("Boulder"));

        testProjects.addClimb(testLeadClimb);
        assertEquals(testProjects.getListOfLeadClimbs(), testProjects.getProjectsByType("Lead Climb"));

        assertEquals(0, testProjects3.getProjectsByType("Top Rope").size());
        testProjects3.addClimb(testTopRope);
        assertEquals(testProjects3.getListOfTopRopes(), testProjects3.getProjectsByType("Top Rope"));

        assertEquals(0, testProjects.getProjectsByType("Indoor").size());

        assertEquals(0, testProjects3.getProjectsByType("Lead Climb").size());
    }

    @Test
    void testGetProjectsByLocation() {
        testProjects.addClimb(testBoulder);
        validClimbs.add(testBoulder);
        testProjects.addClimb(testLeadClimb);
        validClimbs.add(testLeadClimb);
        assertEquals(validClimbs, testProjects.getProjectsByLocation("gym"));

        assertEquals(0, testProjects.getProjectsByLocation("Gym").size());
    }

    @Test
    void testGetProjectsByDifficulty() {
        testProjects.addClimb(testBoulder);
        testProjects.addClimb(testLeadClimb);
        validClimbs.add(testLeadClimb);
        assertEquals(validClimbs, testProjects.getProjectsByDifficulty("4a"));
        validClimbs.remove(testLeadClimb);

        testProjects2.addClimb(testTopRope);
        validClimbs.add(testTopRope);
        assertEquals(validClimbs, testProjects2.getProjectsByDifficulty("4a"));

        assertEquals(0, testProjects3.getProjectsByDifficulty("v1").size());
    }

    @Test
    void testGetProjectByStatus() {
        testProjects.addClimb(testBoulder);
        validClimbs.add(testBoulder);
        testProjects.addClimb(testLeadClimb);
        validClimbs.add(testLeadClimb);
        assertEquals(validClimbs, testProjects.getProjectsByLocation("gym"));

        assertEquals(0, testProjects.getProjectsByLocation("Gym").size());
    }

    @Test
    void testGetProjectsByDifficultyAndLocation() {
        testProjects.addClimb(testBoulder);
        testProjects.addClimb(testLeadClimb);
        validClimbs.add(testLeadClimb);
        assertEquals(validClimbs, testProjects.getProjectsByDifficultyAndLocation("4a", "gym"));

        testProjects2.addClimb(testTopRope);
        validClimbs2.add(testTopRope);
        assertEquals(validClimbs2, testProjects2.getProjectsByDifficultyAndLocation("4a", "gym"));
        
        assertEquals(0, testProjects3.getProjectsByDifficultyAndLocation("v1", "indoor").size());

        testProjects3.addClimb(testBoulder);
        assertEquals(0, testProjects3.getProjectsByDifficultyAndLocation("6a", "gym").size());
        assertEquals(0, testProjects3.getProjectsByDifficultyAndLocation("v0", "indoor").size());
    }

    @Test
    void testFindClimbByName() {
        testProjects.addClimb(testBoulder);
        assertEquals(testBoulder, testProjects.findClimbByName("Boulder"));

        assertEquals(null, testProjects.findClimbByName("w"));
    }

    @Test
    void testClimbstoString() {
        ArrayList<String> strings = new ArrayList<>();
        testProjects.addClimb(testBoulder);
        strings.add("name: Boulder type: Boulder location: gym grade: v0\n");
        testProjects.addClimb(testLeadClimb);
        strings.add("name: Lead Climb type: Lead Climb location: gym grade: 4a\n");
        ArrayList<Climb> climbs = testProjects.getListOfAllClimbs();
        assertEquals(2, testProjects.climbsToStrings(climbs).size());
    }

    @Test
    void testPrintLog() {
        EventLog.getInstance().logEvent(new Event("test"));
        assertEquals("printed", testProjects.printLog());
    }
}
