package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Random;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;

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
	
	public AutonomousAgentMind(Random rng, String bodyId) {
		super(rng, bodyId);
	}
	
	public AutonomousAgentMind(String bodyId) {
		super(bodyId);
	}

	/**
	 * Starts a new {@link #perceive(Object)} - {@link #decide(Object[])} - {@link #execute(EnvironmentalAction)} cycle.
	 * 
	 * @param perviousActionResultWrapper : an {@link Object} which wraps the result of the previous execution (if any).
	 */
	protected abstract void stepCycle(Object perviousActionResultWrapper);
}