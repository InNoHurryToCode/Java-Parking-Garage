package simulation;

import daytime.Daytime;
import daytime.DaytimeManager;
import daytime.DaytimeUtility;
import daytime.Weekday;

import java.util.Random;

/**
 * Simulator contains most of the simulation logic
 * @author Hanzehogeschool of Applied Sciences
 */
public class Simulator {
	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;
    private DaytimeManager daytimeManager;
    private int tickPause = 100;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour
    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    /**
     * The Simulator constructor
     * @author Hanzehogeschool of Applied Sciences
     */
    public Simulator() {
        this.entranceCarQueue = new CarQueue();
        this.entrancePassQueue = new CarQueue();
        this.paymentCarQueue = new CarQueue();
        this.exitCarQueue = new CarQueue();
        this.simulatorView = new SimulatorView(3, 6, 30);
        this.daytimeManager = new DaytimeManager();
    }

    /**
     * Initiate the simulation
     * @author Hanzehogeschool of Applied Sciences
     */
    public void run() {
        for (int i = 0; i < 10000; ++i) {
            this.tick();
        }
    }

    /**
     * Update the simulation
     * @author Hanzehogeschool of Applied Sciences
     */
    private void tick() {
        this.daytimeManager.tick();
        this.handleExit();
        this.updateViews();

    	// Pause.
        try {
            Thread.sleep(this.tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.handleEntrance();
    }

    /**
     * Update entrance queues
     * @author Hanzehogeschool of Applied Sciences
     */
    private void handleEntrance() {
        this.carsArriving();
        this.carsEntering(this.entrancePassQueue);
        this.carsEntering(this.entranceCarQueue);
    }

    /**
     * Update exit queues
     * @author Hanzehogeschool of Applied Sciences
     */
    private void handleExit() {
        this.carsReadyToLeave();
        this.carsPaying();
        this.carsLeaving();
    }

    /**
     * Update SimultorView
     * @author Hanzehogeschool of Applied Sciences
     */
    private void updateViews() {
        this.simulatorView.tick();

        // Update the car park view.
        this.simulatorView.updateView();
    }

    /**
     * Update arriving cars
     * @author Hanzehogeschool of Applied Sciences
     */
    private void carsArriving() {
    	int numberOfCars = this.getNumberOfCars(this.weekDayArrivals, this.weekendArrivals);

        this.addArrivingCars(numberOfCars, AD_HOC);

    	numberOfCars = this.getNumberOfCars(this.weekDayPassArrivals, this.weekendPassArrivals);

        this.addArrivingCars(numberOfCars, PASS);
    }

    /**
     * Update entering cars
     * @author Hanzehogeschool of Applied Sciences
     * @param queue
     */
    private void carsEntering(CarQueue queue) {
        int i = 0;

        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue() > 0 && this.simulatorView.getNumberOfOpenSpots() > 0 && i < this.enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = this.simulatorView.getFirstFreeLocation();

            this.simulatorView.setCarAt(freeLocation, car);
            ++i;
        }
    }

    /**
     * Update cars ready to leave
     * @author Hanzehogeschool of Applied Sciences
     */
    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = this.simulatorView.getFirstLeavingCar();

        while (car != null) {
        	if (car.getHasToPay()) {
	            car.setIsPaying(true);
                this.paymentCarQueue.addCar(car);
        	} else {
                this.carLeavesSpot(car);
        	}

            car = this.simulatorView.getFirstLeavingCar();
        }
    }

    /**
     * Update paying cars
     * @author Hanzehogeschool of Applied Sciences
     */
    private void carsPaying() {
        // Let cars pay.
    	int i = 0;

    	while (this.paymentCarQueue.carsInQueue() > 0 && i < this.paymentSpeed) {
            Car car = this.paymentCarQueue.removeCar();

            // TODO Handle payment.
            this.carLeavesSpot(car);
            ++i;
    	}
    }

    /**
     * Update leaving cars
     * @author Hanzehogeschool of Applied Sciences
     */
    private void carsLeaving(){
        // Let cars leave.
    	int i = 0;

    	while (this.exitCarQueue.carsInQueue() > 0 && i < this.exitSpeed) {
            this.exitCarQueue.removeCar();
            ++i;
    	}	
    }

    /**
     * Get number of cars
     * @author Hanzehogeschool of Applied Sciences
     * @param weekDay the number of weekday cars
     * @param weekend the number of weekend cars
     * @return random number of cars based on gaussian
     */
    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        Daytime daytime = this.daytimeManager.getDaytime();

        int averageNumberOfCarsPerHour = 0;

        if ((DaytimeUtility.getWeekday(daytime) == Weekday.SATURDAY) || (DaytimeUtility.getWeekday(daytime) == Weekday.SUNDAY)) {
            averageNumberOfCarsPerHour = weekend;
        } else {
            averageNumberOfCarsPerHour = weekDay;
        }

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;

        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    /**
     * Add arriving cars
     * @author Hanzehogeschool of Applied Sciences
     * @param numberOfCars the amount of cars
     * @param type the car type
     */
    private void addArrivingCars(int numberOfCars, String type) {
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; ++i) {
                this.entranceCarQueue.addCar(new AdHocCar());
            }
            break;

    	case PASS:
            for (int i = 0; i < numberOfCars; ++i) {
                this.entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;	            
    	}
    }

    /**
     * Make the car leave their spot
     * @author Hanzehogeschool of Applied Sciences
     * @param car the car to leave it's spot
     */
    private void carLeavesSpot(Car car) {
        this.simulatorView.removeCarAt(car.getLocation());
        this.exitCarQueue.addCar(car);
    }
}
