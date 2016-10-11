package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.UUID;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * The most generic class implementing {@link Actuator}. It also extends {@link CustomObservable}.
 * It may contain an {@link Event} to perform and the corresponding {@link EnvironmentalAction}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractActuator extends CustomObservable implements Actuator {
	private EnvironmentalAction actionToPerform;
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
	 * Return the {@link EnvironmentalAction} to attempt.
	 * 
	 * @return the {@link EnvironmentalAction} to attempt.
	 */
	protected EnvironmentalAction getActionToPerform() {
		return this.actionToPerform;
	}
	
	/**
	 * Return the {@link Event} wrapping the {@link EnvironmentalAction} to attempt.
	 * 
	 * @return the {@link Event} wrapping the {@link EnvironmentalAction} to attempt.
	 */
	protected Event getEventToPerform() {
		return this.eventToPerform;
	}
	
	/**
	 * Sets the {@link EnvironmentalAction} to attempt.
	 * 
	 * @param action : the {@link EnvironmentalAction} to attempt.
	 */
	protected void setActionToPerform(EnvironmentalAction action) {
		this.actionToPerform = action;
	}
	
	/**
	 * Sets the {@link Event} wrapping the {@link EnvironmentalAction} to attempt.
	 * 
	 * @param event : the {@link Event} wrapping the {@link EnvironmentalAction} to attempt.
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
}