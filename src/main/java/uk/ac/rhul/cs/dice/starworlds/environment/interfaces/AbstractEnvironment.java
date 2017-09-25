package uk.ac.rhul.cs.dice.starworlds.environment.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Observable;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.AbstractSubscriptionHandler;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.SensorSubscriptionHandler;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;
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
	protected AbstractSubscriptionHandler subscriber;

	//
	// protected Stack<Entity> safeBodyBuffer;
	// protected Stack<>

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
		init(new SensorSubscriptionHandler(), ambient, physics, new EnvironmentAppearance(
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
		init(new SensorSubscriptionHandler(), ambient, physics, appearance, possibleActions,
				false);
	}

	/**
	 * Constructor.
	 *
	 * @param ambient
	 *            : a {@link Ambient} instance
	 * @param physics
	 *            : the {@link Physics} of the environment
	 * @param subscriptionHandler
	 *            : the {@link AbstractSubscriptionHandler} that will handle
	 *            subscriptions in this {@link Environment}.
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
			AbstractSubscriptionHandler subscriptionHandler,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Boolean bounded) {
		init(subscriptionHandler, ambient, physics, appearance,
				possibleActions, bounded);
	}

	private void init(
			AbstractSubscriptionHandler subscriber,
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
		this.ambient.getAgents().forEach((AbstractAutonomousAgent agent) -> {
			subscribeActiveBody(agent);
		});
		this.ambient.getAvatars().forEach((AbstractAvatarAgent<?> avatar) -> {
			subscribeActiveBody(avatar);
		});
		this.ambient.getActiveBodies().forEach((ActiveBody body) -> {
			subscribeActiveBody(body);
		});
	}

	/**
	 * Subscribes the given {@link AbstractAvatarAgent}s {@link Sensor}s to this
	 * {@link Environment} and adds it to this {@link Environment}s
	 * {@link Ambient}.
	 * 
	 * @param avatar
	 *            : to add
	 */
	public void safeAddAvatar(AbstractAvatarAgent<?> avatar) {
		subscribeActiveBody(avatar);
		this.ambient.addAvatar(avatar);
	}

	/**
	 * Subscribes the given {@link AbstractAutonomousAgent}s {@link Sensor}s to
	 * this {@link Environment} and adds it to this {@link Environment}s
	 * {@link Ambient}.
	 * 
	 * @param agent
	 *            : to add
	 */
	public void safeAddAgent(AbstractAutonomousAgent agent) {
		subscribeActiveBody(agent);
		this.ambient.addAgent(agent);
	}

	/**
	 * Subscribes the given {@link ActiveBody}s {@link Sensor}s to this
	 * {@link Environment} and adds it to this {@link Environment}s
	 * {@link Ambient}.
	 * 
	 * @param body
	 *            : to add
	 */
	public void safeAddActiveBody(ActiveBody body) {
		subscribeActiveBody(body);
		this.ambient.addActiveBody(body);
	}

	/**
	 * Adds the {@link PassiveBody} to this {@link Environment}s {@link Ambient}
	 * .
	 * 
	 * @param agent
	 *            : to add
	 */
	public void safeAddPassiveBody(PassiveBody body) {
		this.ambient.addPassiveBody(body);
	}

	/**
	 * Subscribes the given {@link AbstractAvatarAgent}s {@link Sensor}s to this
	 * {@link Environment} and adds it to this {@link Environment}s
	 * {@link Ambient}. This method should only be called outside of out the
	 * {@link Environment} cycle to prevent conflict of access, consider calling
	 * in {@link AbstractPhysics#cycleAddition()} or using
	 * {@link AbstractEnvironment#safeAddAvatar(AbstractAvatarAgent)} instead.
	 * 
	 * @param avatar
	 *            : to add
	 */
	public void addAvatar(AbstractAvatarAgent<?> avatar) {
		subscribeActiveBody(avatar);
		this.ambient.addAvatar(avatar);
	}

	/**
	 * Subscribes the given {@link AbstractAutonomousAgent}s {@link Sensor}s to
	 * this {@link Environment} and adds it to this {@link Environment}s
	 * {@link Ambient}. This method should only be called outside of out the
	 * {@link Environment} cycle to prevent conflict of access, consider calling
	 * in {@link AbstractPhysics#cycleAddition()} or using
	 * {@link AbstractEnvironment#safeAddAgent(AbstractAutonomousAgent)}
	 * instead.
	 * 
	 * @param agent
	 *            : to add
	 */
	public void addAgent(AbstractAutonomousAgent agent) {
		subscribeActiveBody(agent);
		this.ambient.addAgent(agent);
	}

	/**
	 * Subscribes the given {@link ActiveBody}s {@link Sensor}s to this
	 * {@link Environment} and adds it to this {@link Environment}s
	 * {@link Ambient}. This method should only be called outside of out the
	 * {@link Environment} cycle to prevent conflict of access, consider calling
	 * in {@link AbstractPhysics#cycleAddition()} or using
	 * {@link AbstractEnvironment#safeAddActiveBody(ActiveBody)} instead.
	 * 
	 * @param body
	 *            : to add
	 */
	public void addActiveBody(ActiveBody body) {
		subscribeActiveBody(body);
		this.ambient.addActiveBody(body);
	}

	/**
	 * Adds the {@link PassiveBody} to this {@link Environment}s {@link Ambient}
	 * . This method should only be called outside of out the
	 * {@link Environment} cycle to prevent conflict of access, consider calling
	 * in {@link AbstractPhysics#cycleAddition()} or using
	 * {@link AbstractEnvironment#safeAddPassiveBody(PassiveBody)} instead.
	 * 
	 * @param agent
	 *            : to add
	 */
	public void addPassiveBody(PassiveBody body) {
		this.ambient.addPassiveBody(body);
	}

	/**
	 * Subscribes the given {@link ActiveBody}'s {@link Sensor}s to this
	 * {@link Environment}.
	 * 
	 * @param body
	 *            : to subscribe
	 */
	public void subscribeActiveBody(ActiveBody body) {
		body.setEnvironment(this);
		this.subscribe(body, representSensors(body.getSensors()));
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

	public synchronized AbstractSubscriptionHandler getSubscriber() {
		return subscriber;
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