package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.UUID;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * The most generic class implementing {@link Actuator}. It also extends {@link CustomObservable}.
 * It may contain an {@link Event} to perform and the corresponding {@link Action}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractActuator extends CustomObservable implements Actuator {
	private Action actionToPerform;
	private Event eventToPerform;
	private String id;
	
	/**
	 * Default constructor. A random uuid is generated and stored.
	 */
	public AbstractActuator() {
		UUID uuid = UUID.randomUUID();
		this.id = uuid.toString();
	}
	
	/**
	 * Return the {@link Action} to attempt.
	 * 
	 * @return the {@link Action} to attempt.
	 */
	protected Action getActionToPerform() {
		return this.actionToPerform;
	}
	
	/**
	 * Return the {@link Event} wrapping the {@link Action} to attempt.
	 * 
	 * @return the {@link Event} wrapping the {@link Action} to attempt.
	 */
	protected Event getEventToPerform() {
		return this.eventToPerform;
	}
	
	/**
	 * Sets the {@link Action} to attempt.
	 * 
	 * @param action : the {@link Action} to attempt.
	 */
	protected void setActionToPerform(Action action) {
		this.actionToPerform = action;
	}
	
	/**
	 * Sets the {@link Event} wrapping the {@link Action} to attempt.
	 * 
	 * @param event : the {@link Event} wrapping the {@link Action} to attempt.
	 */
	protected void setEventToPerform(Event event) {
		this.eventToPerform = event;
	}
	
	/**
	 * Returns the actuator ID as a {@link String}.
	 * 
	 * @return the actuator ID as a {@link String}.
	 */
	public String getActuatorId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((actionToPerform == null) ? 0 : actionToPerform.hashCode());
		result = prime * result + ((eventToPerform == null) ? 0 : eventToPerform.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AbstractActuator other = (AbstractActuator) obj;
		if (actionToPerform == null) {
			if (other.actionToPerform != null)
				return false;
		} else if (!actionToPerform.equals(other.actionToPerform))
			return false;
		if (eventToPerform == null) {
			if (other.eventToPerform != null)
				return false;
		} else if (!eventToPerform.equals(other.eventToPerform))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}