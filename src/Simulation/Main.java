package simulation;

import simulation.services.Simulator;

/**
 * The main application class
 * @author Merijn Hendriks
 */
public class Main {
    /**
     * The main application function
     * @author Merijn Hendriks
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.run();
    }
}
