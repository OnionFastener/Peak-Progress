package ui;

import model.*;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// App where a user can add, edit, and see climbing projects
public class PeakProgressApp {
    private Projects projects;
    
    private static final String JSON_STORE = "./data/projects.json";
    private Scanner scanner;
    private boolean isRunning;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: create an instance of the apps console ui
    public PeakProgressApp() throws FileNotFoundException {
        init();

        while (isRunning) {
            handleMenu();
        }
    }
    
    // MODIFIES: this
    // EFFECTS: intializes the app with starting values
    private void init() {
        projects = new Projects();
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        isRunning = true;
    }

    // EFFECTS: display and process user inputs for main menu
    private void handleMenu() {
        displayMenu();
        String input = this.scanner.nextLine();
        processMenuInput(input);
    }

    // EFFECTS: display a list of commands in the main menu
    private void displayMenu() {
        System.out.println("Please select an option: \n");
        System.out.println("l: Load climbs from file");
        System.out.println("a: Add a new climb");
        System.out.println("v: See viewing options");
        System.out.println("r: Remove a climb");
        System.out.println("q: Exit the app");
    }

    // EFFECTS: processes user inputs on main menu
    private void processMenuInput(String input) {
        switch (input) {
            case "l":
                loadProjects();
                break;
            case "a":
                addNewClimb();
                break;
            case "v":
                viewingOptions();
                break;
            case "r":
                removeClimb();
                break;
            case "q":
                quitApp();
                break;
            default:
                System.out.println("Invalid option. Try again");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new climb to projects
    private void addNewClimb() {
        System.out.println("Please enter the name of the climb");
        String name = this.scanner.nextLine();

        System.out.println("Please enter the climbing grade");
        String difficulty = this.scanner.nextLine();

        System.out.println("Please enter the climb's location");
        String location = this.scanner.nextLine();

        System.out.println("Please enter the climb's type");
        String type = this.scanner.nextLine();

        Climb newClimb = new Climb(name, difficulty, location, type);

        projects.addClimb(newClimb);
        System.out.println("Climb has been added");
    }

    // EFFECTS: display and process inputs for viewing options
    private void viewingOptions() {
        displayViewingOptions();
        String input = this.scanner.nextLine();
        processViewInput(input);

    }

    // EFFECTS: display list of commands that can be used in viewing options menu
    private void displayViewingOptions() {
        System.out.println("Please select a viewing option");
        System.out.println("a: View all climbs");
        System.out.println("t: View climbs of a certain type");
        System.out.println("l: View climbs at a certain location");
        System.out.println("d: View climbs of a certain grade");
        System.out.println("d/l: View climbs of a certain grade at a certain location");
        System.out.println("q: Exit the application");

    }

    // EFFECTS: process user input for viewing options
    private void processViewInput(String input) {
        switch (input) {
            case "a":
                viewAllClimbs();
                break;
            case "t":
                viewByType();
                break;
            case "l":
                viewByLocation();
                break;
            case "d":
                viewByDifficulty();
                break;
            case "d/l":
                viewByDifficultyAndLocation();
                break;
            case "q":
                quitApp();
                break;
            default:
                System.out.println("Invalid input. Try again");
        }
    }
    
    // EFFECTS: display all climbs in projects
    private void viewAllClimbs() {
        ArrayList<Climb> climbs = projects.getListOfAllClimbs();
        System.out.println(projects.climbsToStrings(climbs));
    }

    // REQUIRES: input type to be "Boulder", "Top Rope" or "Lead Climb"
    //              and that at least one climb of that type exists
    // EFFECTS: display all climbs of a certain type
    private void viewByType() {
        System.out.println("Please enter a type of climb");
        String type = this.scanner.nextLine();

        System.out.println("Here are your projects of that type");
        ArrayList<Climb> climbs = projects.getProjectsByType(type);
        System.out.println(projects.climbsToStrings(climbs));
    }

    // REQUIRES: input location to have at least one climb with that location
    // EFFECTS: display all climbs from a certain location
    private void viewByLocation() {
        System.out.println("Please enter a location");
        String location = this.scanner.nextLine();

        System.out.println("Here are your projects at that location");
        ArrayList<Climb> climbs = projects.getProjectsByLocation(location);
        System.out.println(projects.climbsToStrings(climbs));
    }

    // REQUIRES: input difficulty to have at least one climb with that grade
    // EFFECTS: display all climbs of a certain difficulty grade
    private void viewByDifficulty() {
        System.out.println("Please enter a climbing grade");
        String grade = this.scanner.nextLine();

        System.out.println("Here are your projects of that grade");
        ArrayList<Climb> climbs = projects.getProjectsByDifficulty(grade);
        System.out.println(projects.climbsToStrings(climbs));
    }

    // REQUIRES: have at least one climb with the specified location and difficulty
    // EFFECTS: display all climbs of a certain difficulty and from a certain location
    private void viewByDifficultyAndLocation() {
        System.out.println("Please enter a climbing grade");
        String grade = this.scanner.nextLine();

        System.out.println("Please enter a location");
        String location = this.scanner.nextLine();

        System.out.println("Here are your projects of that grade in that location");
        ArrayList<Climb> climbs = projects.getProjectsByDifficultyAndLocation(grade, location);
        System.out.println(projects.climbsToStrings(climbs));
    }

    // MODIFIES: this
    // EFFECTS: removes a climb by name
    private void removeClimb() {
        System.out.println("Please enter the name of the climb to remove");
        String name = this.scanner.nextLine();

        Climb climb = projects.findClimbByName(name);
        projects.removeClimb(climb);
        System.out.println("Climb has been removed");
    }

    // MODIFIES: this
    // EFFECTS: prints a closing message and ends the application
    private void quitApp() {
        System.out.println("Thanks for using Peak Progress");
        System.out.println("Would you like to save your projects? (y/n)");
        String input = this.scanner.nextLine();
        processSaveInput(input);

        System.out.println("See you again");
        isRunning = false;
    }

    // EFFECTS: process user input for saving menu options
    private void processSaveInput(String input) {
        switch (input) {
            case "y":
                saveProjects();
                break;
            case "n":
                break;
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveProjects() {
        try {
            jsonWriter.open();
            jsonWriter.write(projects);
            jsonWriter.close();
            System.out.println("Saved climbs to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadProjects() {
        try {
            projects = jsonReader.read();
            System.out.println("Loaded climbs from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
