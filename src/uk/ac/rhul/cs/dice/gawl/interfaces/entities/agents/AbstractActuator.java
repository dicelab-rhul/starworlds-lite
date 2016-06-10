package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;

/**
 * 
 * 
 * @author cloudstrife9999
 *
 */
public abstract class AbstractActuator extends Observable implements Actuator, Observer {
	private Action actionToPerform;
	private Event eventToPerform;
	
	protected Action getActionToPerform() {
		return this.actionToPerform;
	}
	
	protected Event getEventToPerform() {
		return this.eventToPerform;
	}
	
	protected void setActionToPerform(Action action) {
		this.actionToPerform = action;
	}
	
	protected void setEventToPerform(Event event) {
		this.eventToPerform = event;
	}
}