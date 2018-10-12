package hxh;

import java.util.List;
import java.util.Random;

/**
 * A simple model of a tuna.
 * Tuna age, move, breed, and die.
 * They eat herring.
 *
 * @author Richard Jones and Michael Kolling
 */
public class Tuna extends Fish implements Actor
{
    private final double BREEDING_PROBABILITY = 0.35;
    private final int MAX_BREED_PER_ROUND = 4;
    private static int BREEDING_AGE = 4;

    /**
     * Constructor for the Tuna to place it in a location in the ocean. Also,
     * set the maximum age for it to live.
     *
     * @param ocean The ocean to place the tuna
     * @param location The location to place the tuna
     * @return A new tuna
     */
    public Tuna(Ocean ocean, Location location)
    {
        super(ocean,location);
        setMaxAge(20);
    }

    /**
     * Eat food at the location.
     *
     * @param location Location where the food is
     */
    public void eat(Location location)
    {
        Fish fish = getOcean().getFishAt(location);

        if (fish instanceof Sardine) {
            this.setfoodLevel(fish.getfoodLevel());
            //setLocation(location);
            setInOcean(location);
            fish.setDead();
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

        if (/*canBreed() && */ rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_BREED_PER_ROUND) + 1;
        }

        return births;
    }

    /**
     * Check whether or not this tuna is to give birth at this step.
     * New births will be made into free adjacent locations.
     *
     * @param newTuna A list to add newly born tuna to.
     */
    protected void giveBirth(List<Actor> newTuna)
    {
        // New sardines are born into adjacent locations.
        // Get a list of adjacent free locations.
        Ocean ocean = getOcean();
        List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
        int births = breed();

        for (int b = 0; b < births && free.size() > 0; b++) {
            Location location = free.remove(0);
            newTuna.add(new Tuna(ocean, location));
        }
    }

    /**
     * Make the tuna act. In this case, it will get hungrier, older and maybe
     * give birth to new tunas and also move. If the tuna is old enough or
     * starves, dying is also a part of act.
     */
    public void act(List<Actor> tunas)
    {
        incrementHunger();
        incrementAge();

        if (isAlive()) {
            giveBirth(tunas);
            Location loc = getLocation();
            Location newLocation = findFood(loc, Sardine.class);

            if (newLocation != null) {
                eat(newLocation);
            }
            else {
                newLocation = getOcean().freeAdjacentLocation(loc);

                if (newLocation != null)
                    setInOcean(newLocation);
            }

            if (newLocation == null) {
                setDead();
            }
        }
    }

    /**
     * A tuna can breed if it has reached the breeding age.
     *
     * @return true if the tuna can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return getAge() >= BREEDING_AGE;
    }
}
