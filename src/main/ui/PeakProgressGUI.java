package ui;

import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import model.Climb;
import model.Projects;
import persistence.JsonReader;
import persistence.JsonWriter;

// App where a user can add, edit, and see climbing projects with a GUI
public class PeakProgressGUI {
    private String logo = "data/PeakProgressLogo.png";
    private String title = "Peak Progress";
    private Projects projects;

    private JFrame frame = new JFrame();
    private int width;
    private int height;

    private static final String JSON_STORE = "./data/projects.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    
    // creates a gui instance for Peak Progress
    public PeakProgressGUI() throws IOException {
        projects = new Projects();

        this.width = 800;
        this.height = 600;

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        init();
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                exit();
            }
        });
    }

    // MODIFIES:
    // EFFECTS: prints event log to console and then quits the application
    //          when closing the application window
    public void exit() {
        projects.printLog();
        frame.dispose();
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: intializes app with logo
    public void init() {

        frame.setTitle(title);
        frame.setSize(width, height);

        createLogo();
    }

    // MODIFIES: this
    // EFFECTS: creates a new panel that displays the logo for PeakProgress
    //          then directs to home page with menu
    public void createLogo() {
        JPanel panel = new JPanel();
        JButton startButton = new JButton("Start!");
        ImageIcon icon = new ImageIcon(logo);
        JLabel label = new JLabel();
        label.setIcon(icon);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                createMenu();
                createHomePanel();
                frame.setVisible(true);
            }
        });
        panel.add(startButton);
        panel.add(label);
        frame.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: changes the visible panel from current to nextPanel
    private void changePanel(JPanel current, JPanel nextPanel) {
        frame.remove(current);
        frame.add(nextPanel);
        frame.setVisible(true);
    }

    //MODIFIES: this
    // EFFECTS: creates a home page
    private void createHomePanel() {
        JPanel homePanel = new JPanel();
        addHomeButtons(homePanel);

        frame.add(homePanel);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to the home page
    private void addHomeButtons(JPanel panel) {
        JButton addClimbButton = new JButton("Add new climb");
        addClimbButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addClimb(panel);
            }
        });

        JButton removeClimbButton = new JButton("Remove a climb");
        removeClimbButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeClimb(panel);
            }
        });
        JButton viewClimbButton = new JButton("View climbs");
        viewClimbButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewClimbOptions(panel);
            }
        });

        panel.add(addClimbButton);
        panel.add(removeClimbButton);
        panel.add(viewClimbButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a drop down menu with save, load, add climb, and view climb options
    private void createMenu() {
        JPanel emptyPanel = new JPanel();
        JMenuBar menuBar = new JMenuBar();

        JButton homeButton = new JButton("Home");
        loadHomePageFromMenu(homeButton);

        JMenu fileMenu = new JMenu("File");
        JMenuItem loadItem = new JMenuItem("Load");
        loadClimbsFromMenu(loadItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveClimbsFromMenu(saveItem);

        JMenu climbMenu = new JMenu("Climbs");
        JMenuItem addClimb = new JMenuItem("Add Climb");
        addClimbFromMenu(addClimb, emptyPanel);
        
        JMenuItem viewClimbs = new JMenuItem("View Climbs");
        viewClimbsFromMenu(viewClimbs, emptyPanel);

        menuBar.add(homeButton);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);

        climbMenu.add(addClimb);
        climbMenu.add(viewClimbs);
        menuBar.add(climbMenu);

        frame.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: adds the ability to go to the home page at any time
    private void loadHomePageFromMenu(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                createHomePanel();
            }
        });
    }

    //MODIFIES: this
    // EFFECTS: adds the load climbs functionality to the menu button
    //          after loading, successfully or not, option to go to home page presented
    private void loadClimbsFromMenu(JMenuItem loadItem) {
        JPanel panel = new JPanel();
        JButton continueLoad = new JButton("continue");
        panel.add(continueLoad);
        doneViewing(panel, continueLoad);
        loadItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                try {
                    projects = jsonReader.read();
                    frame.add(successLoadMessage(continueLoad));
                } catch (IOException ex) {
                    JTextField exceptionMessage = new JTextField("File cannot be read");
                    panel.add(exceptionMessage);
                    frame.add(panel);
                }
                frame.setVisible(true); 
            }
        });
    }

    //EFFECTS: creates a panel with a successful load message
    private JPanel successLoadMessage(JButton button) {
        frame.getContentPane().removeAll();
        JPanel panel = new JPanel();
        JTextField success = new JTextField("Climbs have been loaded");
        panel.add(success);
        panel.add(button);
        doneViewing(panel, button);
        return panel;
    }

    //MODIFIES: this
    // EFFECTS: adds the save climbs functionality to the menu button
    //          after saving, successfully or not, option to go to home page presented
    private void saveClimbsFromMenu(JMenuItem saveItem) {
        JPanel panel = new JPanel();
        JButton continueSave = new JButton("continue");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                try {
                    jsonWriter.open();
                    jsonWriter.write(projects);
                    jsonWriter.close();
                    frame.add(successSaveMessage(continueSave));
                } catch (FileNotFoundException ex) {
                    JTextField exceptionMessage = new JTextField("File cannot be read");
                    panel.add(exceptionMessage);
                    frame.add(panel);                    
                }
                frame.setVisible(true); 
            }
        });
    }

    //EFFECTS: creates a panel with a successful save message
    private JPanel successSaveMessage(JButton button) {
        frame.getContentPane().removeAll();
        JPanel panel = new JPanel();
        JTextField success = new JTextField("Climbs have been saved");
        panel.add(success);
        panel.add(button);
        doneViewing(panel, button);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: adds the addClimb functionality to the menu button
    private void addClimbFromMenu(JMenuItem addClimb, JPanel panel) {
        addClimb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                addClimb(panel);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds the viewClimbs functionality to the menu button
    private void viewClimbsFromMenu(JMenuItem viewClimbs, JPanel panel) {
        viewClimbs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                viewClimbOptions(panel);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a new panel where a user can input a new climb
    private void addClimb(JPanel panel) {
        frame.getContentPane().removeAll();
        JPanel addClimbsPanel = new JPanel();
        JTextField nameField = new JTextField("Name your climb");
        JTextField gradeField = new JTextField("Grade of climb");
        JTextField locationField = new JTextField("Location of climb");
        String[] choices = {"Boulder", "Top Rope", "Lead Climb"};
        final JComboBox<String> cb = new JComboBox<String>(choices);
        JButton save = new JButton("Save");

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String grade = gradeField.getText();
                String location = locationField.getText();
                String type = cb.getSelectedItem().toString();
                saveNewClimb(name, grade, location, type, addClimbsPanel);
            }
        });

        addClimbsPanel.add(nameField);
        addClimbsPanel.add(gradeField);
        addClimbsPanel.add(locationField);
        addClimbsPanel.add(cb);
        addClimbsPanel.add(save);

        changePanel(panel, addClimbsPanel);
    }

    // MODIFIES: this
    // EFFECTS: saves the new added climb, adding it to the projects list, and returns the user to home page
    private void saveNewClimb(String name, String grade, String location, String type, JPanel panel) {
        Climb newClimb = new Climb(name, grade, location, type);
        this.projects.addClimb(newClimb);
        frame.remove(panel);
        createHomePanel();
    }

    // MODIFIES: this
    // EFFECTS: creates a new panel where a user can remove a climb by name
    private void removeClimb(JPanel panel) {
        frame.getContentPane().removeAll();
        JPanel removePanel = new JPanel();
        JTextField nameField = new JTextField("Name of climb");
        JButton next = new JButton("Remove");

        removePanel.add(nameField);
        removePanel.add(next);

        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                Climb removedClimb = projects.findClimbByName(name);
                projects.removeClimb(removedClimb);
                frame.remove(removePanel);
                createHomePanel();
            }
        });

        changePanel(panel, removePanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel where a user can see different viewing options for climbs
    private void viewClimbOptions(JPanel panel) {
        frame.getContentPane().removeAll();
        JPanel viewClimbsPanel = new JPanel();
        viewAllClimbs(viewClimbsPanel);
        viewByType(viewClimbsPanel);
        viewByLocation(viewClimbsPanel);
        viewByGrade(viewClimbsPanel);
        viewByGradeAndLocation(viewClimbsPanel);
        
        changePanel(panel, viewClimbsPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel where all climbs in projects are listed
    private void viewAllClimbs(JPanel panel) {
        JButton button1 = new JButton("View all climbs");
        panel.add(button1);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JPanel allClimbsPanel = new JPanel();
                JButton done = new JButton("Done");
                doneViewing(allClimbsPanel, done);

                ArrayList<Climb> climbs = projects.getListOfAllClimbs();
                ArrayList<String> climbStrings = projects.climbsToStrings(climbs);
                String output = String.join(", ", climbStrings);
                JTextArea text = new JTextArea(output, 20, 60);

                allClimbsPanel.add(text);
                allClimbsPanel.add(done);
                frame.setVisible(true);

                changePanel(panel, allClimbsPanel);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a panel where user can sort climbs by type
    private void viewByType(JPanel panel) {
        JButton button1 = new JButton("View by type");
        panel.add(button1);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel typeClimbsPanel = new JPanel();
                String[] choices = {"Boulder", "Top Rope", "Lead Climb"};
                final JComboBox<String> cb = new JComboBox<String>(choices);
                JButton next = new JButton("next");

                typeClimbsPanel.add(cb);
                typeClimbsPanel.add(next);

                changePanel(panel, typeClimbsPanel);

                typeChoice(next, typeClimbsPanel, cb);
                
                frame.setVisible(true);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: changes the panel to display climbs of the selected type
    public void typeChoice(JButton next, JPanel typeClimbsPanel, JComboBox<String> cb) {
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String type = cb.getSelectedItem().toString();
                ArrayList<Climb> climbs = projects.getProjectsByType(type);
                ArrayList<String> climbStrings = projects.climbsToStrings(climbs);
                String output = String.join(", ", climbStrings);
                JTextArea text = new JTextArea(output,20, 60);

                JButton done = new JButton("Done");
                doneViewing(typeClimbsPanel, done);

                typeClimbsPanel.add(text);
                typeClimbsPanel.add(done);
                typeClimbsPanel.remove(cb);
                typeClimbsPanel.remove(next);

                frame.setVisible(true);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a new panel where a user can sort climbs by location
    private void viewByLocation(JPanel panel) {
        JButton button1 = new JButton("View by location");
        panel.add(button1);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel locationClimbsPanel = new JPanel();
                JTextField locationField = new JTextField("Enter a location");
                JButton next = new JButton("next");

                locationClimbsPanel.add(locationField);
                locationClimbsPanel.add(next);

                changePanel(panel, locationClimbsPanel);

                locationChoice(next, locationClimbsPanel, locationField);
                
                frame.setVisible(true);            
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: changes the panel to display climbs sorted by location
    private void locationChoice(JButton next, JPanel panel, JTextField locationField) {
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String location = locationField.getText();
                ArrayList<Climb> climbs = projects.getProjectsByLocation(location);
                ArrayList<String> climbStrings = projects.climbsToStrings(climbs);
                String output = String.join(", ", climbStrings);
                JTextArea text = new JTextArea(output,20, 60);

                JButton done = new JButton("Done");
                doneViewing(panel, done);

                panel.removeAll();
                panel.add(text);
                panel.add(done);

                frame.setVisible(true);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a new panel where a user can sort climbs by grade
    private void viewByGrade(JPanel panel) {
        JButton button1 = new JButton("View by grade");
        panel.add(button1);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel gradeClimbsPanel = new JPanel();
                JTextField gradeField = new JTextField("Enter a grade");
                JButton next = new JButton("next");

                gradeClimbsPanel.add(gradeField);
                gradeClimbsPanel.add(next);

                changePanel(panel, gradeClimbsPanel);

                gradeChoice(next, gradeClimbsPanel, gradeField);
                
                frame.setVisible(true);            
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: changes the panel to display climbs sorted by grade
    private void gradeChoice(JButton next, JPanel panel, JTextField gradeField) {
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String grade = gradeField.getText();
                ArrayList<Climb> climbs = projects.getProjectsByDifficulty(grade);
                ArrayList<String> climbStrings = projects.climbsToStrings(climbs);
                String output = String.join(", ", climbStrings);
                JTextArea text = new JTextArea(output,20, 60);

                JButton done = new JButton("Done");
                doneViewing(panel, done);

                panel.removeAll();
                panel.add(text);
                panel.add(done);

                frame.setVisible(true);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a new panel where a user can sort climbs by grade and location
    private void viewByGradeAndLocation(JPanel panel) {
        JButton button1 = new JButton("View by grade and location");
        panel.add(button1);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel gradeAndLocationPanel = new JPanel();
                JTextField gradeField = new JTextField("Enter a grade");
                JTextField locationField = new JTextField("Enter a location");
                JButton next = new JButton("next");

                gradeAndLocationPanel.add(gradeField);
                gradeAndLocationPanel.add(locationField);
                gradeAndLocationPanel.add(next);

                changePanel(panel, gradeAndLocationPanel);

                gradeAndLocationChoice(next, gradeAndLocationPanel, gradeField, locationField);
                
                frame.setVisible(true);            
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: changes the panel to display climbs sorted by grade and location
    private void gradeAndLocationChoice(JButton next, JPanel panel, JTextField gradeField, JTextField locationField) {
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String grade = gradeField.getText();
                String location = locationField.getText();
                ArrayList<Climb> climbs = projects.getProjectsByDifficultyAndLocation(grade, location);
                ArrayList<String> climbStrings = projects.climbsToStrings(climbs);
                String output = String.join(", ", climbStrings);
                JTextArea text = new JTextArea(output,20, 60);

                JButton done = new JButton("Done");
                doneViewing(panel, done);

                panel.removeAll();
                panel.add(text);
                panel.add(done);

                frame.setVisible(true);
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: changes the panel from the current viewing page 
    //          to the home page so the user can exit the viewing page
    private void doneViewing(JPanel panel, JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel);
                createHomePanel();
                frame.setVisible(true);
            }
        });
    }

}
