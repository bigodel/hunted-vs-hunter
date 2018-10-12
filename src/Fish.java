package src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Write a description of class Fish here.
 *
 * NOTE: This should serve as a superclass to all specific tyes of fish
 *
 * @author João Pedro de A. Paula, Max William S. Filgueira
 * @version 2018-10-09
 */
abstract public class Fish extends AquaticLife
{
    private int age;    
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

        if(location != null) {
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

        while(it.hasNext()) {

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
     * All fish starve eventually.
     */
    public void incrementHunger()
    {
        setfoodLevel(getfoodLevel() - 1);

        if(getfoodLevel() <= 0){
            setDead();
        }
    }
    public void incrementAge()
    {
        age++;
        if(age >= 10){
            setDead();
        }
    }
    public int getAge(){
        return age;
    }
}
