package tests;

import flightSystem.Plane;
import flightSystem.Waypoint;
import gui.PlaneOnMapPanel;

import org.junit.Before;
import org.junit.Test;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.Assert.*;

public class TestPlaneOnMapPanel {

    public PlaneOnMapPanel planeOnMap;

    @Before
    public void setUp() {
        // Initialize the PlaneOnMapPanel
        planeOnMap = new PlaneOnMapPanel();

        }

    @Test
    public void testInitialization() {
    // Check if the plane and waypoints are not null
        assertNotNull(planeOnMap.getPlane());
        assertNotNull(planeOnMap.getWaypoints());

        // Check if the waypoints list is empty
        assertTrue(planeOnMap.getWaypoints().isEmpty());

        // Check if the size of waypoints list is 0
        assertEquals(0, planeOnMap.getWaypoints().size());

        // Check if the size of plane's waypoints list is 0
        // Check if the size of plane's waypoints list is 0
        assertEquals(0, planeOnMap.getPlane().getWaypoints().size());   
    }

    @Test
    public void testLoadBackgroundImage() {
        planeOnMap.loadBackgroundImage();

        // Check if the background image is not null
        assertNotNull(planeOnMap.getBackground());
    }

    @Test
    public void testAddWaypoint() {
        Point point = new Point(100, 100);
        planeOnMap.addWaypoint(point);
        List<Waypoint> waypoints = planeOnMap.getWaypoints();
        
        // Check if the size of waypoints list is 1
        assertEquals(1, waypoints.size());
        
        // Check if the added waypoint is the same as the expected point
        assertEquals(point, waypoints.get(0).getPoint());
    }

    @Test 
    public void testWaypointAddedToPlane() {
        Point point = new Point(100, 100);
        planeOnMap.addWaypoint(point);

        // Check if the size of plane's waypoints list is 1
        assertEquals(1, planeOnMap.getPlane().getWaypoints().size());

        // Check if the added waypoint is the same as the expected point
        assertEquals(point, planeOnMap.getPlane().getWaypoints().get(0));
    }

    @Test
    public void testMouseClickedWithLessThanTwoWaypoints() {
        MouseEvent mouseEvent = new MouseEvent(planeOnMap, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
                0, 100, 100, 1, false);
                planeOnMap.handleMousePress(mouseEvent);

        // Check if the size of waypoints list is 1
        assertEquals(1, planeOnMap.getWaypoints().size());
    }

    @Test
    public void testMouseClickedWithTwoWaypoints() {
        MouseEvent mouseEvent = new MouseEvent(planeOnMap, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
                0, 100, 100, 1, false);
        planeOnMap.handleMousePress(mouseEvent);

        // Check if the size of waypoints list is 1
        assertEquals(1, planeOnMap.getWaypoints().size());

        MouseEvent mouseEvent2 = new MouseEvent(planeOnMap, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
                0, 200, 200, 1, false);
        planeOnMap.handleMousePress(mouseEvent2);

        // Check if the size of waypoints list is 2
        assertEquals(2, planeOnMap.getWaypoints().size());
    }

    @Test
    public void testMouseClickedWithThreeClicks() {
        MouseEvent mouseEvent = new MouseEvent(planeOnMap, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
                0, 100, 100, 1, false);
        planeOnMap.handleMousePress(mouseEvent);

        // Check if the size of waypoints list is 1
        assertEquals(1, planeOnMap.getWaypoints().size());

        MouseEvent mouseEvent2 = new MouseEvent(planeOnMap, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
                0, 200, 200, 1, false);
        planeOnMap.handleMousePress(mouseEvent2);

        // Check if the size of waypoints list is 2
        assertEquals(2, planeOnMap.getWaypoints().size());

        MouseEvent mouseEvent3 = new MouseEvent(planeOnMap, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
                0, 300, 300, 1, false);
        planeOnMap.handleMousePress(mouseEvent3);

        // Check if the size of waypoints list is 2
        assertEquals(2, planeOnMap.getWaypoints().size());
    }

    @Test
    public void testUpdateWithWaypoints() {
        Point point = new Point(50, 50);
        planeOnMap.addWaypoint(point);

        Plane plane = planeOnMap.getPlane();

        ActionEvent event = new ActionEvent(planeOnMap, ActionEvent.ACTION_PERFORMED, "update");
        planeOnMap.update(event);

        // Check if the plane's destination (the point its goint toward) is the same as the expected point
        assertEquals(point, plane.getDestination());
    }

    
    @Test
    public void testUpdateWithoutWaypoints() {
        Plane plane = planeOnMap.getPlane();

        double initialX = plane.getPlaneX();
        double initialY = plane.getPlaneY();

        ActionEvent event = new ActionEvent(planeOnMap, ActionEvent.ACTION_PERFORMED, "update");
        planeOnMap.update(event);

        double updatedX = plane.getPlaneX();
        double updatedY = plane.getPlaneY();

        // Check if the plane's position has changed
        assertTrue(initialX != updatedX || initialY != updatedY);
    }


    @Test
    public void testPaintComponent() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        planeOnMap.paint(g);

        // Verifying the background image is drawn
        assertNotNull(planeOnMap.backgroundImage);

        // Adding waypoints and plane for verification
        planeOnMap.addWaypoint(new Point(10, 10));
        planeOnMap.addWaypoint(new Point(20, 20));

        // Simulate painting
        planeOnMap.paint(g);

        // Since we cannot verify drawing on a Graphics object directly,
        // we can check that the waypoints and plane are correctly managed in the panel
        assertEquals(2, planeOnMap.getWaypoints().size());
    }

    @Test
    public void testGetPlane() {
        Plane plane = planeOnMap.getPlane();

        // Check if the plane is not null
        assertNotNull(plane);
    }

    @Test
    public void testGetWaypoints() {
        List<Waypoint> waypoints = planeOnMap.getWaypoints();

        // Check if the waypoints list is not null
        assertNotNull(waypoints);
    }

    @Test
    public void testRandomMovement() {
        Plane plane = planeOnMap.getPlane();

        double initialX = plane.getPlaneX();
        double initialY = plane.getPlaneY();

        planeOnMap.getPlane().randomMovement(100, 100);;

        double updatedX = plane.getPlaneX();
        double updatedY = plane.getPlaneY();

        // Check if the plane's position has changed
        assertTrue(initialX != updatedX || initialY != updatedY);
    }

    @Test
    public void testGetPlaneX() {
        Plane plane = planeOnMap.getPlane();

        // Check if the plane's initial x position is 100
        assertEquals(100, plane.getPlaneX(), 0.0);
    }

    @Test
    public void testGetPlaneY() {
        Plane plane = planeOnMap.getPlane();

        // Check if the plane's initial y position is 100
        assertEquals(100, plane.getPlaneY(), 0.0);
    }

    @Test
    public void testSetPlaneX() {
        Plane plane = planeOnMap.getPlane();
        plane.setPlaneX(100);

        // Check if the plane's x position is 100
        assertEquals(100, plane.getPlaneX(), 0.0);
    }

    @Test
    public void testSetPlaneY() {
        Plane plane = planeOnMap.getPlane();
        plane.setPlaneY(100);

        // Check if the plane's y position is 100
        assertEquals(100, plane.getPlaneY(), 0.0);
    }
}