package tests;

import gui.HazardPanel;
import avionics.SensorSimulator;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.swing.JPanel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.awt.GridBagConstraints;
import java.awt.Color;

public class TestHazards {

    private HazardPanel hazardPanel;
    private SensorSimulator sensorSimulator;


    @Before

    public void setUp() {
        // Create an instance of HazardPanel and sensorSimulator
        sensorSimulator = new SensorSimulator();
        hazardPanel = new HazardPanel(sensorSimulator);
    }

    // Tests the initial state of the hazard panel
    @Test
    public void testInitialState() {
        assertTrue(hazardPanel.isSystemSafe());
        assertFalse(hazardPanel.isSensorFault());
        assertFalse(hazardPanel.isAirspeedFault());
        assertFalse(hazardPanel.isAltitudeFault());
        assertFalse(hazardPanel.isAttitudeFault());
    }

    // Tests the getters and setters
    @Test
    public void testSetSystemSafe() {
        hazardPanel.setSystemSafe(true);
        assertTrue(hazardPanel.isSystemSafe());
    }

    @Test
    public void testSetSensorFault() {
        hazardPanel.setSensorFault(true);
        assertTrue(hazardPanel.isSensorFault());
        hazardPanel.setSensorFault(false);
        assertFalse(hazardPanel.isSensorFault());
    }

    @Test
    public void testSetAirspeedFault() {
        hazardPanel.setAirspeedFault(true);
        assertTrue(hazardPanel.isAirspeedFault());
        hazardPanel.setAirspeedFault(false);
        assertFalse(hazardPanel.isAirspeedFault());
    }

    @Test
    public void testSetAltitudeFault() {
        hazardPanel.setAltitudeFault(true);
        assertTrue(hazardPanel.isAltitudeFault());
        hazardPanel.setAltitudeFault(false);
        assertFalse(hazardPanel.isAltitudeFault());
    }

    @Test
    public void testSetAttitudeFault() {
        hazardPanel.setAttitudeFault(true);
        assertTrue(hazardPanel.isAttitudeFault());
        hazardPanel.setAttitudeFault(false);
        assertFalse(hazardPanel.isAttitudeFault());
    }

    // The below tests check that if any of the faults are true, isSystemSafe returns false
    @Test
    public void testSystemUnsafe1() {
        hazardPanel.setAirspeedFault(true);
        assertFalse(hazardPanel.isSystemSafe());
    }

    @Test
    public void testSystemUnsafe2() {
        hazardPanel.setAltitudeFault(true);
        assertFalse(hazardPanel.isSystemSafe());
    }

    @Test
    public void testSystemUnsafe3() {
        hazardPanel.setAttitudeFault(true);
        assertFalse(hazardPanel.isSystemSafe());
    }

    @Test
    public void testSystemUnsafe4() {
        hazardPanel.setSensorFault(true);
        assertFalse(hazardPanel.isSystemSafe());
    }

    // Below tests check that if there is a airspeed or attitude or altitude fault, then sensor fault is true
    @Test
    public void testSensorFault1() {
        hazardPanel.setAirspeedFault(true);
        assertTrue(hazardPanel.isSensorFault());
    }

    @Test
    public void testSensorFault2() {
        hazardPanel.setAltitudeFault(true);
        assertTrue(hazardPanel.isSensorFault());
    }

    @Test
    public void testSensorFault3() {
        hazardPanel.setAttitudeFault(true);
        assertTrue(hazardPanel.isSensorFault());
    }

    @Test
    public void testAddConditionLight() {
        GridBagConstraints constraints = new GridBagConstraints();
        hazardPanel.addConditionLight("testCondition", 0, constraints);

        Map<String, JPanel> conditionLights = hazardPanel.getConditionLights();
        assertTrue(conditionLights.containsKey("testCondition")); 
        assertEquals(Color.GRAY, conditionLights.get("testCondition").getBackground()); 
    }

    @Test
    public void testUpdateConditionColor() {
        hazardPanel.updateConditionColor("systemSafe", Color.GREEN);
        JPanel light = hazardPanel.getConditionLights().get("systemSafe");
        assertNotNull(light); 
        assertEquals(Color.GREEN, light.getBackground()); 
    }
    
    @Test
    public void testUpdateSystemSafe() {
        hazardPanel.setAirspeedFault(true);
        hazardPanel.updateSystemSafe();
        assertFalse(hazardPanel.isSystemSafe()); 

        hazardPanel.setAirspeedFault(false);
        hazardPanel.updateSystemSafe();
        assertTrue(hazardPanel.isSystemSafe()); 
    }

    @Test
    public void testUpdateSensorFault() {
        hazardPanel.setAirspeedFault(true);
        hazardPanel.updateSensorFault();
        assertTrue(hazardPanel.isSensorFault()); 

        hazardPanel.setAirspeedFault(false);
        hazardPanel.updateSensorFault();
        assertFalse(hazardPanel.isSensorFault()); 
    }

}