package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.BodyAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractActuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractSensor;
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
	
	/**
	 * Constructor with a {@link BodyAppearance}, a {@link Set} of {@link Sensor} instances and
	 * one of {@link Actuator} instances.
	 * 
	 * @param appearance : the {@link BodyAppearance} of the {@link ActiveBody}.
	 * @param sensors : a {@link Set} of {@link Sensor} instances.
	 * @param actuators : a {@link Set} of {@link Actuator} instances.
	 */
	public ActiveBody(BodyAppearance appearance, Set<Sensor> sensors, Set<Actuator> actuators) {
		super(appearance);
		
		this.sensors = sensors != null ? sensors : new HashSet<>();
		this.actuators = actuators != null ? actuators : new HashSet<>();
		
		init();
	}

	private void init() {
		if(!this.sensors.isEmpty()){
			initSensors();
		}
		
		if(!this.actuators.isEmpty()) {
			initActuators();
		}
	}

	private void initActuators() {
		for(Actuator a : this.actuators) {
			registerActuator(a);
		}
	}

	private void initSensors() {
		for(Sensor s : this.sensors) {
			registerSensor(s);
		}
	}

	/**
	 * Returns a {@link Set} of {@link Sensor} instances.
	 * 
	 * @return the {@link Set} of {@link Sensor} instances.
	 */
	public Set<Sensor> getSensors() {
		return this.sensors;
	}
	
	/**
	 * Returns a {@link Set} of {@link Actuator} instances.
	 * 
	 * @return the {@link Set} of {@link Actuator} instances.
	 */
	public Set<Actuator> getActuators() {
		return this.actuators;
	}
	
	/**
	 * Adds a {@link Sensor} to the {@link Set}.
	 * 
	 * @param sensor : the {@link Sensor} to be added to the {@link Set}.
	 */
	public void addSensor(Sensor sensor) {
		registerSensor(sensor);
		
		this.sensors.add(sensor);
	}
	
	/**
	 * Adds an {@link Actuator} to the {@link Set}.
	 * 
	 * @param sensor : the {@link Actuator} to be added to the {@link Set}.
	 */
	public void addActuator(Actuator actuator) {
		registerActuator(actuator);
		
		this.actuators.add(actuator);
	}
	
	private void registerActuator(Actuator actuator) {
		if(actuator instanceof AbstractActuator) {
			((AbstractActuator) actuator).addObserver(this);
			this.addObserver(actuator);
		}
	}
	
	private void registerSensor(Sensor sensor) {
		if(sensor instanceof AbstractSensor) {
			((AbstractSensor) sensor).addObserver(this);
			this.addObserver(sensor);
		}
	}
}