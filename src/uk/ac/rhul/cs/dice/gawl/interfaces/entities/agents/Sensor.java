package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The interface for sensor objects. It extends {@link CustomObserver}.<br/><br/>
 * 
 * Known implementations: {@link AbstractSensor}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Sensor<T extends Enum<?>> extends CustomObserver {
	public abstract T getRole();
	public abstract Object getSensorId();
	public abstract Object getBodyId();
	public abstract Perception getPerception();
	public abstract void setPerception(Perception perception);
}