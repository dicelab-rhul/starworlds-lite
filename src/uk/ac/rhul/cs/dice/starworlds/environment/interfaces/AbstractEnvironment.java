package uk.ac.rhul.cs.dice.starworlds.environment.interfaces;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.Subscriber;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;
import uk.ac.rhul.cs.dice.starworlds.initialisation.ReflectiveMethodStore;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

/**
 *
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractEnvironment extends Observable implements
		Environment {

	protected Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions;
	protected AbstractAmbient ambient;
	protected AbstractPhysics physics;
	protected Boolean bounded;
	protected EnvironmentAppearance appearance;
	protected AbstractSubscriber subscriber;

	/**
	 * Constructor. The {@link Appearance} of this {@link AbstractEnvironment}
	 * defaults to an {@link EnvironmentAppearance}. The {@link Environment} is
	 * unbounded by default.
	 * 
	 * @param ambient
	 *            : a {@link Ambient} instance
	 * @param physics
	 *            : the {@link Physics} of the environment
	 * @param possibleActions
	 *            : the {@link Collection} of {@link Action}s that are possible
	 *            in this {@link Environment}
	 * 
	 */
	public AbstractEnvironment(
			AbstractAmbient ambient,
			AbstractPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		init(new Subscriber(), ambient, physics, new EnvironmentAppearance(
				IDFactory.getInstance().getNewID(), false, true),
				possibleActions, false);
	}

	/**
	 * Constructor. The {@link Environment} is unbounded by default.
	 *
	 * @param ambient
	 *            : a {@link Ambient} instance
	 * @param physics
	 *            : the {@link Physics} of the environment
	 * @param appearance
	 *            : the {@link Appearance} of the environment
	 * @param possibleActions
	 *            : the {@link Collection} of {@link Action}s that are possible
	 *            in this {@link Environment}
	 */
	public AbstractEnvironment(
			AbstractAmbient ambient,
			AbstractPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		init(new Subscriber(), ambient, physics, appearance, possibleActions,
				false);
	}

	/**
	 * Constructor.
	 *
	 * @param ambient
	 *            : a {@link Ambient} instance
	 * @param physics
	 *            : the {@link Physics} of the environment
	 * @param appearance
	 *            : the {@link Appearance} of the environment
	 * @param possibleActions
	 *            : the {@link Collection} of {@link Action}s that are possible
	 *            in this {@link Environment}
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not
	 */
	public AbstractEnvironment(
			AbstractAmbient ambient,
			AbstractPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Boolean bounded) {
		init(new Subscriber(), ambient, physics, appearance, possibleActions,
				bounded);
	}

	private void init(
			AbstractSubscriber subscriber,
			AbstractAmbient ambient,
			AbstractPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Boolean bounded) {
		this.ambient = ambient;
		this.physics = physics;
		this.bounded = bounded;
		this.appearance = appearance;
		this.physics.setEnvironment(this);
		this.subscriber = subscriber;
		this.possibleActions = possibleActions;
		this.subscriber.setPossibleActions(possibleActions);
		this.ambient.getAgents().forEach((AbstractAgent agent) -> {
			agent.setEnvironment(this);
			this.subscribe(agent, representSensors(agent.getSensors()));
		});
		this.ambient.getActiveBodies().forEach((ActiveBody body) -> {
			body.setEnvironment(this);
			this.subscribe(body, representSensors(body.getSensors()));
		});
	}

	protected AbstractSensor[] representSensors(List<Sensor> sensors) {
		return sensors.toArray(new AbstractSensor[] {});
	}

	protected void notifySensor(AbstractSensor sensor,
			AbstractPerception<?> perception) {
		sensor.notify(perception);
	}

	@Override
	public synchronized void updateAmbient(AbstractEnvironmentalAction action) {
		ambient.filterAction(action);
	}

	// TODO shift functionality to AbstractPhysics, the checkPerceivable method
	// should also be moved
	public void notify(AbstractEnvironmentalAction action,
			ActiveBodyAppearance toNotify,
			Collection<AbstractPerception<?>> perceptions, Ambient context) {
		@SuppressWarnings("rawtypes")
		Map<Class<? extends AbstractPerception>, Set<AbstractSensor>> sensors = subscriber
				.findSensors(toNotify, action);
		for (AbstractPerception<?> perception : perceptions) {
			Set<AbstractSensor> ss = sensors.get(perception.getClass());
			if (ss != null) {
				for (AbstractSensor s : ss) {
					if (checkPerceivable(s, perception, context)) {
						this.notifySensor(s, perception);
					}
				}
			} else {
				// TODO remove
				System.err
						.println("WARNING: No subscribed Sensor to receive perception type: "
								+ perception);
				Thread.dumpStack();
			}
		}
	}

	@Override
	public AbstractAmbient getState() {
		return this.ambient;
	}

	@Override
	public void setState(Ambient state) {
		this.ambient = (AbstractAmbient) state;
	}

	@Override
	public AbstractPhysics getPhysics() {
		return this.physics;
	}

	@Override
	public void setPhysics(Physics physics) {
		this.physics = (AbstractPhysics) physics;
	}

	@Override
	public Boolean isBounded() {
		return this.bounded;
	}

	@Override
	public void setBounded(Boolean bounded) {
		this.bounded = bounded;
	}

	@Override
	public EnvironmentAppearance getAppearance() {
		return this.appearance;
	}

	@Override
	public void setAppearance(Appearance appearance) {
		this.appearance = (EnvironmentAppearance) appearance;
	}

	public synchronized void subscribe(ActiveBody body, AbstractSensor[] sensors) {
		subscriber.subscribe(body, sensors);
	}

	public synchronized AbstractSubscriber getSubscriber() {
		return subscriber;
	}

	protected boolean checkPerceivable(AbstractSensor sensor,
			AbstractPerception<?> perception, Ambient context) {
		try {
			return (boolean) this
					.getPhysics()
					.getClass()
					.getMethod(ReflectiveMethodStore.PERCEIVABLE,
							sensor.getClass(), AbstractPerception.class,
							Ambient.class)
					.invoke(this.getPhysics(), sensor, perception, context);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException e) {
			System.err
					.println("A Perceivable method must be defined in the class: "
							+ this.getPhysics().getClass().getSimpleName()
							+ " and be accessible to the class: "
							+ this.getClass().getSimpleName()
							+ System.lineSeparator()
							+ "   It must have the signature: "
							+ System.lineSeparator()
							+ "   perceivable("
							+ sensor.getClass().getSimpleName()
							+ ","
							+ AbstractPerception.class.getSimpleName()
							+ ","
							+ Ambient.class.getSimpleName() + ")");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.getTargetException().printStackTrace();
		}
		return false;
	}

	@Override
	public String getId() {
		return this.appearance.getId();
	}

	@Override
	public void setId(String id) {
		this.appearance.setId(id);
	}

	public Collection<Class<? extends AbstractEnvironmentalAction>> getPossibleActions() {
		return possibleActions;
	}

	@Override
	public String toString() {
		return this.appearance.toString();
	}
}