package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;

/**
 * A subclass of {@link AbstractAgent} whose {@link Mind} is a {@link AutonomousAgentMind}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AutonomousAgent extends AbstractAgent {

	/**
	 * The class constructor.
	 * 
	 * @param appearance : the {@link AbstractAgentAppearance}.
	 * @param sensors : a {@link Set} of {@link Sensor} instances.
	 * @param actuators : a {@link Set} of {@link Actuator} instances.
	 * @param mind : the {@link AutonomousAgentMind}.
	 * @param brain : the {@link AbstractAgentBrain}.
	 */
	public AutonomousAgent(AbstractAgentAppearance appearance, Set<Sensor> sensors, Set<Actuator> actuators, AutonomousAgentMind mind, AbstractAgentBrain brain) {
		super(appearance, sensors, actuators, mind, brain);
	}
}