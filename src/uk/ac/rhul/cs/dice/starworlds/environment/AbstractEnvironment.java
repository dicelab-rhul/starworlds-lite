package uk.ac.rhul.cs.dice.starworlds.environment;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractSubscriber;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

/**
 * The most general class representing an environment. It has an
 * {@link EnvironmentalSpace}, an instance of {@link Physics}, a {@link Boolean}
 * indicating whether it is bounded or not, and an {@link Appearance}.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link SimpleEnvironment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractEnvironment<T> implements Environment, Container {

	protected State state;
	protected Physics physics;
	protected Boolean bounded;
	protected Appearance appearance;
	protected AbstractSubscriber<T> subscriber;

	/**
	 * Constructor. This constructor should be used when any {@link Environment}
	 * s that may interact with this one are running on the same machine.
	 * 
	 * @param subscriber
	 *            : used to manage {@link Sensor}s in the system.
	 * @param state
	 *            : an {@link State} instance.
	 * @param physics
	 *            : the {@link Physics} of the environment.
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not.
	 * @param appearance
	 *            : the {@link Appearance} of the environment.
	 */
	public AbstractEnvironment(
			AbstractSubscriber<T> subscriber,
			State state,
			Physics physics,
			Boolean bounded,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		init(subscriber, state, physics, bounded, appearance, possibleActions);
	}

	private void init(
			AbstractSubscriber<T> subscriber,
			State state,
			Physics physics,
			Boolean bounded,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		this.state = state;
		this.physics = physics;
		this.bounded = bounded;
		this.appearance = appearance;
		this.physics.setEnvironment(this);
		this.subscriber = subscriber;
		this.subscriber.setPossibleActions(possibleActions);
	}

	public abstract boolean isDistributed();

	@Override
	public synchronized void updateState(AbstractEnvironmentalAction action) {
		// System.out.println("UPDATING STATE WITH: " + action);
		state.filterAction(action);
		// System.out.println(Arrays.toString(state.getSensingActions().toArray()));
	}

	public void notify(AbstractEnvironmentalAction action, ActiveBody body,
			Collection<AbstractPerception<?>> perceptions, State context) {
		Map<Class<? extends AbstractPerception>, Set<T>> sensors = subscriber
				.findSensors(body, action);
		for (AbstractPerception<?> perception : perceptions) {
			Set<T> ss = sensors.get(perception.getClass());
			if (ss != null) {
				for (T s : ss) {
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

	protected abstract void notifySensor(T sensor,
			AbstractPerception<?> perception);

	@Override
	public State getState() {
		return this.state;
	}

	@Override
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public Physics getPhysics() {
		return this.physics;
	}

	@Override
	public void setPhysics(Physics physics) {
		this.physics = physics;
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
	public Appearance getAppearance() {
		return this.appearance;
	}

	@Override
	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}

	public synchronized void subscribe(ActiveBody body, T[] sensors) {
		subscriber.subscribe(body, sensors);
	}

	protected synchronized AbstractSubscriber<T> getSubscriber() {
		return subscriber;
	}

	protected boolean checkPerceivable(T sensor,
			AbstractPerception<?> perception, State context) {
		try {
			return (boolean) this
					.getPhysics()
					.getClass()
					.getMethod("perceivable", sensor.getClass(),
							AbstractPerception.class, State.class)
					.invoke(this.getPhysics(), sensor, perception, context);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.err.println("A Perceivable method must be defined in: "
					+ this.getPhysics().getClass().getSimpleName()
					+ " and be accessible to: "
					+ this.getClass().getSimpleName() + System.lineSeparator()
					+ "It must have the signature: " + System.lineSeparator()
					+ "perceivable(" + sensor.getClass().getSimpleName() + ","
					+ AbstractPerception.class.getSimpleName() + ","
					+ State.class.getSimpleName() + ")");
			e.printStackTrace();
			return false;
		}
	}
}