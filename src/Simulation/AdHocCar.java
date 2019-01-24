package simulation;

import java.util.Random;
import java.awt.*;

/**
 * AdHocCar is an ad hoc implementation of Car
 * @author Hanzehogeschool of Applied Sciences
 */
public class AdHocCar extends Car {
	private static final Color COLOR = Color.red;

    /**
     * The AdHocCar constructor
     * @author Hanzehogeschool of Applied Sciences
     */
    public AdHocCar() {
    	Random random = new Random();
    	int stayMinutes = (int)(15 + random.nextFloat() * 3 * 60);

    	this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
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
