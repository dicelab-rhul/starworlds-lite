package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;

/**
 * A subclass of {@link AbstractAgent} whose {@link Mind} is a {@link AvatarMind}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class Avatar extends AbstractAgent {

	/**
	 * The class constructor.
	 * 
	 * @param appearance : the {@link AbstractAgentAppearance} of the {@link Avatar}.
	 * @param sensors : a {@link Set} of {@link Sensor} instances.
	 * @param actuators : a {@link Set} of {@link Actuator} instances.
	 * @param mind : the {@link AvatarMind}.
	 * @param brain : the {@link AbstractAgentBrain} of the {@link Avatar}.
	 */
	public Avatar(AbstractAgentAppearance appearance, Set<Sensor> sensors, Set<Actuator> actuators, AvatarMind mind, AbstractAgentBrain brain) {
		super(appearance, sensors, actuators, mind, brain);
	}
}