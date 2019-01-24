package simulation;
import java.util.LinkedList;
import java.util.Queue;

/**
 * CarQueue is a queue for cars
 * @author Hanzehogeschool of Applied Sciences
 */
public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    /**
     * Adds a car to the queue
     * @author Hanzehogeschool of Applied Sciences
     * @param car the car to add
     * @return whenever the car is added to the queue
     */
    public boolean addCar(Car car) {
        return this.queue.add(car);
    }

    /**
     * Removes a car fom the queue
     * @author Hanzehogeschool of Applied Sciences
     * @return the first car in the queue
     */
    public Car removeCar() {
        return this.queue.poll();
    }

    /**
     * Get the amount of cars in the queue
     * @author Hanzehogeschool of Applied Sciences
     * @return the cars in the queue
     */
    public int carsInQueue() {
        return this.queue.size();
    }
}
