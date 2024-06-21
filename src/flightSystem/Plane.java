package flightSystem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import gui.ManagementPanel;

/**
 * The Plane class represents an aircraft in the flight system. It handles the plane's
 * properties, movement, waypoints, and rendering itself.
 */
public class Plane {
    private double planeX;
    private double planeY;

    private double speedFactor;
    private double airSpeed;
    private double altitude;
    private double pitch;
    private double roll;
    private double yaw;

    private List<Point> waypoints;
    private Point destination;
    private boolean atDestination;

    private Image planeImage;

    private double planeImageAngle;

    private static final int PLANE_WIDTH = 30;
    private static final int PLANE_HEIGHT = 30;

    private final Random random = new Random();
    private ManagementPanel managementPanel;

    /**
     * Constructor to initialize the Plane with default values.
     */
    public Plane() {
        initializePlane();
        loadImage();
    }

    /**
     * Initializes the plane's properties.
     */
    private void initializePlane() {
        this.planeX = 100;
        this.planeY = 100;
        this.speedFactor = 0.01; 
        this.airSpeed = 160;
        this.altitude = 0;
        this.pitch = 0;
        this.roll = 0;
        this.yaw = 0;
        this.planeImageAngle = Math.toRadians(15);
        this.waypoints = new ArrayList<>();
        this.atDestination = false;
    }

    /**
     * Loads the plane image.
     */
    private void loadImage() {
        planeImage = new ImageIcon("src/resources/images/boeing747.png").getImage().getScaledInstance(
                PLANE_WIDTH, PLANE_HEIGHT, Image.SCALE_SMOOTH);
    }

    /**
     * Draws the plane on the given graphics context.
     * @param g the graphics context
     */
    public void draw(Graphics g) {
        managementPanel.checkAndStopTimer();
        if (planeImage == null) {
            drawFallback(g);
        } else {
            drawPlaneImage(g);
        }
    }

    /**
     * Draws a fallback rectangle when the plane image is not available.
     * @param g the graphics context
     */
    private void drawFallback(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) planeX, (int) planeY, 10, 10); 
    }

    /**
     * Draws the plane image.
     * @param g the graphics context
     */
    private void drawPlaneImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform tx = new AffineTransform();
        tx.translate(planeX, planeY);
        tx.rotate(planeImageAngle + Math.PI / 2);
        g2d.setTransform(tx);
        g2d.drawImage(planeImage, -PLANE_WIDTH / 2, -PLANE_HEIGHT / 2, null);
        g2d.dispose();
    }

    /**
     * Moves the plane towards the first waypoint.
     */
    public void moveToFirstPoint() {
        assert destination != null : "Destination should not be null in moveToFirstPoint()";
        assert !waypoints.isEmpty() : "Waypoints should not be empty in moveToFirstPoint()";
        
        if (destination == null) return;

        if (!atDestination) {
            moveTowardsDestination();
        }

        if (atDestination) {
            proceedToNextWaypointIfNeeded();
        }
    }

    /**
     * Moves the plane towards the current destination.
     */
    private void moveTowardsDestination() {
        double dx = destination.x - planeX;
        double dy = destination.y - planeY;
        double distance = calculateDistance(dx, dy);
        planeImageAngle = Math.atan2(dy, dx);

        if (distance > PLANE_WIDTH / 5) {
            planeX += (speedFactor * airSpeed * dx / distance);
            planeY += (speedFactor * airSpeed * dy / distance);
        } else {
            atDestination = true;
            managementPanel.checkAndStopTimer();
        }
    }

    /**
     * Calculates the distance between two points.
     * @param dx the difference in x coordinates
     * @param dy the difference in y coordinates
     * @return the distance
     */
    private double calculateDistance(double dx, double dy) {
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Sets the next waypoint as the destination if the plane reaches the current one.
     */
    private void proceedToNextWaypointIfNeeded() {
        if (waypoints.size() > 1) {
            destination = waypoints.get(1);
            double dx = destination.x - planeX;
            double dy = destination.y - planeY;
            planeImageAngle = Math.atan2(dy, dx);
        }
    }

    /**
     * Proceeds to the next waypoint if the plane has reached the current destination.
     */
    public void proceedToNextWaypoint() {
        assert !waypoints.isEmpty() : "Waypoints should not be empty in proceedToNextWaypoint()";
        assert atDestination : "Plane should be at destination in proceedToNextWaypoint()";
        
        if (atDestination && !waypoints.isEmpty()) {
            waypoints.remove(0);
            if (!waypoints.isEmpty()) {
                destination = waypoints.get(0);
                atDestination = false;
                managementPanel.checkAndStopTimer();
            }
        }
    }

    /**
     * Moves the plane randomly within the given bounds.
     * @param maxWidth the maximum width of the movement area
     * @param maxHeight the maximum height of the movement area
     */
    public void randomMovement(int maxWidth, int maxHeight) {
        assert maxWidth >= 0 : "MaxWidth should be greater than 0 in randomMovement()";
        assert maxHeight >= 0 : "MaxHeight should be greater than 0 in randomMovement()";

        planeX += speedFactor * airSpeed * Math.cos(planeImageAngle);
        planeY += speedFactor * airSpeed * Math.sin(planeImageAngle);

        wrapAroundScreen(maxWidth, maxHeight);
        planeImageAngle += (random.nextDouble() - 0.5) * Math.toRadians(10);
    }

    /**
     * Wraps the plane around the screen when it moves out of bounds.
     * @param maxWidth the maximum width of the movement area
     * @param maxHeight the maximum height of the movement area
     */
    private void wrapAroundScreen(int maxWidth, int maxHeight) {
        if (planeX < 0) {
            planeX = maxWidth + PLANE_WIDTH;
        } else if (planeX > maxWidth + PLANE_WIDTH) {
            planeX = 0;
        }

        if (planeY < 0 || planeY > maxHeight) {
            planeImageAngle = -planeImageAngle;
            planeY = Math.max(0, Math.min(planeY, maxHeight));
        }
    }

    /**
     * Adds a waypoint to the plane's list of waypoints.
     * @param waypoint the waypoint to add
     */
    public void addWaypoint(Point waypoint) {
        assert waypoint != null : "Waypoint should not be null in addWaypoint()";
        assert waypoint.x >= 0 && waypoint.y >= 0 : "Waypoint coordinates should be non-negative in addWaypoint()";

        waypoints.add(waypoint);
        if (destination == null) {
            destination = waypoint;
        }
    }

    /**
     * Clears all waypoints from the plane's list.
     */
    public void clearWaypoints() {
        waypoints.clear();
        atDestination = false;
        destination = null;
    }

    /**
     * Checks if the plane is at the destination.
     * @return true if at the destination, false otherwise
     */
    public boolean isAtDestination() {
        // assert atDestination == (destination == null || (planeX == destination.x && planeY == destination.y))
        //     : "Inconsistent state for atDestination in isAtDestination()";
        return atDestination;
    }

    /**
     * Gets the list of waypoints.
     * @return the list of waypoints
     */
    public List<Point> getWaypoints() {
        assert waypoints != null : "Waypoints should not be null in getWaypoints()";
        return waypoints;
    }

    /**
     * Sets the management panel for the plane.
     * @param managementPanel the management panel
     */
    public void setManagementPanel(ManagementPanel managementPanel) {
        this.managementPanel = managementPanel;
    }

    // GETTERS AND SETTERS

    public double getPlaneX() {
        assert planeX >= 0 : "PlaneX should be non-negative in getPlaneX()";
        return planeX;
    }

    public void setPlaneX(double planeX) {
        this.planeX = planeX;
    }

    public double getPlaneY() {
        assert planeY >= 0 : "PlaneY should be non-negative in getPlaneY()";
        return planeY;
    }

    public void setPlaneY(double planeY) {
        this.planeY = planeY;
    }

    public double getAirSpeed() {
        assert airSpeed > 0 : "AirSpeed should be positive in getAirSpeed()";
        return airSpeed;
    }

    public void setAirSpeed(double airSpeed) {
        this.airSpeed = airSpeed;
    }

    public double getAltitude() {
        assert altitude >= 0 : "Altitude should be non-negative in getAltitude()";
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getPitch() {
        assert pitch >= 0 : "Pitch should be non-negative in getPitch()";
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getRoll() {
        assert roll >= 0 : "Roll should be non-negative in getRoll()";
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public Image getPlaneImage() {
        assert planeImage != null : "PlaneImage should not be null in getPlaneImage()";
        return planeImage;
    }

    public double getPlaneImageAngle() {
        assert planeImageAngle >= 0 : "PlaneImageAngle should be non-negative in getPlaneImageAngle()";
        return planeImageAngle;
    }
}
