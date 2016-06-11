package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.AbstractAgentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.ActiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Agent;

/**
 * A subclass of {@link ActiveBody} implementing the {@link Agent} interface.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractAgent extends ActiveBody implements Agent {

	public AbstractAgent(AbstractAgentAppearance appearance, Set<Sensor> sensors, Set<Actuator> actuators) {
		super(appearance, sensors, actuators);
	}
}