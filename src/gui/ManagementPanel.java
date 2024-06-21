package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import flightSystem.Plane;
import flightSystem.Waypoint;
import avionics.SensorSimulator;

public class ManagementPanel extends JPanel {
    private SensorSimulator sensorSimulator;
    private PlaneOnMapPanel planeOnMapPanel;
    private Plane plane;
    public Timer timer;
    private long startTime;
    private JLabel timerLabel;
    private JLabel waypointsLabel;
    private int maxAirspeed;
    private int minAirspeed;
    private int maxAltitude;
    private int minAltitude;
    public long elapsedTime;
    private JFormattedTextField maxAirspeedTextField;
    private JFormattedTextField minAirspeedTextField;
    private JFormattedTextField maxAltitudeTextField;
    private JFormattedTextField minAltitudeTextField;
    private JButton submitButton;
    private JButton clearButton;
    private JButton newSubmitButton;

    /**
     * Creates a Management Panel object on the Flight Simulation. This is one of 6
     * panels on the program.
     * 
     * @param s      sends altitude and airspeed values to the sensor.
     * @param POMPan communicates with the planeOnMapPanel class to fly the plane on
     *               the map, based on the selected waypoints.
     */
    public ManagementPanel(SensorSimulator s, PlaneOnMapPanel POMPan) {
        assert s != null : "SensorSimulator object cannot be null";
        assert POMPan != null : "PlaneOnMapPanel object cannot be null";
        this.sensorSimulator = s; // Set the sensorSimulator object
        this.planeOnMapPanel = POMPan; // Set the planeOnMapPanel object
        this.plane = planeOnMapPanel.getPlane(); // Get the plane from the planeOnMapPanel
        assert this.plane != null : "Plane object cannot be null";
        plane.setManagementPanel(this); // Add this line to set the management panel in the plane
        s.getSensors(); // Get sensors from Sensor Simulator class
        setLayout(null);

        createButtons();
        createLabels();
        createTextFields();
        setUpActionListeners();
    }

    /**
     * Creates and configures the buttons for the management panel.
     * Adds the buttons to the panel.
     */
    private void createButtons() {
        submitButton = new JButton("Submit Flight Path");
        submitButton.setFont(new Font("Arial", Font.BOLD, 10));
        clearButton = new JButton("Clear Waypoints");
        clearButton.setFont(new Font("Arial", Font.BOLD, 10));
        newSubmitButton = new JButton("Submit Values");
        newSubmitButton.setFont(new Font("Arial", Font.BOLD, 10));

        newSubmitButton.setBounds(40, 180, 150, 25);
        submitButton.setBounds(40, 320, 150, 25);
        clearButton.setBounds(40, 350, 150, 25);

        assert submitButton != null : "Submit button must be initialised before adding to the panel";
        assert clearButton != null : "Clear button must be initialised before adding to the panel";
        assert newSubmitButton != null : "New submit button must be initialised before adding to the panel";

        add(newSubmitButton);
        add(submitButton);
        add(clearButton);

    }

    /**
     * Creates and configures the labels for the management panel.
     * Adds the labels to the panel.
     */
    private void createLabels() {
        JLabel label = new JLabel("Management Panel"); // Create a title label for the management panel
        label.setFont(new Font("Arial", Font.BOLD, 16));

        this.waypointsLabel = new JLabel("Waypoints: \n"); // Create a label for the waypoints
        this.waypointsLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        this.timerLabel = new JLabel("Elapsed time: 0 hours"); // Create a label for the time passed since plane started
                                                               // // flight path
        this.timerLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Set the bounds for the buttons and labels
        label.setBounds(40, 20, 200, 20);
        waypointsLabel.setBounds(80, 180, 170, 150);
        timerLabel.setBounds(53, 290, 150, 25);

        assert label != null : "Label must be initialised before adding to the panel";
        assert waypointsLabel != null : "Waypoints label must be initialised before adding to the panel";
        assert timerLabel != null : "Timer label must be initialised before adding to the panel";

        // Add the buttons and labels to the panel
        add(label);
        add(waypointsLabel);
        add(timerLabel);

        // Create and add a label to the panel for min airspeed
        JLabel minAirspeed = new JLabel("Min Airspeed:");
        minAirspeed.setBounds(40, 70, 150, 50);
        
        assert minAirspeed != null : "Min airspeed label must be initialised before adding to the panel";

        add(minAirspeed);

        // Create and add a label to the panel for max airspeed
        JLabel maxAirspeed = new JLabel("Max Airspeed:");
        maxAirspeed.setBounds(40, 90, 150, 50);
        
        assert maxAirspeed != null : "Max airspeed label must be initialised before adding to the panel";

        add(maxAirspeed);

        // Create and add a label to the panel for max altitude
        JLabel maxAltitude = new JLabel("Max Altitude:");
        maxAltitude.setBounds(40, 130, 150, 50);
        
        assert maxAltitude != null : "Max altitude label must be initialised before adding to the panel";
        
        add(maxAltitude);

        // Create and add a label to the panel for min altitude
        JLabel minAltitude = new JLabel("Min Altitude:");
        minAltitude.setBounds(40, 110, 150, 50);
        
        assert minAltitude != null : "Min altitude label must be initialised before adding to the panel";
        
        add(minAltitude);
    }

    /**
     * Creates and configures the text fields for the management panel.
     * Adds the text fields to the panel.
     */
    private void createTextFields() {

        // Create and add a text field to the panel for the pilot to specify min
        // airspeed
        minAirspeedTextField = new JFormattedTextField();
        minAirspeedTextField.setValue(200);
        minAirspeedTextField.setBorder(new LineBorder(Color.BLACK));
        minAirspeedTextField.setBounds(135, 85, 50, 18);
        
        // Create and add a text field to the panel for the pilot to specify max
        // airspeed
        maxAirspeedTextField = new JFormattedTextField();
        maxAirspeedTextField.setValue(400);
        maxAirspeedTextField.setBorder(new LineBorder(Color.BLACK));
        maxAirspeedTextField.setBounds(135, 105, 50, 18);
        
        // Create and add a text field to the panel for the pilot to specify max alitude
        maxAltitudeTextField = new JFormattedTextField();
        maxAltitudeTextField.setValue(23000);
        maxAltitudeTextField.setBorder(new LineBorder(Color.BLACK));
        maxAltitudeTextField.setBounds(135, 145, 50, 18);
        
        // Create and add a text field to the panel for the pilot to specify min
        // altitude
        minAltitudeTextField = new JFormattedTextField();
        minAltitudeTextField.setValue(2000);
        minAltitudeTextField.setBorder(new LineBorder(Color.BLACK));
        minAltitudeTextField.setBounds(135, 125, 50, 18);
        
        assert minAirspeedTextField != null : "Min airspeed text field must be initialised before adding to the panel";
        assert maxAirspeedTextField != null : "Max airspeed text field must be initialised before adding to the panel";
        assert maxAltitudeTextField != null : "Max altitude text field must be initialised before adding to the panel";

        add(minAirspeedTextField);
        add(maxAirspeedTextField);
        add(maxAltitudeTextField);
        add(minAltitudeTextField);
    }

    /**
     * Starts the timer for tracking elapsed time when the submit flight path is
     * selected.
     * 
     * @param startTime the time at which the timer starts, measured in milliseconds
     *                  since the epoch.
     * @param e         the ActionEvent triggered when the submit flight path s
     *                  selected.
     */
    private void startTimer() {
        startTime = System.currentTimeMillis();
        assert startTime > 0 : "Start time must be initialised before starting the timer";

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // The elapsed time is the time passed since the start time
                long elapsedTime = System.currentTimeMillis() - startTime;

                // Display the time passed in hours to be more accurate of an actual flight
                timerLabel.setText("Elapsed time: " + elapsedTime / 333 + " hours");
            }
        });
        timer.start();
    }

    /**
     * Action listener for the airspeed and altitude value submit button, the clear
     * waypoint button, and the submit waypoint button.
     * 
     * @param e the ActionEvent
     */
    private void setUpActionListeners() {
        newSubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Listener for the airspeed and altitude values specified by the pilot, which
                // converts them to integers
                setMaxAirspeed(((Number) maxAirspeedTextField.getValue()).intValue());
                setMinAirspeed(((Number) minAirspeedTextField.getValue()).intValue());
                setMaxAltitude(((Number) maxAltitudeTextField.getValue()).intValue());
                setMinAltitude(((Number) minAltitudeTextField.getValue()).intValue());

                // Check if values are within specified ranges
                if (getMinAirspeed() < 40 || getMaxAirspeed() > 450 || getMinAltitude() < 1000
                        || getMaxAltitude() > 37000) {
                    // Display an error message
                    JOptionPane.showMessageDialog(null,
                            "Invalid input! Ensure min airspeed is >= 40 knots, max airspeed is <= 450 knots, min altitude is >= 1000 ft, and max altitude is <= 37000 ft");
                    return; // Exit the method to prevent further execution
                }

                assert sensorSimulator.getSensors() != null
                        : "SensorSimulator.getSensors() returned null, sensor values cannot be set";
                assert sensorSimulator.getSensors().get("altitude") != null &&
                        sensorSimulator.getSensors().get("airspeed") != null
                        : "Altitude or airspeed sensor not found in SensorSimulator.getSensors()";
                // Set the altitude and airspeed sensor values to those specified by the pilot
                sensorSimulator.getSensors().get("altitude").setMinMax(getMinAltitude(), getMaxAltitude());
                sensorSimulator.getSensors().get("airspeed").setMinMax(getMinAirspeed(), getMaxAirspeed());

            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                assert plane != null : "Plane object must be initialised before submitting flight path";
                if (!plane.isAtDestination() && (plane.getWaypoints().size() == 2 || plane.getWaypoints().size() == 0
                        || plane.getWaypoints().size() == 1)) {
                    // Do not start the timer if the plane is not at destination, or does not have
                    // both waypoints specified
                    return;
                }

                // If the submit button has been pressed, and none of the conditions above are
                // met, move the plane to the next waypoint,
                // start the timer, and update the latitude and longitude values of the selected
                // waypoint
                plane.proceedToNextWaypoint();
                startTimer();
                updateWaypointsLabel();
            }
        });

        clearButton.addActionListener(new ActionListener() { // Action listener for clearing waypoints
            public void actionPerformed(ActionEvent e) {
                assert planeOnMapPanel.getWaypoints() != null : "PlaneOnMapPanel waypoints list is null";

                // If two waypoints are selected, and the user presses the clear button, clear
                // these
                if (planeOnMapPanel.getWaypoints().size() == 2) {
                    plane.clearWaypoints();
                    planeOnMapPanel.getWaypoints().clear();
                    stopTimer();
                    timerLabel.setText("Elapsed time: 0 hours");
                    planeOnMapPanel.repaint(); // Repaint the map panel to reflect the cleared waypoints
                    updateWaypointsLabel(); // Update the waypoints label
                }
            }
        });
    }

    /**
     * Check to see if plane is at destination or there are no more waypoints
     * remaining.
     * 
     * If either condition is met, the timer is stopped.
     */
    public void checkAndStopTimer() {
        assert plane != null : "Plane object must be initialised before checking and stopping timer";
        if (plane.isAtDestination() || plane.getWaypoints().size() == 0) {
            // Stops timer when plane reaches destination
            stopTimer();
        }
    }

    /**
     * Stops the timer if the conditions in the checkAndStopTimer method are met.
     * 
     * Will give the pilot a final elapsed flight time. Each second passed is
     * displayed as an hour.
     * 
     * @param elapsedTime the total elapsed time since the timer started, measured
     *                    in milliseconds.
     * @param startTime   the time at which the timer was started.
     */
    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            assert timer != null : "Timer must be initialised before stopping";
            // Stops the timer if it is currently running
            timer.stop();
            long elapsedTime = System.currentTimeMillis() - startTime;
            timerLabel.setText("Final time: " + elapsedTime / 333 + " hours");
        }
    }

    /**
     * Updates the waypoints label with the current waypoints displayed on the map.
     * 
     * @param labelTextBuilder a StringBuilder used to construct the label text.
     * @param waypoints        a list of waypoints representing the current
     *                         waypoints displayed on the map, with x and y values.
     * @param latitude         the latitude of the current waypoint.
     * @param longitude        the longitude of the current waypoint.
     */
    private void updateWaypointsLabel() {
        StringBuilder labelTextBuilder = new StringBuilder("<html>Waypoints:<br>");
        List<Waypoint> waypoints = planeOnMapPanel.getWaypoints();
        assert waypoints != null : "Waypoints list must be initialised before updating the label";
        for (Waypoint waypoint : waypoints) {
            // Convert the x and y coordinates in the waypoint list to latitude and
            // longitude
            double latitude = convertToLatitude(waypoint.getPoint().y, planeOnMapPanel.getHeight());
            double longitude = convertToLongitude(waypoint.getPoint().x, planeOnMapPanel.getWidth());

            labelTextBuilder.append(String.format("Latitude: %.6f, Longitude: %.6f<br>", latitude, longitude));
        }
        labelTextBuilder.append("</html>");
        waypointsLabel.setText(labelTextBuilder.toString()); // Update the label with the constructed string
    }

    /**
     * Converts the y coordinate value to latitude.
     * 
     * Takes into account the max and minimum latitude of 90 and -90 respectively.
     * 
     * @param y         the y coordinate value to be converted
     * @param mapHeight the height of the map
     * @param latMin    the minimum latitude value
     * @param latMax    the maximum latitude value
     * 
     * @return the latitude value corresponding to the given y coordinate
     */
    private double convertToLatitude(int y, int mapHeight) {
        assert mapHeight > 0 : "Map height must be greater than zero";

        double latMin = -90.0; // Minimum latitude
        double latMax = 90.0; // Maximum latitude
        return latMax - ((double) y / mapHeight) * (latMax - latMin);
    }

    /**
     * Converts the x coordinate value to longitude.
     * 
     * Takes into account the max and minimum longitude of 180 and -180
     * respectively.
     * 
     * @param x         the x coordinate value to be converted
     * @param mapHeight the height of the map
     * @param lonMin    the minimum longitude value
     * @param lonMax    the maximum longitude value
     * 
     * @return the longitude value corresponding to the given x coordinate
     */
    private double convertToLongitude(int x, int mapWidth) {
        assert mapWidth > 0 : "Map width must be greater than zero";

        double lonMin = -180.0; // Minimum longitude
        double lonMax = 180.0; // Maximum longitude
        return lonMin + ((double) x / mapWidth) * (lonMax - lonMin);
    }

    /**
     * Gets the elapsed time since the timer started.
     * 
     * @return the elapsed time in milliseconds
     */
    public long getElapsedTime() {
        assert elapsedTime >= 0 : "Elapsed time should be non-negative";
        return elapsedTime;
    }

    /**
     * Gets the timer.
     * 
     * @return the timer
     */
    public Timer getTimer() {
        assert timer != null : "Timer should not be null";
        return timer;
    }

    /**
     * Gets the maximum airspeed specified by the pilot.
     * 
     * @return the maximum airspeed specified by the pilot
     */
    public int getMaxAirspeed() {
        assert maxAirspeed >= 0 : "Max airspeed should be non-negative";
        return maxAirspeed;
    }

    /**
     * Sets the maximum airspeed specified by the pilot.
     * 
     * @param maxAirspeed the maximum airspeed specified by the pilot
     */
    public void setMaxAirspeed(int maxAirspeed) {
        assert maxAirspeed >= 0 : "Max airspeed should be non-negative";
        this.maxAirspeed = maxAirspeed;
    }

    /**
     * Gets the minimum airspeed specified by the pilot.
     * 
     * @return the minimum airspeed specified by the pilot
     */
    public int getMinAirspeed() {
        assert minAirspeed >= 0 : "Min airspeed should be non-negative";
        return minAirspeed;
    }

    /**
     * Sets the minimum airspeed specified by the pilot.
     * 
     * @param minAirspeed the minimum airspeed specified by the pilot
     */
    public void setMinAirspeed(int minAirspeed) {
        assert minAirspeed >= 0 : "Min airspeed should be non-negative";
        this.minAirspeed = minAirspeed;
    }

    /**
     * Gets the maximum altitude specified by the pilot.
     * 
     * @return the maximum altitude specified by the pilot
     */
    public int getMaxAltitude() {
        assert maxAltitude >= 0 : "Max altitude should be non-negative";
        return maxAltitude;
    }

    /**
     * Sets the maximum altitude specified by the pilot.
     * 
     * @param maxAltitude the maximum altitude specified by the pilot
     */
    public void setMaxAltitude(int maxAltitude) {
        assert maxAltitude >= 0 : "Max altitude should be non-negative";
        this.maxAltitude = maxAltitude;
    }

    /**
     * Gets the minimum altitude specified by the pilot.
     * 
     * @return the minimum altitude specified by the pilot
     */
    public int getMinAltitude() {
        assert minAltitude >= 0 : "Min altitude should be non-negative";
        return minAltitude;
    }

    /**
     * Sets the minimum altitude specified by the pilot.
     * 
     * @param minAltitude the minimum altitude specified by the pilot
     */
    public void setMinAltitude(int minAltitude) {
        assert minAltitude >= 0 : "Min altitude should be non-negative";
        this.minAltitude = minAltitude;
    }


}