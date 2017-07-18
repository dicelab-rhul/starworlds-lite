package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.Actor;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;
import uk.ac.rhul.cs.dice.starworlds.perception.NullPerception;

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

	private static final long serialVersionUID = 1L;

	// All actions may return a null perception
	@SensiblePerception
	public static final Class<?> NULLPERCEPTION = NullPerception.class;

	private String id;
	private ActiveBodyAppearance actor;
	private Class<?> actuatorclass;
	private EnvironmentAppearance localEnvironment;

	/**
	 * The default constructor.
	 */
	public AbstractEnvironmentalAction() {
		this.id = IDFactory.getInstance().getNewID();
	}

	/**
	 * The constructor with an {@link Actor}.
	 * 
	 * @param actor
	 *            : the {@link Actor} of the {@link EnvironmentalAction}.
	 */
	public AbstractEnvironmentalAction(ActiveBodyAppearance actor) {
		this.actor = actor;
		this.id = IDFactory.getInstance().getNewID();
	}

	/**
	 * Returns the {@link ActiveBodyAppearance} of the
	 * {@link EnvironmentalAction}.
	 * 
	 * @return the {@link ActiveBodyAppearance} of the
	 *         {@link EnvironmentalAction}.
	 */
	@Override
	public ActiveBodyAppearance getActor() {
		return this.actor;
	}

	/**
	 * Sets the {@link Actor} of the {@link EnvironmentalAction}.
	 * 
	 * @param actor
	 *            : the {@link Actor} of the {@link EnvironmentalAction}.
	 */
	@Override
	public void setActor(ActiveBodyAppearance actor) {
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

	@Override
	public String getId() {
		// TODO networked actions? ensuring unique ids?
		return this.id;
	}

	/**
	 * The id of an {@link AbstractEnvironmentalAction} cannot be changed, this
	 * method does nothing.
	 */
	@Override
	public void setId(String id) {
		// the id of an action cannot be changed
	}

	public EnvironmentAppearance getLocalEnvironment() {
		return localEnvironment;
	}

	public void setLocalEnvironment(EnvironmentAppearance localEnvironment) {
		this.localEnvironment = localEnvironment;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":" + id;
	}
}