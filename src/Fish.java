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
                alive = true;
                this.ocean = ocean;
                setLocation(location);
        }
        abstract public void act(List<Actor> actors);
        
        public Location findFood(Location location, Class<?> Food)
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
        
        abstract public void eat(Location loc);
        
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
        
        public void incrementHunger()
        {
                foodLevel--;
                if(foodLevel <= 0){
                        setDead();
                }
        }
}