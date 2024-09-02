package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a climb with a string for difficulty, location, 
// and type of climb
public class Climb implements Writable {
    protected String name;
    protected String difficulty;
    protected String location;
    protected String type;

    /*
     * REQUIRES: difficulty and type has non-zero length
     * EFFECTS: difficulty of climb is set to difficulty,
     *          location of climb is set to location,
     *          type of climb is set to type
     */
    public Climb(String name, String difficulty, String location, String type) {
        this.name = name;
        this.difficulty = difficulty;
        this.location = location;
        this.type = type;
    }

    /*
     * MODIFIES: this
     * REQUIRES: name is non-zero length
     * EFFECTS: sets name of the climb to name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * MODIFIES: this
     * REQUIRES: diff is non-zero length
     * EFFECTS: sets the difficulty of the climb to diff
     */
    public void setDifficulty(String diff) {
        this.difficulty = diff;
    }

    /*
     * MODIFIES: this
     * REQUIRES: loc is non-zero length
     * EFFECTS: sets the location of the climb to dilocff
     */
    public void setLocation(String loc) {
        this.location = loc;
    }

    /*
     * EFFECTS: returns the name of the climb
     */
    public String getName() {
        return name;
    }

    /*
     * EFFECTS: returns the difficulty of the climb
     */
    public String getDifficulty() {
        return difficulty;
    }

    /*
     * EFFECTS: returns the location of the climb
     */
    public String getLocation() {
        return location;
    }

    /*
     * EFFECTS: returns the type of the climb
     */
    public String getType() {
        return type;
    }

    // EFFECTS: turns the climb to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("difficulty", difficulty);
        json.put("location", location);
        json.put("type", type);
        return json;
    }

    // REQUIRES: climb to be an existing climb
    // EFFECTS: returns a climb in a readable string with descriptors
    public String climbToString() {
        return "name: " + getName() + ", type: " + getType() + ", location: " 
                    + getLocation() + ", grade: " + getDifficulty() + "\n";
    }
}
