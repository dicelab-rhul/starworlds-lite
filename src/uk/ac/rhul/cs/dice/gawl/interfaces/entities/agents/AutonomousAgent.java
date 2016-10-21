package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

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
public abstract class AutonomousAgent<T1 extends Enum<?>, T2 extends Enum<?>,  P extends Perception> extends AbstractAgent<T1, T2, P> {

	/**
	 * The class constructor.
	 * 
	 * @param appearance : the {@link AbstractAgentAppearance}.
	 * @param sensors : a {@link List} of {@link Sensor} instances.
	 * @param actuators : a {@link List} of {@link Actuator} instances.
	 * @param mind : the {@link AutonomousAgentMind}.
	 * @param brain : the {@link AbstractAgentBrain}.
	 */
	public AutonomousAgent(AbstractAgentAppearance appearance, List<Sensor<T1>> sensors, List<Actuator<T2, P>> actuators, AutonomousAgentMind<P> mind, AbstractAgentBrain<P> brain) {
		super(appearance, sensors, actuators, mind, brain);
	}
}