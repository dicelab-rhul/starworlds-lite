package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.EnvironmentalAction;

/**
 * The interface for minds. it extends {@link CustomObserver}.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractAgentMind}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Mind {

	/**
	 * Setter for the brain that this mind is contained within.
	 * 
	 * @param brain
	 *            containing this mind
	 */
	public void setBrain(AbstractAgentBrain brain);

	/**
	 * 
	 * The decide() routine. At the end of it the next action is returned. This
	 * method needs to be implemented by subclasses.
	 * 
	 * @param parameters
	 *            an array of optional parameters.
	 * @return the next {@link EnvironmentalAction} to execute.
	 * 
	 */
	public void decide();

}