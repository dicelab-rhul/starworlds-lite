package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents;

import java.util.UUID;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractEvent;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.observer.CustomObservable;

/**
 * The most generic class implementing {@link Actuator}. It also extends
 * {@link CustomObservable}. It may contain an {@link Event} to perform and the
 * corresponding {@link EnvironmentalAction}.<br/>
 * <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractActuator extends CustomObservable implements Actuator {
    private AbstractAction actionToPerform;
    private AbstractEvent eventToPerform;
    private String id;
    private String bodyId;
    private ActuatorPurpose purpose;

    /**
     * Default constructor. A random uuid is generated and stored.
     */
    public AbstractActuator(String bodyId, ActuatorPurpose purpose) {
	this.id = UUID.randomUUID().toString();
	this.bodyId = bodyId;
	this.purpose = purpose;
    }

    /**
     * Return the {@link EnvironmentalAction} to attempt.
     * 
     * @return the {@link EnvironmentalAction} to attempt.
     */
    @Override
    public AbstractAction getActionToPerform() {
	return this.actionToPerform;
    }

    /**
     * Return the {@link Event} wrapping the {@link EnvironmentalAction} to
     * attempt.
     * 
     * @return the {@link Event} wrapping the {@link EnvironmentalAction} to
     *         attempt.
     */
    @Override
    public AbstractEvent getEventToPerform() {
	return this.eventToPerform;
    }

    /**
     * Sets the {@link EnvironmentalAction} to attempt.
     * 
     * @param action
     *            : the {@link EnvironmentalAction} to attempt.
     */
    @Override
    public void setActionToPerform(AbstractAction action) {
	this.actionToPerform = action;
    }

    /**
     * Sets the {@link Event} wrapping the {@link EnvironmentalAction} to
     * attempt.
     * 
     * @param event
     *            : the {@link Event} wrapping the {@link EnvironmentalAction}
     *            to attempt.
     */
    @Override
    public void setEventToPerform(AbstractEvent event) {
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
    public ActuatorPurpose getPurpose() {
	return this.purpose;
    }

    @Override
    public String getBodyId() {
	return this.bodyId;
    }
}