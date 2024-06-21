package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SensorDataPanel extends JPanel {

    // Labels for displaying sensor data
    private JLabel airspeedLabel;
    private JLabel altitudeLabel;
    private JLabel pitchLabel;
    private JLabel rollLabel;
    private JLabel yawLabel;
    private JLabel thrustLabel;

    // Path to the JSON file containing sensor data
    private static final String FILE_PATH = "src/resources/data/sensor_data.json";
    
    /**
     * A panel that displays sensor data.
     * Initializes the panel displaying updating airspeed, altitude, pitch, roll, yaw, and thrust values.
     * 
     */
    public SensorDataPanel() {
        setLayout(new GridBagLayout()); // Set layout manager

        // Initialise labels with default text
        airspeedLabel = new JLabel("Airspeed (knots): N/A");
        altitudeLabel = new JLabel("Altitude (feet AMSL): N/A");
        thrustLabel = new JLabel("Thrust (IBF): N/A");
        pitchLabel = new JLabel("Pitch (degrees): N/A");
        rollLabel = new JLabel("Roll (degrees): N/A");
        yawLabel = new JLabel("Yaw (degrees): N/A");

        // Set font for the labels
        Font font = new Font("Arial", Font.BOLD, 16);
        airspeedLabel.setFont(font);
        altitudeLabel.setFont(font);
        pitchLabel.setFont(font);
        rollLabel.setFont(font);
        yawLabel.setFont(font);
        thrustLabel.setFont(font);

        // GridBagConstraints for layout management
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0; // Set column position
        constraints.gridy = GridBagConstraints.RELATIVE; // Set row position to relative (stack vertically)
        constraints.anchor = GridBagConstraints.WEST; // Align components to the west (left)
        constraints.insets = new java.awt.Insets(5, 5, 5, 5); // Set padding around components

        // Add labels to the panel using the specified constraints
        add(airspeedLabel, constraints);
        add(altitudeLabel, constraints);
        add(thrustLabel, constraints);
        add(pitchLabel, constraints);
        add(rollLabel, constraints);
        add(yawLabel, constraints);
    }

    
    /**
     * Updates the sensor data displayed in the GUI.
     * Reads the sensor data from a JSON file and updates the corresponding labels.
     */
    public void updateData() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            // Parse the JSON data from the file
            JsonElement jsonElement = JsonParser.parseReader(reader);

            // Check if the JSON data is valid (happens when json is being written to the file from sensor simulator and is being read by this method at the same time)
            if (jsonElement == null || !jsonElement.isJsonObject()) {
                //System.err.println("Invalid or null JSON data");
                return; // Exit the method if JSON data is invalid
            }

            // Get the JSON object from the parsed data
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            // Update the labels with the new sensor data if jsonObject is not null
            if (jsonObject != null) {
                airspeedLabel.setText("Airspeed (knots): " + jsonObject.get("Airspeed").getAsInt());
                altitudeLabel.setText("Altitude (feet AMSL): " + jsonObject.get("Altitude").getAsInt());
                thrustLabel.setText("Engine Thrust (IBF): " + jsonObject.get("Thrust").getAsInt());
                pitchLabel.setText("Pitch (degrees): " + jsonObject.get("Pitch").getAsInt());
                rollLabel.setText("Roll (degrees): " + jsonObject.get("Roll").getAsInt());
                yawLabel.setText("Yaw (degrees): " + jsonObject.get("Yaw").getAsInt());
                assert jsonObject.get("Airspeed") != null : "Airspeed is null";
                assert jsonObject.get("Altitude") != null : "Altitude is null";
                assert jsonObject.get("Thrust") != null : "Thrust is null";
                assert jsonObject.get("Pitch") != null : "Pitch is null";
                assert jsonObject.get("Roll") != null : "Roll is null";
                assert jsonObject.get("Yaw") != null : "Yaw is null";
            }

        } catch (JsonSyntaxException e) {
            // Handle JSON syntax errors
            //System.err.println("Failed to parse JSON data");
            e.printStackTrace();
        } catch (IOException e) {
            // Handle IO exceptions
            e.printStackTrace();
        }
    }
}