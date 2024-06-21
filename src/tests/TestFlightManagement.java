package tests;

import gui.ManagementPanel;
import gui.PlaneOnMapPanel;
import avionics.SensorSimulator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

public class TestFlightManagement {
    
    private ManagementPanel managementPanel;
    
    @Before
    public void setUp() {
        // Create an instance of ManagementPanel
        managementPanel = new ManagementPanel(new SensorSimulator(), new PlaneOnMapPanel());
    }

    @Test
    public void testSetAndGetMaxAirspeed() {
        // Set the maximum airspeed
        managementPanel.setMaxAirspeed(400);
        // Assert that the getter returns the correct value
        assertEquals(400, managementPanel.getMaxAirspeed());
    }

    @Test
    public void testSetAndGetMaxAltitude() {
        // Set the maximum altitude
        managementPanel.setMaxAltitude(30000);
        // Assert that the getter returns the correct value
        assertEquals(30000, managementPanel.getMaxAltitude());
    }

    @Test
    public void testSetAndGetMinAirspeed() {
        // Set the maximum airspeed
        managementPanel.setMinAirspeed(150);
        // Assert that the getter returns the correct value
        assertEquals(150, managementPanel.getMinAirspeed());
    }

    @Test
    public void testSetAndGetMinAltitude() {
        // Set the maximum altitude
        managementPanel.setMinAltitude(1000);
        // Assert that the getter returns the correct value
        assertEquals(1000, managementPanel.getMinAltitude());
    }


    
}