package simulation;

import daytime.Daytime;
import daytime.DaytimeSimulator;
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
    private Daytime startDayTime;
    private Daytime endDayTime;
    private SimulatorView simulatorView;
    private DaytimeSimulator daytimeSimulator;
    private GarageSimulator garageSimulator;
    private int tickPause = 100;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    /**
     * The Simulator constructor
     * @author Hanzehogeschool of Applied Sciences
     */
    public Simulator() {
        this.startDayTime = new Daytime();
        this.endDayTime = new Daytime();
        this.endDayTime.hours = 1;
        this.simulatorView = new SimulatorView(3, 6, 30);
        this.daytimeSimulator = new DaytimeSimulator(this.startDayTime);
        this.garageSimulator = new GarageSimulator(simulatorView);
    }

    /**
     * Initiate the simulation
     * @author Hanzehogeschool of Applied Sciences
     */
    public void run() {
        while (true) {
            if (daytimeSimulator.getDaytime().equals(endDayTime)) {
                break;
            }

            this.tick();
        }
    }

    /**
     * Update the simulation
     * @author Hanzehogeschool of Applied Sciences
     */
    private void tick() {
        this.daytimeSimulator.tick();
        this.carsArriving();
        this.garageSimulator.tick();
        this.updateViews();

    	// Pause.
        try {
            Thread.sleep(this.tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
     * Get number of cars
     * @author Hanzehogeschool of Applied Sciences
     * @param weekDay the number of weekday cars
     * @param weekend the number of weekend cars
     * @return random number of cars based on gaussian
     */
    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        Daytime daytime = this.daytimeSimulator.getDaytime();

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
                    this.garageSimulator.addArrivingAdHocCar();
                }
                break;

            case PASS:
                for (int i = 0; i < numberOfCars; ++i) {
                    this.garageSimulator.addArrivingPassCar();
                }
                break;
        }
    }
}
