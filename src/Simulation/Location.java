package Simulation;

/**
 * Location contains the car spot properties
 * @author Hanzehogeschool of Applied Sciences
 */
public class Location {
    private int floor;
    private int row;
    private int place;

    /**
     * Constructor for objects of class Location
     * @author Hanzehogeschool of Applied Sciences
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
    }

    /**
     * Implement content equality.
     * @author Hanzehogeschool of Applied Sciences
     */
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location)obj;

            if (floor == other.getFloor() && row == other.getRow() && place == other.getPlace()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Return a string of the form floor,row,place.
     * @author Hanzehogeschool of Applied Sciences
     * @return A string representation of the location.
     */
    public String toString() {
        return floor + "," + row + "," + place;
    }

    /**
     * Use the 10 bits for each of the floor, row and place
     * values. Except for very big car parks, this should give
     * a unique hash code for each (floor, row, place) tupel.
     * @author Hanzehogeschool of Applied Sciences
     * @return A hashcode for the location.
     */
    public int hashCode() {
        return (floor << 20) + (row << 10) + place;
    }

    /**
     * Get the floor
     * @author Hanzehogeschool of Applied Sciences
     * @return The floor.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Get the row
     * @author Hanzehogeschool of Applied Sciences
     * @return The row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the place
     * @author Hanzehogeschool of Applied Sciences
     * @return The place.
     */
    public int getPlace() {
        return place;
    }

}