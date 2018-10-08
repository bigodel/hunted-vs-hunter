package src;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * (Fill in description and author info here)
 */
public class Ocean
{
	private final Integer [] [] seaweed;
	private final Fish [] [] fish;
	private final Integer height;
	private final Integer width;
	private final static int DEFAULT_HEIGHT = 200;	
	private final static int DEFAULT_WIDTH = 200;	
		
    /**
     * Represent an ocean of the given dimensions.
     * @param height The height of the ocean.
     * @param width The width of the ocean.
     */
    public Ocean(int height, int width)
    {
        this.height = height;
        this.width = width;
        seaweed = new Integer[height][width];
        fish = new Fish[height][width];
    }
    public Ocean()
    {
        this.height = DEFAULT_HEIGHT;
        this.width = DEFAULT_WIDTH;
        seaweed = new Integer[height][width];
        fish = new Fish[height][width];
    }
    
    /**
     * Return the fish at the given location, if any.
     * @param height The desired row.
     * @param width The desired column.
     * @return The fish at the given location, or null if there is none.
     */
    public Fish getFishAt(int height, int width)
    {
        return fish[height][width];
    }
    
    /**
     * Return the fish at the given location, if any.
     * @param location The desired location.
     * @return The fish at the given location, or null if there is none.
     */
    public Fish getFishAt(Location location)
    {
        return fish[location.getRow()][location.getCol()];
    }
    /**
     * @return The height of the ocean.
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * @return The width of the ocean.
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new ArrayList<Location>();
        if(location != null) {
            int height = location.getRow();
            int width = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = height + roffset;
                if(nextRow >= 0 && nextRow < height) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = width + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
//            Collections.shuffle(locations, rand);
        }
        return locations;
    }
/**
         * Empty the field.
         */
        public void clear()
        {
                for(int row = 0; row < this.height; row++) {
                        for(int col = 0; col < this.width; col++) {
                                fish[height][width] = null;
                        }
                }
        }
        
        /**
         * Clear the given location.
         * @param location The location to clear.
         */
        public void clear(Location location)
        {
                fish[location.getRow()][location.getCol()] = null;
        }
        
        /**
         * Place an fish at the given location.
         * If there is already an fish at the location it will
         * be lost.
         * @param fish The fish to be placed.
         * @param height Row coordinate of the location.
         * @param width Column coordinate of the location.
         */
        public void place(Fish fish, int height, int width)
        {
                place(fish, new Location(height, width));
        }
        
        /**
         * Place an fish at the given location.
         * If there is already an fish at the location it will
         * be lost.
         * @param newFish The fish to be placed.
         * @param location Where to place the fish.
         */
        public void place(Fish newFish, Location location)
        {
            fish[location.getRow()][location.getCol()] = newFish;
        }
     /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the ocean.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }
     /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new ArrayList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getFishAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }



}
