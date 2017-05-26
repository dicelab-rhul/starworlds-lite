package uk.ac.rhul.cs.dice.starworlds.actions;

import javax.swing.AbstractAction;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.Actor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;

/**
 * The interface for all the actions.<br/>
 * <br/>
 * 
 * Known implementations: {@link AbstractAction}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Action {

	/**
	 * Sets the {@link Actuator} that will perform this action. Note that this
	 * may also be a {@link Sensor} in the case of a {@link SensingAction}.
	 * 
	 * @param actuator
	 *            that will perform this {@link Action}
	 */
	public void setActuator(Class<?> actuator);

	/**
	 * Gets the {@link Class} of {@link Actuator} that will be performing this
	 * action. Note this may be a {@link Sensor} {@link Class} if the action is
	 * a {@link SensingAction}.
	 * 
	 * @return the {@link Actuator} {@link Class}
	 */
	public Class<?> getActuator();

	/**
	 * Returns the {@link Actor}.
	 * 
	 * @return the {@link Actor}.
	 */
	public Actor getActor();

	/**
	 * Sets the {@link Actor}.
	 * 
	 * @param actor
	 *            : the {@link Actor} to be set.
	 */
	public void setActor(Actor actor);
}