package hxh;

import java.util.List;
import java.util.Random;

/**
 * A simple model of a shark.
 * Sharks age, move, breed, and die.
 * Sharks eat sardine or tuna but they prefer tuna.
 *
 * @author Richard Jones and Michael Kolling
 */
public class Shark extends Fish  implements Actor
{
    private final double BREEDING_PROBABILITY = 0.35;
    private static int BREEDING_AGE = 5;
    private final int MAX_BREED_PER_ROUND = 2;

    /**
     * Constructor for the Shark to place it in a location in the ocean. Also,
     * set the maximum age for it to live.
     *
     * @param ocean The ocean to place the shark
     * @param location The location to place the shark
     * @return A new shark
     */
    public Shark(Ocean ocean, Location location){
        super(ocean,location);
        setMaxAge(70);
    }

    /**
     * Eat food at the location.
     *
     * @param location Location where the food is
     */
    public void eat(Location location)
    {
        Fish fish = getOcean().getFishAt(location);

        this.setfoodLevel(fish.getfoodLevel());
        //setLocation(location);
        fish.setDead();
        setInOcean(location);
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

        if (/*canBreed() && */rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_BREED_PER_ROUND) + 1;
        }

        return births;
    }

    /**
     * Check whether or not this shark is to give birth at this step.
     * New births will be made into free adjacent locations.
     *
     * @param newShark A list to add newly born sharks to.
     */
    protected void giveBirth(List<Actor> newShark)
    {
        // New sardines are born into adjacent locations.
        // Get a list of adjacent free locations.
        Ocean ocean = getOcean();
        List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
        int births = breed();

        for (int b = 0; b < births && free.size() > 0; b++) {
            Location location = free.remove(0);
            newShark.add(new Shark(ocean, location));
        }
    }

    /**
     * Make the shark act. In this case, it will get hungrier, older and maybe
     * give birth to new sharks and also move. If the shark is old enough or
     * starves, dying is also a part of act.
     */
    public void act(List<Actor> shark)
    {
        incrementHunger();
        incrementAge();

        if (isAlive()) {
            giveBirth(shark);
            Location loc = getLocation();
            Location newLocation = findFood(loc, Tuna.class);

            if (newLocation != null){
                eat(newLocation);
            }
            else {
                newLocation = findFood(loc, Sardine.class);
                if (newLocation != null){
                    eat(newLocation);
                }
            }

            if(newLocation == null){
                newLocation = getOcean().freeAdjacentLocation(loc);

                if (newLocation != null) {
                    setInOcean(newLocation);
                }
                else {
                    setDead();
                }
            }
        }
    }

    /**
     * A shark can breed if it has reached the breeding age.
     * @return true if the shark can breed, false otherwise.
     */
    private boolean canBreed()
    {
       return getAge() >= BREEDING_AGE;
    }
}
