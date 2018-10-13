package hxh;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * The simulation itself. This is the class where everything happens and all of
 * the parts come together to create the simulation of the aquatic environment.
 *
 * @author João Pedro de A. Paula, Max William S. Filgueira
 * @version 2018.10.10
 */
public class Simulator
{
    // Constants for default values for the simulation
    // default height
    private static final Integer DEFAULT_HEIGHT = 50;
    // default width
    private static final Integer DEFAULT_WIDTH = 50;
    // probability for creating a sardine
    private static final double SARDINE_PROBABILITY = 0.1;
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

    /**
     * Default constructor for the Simulator with the default values for height
     * and width.
     *
     * @return A new Simulator
     */
    public Simulator() {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    /**
     * Constructor for the Simulator. If the height or width is less than or
     * equal to zero, print out an error message and create a simulator with the
     * default values.
     *
     * @param height The height of the simulator
     * @param width The width of the simulator
     * @return A new simulator
     */
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
        simView.setColor(Sardine.class, Color.magenta);
        simView.setColor(Tuna.class, Color.yellow);
        simView.setColor(Shark.class, Color.blue);

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
    }

    /**
     * Do only one step of the simulation.
     */
    public void simulateOneStep()
    {
        step++;

        // Create an array list for the new actors that might be born
        ArrayList<Actor> newActors = new ArrayList<Actor>();

        int sea = 0;
        int shark = 0;
        int sardine = 0;
        int tuna = 0;

        for (Actor actor : actors){
            if (actor instanceof Seaweed) sea++;
            if (actor instanceof Shark) shark++;
            if (actor instanceof Sardine) sardine++;
            if (actor instanceof Tuna) tuna++;
        }

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

    /**
     * Run a certain amout of steps of the simulation. I added a little delay to
     * make it easier to see what's happening in each step.
     *
     * @param steps Amount of steps the simulation will make
     */
    public void run(int steps) throws InterruptedException
    {
        simView.showStatus(0, ocean);

        for (int i = 0; i < steps; ++i) {
            simulateOneStep();
            Thread.sleep(200);
        }
    }

    /**
     * The main function.
     */
    public static void main(String[] args)
    {
        Simulator sim = new Simulator(100, 100);
        try {
            sim.run(100);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        System.exit(0);
    }
}
