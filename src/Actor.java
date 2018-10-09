/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
