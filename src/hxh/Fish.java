package hxh;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Abstract class for all types of fish. Fish eat, find food, give birth and get
 * hungry. The only abstract method is giveBirth since that is specific for each
 * type of fish
 *
 * NOTE: This should serve as a superclass to all specific types of fish
 *
 * @author João Pedro de A. Paula, Max William S. Filgueira
 * @version 2018-10-09
 */
abstract public class Fish extends AquaticLife
{
    /**
     * Create a new fish at location in ocean.
     *
     * @param ocean The ocean currently occupied.
     * @param location The location within the ocean.
     */
    public Fish(Ocean ocean, Location location)
    {
        super(ocean, location);
        setInOcean(location);
    }

    /**
     * Place the fish at the new location in the given ocean.
     *
     * @param newLocation The fish's new location.
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
     * Look for food of a given class and returns its location.
     *
     * @param location Fish's current location.
     * @param Food Which class to hunt.
     * @return returns the location of the found prey
     *         or null if none was found.
     */
    public Location findFood(Location location, Class<?> Food)
    {
        Ocean ocean = getOcean();
        List<Location> adjacent = ocean.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();

        while (it.hasNext()) {
            Location where = it.next();
            Fish fish = ocean.getFishAt(where.getRow(),where.getCol());

            if(fish != null && fish.getClass() == Food) {
                return where;
            }
        }

        return null;
    }

    abstract protected void giveBirth(List<Actor> newFish);

    /**
     * Increment a fish's hunger.
     * All fish starve eventually.
     */
    public void incrementHunger()
    {
        setfoodLevel(getfoodLevel() - 1);

        if (getfoodLevel() <= 0){
            setDead();
        }
    }
}
