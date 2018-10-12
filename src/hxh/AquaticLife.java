package hxh;

import java.util.Random;

/**
 * The class emcompasses all aquatic life, be it fish or seaweed or crustacean,
 * etc. It has no abstract methods, so it probably shouldn't even be abstract...
 * Every aquatic thing that has life is either alive or dead. It also stores the
 * ocean the life form is in and its location.
 *
 * @author Max William S. Filgueira, João Pedro de A. Paula
 */
abstract public class AquaticLife
{
    private boolean alive;
    private int foodLevel;
    private Location location;
    private Ocean ocean;
    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for an aquatic life in a given ocean and location.
     *
     * @param ocean The ocean the aquatic life is in.
     * @param location A specified location to place the aquatic life form.
     * @return A new aquatic life.
     */
    public AquaticLife(Ocean ocean, Location location)
    {
        alive = true;
        this.ocean = ocean;
        this.location = location;
        foodLevel = 5;
    }

    /**
     * @return true if the life form is alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the lifeform is no longer alive.
     * It is removed from the ocean.
     */
    public void setDead()
    {
        alive = false;

        if(location != null) {
            ocean.clear(location);
            location = null;
            ocean = null;
        }
    }

    /**
     * Indicate that the lifeform is no longer alive.
     * Used only with seaweeds;
     */
    public void death()
    {
        alive = false;
    }

    /**
     * Return the lifeform's location.
     * @return The lifeform's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Sets the lifeform's location.
     * @param newLocation the lifeform's new location.
     */
    public void setLocation(Location newLocation)
    {
        Ocean ocean = getOcean();
        if(location != null){
            ocean.clear(location);
        }
        this.location = newLocation;
    }

    /**
     * Return the lifeform's ocean.
     * @return The lifeform's ocean.
     */
    public Ocean getOcean()
    {
        return ocean;
    }

    /**
     * Sets the lifeform's ocean.
     * @param ocean new ocean where the life form will swim.
     */
    public void setOcean(Ocean ocean)
    {
        this.ocean = ocean;
    }
    /**
     * Return the food level of the life form.
     * @return the value of the foodLevel;
     */
    public int getfoodLevel()
    {
        return foodLevel;
    }

    /**
     * Sets the lifeform's food level.
     * @param foodLevel the new foodlevel;
     */
    public void setfoodLevel(int foodLevel)
    {
        if (foodLevel >= 10){
            this.foodLevel = 10;
        }
        else {
            this.foodLevel = foodLevel;
        }
    }

    /**
     * Return a random ???
     * @return A random ???
     */
    public Random getRand()
    {
        return rand;
    }
}
