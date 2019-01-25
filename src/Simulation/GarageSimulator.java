package simulation;

/**
 * GarageSimulation contains logic to simulate the garage
 * @author Merijn Hendriks
 */
public class GarageSimulator {
    private SimulatorView simulatorView;
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private int enterSpeed = 3; // number of cars that can enter per minute
    private int paymentSpeed = 7; // number of cars that can pay per minute
    private int exitSpeed = 5; // number of cars that can leave per minute

    /**
     * The GarageSimulator contructor
     * @author Merijn Hendriks
     */
    public GarageSimulator(SimulatorView simulatorView) {
        this.simulatorView = simulatorView;
        this.entranceCarQueue = new CarQueue();
        this.entrancePassQueue = new CarQueue();
        this.paymentCarQueue = new CarQueue();
        this.exitCarQueue = new CarQueue();
    }

    /**
     * Update simulation
     * @author Merijn Hendriks
     */
    public void tick() {
        this.handleExit();
        this.handleEntrance();
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
     * Make the car leave their spot
     * @author Hanzehogeschool of Applied Sciences
     * @param car the car to leave it's spot
     */
    private void carLeavesSpot(Car car) {
        this.simulatorView.removeCarAt(car.getLocation());
        this.exitCarQueue.addCar(car);
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
