package simulation.views;

import simulation.models.Car;
import simulation.models.Location;
import simulation.services.GarageSimulator;

import javax.swing.*;
import java.awt.*;

/**
 * SimulatorView contains all the view logic and partial simulation logic
 * @author Hanzehogeschool of Applied Sciences
 */
public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private GarageSimulator garageSimulator;

    /**
     * Simulator view constructor
     * @author Hanzehogeschool of Applied Sciences
     * @param garageSimulator the garage simulator
     */
    public SimulatorView(GarageSimulator garageSimulator) {
        this.carParkView = new CarParkView();
        this.garageSimulator = garageSimulator;

        Container contentPane = getContentPane();

        contentPane.add(this.carParkView, BorderLayout.CENTER);
        pack();
        setVisible(true);
        this.tick();
    }

    /**
     * Update the view
     * @author Hanzehogeschool of Applied Sciences
     */
    public void tick() {
        this.carParkView.updateView();
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

            for (int floor = 0; floor < garageSimulator.getNumberOfFloors(); ++floor) {
                for (int row = 0; row < garageSimulator.getNumberOfRows(); ++row) {
                    for (int place = 0; place < garageSimulator.getNumberOfPlaces(); ++place) {
                        Location location = new Location(floor, row, place);
                        Car car = garageSimulator.getCarAt(location);
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
