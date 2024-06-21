package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import flightSystem.Plane;
import flightSystem.Waypoint;

/**
 * PlaneOnMapPanel is a JPanel that displays a plane on a map with waypoints.
 * It handles mouse clicks to add waypoints and key presses to control the plane.
 */
public class PlaneOnMapPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Plane plane;
    private List<Waypoint> waypoints;
    public BufferedImage backgroundImage;

    /**
     * Constructs a new PlaneOnMapPanel.
     * Initializes the plane and waypoints, loads the background image, and sets up event listeners.
     */
    public PlaneOnMapPanel() {
        this.plane = new Plane();
        assert plane != null : "Plane is null";
        this.waypoints = new ArrayList<>();
        assert waypoints != null : "Waypoints is null";
        loadBackgroundImage();
        initializeListeners();
        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * Loads the background image from a file.
     */
    public void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("src/resources/images/worldmap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes mouse and keyboard listeners.
     */
    private void initializeListeners() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                assert e != null : "MouseEvent is null";
                handleMousePress(e);
            }
        });
    }

    protected void paintComponent(Graphics g) {
        assert g != null : "Graphics object is null";
        super.paintComponent(g);
        drawBackgroundImage(g);
        drawPlaneAndWaypoints(g);
    }

    /**
     * Draws the background image.
     * @param g the Graphics object to draw on
     */
    private void drawBackgroundImage(Graphics g) {
        assert g != null : "Graphics object is null";
        assert backgroundImage != null : "Background image is null";
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Draws the plane and its waypoints.
     * @param g the Graphics object to draw on
     */
    private void drawPlaneAndWaypoints(Graphics g) {
        assert g != null : "Graphics object is null";
        plane.draw(g);
        assert waypoints != null : "Waypoints is null";
        for (Waypoint waypoint : waypoints) {
            waypoint.draw(g);
        }
    }

    /**
     * Handles mouse press events to add waypoints.
     * @param e the MouseEvent that occurred
     */
    public void handleMousePress(MouseEvent e) {
        assert e != null : "MouseEvent is null";
        Point clickPoint = e.getPoint();
        assert waypoints != null : "Waypoints is null";
        if (waypoints.size() < 2) {
            addWaypoint(clickPoint);
        }
    }

    /**
     * Adds a new waypoint at the specified point and updates the plane.
     * @param point the point where the waypoint is added
     */
    public void addWaypoint(Point point) {
        assert point != null : "Point is null";
        assert waypoints != null : "Waypoints is null";
        waypoints.add(new Waypoint(point));
        plane.addWaypoint(point);
        repaint();
    }

    /**
     * Updates the plane's position and triggers a repaint.
     * @param e the ActionEvent that occurred
     */
    public void update(ActionEvent e) {
        assert e != null : "ActionEvent is null";
        assert waypoints != null : "Waypoints is null";
        if (waypoints != null && !waypoints.isEmpty()) {
            plane.moveToFirstPoint();
        } else {
            plane.randomMovement(getWidth(), getHeight());
        }
        repaint();
    }

    /**
     * Gets the plane.
     * @return the plane
     */
    public Plane getPlane() {
        assert plane != null : "Plane is null";
        return plane;
    }

    /**
     * Gets the list of waypoints.
     * @return the list of waypoints
     */
    public List<Waypoint> getWaypoints() {
        assert waypoints != null : "Waypoints is null";
        return waypoints;
    }
}
