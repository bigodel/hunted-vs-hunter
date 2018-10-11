package src;

import java.util.Random;

/**
 *
 * @author cyborg
 */
abstract public class AquaticLife
{
    private boolean alive;
    private int foodLevel;
    private Location location;
    private Ocean ocean;
    private static final Random rand = Randomizer.getRandom();

    public AquaticLife(Ocean ocean, Location location)
    {
        alive = true;
        this.ocean = ocean;
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
     * Return the lifeform's location.
     * @return The lifeform's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Return the lifeform's location.
     * @param newLocation the lifeform's new location.
     * @return The lifeform's location.
     */
    public void setLocation(Location newLocation)
    {
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
     *
     * @return the value of the foodLevel;
     */
    public int getfoodLevel()
    {
        return foodLevel;
    }

    /**
     *
     * @param foodLevel the new foodlevel;
     */
    public void setfoodLevel(int foodLevel)
    {
        this.foodLevel = foodLevel;
    }

    /**
     * @return
     */
    public Random getRand()
    {
        return rand;
    }
}
