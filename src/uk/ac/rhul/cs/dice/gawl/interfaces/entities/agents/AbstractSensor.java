package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.gawl.interfaces.common.Perception;

/**
 * 
 * 
 * @author cloudstrife9999
 *
 */
public abstract class AbstractSensor extends Observable implements Sensor, Observer {
	private Perception perception;
	
	protected void setPerception(Perception perception) {
		this.perception = perception;
	}
	
	protected Perception getPerception() {
		return this.perception;
	}
}