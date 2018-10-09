package src;

import java.awt.Color;

/**
 * (Fill in description and author info here)
 */

public class Simulator
{
    // Constants for default values for the simulation
    // default height
    private static final int DEFAULT_HEIGHT = 50;
    // default width
    private static final int DEFAULT_WIDTH = 50;
    // probability for creating a sardine
    private static final double SARDINE_PROBABILITY = 0.05;
    // probability for creating a tuna
    private static final double TUNA_PROBABILITY = 0.08;
    // probability for creating a shark
    private static final double SHARK_PROBABILITY = 0.02;
    // probability for creating a seaweed
    private static final double SEAWEED_PROBABILITY = 0.1;

    // The ocean where everything happens
    private Ocean ocean;
    // A way to graphically view the simulation
    private SimulatorView simView;

    public Simulator () {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    public Simulator(int height, int width)
    {
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);

        // define in which color fish should be shown
        simView.setColor(Fish.class, Color.red);
    }

    public static void main(String[] args)
    {
        Simulator sim = new Simulator(50, 60);
        sim.run(1000);
    }

    public void run(int steps)
    {
        // put the simulation main loop here

        simView.showStatus(0, ocean);
    }
}
