package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

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
public abstract class Avatar<T1 extends Enum<?>, T2 extends Enum<?>, P extends Perception> extends AbstractAgent<T1, T2, P> {

	/**
	 * The class constructor.
	 * 
	 * @param appearance : the {@link AbstractAgentAppearance} of the {@link Avatar}.
	 * @param sensors : a {@link List} of {@link Sensor} instances.
	 * @param actuators : a {@link List} of {@link Actuator} instances.
	 * @param mind : the {@link AvatarMind}.
	 * @param brain : the {@link AbstractAgentBrain} of the {@link Avatar}.
	 */
	public Avatar(AbstractAgentAppearance appearance, List<Sensor<T1>> sensors, List<Actuator<T2, P>> actuators, AvatarMind<P> mind, AbstractAgentBrain brain) {
		super(appearance, sensors, actuators, mind, brain);
	}
}