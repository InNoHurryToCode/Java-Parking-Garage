package simulation;

import java.awt.*;

/**
 * Car contains a generic implementation
 * @author Hanzehogeschool of Applied Sciences
 */
public abstract class Car {
    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;

    /**
     * Constructor for objects of class Car
     * @author Hanzehogeschool of Applied Sciences
     */
    public Car() {

    }

    /**
     * Get the car's location
     * @author Hanzehogeschool of Applied Sciences
     * @return the car's location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the car to a new location
     * @author Hanzehogeschool of Applied Sciences
     * @param location the car's location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Get the amount of minutes left before the car leaves the spot
     * @author Hanzehogeschool of Applied Sciences
     * @return the amount of minutes left
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Set the amount of minutes left before the car leaves the spot
     * @author Hanzehogeschool of Applied Sciences
     * @param minutesLeft the amount of minutes left
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    /**
     * Get if of the car is paying
     * @author Hanzehogeschool of Applied Sciences
     * @return if the car is paying
     */
    public boolean getIsPaying() {
        return isPaying;
    }

    /**
     * Set if the car is paying
     * @author Hanzehogeschool of Applied Sciences
     * @param isPaying if the car is paying
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * Get if the car has to pay
     * @author Hanzehogeschool of Applied Sciences
     * @return
     */
    public boolean getHasToPay() {
        return hasToPay;
    }

    /**
     * Set if the car has to pay
     * @author Hanzehogeschool of Applied Sciences
     * @param hasToPay if the car has to pay
     */
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    /**
     * Update the car
     * @author Hanzehogeschool of Applied Sciences
     */
    public void tick() {
        --minutesLeft;
    }

    /**
     * Get the car color
     * @author Hanzehogeschool of Applied Sciences
     * @return the color
     */
    public abstract Color getColor();
}