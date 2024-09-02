package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class Projects implements Writable {
    ArrayList<Climb> listOfBoulders = new ArrayList<>();
    ArrayList<Climb> listOfTopRopes = new ArrayList<>();
    ArrayList<Climb> listOfLeadClimbs = new ArrayList<>();
    ArrayList<Climb> listOfAllClimbs = new ArrayList<>();

    public Projects() {

    }
    /*
     * MODIFIES: this
     * REQUIRES: climb to be a valid climb with type of either
     *           Boulder, Top Rope, or Lead Climb
     * EFFECTS: add climb to list of climbs with the same type,
     *          if the climb already exists in any list, do nothing
     */

    public void addClimb(Climb climb) {

        if (!listOfAllClimbs.contains(climb)) {
            listOfAllClimbs.add(climb);
            if (climb.getType().equals("Boulder")) {
                listOfBoulders.add(climb);
            } else if (climb.getType().equals("Top Rope")) {
                listOfTopRopes.add(climb);
            } else {
                listOfLeadClimbs.add(climb);
            }
        }

        EventLog.getInstance().logEvent(new Event("Added climb " + climb.climbToString()));
    }

    /*
     * REQUIRES: climb with specific name to exist
     * EFFECTS: find a climb with a specific name
     *          return null if climb does not exist
     */
    public Climb findClimbByName(String name) {
        Climb result = null;
        for (Climb c : listOfAllClimbs) {
            if (name.equals(c.getName())) {
                result = c;
            }
        }
        return result;
    }

    /*
     * MODIFIES: this
     * REQUIRES: Climb to be a valid climb
     * EFFECTS: removes the climb from the list of projects and logs and event
     *          if the list is already empty, do nothing and log an unsuccessful removale event
     *          if the climb isn't found, also do nothing and log an unsuccessful removale event
     */
    public void removeClimb(Climb climb) {
        if (listOfAllClimbs.contains(climb)) {
            EventLog.getInstance().logEvent(new Event("Removed climb " + climb.climbToString()));

            listOfAllClimbs.remove(climb);
            if (climb.getType().equals("Boulder")) {
                listOfBoulders.remove(climb);
            } else if (climb.getType().equals("Top Rope")) {
                listOfTopRopes.remove(climb);
            } else {
                listOfLeadClimbs.remove(climb);
            }
        } else {
            EventLog.getInstance().logEvent(new Event("Unsuccessful in removing a climb"));
        }

    }

    // REQUIRES: climbs to be a non-empty list
    // EFFECTS: returns a list of climbs as a list of strings that describe those climbs
    public ArrayList<String> climbsToStrings(ArrayList<Climb> climbs) {
        ArrayList<String> result = new ArrayList<>();
        for (Climb c : climbs) {
            result.add(c.climbToString());
        }
        return result;
    }

    /*
     * REQUIRES: type to be "Boulder", "Top Rope", or "Lead Climb"
     * EFFECTS: return a list of the given type
     *          if there is no climbs of the given type, return an empty list
     */
    public ArrayList<Climb> getProjectsByType(String type) {
        ArrayList<Climb> result = new ArrayList<>();
        if (type.equals("Boulder")) {
            result = listOfBoulders;

        } else if (type.equals("Top Rope")) {
            result = listOfTopRopes;

        } else if (type.equals("Lead Climb")) {
            result = listOfLeadClimbs;
        }
        EventLog.getInstance().logEvent(new Event("Sorted climb by type " + type));
        return result;
    }

    /*
     * REQUIRES: location to be of non-zero length
     * EFFECTS: return a list of climbs of the given location
     *          if there is no climbs of the given location, return and empty list
     */
    public ArrayList<Climb> getProjectsByLocation(String location) {
        ArrayList<Climb> result = new ArrayList<>();
        for (Climb c: listOfAllClimbs) {
            if (c.getLocation().equals(location)) {
                result.add(c);
            }
        }
        EventLog.getInstance().logEvent(new Event("Sorted climb by location " + location));
        return result;
    }



    /*
     * REQUIRES: difficulty to be of non-zero length
     * EFFECTS: return a list of climbs of the given difficulty
     *          if there is no climbs of the given difficulty, return and empty list
     */
    public ArrayList<Climb> getProjectsByDifficulty(String difficulty) {
        ArrayList<Climb> result = new ArrayList<>();
        for (Climb c: listOfAllClimbs) {
            if (c.getDifficulty().equals(difficulty)) {
                result.add(c);
            }
        }
        EventLog.getInstance().logEvent(new Event("Sorted climb by difficulty " + difficulty));
        return result;
    }

    /*
     * REQUIRES: difficulty and location to be of non-zero length
     * EFFECTS: return a list of climbs of the given difficulty in the given location
     *          if there is no climbs of the given specifications, return null
     */
    public ArrayList<Climb> getProjectsByDifficultyAndLocation(String difficulty, String location) {
        ArrayList<Climb> result = new ArrayList<>();
        for (Climb c: listOfAllClimbs) {
            if (c.getDifficulty().equals(difficulty) && c.getLocation().equals(location)) {
                result.add(c);
            }
        }
        EventLog.getInstance().logEvent(new Event("Sorted climb by difficulty " + difficulty 
                + " and difficulty " + difficulty));
        return result;
    }

    // getters

    /*
     * EFFECTS: return a list of Boulders
     */
    public ArrayList<Climb> getListOfBoulders() {
        return listOfBoulders;
    }

    /*
     * EFFECTS: return a list of Top Ropes
     */
    public ArrayList<Climb> getListOfTopRopes() {
        return listOfTopRopes;
    }

    /*
     * EFFECTS: return a list of Lead Climbs
     */
    public ArrayList<Climb> getListOfLeadClimbs() {
        return listOfLeadClimbs;
    }

    /*
     * EFFECTS: return a list of all climbs
     * note： this method is only used for viewing options, hence why it has en event log
     */
    public ArrayList<Climb> getListOfAllClimbs() {
        EventLog.getInstance().logEvent(new Event("Displayed all saved climbs"));
        return listOfAllClimbs;
    }

    // EFFECTS: turns a climb into JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("climbs", climbsToJson());
        return json;
    }

    // EFFECTS: turns climbs in listOfAllClimbs into JSON
    private JSONArray climbsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Climb c : listOfAllClimbs) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    // Effects: prints the log
    public String printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }
        return "printed";
    }

}
