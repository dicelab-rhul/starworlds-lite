package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractEvent;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The interface for actuator objects. It extends {@link CustomObserver}.<br/><br/>
 * 
 * Known implementations: {@link AbstractActuator}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Actuator<T extends Enum<?>, P extends Perception> extends CustomObserver {
	public abstract T getRole();
	public abstract Object getActuatorId();
	public abstract Object getBodyId();
	public abstract AbstractAction<P> getActionToPerform();
	public abstract void setActionToPerform(AbstractAction<P> action);
	public abstract AbstractEvent<P> getEventToPerform();
	public void setEventToPerform(AbstractEvent<P> event);
}