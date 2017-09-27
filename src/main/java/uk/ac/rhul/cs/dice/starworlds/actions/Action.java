package uk.ac.rhul.cs.dice.starworlds.actions;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.Actor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.event.Event;

/**
 * The interface for all actions.<br>
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Action extends Event {

	/**
	 * Sets the {@link Actuator} that will perform this action. Note that this
	 * may also be a {@link Sensor} in the case of a {@link SensingAction}.
	 * 
	 * @param actuator
	 *            that will perform this {@link Action}
	 */
	public abstract void setActuator(Class<?> actuator);

	/**
	 * Gets the {@link Class} of {@link Actuator} that will be performing this
	 * action. Note this may be a {@link Sensor} {@link Class} if the action is
	 * a {@link SensingAction}.
	 * 
	 * @return the {@link Actuator} {@link Class}
	 */
	public abstract Class<?> getActuator();

	/**
	 * Returns the {@link ActiveBodyAppearance} of the {@link Actor} that
	 * performed this {@link Action}
	 * 
	 * @return the {@link ActiveBodyAppearance}.
	 */
	public abstract ActiveBodyAppearance getActor();

	/**
	 * Sets the {@link ActiveBodyAppearance} of the {@link Actor} that performed
	 * this {@link Action}.
	 * 
	 * @param actor
	 *            : the {@link ActiveBodyAppearance} to be set.
	 */
	public abstract void setActor(ActiveBodyAppearance actor);
}