package avionics;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.*;

// This class simulates the sensors on the plane
public class SensorSimulator implements Runnable {
    private static final String FILE_PATH = "src/resources/data/sensor_data.json";   // Path to the JSON file that stores the sensor data
    private static Map<String, Sensor> sensors = new HashMap<>();                       // Map to store the sensors
    
    /**
     * Runs the sensor simulator.
     * 
     * This method creates and initializes the sensors, creates a JSON file with default values if it doesn't exist,
     * and schedules the updates for each sensor at fixed intervals using a scheduled executor service.
     */
    public void run() {
        // Create the sensors with their respective names, max and min values
        sensors.put("thrust", new Sensor("Thrust", 140500, 130500));
        sensors.put("airspeed", new Sensor("Airspeed", 1000, 100));
        sensors.put("altitude", new Sensor("Altitude", 1000, 100));
        sensors.put("pitch", new Sensor("Pitch", 90, -90));
        sensors.put("roll", new Sensor("Roll", 180, -180));
        sensors.put("yaw", new Sensor("Yaw", 360, 0));

        assert sensors.isEmpty() == false : "Sensors must not be empty";
        assert sensors.size() == 6 : "There should be 6 sensors";
        
        // Create the JSON file with default values if it doesn't exist
        File jsonFile = new File(FILE_PATH);
        assert jsonFile.exists() : "JSON file does not exist";
        if (!jsonFile.exists()) {
            createDefaultJsonFile();
        }

        // Create a scheduled executor service with 3 threads
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        assert scheduler != null : "ScheduledExecutorService should not be null";

        // Airspeed sensor updates every second
        scheduler.scheduleAtFixedRate(() -> {
            sensors.get("airspeed").updateValue();
            updateJsonFile();
        }, 0, 1, TimeUnit.SECONDS);

        // Altitude sensor updates every 500 milliseconds
        scheduler.scheduleAtFixedRate(() -> {
            sensors.get("altitude").updateValue();
            sensors.get("thrust").updateValue();
            updateJsonFile();
        }, 0, 500, TimeUnit.MILLISECONDS);

        // Attitude sensor updates every 500 milliseconds
        scheduler.scheduleAtFixedRate(() -> {
            sensors.get("pitch").updateValue();
            sensors.get("roll").updateValue();
            sensors.get("yaw").updateValue();
            updateJsonFile();
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    /**
     * Returns the map of sensors.
     * 
     * @return The map of sensors.
     */
    public Map<String, Sensor> getSensors() {
        assert sensors.isEmpty() == false : "Sensors must not be empty";
        assert sensors.size() == 6 : "Sensors must contain 6 elements";
        return sensors;
    }

    /**
     * Updates the JSON file with the current sensor data.
     * This method is called every time a sensor value is updated.
     */
    private synchronized void updateJsonFile() {
        assert sensors.size() == 6 : "Sensors must contain 6 elements";
        try (FileReader reader = new FileReader(FILE_PATH)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            List<Sensor> sensorList = new ArrayList<>(sensors.values());
            assert sensorList.size() == 6 : "Sensor list must contain 6 elements";
            for (int i = 0; i < sensorList.size(); i++) {
                jsonObject.addProperty(sensorList.get(i).getName(), sensorList.get(i).getValue());
            }

            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write(jsonObject.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the JSON file with default sensor values.
     * This method is called when the JSON file does not exist.
     */
    private void createDefaultJsonFile() {
        assert sensors.size() == 6 : "Sensors must contain 6 elements";
        JsonObject jsonObject = new JsonObject();
        assert jsonObject != null : "JSON object must not be null";

        List<Sensor> sensorList = new ArrayList<>(sensors.values());
        assert sensorList.size() == 6 : "Sensor list must contain 6 elements";

        for (int i = 0; i < sensorList.size(); i++) {
            jsonObject.addProperty(sensorList.get(i).getName(), sensorList.get(i).getValue());
        }

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}