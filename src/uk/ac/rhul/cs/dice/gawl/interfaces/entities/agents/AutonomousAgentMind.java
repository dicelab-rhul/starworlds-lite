package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;

/**
 * A subclass of {@link AbstractAgentMind} designed for {@link AutonomousAgent} instances.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AutonomousAgentMind extends AbstractAgentMind {
	/**
	 * Starts a new {@link #perceive(Object)} - {@link #decide(Object[])} - {@link #execute(Action)} cycle.
	 * 
	 * @param perviousActionResultWrapper : an {@link Object} which wraps the result of the previous execution (if any).
	 */
	protected abstract void stepCycle(Object perviousActionResultWrapper);
}