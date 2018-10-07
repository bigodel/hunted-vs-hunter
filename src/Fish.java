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
abstract public class Fish
{
        private boolean alive;
        private int FoodLevel;
        private Location location;
        private Ocean ocean;
        /**
         * Constructor for objects of class Fish
         */
        public Fish()
        {
        }
        
        abstract public void act(Fish [] [] fish);
        
        public Location findFood(Location location, Class<? extends Fish> Food)
        {
//            Field ocean = getField();
                List<Location> adjacent = ocean.adjacentLocations(getLocation());
                Iterator<Location> it = adjacent.iterator();
                while(it.hasNext()) {
                        Location where = it.next();
                        Fish fish = ocean.getFishAt(where.getRow(),where.getCol());
                        if(fish.getClass() == Food) {
                                return where;
                        }
                        
                }
                return null;
                
        }
        
        /**
         * Check whether the fish is alive or not.
         * @return true if the fish is still alive.
         */
        public boolean isAlive()
        {
                return alive;
        }
        
        /**
         * Indicate that the fish is no longer alive.
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
         * Return the fish's location.
         * @return The fish's location.
         */
        public Location getLocation()
        {
                return location;
        }
        
        /**
         * Return the fish's ocean.
         * @return The fish's ocean.
         */
        public Field getOcean()
        {
                return ocean;
        }
        
        /**
         * Place the fish at the new location in the given ocean.
         * @param newLocation The fish's new location.
         */
        public void setLocation(Location newLocation)
        {
                if(location != null) {
                        ocean.clear(location);
                }
                location = newLocation;
                ocean.place(this, newLocation);
        }
}

