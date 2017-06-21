package uk.ac.rhul.cs.dice.starworlds.environment.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Container;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber;
import uk.ac.rhul.cs.dice.starworlds.parser.ReflectiveMethodStore;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;

/**
 *
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractEnvironment implements Environment, Container {

	protected String id;
	protected AbstractState state;
	protected AbstractPhysics physics;
	protected Boolean bounded;
	protected EnvironmentAppearance appearance;
	protected AbstractSubscriber subscriber;

	/**
	 * Constructor.
	 *
	 * @param subscriber
	 *            : used to manage {@link Sensor}s in the system.
	 * @param state
	 *            : a {@link State} instance.
	 * @param physics
	 *            : the {@link Physics} of the environment.
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not.
	 * @param appearance
	 *            : the {@link Appearance} of the environment.
	 */
	public AbstractEnvironment(
			AbstractSubscriber subscriber,
			AbstractState state,
			AbstractPhysics physics,
			Boolean bounded,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		init(subscriber, state, physics, bounded, appearance, possibleActions);
	}

	private void init(
			AbstractSubscriber subscriber,
			AbstractState state,
			AbstractPhysics physics,
			Boolean bounded,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		this.state = state;
		this.physics = physics;
		this.bounded = bounded;
		this.appearance = appearance;
		this.id = this.appearance.getName(); // TODO should this be true?
		this.physics.setEnvironment(this);
		this.subscriber = subscriber;
		this.subscriber.setPossibleActions(possibleActions);
		this.state.getAgents().forEach((AbstractAgent agent) -> {
			agent.setEnvironment(this);
			this.subscribe(agent, representSensors(agent.getSensors()));
		});
		this.state.getActiveBodies().forEach((ActiveBody body) -> {
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

	public abstract boolean isDistributed();

	@Override
	public synchronized void updateState(AbstractEnvironmentalAction action) {
		state.filterAction(action);
	}

	public void notify(AbstractEnvironmentalAction action,
			ActiveBodyAppearance toNotify,
			Collection<AbstractPerception<?>> perceptions, State context) {
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
	public AbstractState getState() {
		return this.state;
	}

	@Override
	public void setState(State state) {
		this.state = (AbstractState) state;
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
			AbstractPerception<?> perception, State context) {
		try {
			return (boolean) this
					.getPhysics()
					.getClass()
					.getMethod(ReflectiveMethodStore.PERCEIVABLE,
							sensor.getClass(), AbstractPerception.class,
							State.class)
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
							+ State.class.getSimpleName() + ")");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.getTargetException().printStackTrace();
		}
		return false;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.appearance.toString();
	}
}