package hxh;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Max William S. Filgueira, João Pedro de A. Paula
 */
public class Seaweed extends AquaticLife implements Actor
{
    private static final double BREEDING_PROBABILITY = 0.10;
    private static final int MAX_SEAWEED_PER_ROUND = 3;

    /**
     * Constructor for seaweeds. Create a new seaweed in the given ocean and
     * location.
     *
     * @param ocean The ocean to place the seaweed
     * @param location Where in the ocean to place the seaweed
     * @return A new seaweed
     */
    public Seaweed(Ocean ocean, Location location)
    {
        super(ocean,location);
        setInOcean(location);
    }

    /**
     * Make the seaweed act. In this case, it will feed and give birth to new
     * seaweeds if fed enough.
     */
    public void act(List<Actor> actors)
    {
        feed();

        if (isAlive()) {
            if (getfoodLevel() == 10)
                giveBirth(actors);
        }
    }

    /**
     * Increment the food level of the seaweed by one.
     */
    public void feed()
    {
        setfoodLevel(getfoodLevel() + 1);
    }

    /**
     * Place the seaweed at the new location in the given ocean.
     *
     * @param newLocation The seaweed's new location.
     */
    public void setInOcean(Location newLocation)
    {
        Location location = getLocation();
        Ocean ocean = getOcean();

        if (location != null) {
            ocean.clearSeaweed(location);
        }

        location = newLocation;
        ocean.place(this, newLocation);
    }

    /**
     * Check whether or not this seaweed is to give birth at this step.
     * New births will be made into free adjacent locations.
     *
     * @param newSeaweed A list to add newly born seaweeds to.
     */
    private void giveBirth(List<Actor> newSeaweed)
    {
        // New seaweeds are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = this.getFreeAdjacentLocations(getLocation());
        Ocean ocean = getOcean();
        int births = breed();

        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            newSeaweed.add(new Seaweed(ocean, loc));
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        Random rand = getRand();

        if (rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_SEAWEED_PER_ROUND) + 1;
        }

        return births;
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     * The same as the one in ocean but this time it searches
     * for spaces without seaweed.
     *
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        Ocean ocean = getOcean();
        List<Location> free = new ArrayList<Location>();
        List<Location> adjacent = ocean.adjacentLocations(location);

        for (Location next : adjacent) {
            if (ocean.getSeaweedAt(next) == null) {
                free.add(next);
            }
        }

        return free;
    }

    /**
     * Indicate that the seaweed is no longer alive.
     * It is removed from the ocean.
     */
    @Override
    public void setDead()
    {
        death();

        Ocean ocean = getOcean();
        Location location = getLocation();

        if (location != null) {
            ocean.clearSeaweed(location);
            location = null;
            setOcean(null);
        }
    }
}
