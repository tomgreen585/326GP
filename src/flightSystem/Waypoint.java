package flightSystem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Waypoint class represents a point in the flight system.
 * Each waypoint has a location and can be drawn on a graphics context.
 */
public class Waypoint {
    private final Point location;

    /**
     * Constructs a Waypoint object with the specified location.
     *
     * @param location the location of the waypoint
     */
    public Waypoint(Point location) {
        this.location = location;
    }

    /**
     * Draws the waypoint on the specified graphics context.
     *
     * @param g the graphics context to draw on
     */
    public void draw(Graphics g) {
        assert g != null : "Graphics context cannot be null";
        g.setColor(Color.RED);
        g.fillOval(location.x - 5, location.y - 5, 10, 10);
        g.setColor(Color.BLACK);
        g.drawOval(location.x - 5, location.y - 5, 10, 10);
        assert g.getColor() != null : "Color should not be null after drawing";
    }

    /**
     * Gets the location of the waypoint as a Point object.
     *
     * @return the location of the waypoint as a Point object
     */
    public Point getPoint() {
        assert location != null : "Location should not be null";
        return location;
    }
}