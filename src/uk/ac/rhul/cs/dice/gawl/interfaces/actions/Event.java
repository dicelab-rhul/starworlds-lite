package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The interface for events.<br/>
 * <br/>
 * 
 * Known implementations {@link AbstractEvent}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Event {
    /**
     * Represents the event with a {@link String}.
     * 
     * @return a {@link String} representation of the event.
     */
    public abstract String represent();

    /**
     * Returns the wrapped {@link EnvironmentalAction}.
     * 
     * @return the {@link EnvironmentalAction} wrapped by the event.
     */
    public abstract EnvironmentalAction getAction();

    /**
     * Sets a new {@link EnvironmentalAction}.
     * 
     * @param action
     *            : the new {@link EnvironmentalAction} to be contained in the
     *            event.
     */
    public abstract void setAction(EnvironmentalAction action);

    /**
     * Returns the timestamp of the event.
     * 
     * @return a {@link Long} representation of the timestamp of the event.
     */
    public abstract Long getTimestamp();

    /**
     * Sets a timestamp for the event.
     * 
     * @param timestamp
     *            : the new timestamp, in the formate of a {@link Long}.
     */
    public abstract void setTimestamp(Long timestamp);

    /**
     * Returns the {@link Actor} which performed or will perform the event.
     * 
     * @return the {@link Actor} which performed or will perform the event.
     */
    public abstract Actor getActor();

    /**
     * Sets an {@link Actor} which will perform the event.
     * 
     * @param actor
     *            : the {@link Actor} which will perform the event.
     */
    public abstract void setActor(Actor actor);

    /**
     * Checks the pre-conditions for the event.
     * 
     * @param physics
     *            : the {@link Physics} of the context where the event is
     *            executed.
     * @param context
     *            : the {@link Space} where the event is executed.
     * @return <code>true</code> if the event can take place, <code>false</code>
     *         otherwise.
     */
    public abstract boolean isPossible(Physics physics, Space context);

    /**
     * Checks whether the event taking place is a mandatory condition.
     * 
     * @param physics
     *            : the {@link Physics} of the context where the event is
     *            executed.
     * @param context
     *            : the {@link Space} where the event is executed.
     * @return <code>true</code> if the event must take place,
     *         <code>false</code> otherwise.
     */
    public abstract boolean isNecessary(Physics physics, Space context);

    /**
     * Attempts to execute the event by calling
     * {@link #isPossible(physics, context)}, {@link #perform(physics, context)}
     * and {@link #succeeded(physics, event)}.
     * 
     * @param physics
     *            : the {@link Physics} of the context where the event is
     *            executed.
     * @param context
     *            : the {@link Space} where the event is executed.
     * @return a {@link Result} instance which shows the result of the
     *         execution.
     */
    public abstract Result attempt(Physics physics, Space context);

    /**
     * Performs an event whose pre-conditions were already met.
     * 
     * @param physics
     *            : the {@link Physics} of the context where the event is
     *            executed.
     * @param context
     *            : the {@link Space} where the event is executed.
     * @return a {@link Result} instance which shows the result of the
     *         execution.
     */
    public abstract Result perform(Physics physics, Space context);

    /**
     * Checks the post-conditions (effects) of the event.
     * 
     * @param physics
     *            : the {@link Physics} of the context where the event is
     *            executed.
     * @param context
     *            : the {@link Space} where the event is executed.
     * @return <code>true</code> if the event post-conditions are met,
     *         <code>false</code> otherwise.
     */
    public abstract boolean succeeded(Physics physics, Space context);

    public abstract Object getSensorToCallBackId();

    public abstract void setSensorToCallBackId(String sensorToCallBackId);

    public abstract Object getActuatorRecipientId();

    public abstract void setActuatorRecipientId(String actuatorRecipient);
}