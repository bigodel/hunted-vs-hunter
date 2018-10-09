<<<<<<< HEAD

/**
 * A simple model of a sardine.
 * sardines age, move, breed, and die.
 * They eat plankton.
 * They exhibit flocking behaviour - they tend to seek company. 
 * If they spot a predator close by, they panic.
 * 
 */
public class Sardine extends Fish
{
    
}
||||||| merged common ancestors
=======
package src;
import java.util.List;

/**
 * A simple model of a sardine.
 * sardines age, move, breed, and die.
 * They eat plankton.
 * They exhibit flocking behaviour - they tend to seek company. 
 * If they spot a predator close by, they panic.
 * 
 */
public class Sardine extends Fish implements Actor
{
    public Sardine(Ocean ocean, Location loc){
            super(ocean,loc);
    }    
   
    public void act(List<Actor> actors)
    {
           incrementHunger();
           if(isAlive()){
                   //givebirth ?
                   Location loc = getLocation();
                   Location newLocation = findFood(loc,Seaweed.class);
                   if(newLocation != null){
                           eat(newLocation);
                   }
                   else {
                           newLocation = getOcean().freeAdjacentLocation(loc);
                   }
                   if(newLocation != null) setLocation(newLocation);
                   else setDead();
                   
           }
    }
    /**
     * @param loc Location where the food is
     */
    @Override
    public void eat(Location loc)
    {
        Seaweed seaweed = (Seaweed) getOcean().getSeaweedAt(loc);
        this.setfoodLevel(seaweed.getfoodLevel());
        setLocation(loc);
    }
}
>>>>>>> origin/max
