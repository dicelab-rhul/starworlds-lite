package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

/**
 * 
 * 
 * @author cloudstrife9999
 *
 */
public abstract class ActiveBody extends PhysicalBody {
	private Set<Sensor> sensors;
	private Set<Actuator> actuators;
	
	public ActiveBody(Set<Sensor> sensors, Set<Actuator> actuators) {
		this.sensors = sensors != null ? sensors : new HashSet<Sensor>();
		this.actuators = actuators != null ? actuators : new HashSet<Actuator>();
	}
	
	public Set<Sensor> getSensors() {
		return this.sensors;
	}
	
	public Set<Actuator> getActuators() {
		return this.actuators;
	}
	
	public void addSensor(Sensor sensor) {
		this.sensors.add(sensor);
	}
	
	public void addActuator(Actuator actuator) {
		this.actuators.add(actuator);
	}
}