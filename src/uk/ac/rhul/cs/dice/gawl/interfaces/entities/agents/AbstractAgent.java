package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.ActiveBody;

/**
 * 
 * 
 * @author cloudstrife9999
 *
 */
public abstract class AbstractAgent extends ActiveBody {

	public AbstractAgent(Set<Sensor> sensors, Set<Actuator> actuators) {
		super(sensors, actuators);
	}
}