package src;

import java.util.Iterator;
import java.util.List;

/**
 * Write a description of class Fish here.
 *
 * NOTE: This should serve as a superclass to all specific tyes of fish
 *
 * @author (your name)
 * @version (a version number or a date)
 */
abstract public class Fish extends AquaticLife
{
    private boolean alive;
    private int foodLevel;
    private Location location;
    private Ocean ocean;
    /**
     * Create a new fish at location in field.
     *
     * @param ocean The ocean currently occupied.
     * @param location The location within the ocean.
     */
    public Fish(Ocean ocean, Location location)
    {
        super(ocean,location);
        setInOcean(location);
    }
    /**
     * All fish need to eat.
     */
    abstract public void eat(Location loc);
    /**
     * Place the seaweed at the new location in the given ocean.
     * @param newLocation The seaweed's new location.
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
     *
     * @param location Fish's current location.
     * @param Food  Which class to hunt.
     * @return returns the location of the found prey
     *         or null if none was found.
     */
    public Location findFood(Location location, Class<?> Food)
    {
        List<Location> adjacent = getOcean().adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Fish fish = getOcean().getFishAt(where.getRow(),where.getCol());
            if(fish.getClass() == Food) {
                return where;
            }
        }

        return null;
    }
    /**
     * All fish starve eventually.
     */
    public void incrementHunger()
    {
        setfoodLevel(getfoodLevel()-1);
        if(getfoodLevel() <= 0){
            setDead();
        }
    }
}

