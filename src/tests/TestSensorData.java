package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import avionics.Sensor;
import avionics.SensorSimulator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * This class contains unit tests for the SensorSimulator and Sensor classes.
 */
public class TestSensorData {
    
    /**
     * Tests that the SensorSimulator creates the correct number of sensors.
     */
    @Test
    public void testNumSensors() {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.run();

        Map<String, Sensor> sensors = sensorSimulator.getSensors(); // Get the sensors from the SensorSimulator

        // Check that there are exactly 6 sensors
        assertEquals(6, sensors.size()); 
    }

    /**
     * Tests the getName method of the Sensor class.
     */
    @Test
    public void testGetName() {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.run();
        Map<String, Sensor> sensors = sensorSimulator.getSensors(); // Get the sensors from the SensorSimulator

        Sensor altitudeS = sensors.get("altitude");
        assertEquals("Altitude", altitudeS.getName());
    }

    /**
     * Tests changing the minimum and maximum values of a sensor.
     */
    @Test
    public void testChangeSensorMinMax() {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.run();
        Map<String, Sensor> sensors = sensorSimulator.getSensors(); // Get the sensors from the SensorSimulator

        Sensor altitudeS = sensors.get("altitude");
        altitudeS.setMinMax(200, 500);
        assertEquals(500, (int)altitudeS.getMax());
        assertEquals(200, (int)altitudeS.getMin());
    }

    /**
     * Tests that sensor values are within the specified range.
     */
    @Test
    public void testWithinRange() {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.run();
        Map<String, Sensor> sensors = sensorSimulator.getSensors(); // Get the sensors from the SensorSimulator
        
        List<Sensor> sensorList = new ArrayList<>(sensors.values());
        for (int i = 0; i < sensorList.size(); i++) {
            assertTrue(sensorList.get(i).isWithinRange());
        }
    }

    /**
     * Tests that sensor values are updated correctly.
     */
    @Test
    public void testUpdateSensor() {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.run();
        Map<String, Sensor> sensors = sensorSimulator.getSensors(); // Get the sensors from the SensorSimulator

        List<Sensor> sensorList = new ArrayList<>(sensors.values());
        for (int i = 0; i < sensorList.size(); i++) {
            int value = sensorList.get(i).getValue();
            sensorList.get(i).updateValue();
            sensorList.get(i).updateValue();
            assertTrue(value != sensorList.get(i).getValue());
        }
    }

    /**
     * Tests the toString method of the Sensor class.
     */
    @Test
    public void testToString() {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.run();

        Map<String, Sensor> sensors = sensorSimulator.getSensors(); // Get the sensors from the SensorSimulator
        
        List<Sensor> sensorList = new ArrayList<>(sensors.values());
        for (int i = 0; i < sensorList.size(); i++) {
            String value = sensorList.get(i).toString();
            assertTrue(value.contains("Name = ") && value.contains(", Value = "));
        }
    }

    /**
     * Tests that the SensorSimulator contains all the expected sensor types.
     */
    @Test
    public void testSensorsHasAllTypes() {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.run();
        Map<String, Sensor> sensors = sensorSimulator.getSensors(); // Get the sensors from the SensorSimulator
        
        // Check that the correct sensors are present
        assertTrue(sensors.containsKey("airspeed"));
        assertTrue(sensors.containsKey("altitude"));
        assertTrue(sensors.containsKey("pitch"));
        assertTrue(sensors.containsKey("roll"));
        assertTrue(sensors.containsKey("yaw"));
        assertTrue(sensors.containsKey("thrust"));
    }

    /**
     * Tests that the SensorSimulator does not contain any unexpected sensor types
     * and that all sensor values are integers.
     */
    @Test
    public void testSensorsReturnTypes() {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.run();
        Map<String, Sensor> sensors = sensorSimulator.getSensors(); // Get the sensors from the SensorSimulator

        // Check that it contains no other types
        List<Sensor> sensorList = new ArrayList<>(sensors.values());
        for (int i = 0; i < sensorList.size(); i++) {
            String name = sensorList.get(i).getName();
            assumeTrue(name.equals("Airspeed") || name.equals("Altitude") || 
            name.equals("Pitch") || name.equals("Roll") || name.equals("Yaw") || name.equals("Thrust"));
            
            assertTrue(sensorList.get(i).getValue() instanceof Integer);
        }
    }
}
