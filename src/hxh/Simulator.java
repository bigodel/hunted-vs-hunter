package hxh;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * (Fill in description and author info here)
 */
public class Simulator
{
    // Constants for default values for the simulation
    // default height
    private static final Integer DEFAULT_HEIGHT = 50;
    // default width
    private static final Integer DEFAULT_WIDTH = 50;
    // probability for creating a sardine
    private static final double SARDINE_PROBABILITY = 0.08;
    // probability for creating a tuna
    private static final double TUNA_PROBABILITY = 0.05;
    // probability for creating a shark
    private static final double SHARK_PROBABILITY = 0.02;
    // probability for creating a seaweed
    private static final double SEAWEED_PROBABILITY = 0.1;

    // List of actors that will act
    private ArrayList<Actor> actors;
    // Step of the simulation
    private Integer step;
    // The ocean where everything happens
    private Ocean ocean;
    // A way to graphically view the simulation
    private SimulatorView simView;

    public Simulator () {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    public Simulator(int height, int width)
    {
        if (height <= 0 || width <= 0) {
            System.out.println("Dimensions must be greater than 0.");
            System.out.println("Using default values.");
            height = DEFAULT_HEIGHT;
            width = DEFAULT_WIDTH;
        }

        actors = new ArrayList<Actor>();
        step = 0;
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);

        // define in which color each fish should be shown
        // default fish color
        simView.setColor(Fish.class, Color.red);
        // color for each type of fish
        simView.setColor(Sardine.class, Color.magenta);
        simView.setColor(Tuna.class, Color.yellow);
        simView.setColor(Shark.class, Color.blue);
        // simView.setColor(Seaweed.class, Color.green);

        // Setup the starting point
        reset();
    }

    /**
     * Function to reset the simulation.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        populate();

        simView.showStatus(step, ocean);
    }

    public void simulateOneStep()
    {
        step++;

        // Create an array list for the new actors that might be born
        ArrayList<Actor> newActors = new ArrayList<Actor>();

        // Let them all act
        for (Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newActors);
            if (! actor.isAlive()) {
                it.remove();
            }
        }

        actors.addAll(newActors);

        simView.showStatus(step, ocean);
    }

    /**
     * Populate the ocean with random aquatic life forms.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        ocean.clear();
        for (int row = 0; row < ocean.getHeight(); ++row) {
            for (int col = 0; col < ocean.getWidth(); ++col) {
                if (rand.nextDouble() <= SEAWEED_PROBABILITY) {
                    Location loc = new Location(row, col);
                    Seaweed weed = new Seaweed(ocean, loc);
                    actors.add(weed);
                }
                else if (rand.nextDouble() <= SARDINE_PROBABILITY) {
                    Location loc = new Location(row, col);
                    Sardine sardine = new Sardine(ocean, loc);
                    actors.add(sardine);
                }
                else if (rand.nextDouble() <= TUNA_PROBABILITY) {
                    Location loc = new Location(row, col);
                    Tuna tuna = new Tuna(ocean, loc);
                    actors.add(tuna);
                }
                else if (rand.nextDouble() <= SHARK_PROBABILITY) {
                    Location loc = new Location(row, col);
                    Shark shark = new Shark(ocean, loc);
                    actors.add(shark);
                }
            }
        }
    }

    public void run(int steps)
    {
        simView.showStatus(0, ocean);

        for (int i = 0; i < steps; ++i) {
            simulateOneStep();
        }

    }

    public static void main(String[] args)
    {
        Simulator sim = new Simulator(50, 60);
        sim.run(1000);
    }
}
