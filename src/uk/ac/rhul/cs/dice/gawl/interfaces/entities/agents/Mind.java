package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

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
	 * Stores the received perception for reuse.
	 * 
	 * @param perceptionWrapper : a wrapper for a {@link Perception}.
	 */
	public void perceive(Object perceptionWrapper);
	
	/**
	 * Selects an {@link EnvironmentalAction} to be attempted after a computation involving <code>parameters</code>.
	 * 
	 * @param parameters : an array of {@link Object} elements used to select the {@link EnvironmentalAction} to attempt.
	 * @return the {@link EnvironmentalAction} to attempt.
	 */
	public EnvironmentalAction decide(Object... parameters);
	
	/**
	 * Passes the {@link EnvironmentalAction} to a manager which will decide when, how and who will execute it.
	 * 
	 * @param action
	 */
	public void execute(EnvironmentalAction action);
	
	/**
	 * Returns the {@link List} of all the {@link AbstractAction} instances which can be attempted.
	 * 
	 * @return the {@link List} of all the {@link AbstractAction} instances which can be attempted.
	 */
	public List<Class<? extends AbstractAction>> getAvailableActionsForThisCicle();
	
	/**
	 * Adds an {@link AbstractAction} to the {@link List} of the {@link AbstractAction} instances which can be attempted.
	 * 
	 * @param availableActionForThisCicle : the {@link AbstractAction} to add.
	 */
	public void addAvailableActionForThisCicle(Class<? extends AbstractAction> availableActionForThisCicle);
}