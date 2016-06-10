package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

/**
 * 
 * 
 * @author cloudstrife9999
 *
 */
public abstract class DependentBody extends ActiveBody {

	public DependentBody(Set<Sensor> sensors, Set<Actuator> actuators) {
		super(sensors, actuators);
	}
}