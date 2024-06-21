package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import avionics.SensorSimulator;
import gui.ConsolePanel;

public class TestConsolePanel {

    ConsolePanel consolePanel;
    SensorSimulator sensorSimulator = new SensorSimulator();

    @Before
    public void setup() {
        consolePanel = new ConsolePanel(sensorSimulator);
    }

    @Test
    public void testSensorNames() {
        ArrayList<String> expectedSensorNames = new ArrayList<String>();
        ArrayList<String> testSensorNames = consolePanel.getSensorNames();

        expectedSensorNames.add("airspeed");
        expectedSensorNames.add("altitude");
        expectedSensorNames.add("pitch");
        expectedSensorNames.add("roll");
        expectedSensorNames.add("yaw");
        expectedSensorNames.add("thrust");

        assertTrue(testSensorNames.size() == 6);

        for (int i = 0; i < testSensorNames.size(); i++) {
            assertTrue(expectedSensorNames.contains(testSensorNames.get(i)));
        }
    }

    @Test
    public void testUpdateInsideRange() {
        sensorSimulator.run();
        consolePanel.updateConsole();

        assertTrue(consolePanel.getSensorError() == false);
    }
}