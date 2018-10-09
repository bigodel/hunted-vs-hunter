/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import java.util.List;
/**
 *
 * @author cyborg
 */
public class Seaweed extends AquaticLife implements Actor{
        public Seaweed(Ocean ocean, Location location) {
                super(ocean,location);
                setInOcean(location);
        }
        public void act(List<Actor> actors){
                eat();
                if(isAlive()){
                        //givebirth ?
                        
                }
                else setDead();
        }
        public void eat(){
                setfoodLevel(getfoodLevel()+1);
        }
        /**
         * Place the fish at the new location in the given ocean.
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
        
}
