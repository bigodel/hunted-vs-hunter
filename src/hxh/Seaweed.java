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

    public Seaweed(Ocean ocean, Location location)
    {
        super(ocean,location);
        setInOcean(location);
    }

    public void act(List<Actor> actors)
    {
        feed();

        if (isAlive()) {
            if(getfoodLevel() == 10){
                giveBirth(actors);
            }
        }
    }

    public void feed()
    {
        setfoodLevel(getfoodLevel() + 1);
    }

    /**
     * Place the seaweed at the new location in the given ocean.
     * @param newLocation The seaweed's new location.
     */
    public void setInOcean(Location newLocation)
    {
        Location location = getLocation();
        Ocean ocean = getOcean();

        if (location != null) {
            ocean.clear(location);
        }

        location = newLocation;
        ocean.place(this, newLocation);
    }

    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to add newly born rabbits to.
     */
    private void giveBirth(List<Actor> newSeaweed)
    {
        // New seaweeds are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = this.getFreeAdjacentLocations(getLocation());
        int births = breed();

        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            newSeaweed.add(new Seaweed(getOcean(),loc));
        }
    }


    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        Random rand = getRand();

        if (/*canBreed() && */rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_SEAWEED_PER_ROUND) + 1;
        }

        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    //private boolean canBreed()
    //{
    //   return age >= BREEDING_AGE;
    //}
     /**
     * Get a shuffled list of the free adjacent locations.
     * The same as the one in ocean but this time it searches
     * for spaces without Seaweed.
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
     * Indicate that the lifeform is no longer alive.
     * It is removed from the ocean.
     */
    @Override
    public void setDead()
    {
        death();
        Ocean ocean = getOcean();
        Location location = getLocation();
        if(location != null) {
            ocean.clearSeaweed(location);
            location = null;
            setOcean(null);
        }
    }
}
