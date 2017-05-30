package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import uk.ac.rhul.cs.dice.starworlds.entities.Actor;

/**
 * The most generic action class implementing {@link EnvironmentalAction}.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link PhysicalAction}, {@link CommunicationAction},
 * {@link SensingAction}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractEnvironmentalAction implements
		EnvironmentalAction {

	private Actor actor;
	private Class<?> actuatorclass;

	public abstract Object[] getCanSense();

	/**
	 * The default constructor.
	 */
	public AbstractEnvironmentalAction() {
	}

	/**
	 * The constructor with an {@link Actor}.
	 * 
	 * @param actor
	 *            : the {@link Actor} of the {@link EnvironmentalAction}.
	 */
	public AbstractEnvironmentalAction(Actor actor) {
		this.actor = actor;
	}

	/**
	 * Returns the {@link Actor} of the {@link EnvironmentalAction}.
	 * 
	 * @return the {@link Actor} of the {@link EnvironmentalAction}.
	 */
	@Override
	public Actor getActor() {
		return this.actor;
	}

	/**
	 * Sets the {@link Actor} of the {@link EnvironmentalAction}.
	 * 
	 * @param actor
	 *            : the {@link Actor} of the {@link EnvironmentalAction}.
	 */
	@Override
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	@Override
	public void setActuator(Class<?> actuator) {
		this.actuatorclass = actuator;
	}

	@Override
	public Class<?> getActuator() {
		return this.actuatorclass;
	}
}