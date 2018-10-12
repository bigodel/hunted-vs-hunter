package hxh;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a sardine.
 * sardines age, move, breed, and die.
 * They eat plankton.
 * They exhibit flocking behaviour - they tend to seek company.
 * If they spot a predator close by, they panic.
 *
 * @author Max William S. Filgueira, João Pedro de A. Paula
 * @version 2018.10.09
 */
public class Sardine extends Fish implements Actor
{
    private final double BREEDING_PROBABILITY = 1;
    private final int MAX_BREED_PER_ROUND = 10;
    private final int BREEDING_AGE = 1;

    /**
     * Constructor for the Sardine to place it in a location in the ocean. Also,
     * set the maximum age for it to live.
     *
     * @param ocean The ocean to place the sardine
     * @param location The location to place the sardine
     * @return A new sardine
     */
    public Sardine(Ocean ocean, Location location)
    {
        super(ocean, location);
        setMaxAge(25);
    }

    /**
     * Make the sardine act. In this case, it will get hungrier, older and maybe
     * give birth to new sardines and also move. If the sardine is old enough or
     * starves, dying is also a part of act.
     */
    public void act(List<Actor> actors)
    {
        incrementHunger();
        incrementAge();

        if (isAlive()) {
            giveBirth(actors);
            Location loc = getLocation();
            Location newLocation = findFood(loc);

            if (newLocation != null) {
                setInOcean(newLocation);
            }
            else {
                newLocation = getOcean().freeAdjacentLocation(loc);

                if (newLocation != null)
                    setInOcean(newLocation);
            }

            if (newLocation == null){
                setDead();
            }
        }
    }

    /**
     * Eat food at the location.
     *
     * @param location Location where the food is
     */
    public void eat(Location location)
    {
        Seaweed seaweed = (Seaweed) getOcean().getSeaweedAt(location);
        this.setfoodLevel(seaweed.getfoodLevel());
        setInOcean(location);
    }

    /**
     * Look for food in the surroundings of the sardine. If the sardine doesn't
     * find food in its surroundings, it dies.
     *
     * @param location Position of the sardine in the ocean
     * @return The new location of the sardine
     */
    private Location findFood(Location location)
    {
        Ocean ocean = getOcean();
        List<Location> adjacent = ocean.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();

        while (it.hasNext()) {
            Location where = it.next();
            Seaweed seaweed = ocean.getSeaweedAt(where);

            if (seaweed != null) {
                if (seaweed.isAlive()) {
                    setfoodLevel(seaweed.getfoodLevel());
                    seaweed.setDead();
                    // Remove the eaten seaweed from the field.
                    return where;
                }
            }
        }

        return null;
    }

    /**
     * Check whether or not this sardine is to give birth at this step.
     * New births will be made into free adjacent locations.
     *
     * @param newSardine A list to add newly born rabbits to.
     */
    protected void giveBirth(List<Actor> newSardine)
    {
        // New sardines are born into adjacent locations.
        // Get a list of adjacent free locations.
        Ocean ocean = getOcean();
        List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
        int births = breed();

        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            newSardine.add(new Sardine(ocean, loc));
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
            births = rand.nextInt(MAX_BREED_PER_ROUND) + 1;
        }

        return births;
    }

    /**
     * A sardine can breed if it has reached the breeding age.
     *
     * @return true if the sardine can breed, false otherwise.
     */
    private boolean canBreed()
    {
       return getAge() >= BREEDING_AGE;
    }
}
