package src;

import java.util.List;

/**
 * @author what
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
