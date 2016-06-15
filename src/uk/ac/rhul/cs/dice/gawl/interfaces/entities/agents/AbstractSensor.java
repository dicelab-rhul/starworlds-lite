package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

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
	
	/**
	 * Sets the perceived {@link Perception}.
	 * 
	 * @param perception : the perceived {@link Perception}.
	 */
	protected void setPerception(Perception perception) {
		this.perception = perception;
	}
	
	/**
	 * Returns the perceived {@link Perception}.
	 * 
	 * @return the perceived {@link Perception}.
	 */
	protected Perception getPerception() {
		return this.perception;
	}
}