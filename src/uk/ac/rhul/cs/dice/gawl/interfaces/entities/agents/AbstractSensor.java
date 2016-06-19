package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.UUID;

import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The most generic class implementing {@link Sensor}. It also extends {@link CustomObservable}.
 * It may contain a {@link Perception}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractSensor extends CustomObservable implements Sensor {
	private Perception perception;
	private String id;
	
	/**
	 * Default constructor. A random uuid is generated and stored.
	 */
	public AbstractSensor() {
		UUID uuid = UUID.randomUUID();
		this.id = uuid.toString();
	}
	
	/**
	 * Sets the perceived {@link Perception}.
	 * 
	 * @param perception : the perceived {@link Perception}.
	 */
	public void setPerception(Perception perception) {
		this.perception = perception;
	}
	
	/**
	 * Returns the perceived {@link Perception}.
	 * 
	 * @return the perceived {@link Perception}.
	 */
	public Perception getPerception() {
		return this.perception;
	}
	
	/**
	 * Returns the sensor ID as a {@link String}.
	 * 
	 * @return the sensor ID as a {@link String}.
	 */
	public String getSensorId() {
		return this.id;
	}
}