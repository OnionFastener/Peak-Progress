package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Projects read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        EventLog.getInstance().logEvent(new Event("Loaded projects from file"));
        return parseProjects(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Projects parseProjects(JSONObject jsonObject) {
        Projects pr = new Projects();
        addClimbs(pr, jsonObject);
        return pr;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addClimbs(Projects pr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("climbs");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addClimb(pr, nextThingy);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addClimb(Projects pr, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String difficulty = jsonObject.getString("difficulty");
        String location = jsonObject.getString("location");
        String type = jsonObject.getString("type");
        Climb climb = new Climb(name, difficulty, location, type);
        pr.addClimb(climb);
    }
}