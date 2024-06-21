package tests;

import flightSystem.Autopilot;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAutopilot {
    
    @Test
    public void testEngage() {
        Autopilot autopilot = new Autopilot();
        autopilot.engage();
        assertTrue(autopilot.getEngaged());
    }

    @Test
    public void testDisengage() {
        Autopilot autopilot = new Autopilot();
        autopilot.engage();
        autopilot.disengage();
        assertFalse(autopilot.getEngaged());
    }

    @Test
    public void testOverride() {
        Autopilot autopilot = new Autopilot();
        autopilot.override();
        assertTrue(autopilot.getOverridden());
    }

    @Test
    public void testSetMinSpeed() {
        Autopilot autopilot = new Autopilot();
        autopilot.setMinSpeed(100);
        assertEquals(100, autopilot.getMinSpeed());
    }

    @Test
    public void testSetMaxSpeed() {
        Autopilot autopilot = new Autopilot();
        autopilot.setMaxSpeed(400);
        assertEquals(400, autopilot.getMaxSpeed());
    }

    @Test
    public void testSetMinAltitude() {
        Autopilot autopilot = new Autopilot();
        autopilot.setMinAltitude(1000);
        assertEquals(1000, autopilot.getMinAltitude());
    }

    @Test
    public void testSetMaxAltitude() {
        Autopilot autopilot = new Autopilot();
        autopilot.setMaxAltitude(10000);
        assertEquals(10000, autopilot.getMaxAltitude());
    }

    @Test
    public void testSubmit() {
        Autopilot autopilot = new Autopilot();
        autopilot.submit();
        assertTrue(autopilot.getEngaged());
        assertFalse(autopilot.getDisabled());
        assertFalse(autopilot.getOverridden());
        assertFalse(autopilot.getFault());
        assertFalse(autopilot.getSubmit());
        assertFalse(autopilot.getBadData());
        assertFalse(autopilot.getInput());
    }

    @Test
    public void testBadData() {
        Autopilot autopilot = new Autopilot();
        autopilot.badData();
        assertFalse(autopilot.getEngaged());
        assertTrue(autopilot.getDisabled());
        assertFalse(autopilot.getOverridden());
        assertTrue(autopilot.getFault());
        assertFalse(autopilot.getSubmit());
        assertTrue(autopilot.getBadData());
        assertFalse(autopilot.getInput());
    }


    @Test
    public void testEngageMethod() {
        Autopilot autopilot = new Autopilot();
        autopilot.engage();
        assertTrue(autopilot.getEngaged());
        assertFalse(autopilot.getDisabled());
        assertFalse(autopilot.getOverridden());
        assertFalse(autopilot.getFault());
        assertTrue(autopilot.getSubmit());
        assertFalse(autopilot.getBadData());
        assertFalse(autopilot.getInput());
    }

    @Test
    public void testDisengageMethod() {
        Autopilot autopilot = new Autopilot();
        autopilot.engage();
        autopilot.disengage();

        assertFalse(autopilot.getEngaged());
        assertTrue(autopilot.getDisabled());
        assertFalse(autopilot.getOverridden());
        assertFalse(autopilot.getFault());
        assertTrue(autopilot.getSubmit());
        assertFalse(autopilot.getBadData());
        assertFalse(autopilot.getInput());
    }


    @Test
    public void testOverrideMethod() {
        Autopilot autopilot = new Autopilot();
        autopilot.override();
        assertFalse(autopilot.getEngaged());
        assertTrue(autopilot.getDisabled());
        assertTrue(autopilot.getOverridden());
        assertFalse(autopilot.getFault());
        assertTrue(autopilot.getSubmit());
        assertFalse(autopilot.getBadData());
        assertTrue(autopilot.getInput());
    }

    @Test
    public void testFault() {
        Autopilot autopilot = new Autopilot();
        autopilot.fault();
        assertFalse(autopilot.getEngaged());
        assertFalse(autopilot.getDisabled());
        assertFalse(autopilot.getOverridden());
        assertTrue(autopilot.getFault());
        assertFalse(autopilot.getSubmit());
        assertTrue(autopilot.getBadData());
        assertFalse(autopilot.getInput());
    }
}
