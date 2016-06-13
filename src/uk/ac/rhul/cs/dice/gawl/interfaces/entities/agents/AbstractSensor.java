package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Observable;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.Perception;

/**
 * The most generic class implementing {@link Sensor}. It is also {@link Observable}.
 * It may contain a {@link Perception}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractSensor extends Observable implements Sensor {
	private Perception perception;
	
	protected void setPerception(Perception perception) {
		this.perception = perception;
	}
	
	protected Perception getPerception() {
		return this.perception;
	}
}