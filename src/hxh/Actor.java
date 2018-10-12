package hxh;

import java.util.List;

/**
 * @author João Pedro de A. Paula, Max William S. Filgueira
 */
public interface Actor {
    /**
     * Executa o comportamento regular do ator.
     * @param newActors Uma lista para armazenar os atores criados recentemente.
     */
    void act(List<Actor> newActors);

    /**
     *
     * @return true
     */
    boolean isAlive();
}
