package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;

/**
 * The interface for classes capable of performing an
 * {@link EnvironmentalAction}.<br/>
 * <br>
 * 
 * Known implementations: {@link ActiveBody}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
@FunctionalInterface
public interface Actor {
    
    /**
     * Returns the {@link Actor} ID.
     * 
     * @return the {@link Actor} ID.
     */
    public abstract Object getId();
}