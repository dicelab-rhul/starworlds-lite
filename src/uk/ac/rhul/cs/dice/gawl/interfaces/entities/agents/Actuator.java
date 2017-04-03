package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractEvent;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObserver;

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
public interface Actuator extends CustomObserver {
	public abstract ActuatorPurpose getPurpose();
	public abstract Object getActuatorId();
	public abstract Object getBodyId();
	public abstract AbstractAction getActionToPerform();
	public abstract void setActionToPerform(AbstractAction action);
	public abstract AbstractEvent getEventToPerform();
	public void setEventToPerform(AbstractEvent event);
}