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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((perception == null) ? 0 : perception.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSensor other = (AbstractSensor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (perception == null) {
			if (other.perception != null)
				return false;
		} else if (!perception.equals(other.perception))
			return false;
		return true;
	}
}