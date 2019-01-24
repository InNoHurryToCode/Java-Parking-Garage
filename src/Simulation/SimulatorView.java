package simulation;

import javax.swing.*;
import java.awt.*;

/**
 * SimulatorView contains all the view logic and partial simulation logic
 * @author Hanzehogeschool of Applied Sciences
 */
public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;

    /**
     * Simulator view constructor
     * @author Hanzehogeschool of Applied Sciences
     * @param numberOfFloors the amount of floors to draw
     * @param numberOfRows the amount of rows to draw
     * @param numberOfPlaces the amount of places to draw
     */
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors*numberOfRows*numberOfPlaces;
        this.cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        this.carParkView = new CarParkView();

        Container contentPane = getContentPane();

        contentPane.add(this.carParkView, BorderLayout.CENTER);
        pack();
        setVisible(true);
        this.updateView();
    }

    /**
     * Update the view
     * @author Hanzehogeschool of Applied Sciences
     */
    public void updateView() {
        this.carParkView.updateView();
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
    public void tick() {
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
     * CaParkView contains the view logic
     * @author Hanzehogeschool of Applied Sciences
     */
    private class CarParkView extends JPanel {
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         * @author Hanzehogeschool of Applied Sciences
         */
        public CarParkView() {
            this.size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         * @author Hanzehogeschool of Applied Sciences
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         * @author Hanzehogeschool of Applied Sciences
         */
        public void paintComponent(Graphics g) {
            if (this.carParkImage == null) {
                return;
            }
    
            Dimension currentSize = this.getSize();

            if (this.size.equals(currentSize)) {
                g.drawImage(this.carParkImage, 0, 0, null);
            } else {
                // Rescale the previous image.
                g.drawImage(this.carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }

        /**
         * Update the JPanel
         * @author Hanzehogeschool of Applied Sciences
         */
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!this.size.equals(getSize())) {
                this.size = getSize();
                this.carParkImage = createImage(this.size.width, this.size.height);
            }

            Graphics graphics = this.carParkImage.getGraphics();

            for (int floor = 0; floor < getNumberOfFloors(); ++floor) {
                for (int row = 0; row < getNumberOfRows(); ++row) {
                    for (int place = 0; place < getNumberOfPlaces(); ++place) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        Color color = car == null ? Color.white : car.getColor();

                        this.drawPlace(graphics, location, color);
                    }
                }
            }

            repaint();
        }
    
        /**
         * Paint a place on this car park view in a given color.
         * @author Hanzehogeschool of Applied Sciences
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20, 60 + location.getPlace() * 10, 20 - 1, 10 - 1);
            // TODO use dynamic size or constants
        }
    }
}
