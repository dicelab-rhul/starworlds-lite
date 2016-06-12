package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.BodyAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

/**
 * A subclass of {@link PhysicalBody} capable to perform and/or simulate an {@link Action}, thus implementing {@link Actor} and {@link Simulator}. It has a {@link Set}
 * of {@link Sensor} elements and one of {@link Actuator} elements.<br/><br/>
 * 
 * Known direct subclasses: {@link AbstractAgent}, {@link DependentBody}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class ActiveBody extends PhysicalBody implements Actor, Simulator {
	private Set<Sensor> sensors;
	private Set<Actuator> actuators;
	
	public ActiveBody(BodyAppearance appearance, Set<Sensor> sensors, Set<Actuator> actuators) {
		super(appearance);
		this.sensors = sensors != null ? sensors : new HashSet<>();
		this.actuators = actuators != null ? actuators : new HashSet<>();
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