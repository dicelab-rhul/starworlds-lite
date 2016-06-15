package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

/**
 * The interface for minds. it extends {@link CustomObserver}.<br/><br/>
 * 
 * Known implementations: {@link AbstractAgentMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Mind extends CustomObserver {
	/**
	 * Selects an {@link Action} to be attempted after a computation involving <code>parameters</code>.
	 * 
	 * @param parameters : an array of {@link Object} elements used to select the {@link Action} to attempt.
	 * @return the {@link Action} to attempt.
	 */
	public Action decide(Object... parameters);
	
	/**
	 * Returns the {@link List} of all the {@link Action} instances which can be attempted.
	 * 
	 * @return the {@link List} of all the {@link Action} instances which can be attempted.
	 */
	public List<Action> getAvailableActionsForThisCicle();
	
	/**
	 * Adds an {@link Action} to the {@link List} of the {@link Action} instances which can be attempted.
	 * 
	 * @param availableActionForThisCicle : the {@link Action} to add.
	 */
	public void addAvailableActionForThisCicle(Action availableActionForThisCicle);
}