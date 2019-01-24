package simulation;

import java.util.Random;
import java.awt.*;

/**
 * ParkingPassCar is parking pass implementation of Car
 * @author Hanzehogeschool of Applied Sciences
 */
public class ParkingPassCar extends Car {
	private static final Color COLOR = Color.blue;

    /**
     * The ParkingPassCar constructor
     * @author Hanzehogeschool of Applied Sciences
     */
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int)(15 + random.nextFloat() * 3 * 60);

        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    /**
     * Get the car color
     * @author Hanzehogeschool of Applied Sciences
     * @return the color
     */
    public Color getColor() {
        return COLOR;
    }
}
