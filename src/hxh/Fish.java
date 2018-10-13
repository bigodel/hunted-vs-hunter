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
 * @version 2018.10.09
 */
abstract public class Fish extends AquaticLife
{
    private int age;
    private int maxAge;

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
        age = 0;
        maxAge = 10;
    }

    /**
     * Place the fish at the new location in the given ocean.
     *
     * @param newLocation The fish's new location.
     */
    public void setInOcean(Location newLocation)
    {
        Ocean ocean = getOcean();
        setLocation(newLocation);
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

    /**
     * Every fish gives birth, so "daugther's" of the Fish class need to
     * implement how each fish gives birth.
     *
     * @param newFish A list to add newly born fish's to it
     */
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

    /**
     * Increment the fish's age by one.
     * The fish dies if it gets too old.
     */
    public void incrementAge()
    {
        age++;

        // the fish dies if it gets too old
        if (age >= maxAge){
            setDead();
        }
    }

    /**
     * @return The fish's age
     */
    public int getAge(){
        return age;
    }

    /**
     * Sets the fish maximum age to live.
     */
    public void setMaxAge(int maxAge)
    {
        this.maxAge = maxAge;
    }

}
