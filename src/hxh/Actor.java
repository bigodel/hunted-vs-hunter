package hxh;

import java.util.List;

/**
 * An interface for every thing that might be able to act.
 * Every class that implements Actor needs to define what it means to act for
 * that class.
 *
 * @author João Pedro de A. Paula, Max William S. Filgueira
 * @version 2018.10.06
 */
public interface Actor {
    /**
     * Executa o comportamento regular do ator.
     * @param newActors Uma lista para armazenar os atores criados recentemente.
     */
    void act(List<Actor> newActors);

    /**
     * @return true if the actor is alive. false, otherwise.
     */
    boolean isAlive();
}
