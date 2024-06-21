package gui;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.util.Map;
import java.util.HashMap;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;

import avionics.Sensor;
import avionics.SensorSimulator;

/**
 * The HazardPanel class represents a GUI component that displays the status of various 
 * sensor conditions. It uses color-coded lights to indicate 
 * the status of each condition.
 */
public class HazardPanel extends JPanel {

    // boolean variables for conditions
    private boolean systemSafe = true;
    private boolean sensorFault = false;
    private boolean airspeedFault = false;
    private boolean altitudeFault = false;
    private boolean attitudeFault = false;

    public SensorSimulator sensorSimulator;
    public Map<String, Sensor> sensors;

    private Map<String, JPanel> conditionLights;

    /**
     * Constructs a HazardPanel with a specified SensorSimulator.
     *
     * @param sensorSimulator the sensor simulator used to get sensor data
     */
    public HazardPanel(SensorSimulator sensorSimulator) {
        setLayout(new GridBagLayout());
        this.sensorSimulator = sensorSimulator;
        sensors = sensorSimulator.getSensors();

        conditionLights = new HashMap<>();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.WEST;

        // Labels for each condition
        JLabel systemSafeLabel = new JLabel("        System Safe:");
        JLabel sensorFaultLabel = new JLabel("        Sensor Fault:");
        JLabel airspeedFaultLabel = new JLabel("        Airspeed Fault:");
        JLabel altitudeFaultLabel = new JLabel("        Altitude Fault:");
        JLabel attitudeFaultLabel = new JLabel("        Attitude Fault:");

        // Add the condition labels to panel
        constraints.gridy = 0;
        add(systemSafeLabel, constraints);
        constraints.gridy = 1;
        add(sensorFaultLabel, constraints);
        constraints.gridy = 2;
        add(airspeedFaultLabel, constraints);
        constraints.gridy = 3;
        add(altitudeFaultLabel, constraints);
        constraints.gridy = 4;
        add(attitudeFaultLabel, constraints);

        // Create lights for each condition
        addConditionLight("systemSafe", 0, constraints);
        addConditionLight("sensorFault", 1, constraints);
        addConditionLight("airspeedFault", 2, constraints);
        addConditionLight("altitudeFault", 3, constraints);
        addConditionLight("attitudeFault", 4, constraints);
    }

    /**
     * Adds a condition light to the panel.
     *
     * @param conditionName the name of the condition
     * @param gridy the grid y position for the condition light
     * @param constraints the GridBagConstraints for the layout
     */
    public void addConditionLight(String conditionName, int gridy, GridBagConstraints constraints) {
        JPanel light = new JPanel();
        light.setPreferredSize(new Dimension(20, 20));
        light.setBackground(Color.GRAY); // Default color

        conditionLights.put(conditionName, light);

        constraints.gridx = 1;
        constraints.gridy = gridy;
        add(light, constraints);
    }

    /**
     * Updates the color of the condition light.
     *
     * @param conditionName the name of the condition
     * @param color the new color for the condition light
     */
    public void updateConditionColor(String conditionName, Color color) {
        JPanel light = conditionLights.get(conditionName);
            assert light != null : "Light panel should not be null for condition: " + conditionName;
            light.setBackground(color);
    }

    /**
     * Updates the status of all condition lights based on sensor data.
     */
    public void update() {
        checkSensors();

        updateConditionColor("systemSafe", systemSafe ? Color.GREEN : Color.GRAY);
        updateConditionColor("sensorFault", sensorFault ? Color.RED : Color.GRAY);
        updateConditionColor("airspeedFault", airspeedFault ? Color.RED : Color.GRAY);
        updateConditionColor("altitudeFault", altitudeFault ? Color.RED : Color.GRAY);
        updateConditionColor("attitudeFault", attitudeFault ? Color.RED : Color.GRAY);
        
        repaint();
    }

    /**
     * Checks the status of each sensor and updates conditions accordingly.
     */
    public void checkSensors(){
        Sensor airspeed = sensors.get("airspeed");
        Sensor altitude = sensors.get("altitude");
        Sensor pitch = sensors.get("pitch");
        Sensor roll = sensors.get("roll");
        Sensor yaw = sensors.get("yaw");

        //conditions where attitude fault could occur
        attitudeFault = !pitch.isWithinRange() || !roll.isWithinRange() || !yaw.isWithinRange();
        altitudeFault = !altitude.isWithinRange();
        airspeedFault = !airspeed.isWithinRange();

        sensorFault = attitudeFault || altitudeFault || airspeedFault;
        systemSafe = !sensorFault;

        // assertions
        assert attitudeFault == (!pitch.isWithinRange() || !roll.isWithinRange() || !yaw.isWithinRange()) : "Attitude fault logic error";
        assert altitudeFault == !altitude.isWithinRange() : "Altitude fault logic error";
        assert airspeedFault == !airspeed.isWithinRange() : "Airspeed fault logic error";
        assert sensorFault == (attitudeFault || altitudeFault || airspeedFault) : "Sensor fault logic error";
        assert systemSafe == !sensorFault : "System safe logic error";

    }

    /**
     * Checks if the system is safe.
     *
     * @return true if the system is safe, false otherwise
     */
    public boolean isSystemSafe() {
        assert systemSafe == true || systemSafe == false : "systemSafe should be a boolean";
        return systemSafe;
    }

    /**
     * Checks if there is a sensor fault.
     *
     * @return true if there is a sensor fault, false otherwise
     */
    public boolean isSensorFault() {
        assert sensorFault == true || sensorFault == false : "sensorFault should be a boolean";
        return sensorFault;
    }

    /**
     * Checks if there is an airspeed fault.
     *
     * @return true if there is an airspeed fault, false otherwise
     */
    public boolean isAirspeedFault() {
        assert airspeedFault == true || airspeedFault == false : "airspeedFault should be a boolean";
        return airspeedFault;
    }

    /**
     * Checks if there is an altitude fault.
     *
     * @return true if there is an altitude fault, false otherwise
     */
    public boolean isAltitudeFault() {
        assert altitudeFault == true || altitudeFault == false : "altitudeFault should be a boolean";
        return altitudeFault;
    }

    /**
     * Checks if there is an attitude fault.
     *
     * @return true if there is an attitude fault, false otherwise
     */
    public boolean isAttitudeFault() {
        assert attitudeFault == true || attitudeFault == false : "attitudeFault should be a boolean";
        return attitudeFault;
    }

    /**
     * Gets the condition lights map.
     *
     * @return the map of condition lights
     */
    public Map<String, JPanel> getConditionLights() {
        return conditionLights;
    }

        /**
     * Sets the system safe status.
     *
     * @param systemSafe the new system safe status
     */
    public void setSystemSafe(boolean systemSafe) {
        this.systemSafe = systemSafe;
        assert this.systemSafe == !(sensorFault || airspeedFault || altitudeFault || attitudeFault) 
                : "systemSafe status is inconsistent with fault states";
    }

    /**
     * Sets the sensor fault status.
     *
     * @param sensorFault the new sensor fault status
     */
    public void setSensorFault(boolean sensorFault) {
        this.sensorFault = sensorFault;
        updateSystemSafe();
    }

    /**
     * Sets the airspeed fault status.
     *
     * @param airspeedFault the new airspeed fault status
     */
    public void setAirspeedFault(boolean airspeedFault) {
        this.airspeedFault = airspeedFault;
        updateSystemSafe();
        updateSensorFault();
        assert this.airspeedFault == airspeedFault : "airspeedFault was not correctly updated";
    }

    /**
     * Sets the altitude fault status.
     *
     * @param altitudeFault the new altitude fault status
     */
    public void setAltitudeFault(boolean altitudeFault) {
        this.altitudeFault = altitudeFault;
        updateSystemSafe();
        updateSensorFault();
        assert this.altitudeFault == altitudeFault : "altitudeFault was not correctly updated";
    }

    /**
     * Sets the attitude fault status.
     *
     * @param attitudeFault the new attitude fault status
     */
    public void setAttitudeFault(boolean attitudeFault) {
        this.attitudeFault = attitudeFault;
        updateSystemSafe();
        updateSensorFault();
        assert this.attitudeFault == attitudeFault : "attitudeFault was not correctly updated";
    }

    /**
     * Updates the system safe status based on current fault conditions.
     */
    public void updateSystemSafe() {
        systemSafe = !(sensorFault || airspeedFault || altitudeFault || attitudeFault);
        assert systemSafe == !(sensorFault || airspeedFault || altitudeFault || attitudeFault) 
                : "systemSafe status is inconsistent with fault states";
    }

    /**
     * Updates the sensor fault status based on current fault conditions.
     */
    public void updateSensorFault() {
        sensorFault = airspeedFault || altitudeFault || attitudeFault;
        assert sensorFault == (airspeedFault || altitudeFault || attitudeFault) 
                : "sensorFault status is inconsistent with other fault states";
    }
}
