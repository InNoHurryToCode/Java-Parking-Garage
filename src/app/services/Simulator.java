package app.services;

import daytime.Daytime;
import daytime.DaytimeSimulator;
import app.views.SimulatorView;

/**
 * Simulator contains most of the simulation logic
 * @author Hanzehogeschool of Applied Sciences
 */
public class Simulator {
    private Daytime startDayTime;
    private Daytime endDayTime;
    private DaytimeSimulator daytimeSimulator;
    private GarageSimulator garageSimulator;
    private CarSimulator carSimulator;
    private SimulatorView simulatorView;
    private int tickPause = 100;

    /**
     * The Simulator constructor
     * @author Hanzehogeschool of Applied Sciences
     */
    public Simulator() {
        this.startDayTime = new Daytime();
        this.endDayTime = new Daytime();
        this.endDayTime.hours = 1;
        this.daytimeSimulator = new DaytimeSimulator(this.startDayTime);
        this.garageSimulator = new GarageSimulator(3, 6, 30);
        this.carSimulator = new CarSimulator(daytimeSimulator, garageSimulator);
        this.simulatorView = new SimulatorView(garageSimulator);
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
        this.carSimulator.tick();
        this.garageSimulator.tick();
        this.simulatorView.tick();

    	// Pause.
        try {
            Thread.sleep(this.tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
