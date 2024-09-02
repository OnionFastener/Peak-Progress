package ui;

import java.io.FileNotFoundException;

// creates a new main instance for the application
public class Main {

    // EFFECTS: starts a new Peak Progress instance with both console and gui application
    public static void main(String[] args) throws Exception {
        new PeakProgressGUI();
        try {
            new PeakProgressApp();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot run application, file not found");
        }
    }

}

