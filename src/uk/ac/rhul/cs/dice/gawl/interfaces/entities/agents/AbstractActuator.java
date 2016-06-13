package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.Observable;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;

/**
 * The most generic class implementing {@link Actuator}. It is also {@link Observable}.
 * It may contain an {@link Event} to perform and the corresponding {@link Action}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractActuator extends Observable implements Actuator {
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