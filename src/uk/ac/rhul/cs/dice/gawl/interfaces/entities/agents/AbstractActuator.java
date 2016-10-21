package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.UUID;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractEvent;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

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
public abstract class AbstractActuator<T extends Enum<?>, P extends Perception> extends CustomObservable implements Actuator<T, P> {
	private AbstractAction<P> actionToPerform;
	private AbstractEvent<P> eventToPerform;
	private String id;
	private String bodyId;
	private T role;
	
	/**
	 * Default constructor. A random uuid is generated and stored.
	 */
	public AbstractActuator(String bodyId, T role) {
		this.id = UUID.randomUUID().toString();
		this.bodyId = bodyId;
		this.role = role;
	}
	
	/**
	 * Return the {@link EnvironmentalAction} to attempt.
	 * 
	 * @return the {@link EnvironmentalAction} to attempt.
	 */
	@Override
	public AbstractAction<P> getActionToPerform() {
		return this.actionToPerform;
	}
	
	/**
	 * Return the {@link Event} wrapping the {@link EnvironmentalAction} to attempt.
	 * 
	 * @return the {@link Event} wrapping the {@link EnvironmentalAction} to attempt.
	 */
	@Override
	public AbstractEvent<P> getEventToPerform() {
		return this.eventToPerform;
	}
	
	/**
	 * Sets the {@link EnvironmentalAction} to attempt.
	 * 
	 * @param action : the {@link EnvironmentalAction} to attempt.
	 */
	@Override
	public void setActionToPerform(AbstractAction<P> action) {
		this.actionToPerform = action;
	}
	
	/**
	 * Sets the {@link Event} wrapping the {@link EnvironmentalAction} to attempt.
	 * 
	 * @param event : the {@link Event} wrapping the {@link EnvironmentalAction} to attempt.
	 */
	@Override
	public void setEventToPerform(AbstractEvent<P> event) {
		this.eventToPerform = event;
	}
	
	/**
	 * Returns the actuator ID as a {@link String}.
	 * 
	 * @return the actuator ID as a {@link String}.
	 */
	@Override
	public String getActuatorId() {
		return this.id;
	}
	
	@Override
	public T getRole() {
		return this.role;
	}
	
	@Override
	public String getBodyId() {
		return this.bodyId;
	}
}