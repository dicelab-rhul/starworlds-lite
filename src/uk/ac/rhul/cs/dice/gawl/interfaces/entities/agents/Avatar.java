package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.AbstractAgentAppearance;

/**
 * A subclass of {@link AbstractAgent} whose {@link Mind} is a {@link AvatarMind}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class Avatar extends AbstractAgent {

	public Avatar(AbstractAgentAppearance appearance, Set<Sensor> sensors, Set<Actuator> actuators, AvatarMind mind, AbstractAgentBrain brain) {
		super(appearance, sensors, actuators, mind, brain);
	}
}