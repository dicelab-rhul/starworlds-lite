package uk.ac.rhul.cs.dice.gawl.interfaces.entities;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.Sensor;

/**
 * The interface for classes that can simulate behaviours.<br/><br/>
 * 
 * Known implementations: {@link ActiveBody}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Simulator<T1 extends Enum<?>, T2 extends Enum<?>> {
	
	/**
	 * Performs a simulation.
	 * 
	 * @return the simulation return value as a generic {@link Object}.
	 */
	public abstract Object simulate();
	public abstract List<Sensor<T1>> getSensors();
	public abstract List<Actuator<T2>> getActuators();
	public abstract void addSensor(Sensor<T1> sensor);
	public abstract void addActuator(Actuator<T2> actuator);
}