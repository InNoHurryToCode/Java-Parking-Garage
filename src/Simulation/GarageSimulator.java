package simulation;

/**
 * GarageSimulation contains logic to simulate the garage
 * @author Merijn Hendriks
 */
public class GarageSimulator {
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    private int enterSpeed = 3; // number of cars that can enter per minute
    private int paymentSpeed = 7; // number of cars that can pay per minute
    private int exitSpeed = 5; // number of cars that can leave per minute

    /**
     * The GarageSimulator contructor
     * @author Merijn Hendriks
     * @param numberOfFloors the amount of floors to draw
     * @param numberOfRows the amount of rows to draw
     * @param numberOfPlaces the amount of places to draw
     */
    public GarageSimulator(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.entranceCarQueue = new CarQueue();
        this.entrancePassQueue = new CarQueue();
        this.paymentCarQueue = new CarQueue();
        this.exitCarQueue = new CarQueue();
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors*numberOfRows*numberOfPlaces;
        this.cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
    }

    /**
     * Update simulation
     * @author Merijn Hendriks
     */
    public void tick() {
        this.handleExit();
        this.handleEntrance();
        this.handleCars();
    }

    /**
     * Update entrance queues
     * @author Hanzehogeschool of Applied Sciences
     */
    private void handleEntrance() {
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
     * Update entering cars
     * @author Hanzehogeschool of Applied Sciences
     * @param queue
     */
    private void carsEntering(CarQueue queue) {
        int i = 0;

        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 && this.getNumberOfOpenSpots() > 0 && i < this.enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = this.getFirstFreeLocation();

            this.setCarAt(freeLocation, car);
            ++i;
        }
    }

    /**
     * Update cars ready to leave
     * @author Hanzehogeschool of Applied Sciences
     */
    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = this.getFirstLeavingCar();

        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                this.paymentCarQueue.addCar(car);
            } else {
                this.carLeavesSpot(car);
            }

            car = this.getFirstLeavingCar();
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
     * Make the car leave their spot
     * @author Hanzehogeschool of Applied Sciences
     * @param car the car to leave it's spot
     */
    private void carLeavesSpot(Car car) {
        this.removeCarAt(car.getLocation());
        this.exitCarQueue.addCar(car);
    }

    /**
     * Get the amount of floors
     * @author Hanzehogeschool of Applied Sciences
     * @return the amount of floors
     */
    public int getNumberOfFloors() {
        return this.numberOfFloors;
    }

    /**
     * Get the amount of rows
     * @author Hanzehogeschool of Applied Sciences
     * @return the amount of rows
     */
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    /**
     * Get the amount of places
     * @author Hanzehogeschool of Applied Sciences
     * @return the amount of places
     */
    public int getNumberOfPlaces() {
        return this.numberOfPlaces;
    }

    /**
     * Get the amount of open spots
     * @author Hanzehogeschool of Applied Sciences
     * @return the amount of open spots
     */
    public int getNumberOfOpenSpots() {
        return this.numberOfOpenSpots;
    }

    /**
     * Get the car from a specific location
     * @author Hanzehogeschool of Applied Sciences
     * @param location the car location
     * @return the car on the location
     */
    public Car getCarAt(Location location) {
        if (!this.locationIsValid(location)) {
            return null;
        }

        return this.cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Set the car to a specific location
     * @author Hanzehogeschool of Applied Sciences
     * @param location the car location
     * @param car the car to assign to the location
     * @return whenever the car is set to the position
     */
    public boolean setCarAt(Location location, Car car) {
        if (!this.locationIsValid(location)) {
            return false;
        }

        Car oldCar = this.getCarAt(location);

        if (oldCar == null) {
            this.cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            --this.numberOfOpenSpots;

            return true;
        }

        return false;
    }

    /**
     * Remove the car from a specific location
     * @author Hanzehogeschool of Applied Sciences
     * @param location the car location
     * @return the removed car
     */
    public Car removeCarAt(Location location) {
        if (!this.locationIsValid(location)) {
            return null;
        }

        Car car = this.getCarAt(location);

        if (car == null) {
            return null;
        }

        this.cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        this.numberOfOpenSpots++;
        return car;
    }

    /**
     * Get the first available location
     * @author Hanzehogeschool of Applied Sciences
     * @return the first available location
     */
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < this.getNumberOfFloors(); ++floor) {
            for (int row = 0; row < this.getNumberOfRows(); ++row) {
                for (int place = 0; place < this.getNumberOfPlaces(); ++place) {
                    Location location = new Location(floor, row, place);

                    if (this.getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Get the first car that's about to leave
     * @author Hanzehogeschool of Applied Sciences
     * @return the first leaving car
     */
    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < this.getNumberOfFloors(); ++floor) {
            for (int row = 0; row < this.getNumberOfRows(); ++row) {
                for (int place = 0; place < this.getNumberOfPlaces(); ++place) {
                    Location location = new Location(floor, row, place);

                    Car car = this.getCarAt(location);

                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Update the simulation
     * @author Hanzehogeschool of Applied Sciences
     */
    public void handleCars() {
        for (int floor = 0; floor < this.getNumberOfFloors(); ++floor) {
            for (int row = 0; row < this.getNumberOfRows(); ++row) {
                for (int place = 0; place < this.getNumberOfPlaces(); ++place) {
                    Location location = new Location(floor, row, place);
                    Car car = this.getCarAt(location);

                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    /**
     * Check if the location is valid
     * @author Hanzehogeschool of Applied Sciences
     * @param location the location to check
     * @return if the location is valid
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();

        if (floor < 0 || floor >= this.numberOfFloors || row < 0 || row > this.numberOfRows || place < 0 || place > this.numberOfPlaces) {
            return false;
        }

        return true;
    }

    /**
     * Creates a new AdHocCar at the entrance
     * @author Merijn Hendriks
     */
    public void addArrivingAdHocCar() {
        this.entranceCarQueue.addCar(new AdHocCar());
    }

    /**
     * Creates a new ParkingPassCar at the entrance
     * @author Merijn Hendriks
     */
    public void addArrivingPassCar() {
        this.entrancePassQueue.addCar(new ParkingPassCar());
    }
}
