package simulation.services;

import daytime.Daytime;
import daytime.DaytimeSimulator;
import daytime.DaytimeUtility;
import daytime.Weekday;

import java.util.Random;

/**
 * CarSimulator contains logic to simulate cars
 * @author Merijn Hendriks
 */
public class CarSimulator {
    private static final String AD_HOC = "1";
    private static final String PASS = "2";

    private DaytimeSimulator daytimeSimulator;
    private GarageSimulator garageSimulator;
    private int weekDayArrivals= 100; // average number of arriving cars per hour
    private int weekendArrivals = 200; // average number of arriving cars per hour
    private int weekDayPassArrivals= 50; // average number of arriving cars per hour
    private int weekendPassArrivals = 5; // average number of arriving cars per hour

    /**
     * The CarSimulator constructor
     * @author Merijn Hendriks
     * @param daytimeSimulator the daytime simulator
     * @param garageSimulator the garage simulator
     */
    public CarSimulator(DaytimeSimulator daytimeSimulator, GarageSimulator garageSimulator) {
        this.daytimeSimulator = daytimeSimulator;
        this.garageSimulator = garageSimulator;
    }

    /**
     * Update simulation
     * @author Merijn Hendriks
     */
    public void tick() {
        this.carsArriving();
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
