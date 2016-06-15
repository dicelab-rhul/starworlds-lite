package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

/**
 * The interface for classes that can simulate behaviours.<br/><br/>
 * 
 * Known implementations: {@link ActiveBody}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
@FunctionalInterface
public interface Simulator {
	
	/**
	 * Performs a simulation.
	 * 
	 * @return the simulation return value as a generic {@link Object}.
	 */
	public Object simulate();
}