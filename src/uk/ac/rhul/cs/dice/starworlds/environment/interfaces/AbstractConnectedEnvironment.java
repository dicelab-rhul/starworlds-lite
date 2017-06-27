package uk.ac.rhul.cs.dice.starworlds.environment.interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.Ambient;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.CommandEvent;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.EnvironmentConnectionManager;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.Event;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.inet.INetDefaultMessage;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public abstract class AbstractConnectedEnvironment extends AbstractEnvironment {

	// Defines the relation between ambient's
	public static enum AmbientRelation {
		SUPER() {
			@Override
			public AmbientRelation inverse() {
				return SUB;
			}
		},
		SUB() {
			@Override
			public AmbientRelation inverse() {
				return SUPER;
			}
		},
		NEIGHBOUR() {
			@Override
			public AmbientRelation inverse() {
				return NEIGHBOUR;
			}
		};
		public abstract AmbientRelation inverse();
	}

	private static final Collection<Class<? extends AbstractEnvironmentalAction>> DEFAULTSUBSCRIPTIONACTIONS = new ArrayList<>();
	static {
		DEFAULTSUBSCRIPTIONACTIONS.add(SensingAction.class);
		DEFAULTSUBSCRIPTIONACTIONS.add(CommunicationAction.class);
	}

	public static final String SUBSCRIBE = "SUBSCRIBE", ACTION = "ACTION",
			PERCEPTION = "PERCEPTION";

	protected Map<String, MessageProcessor> messageProcessors;
	protected EnvironmentConnectionManager envconManager;
	protected Map<Pair<String, Integer>, AmbientRelation> initialConnections;
	protected ActionMessageProcessor actionProcessor;

	/**
	 * Constructor. This Constructor allows local {@link Environment}s to
	 * connect to this one.
	 * 
	 * @param ambient
	 *            : a {@link Ambient} instance.
	 * @param physics
	 *            : the {@link Physics} of the {@link Environment}
	 * @param appearance
	 *            : the {@link Appearance} of the {@link Environment}
	 * @param possibleActions
	 *            : a {@link Collection} of {@link Action}s that are possible in
	 *            this {@link Environment}
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the
	 *            {@link Environment} is bounded or not.
	 */
	public AbstractConnectedEnvironment(
			AbstractAmbient ambient,
			AbstractConnectedPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Boolean bounded) {
		super(ambient, physics, appearance, possibleActions, bounded);
		this.envconManager = new EnvironmentConnectionManager(this, null);
		this.initialiseMessageProcessors();
	}

	/**
	 * Constructor. This Constructor allows local and remote {@link Environment}
	 * s to connect to this one. Remote {@link Environment}s should connect via
	 * the give port.
	 *
	 * @param port
	 *            : the port that any remote {@link Environment} will try to
	 *            make connections to
	 * @param ambient
	 *            : a {@link Ambient} instance
	 * @param physics
	 *            : the {@link Physics} of the {@link Environment}
	 * 
	 * @param appearance
	 *            : the {@link Appearance} of the {@link Environment}.
	 * @param possibleActions
	 *            : a {@link Collection} of {@link Action}s that are possible in
	 *            this {@link Environment}
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the
	 *            {@link Environment} is bounded or not
	 */
	public AbstractConnectedEnvironment(
			Integer port,
			AbstractAmbient ambient,
			AbstractConnectedPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Boolean bounded) {
		super(ambient, physics, appearance, possibleActions, bounded);
		this.envconManager = new EnvironmentConnectionManager(this, port);
		this.initialiseMessageProcessors();
	}

	public void initialiseEnvironmentConnections(
			Collection<AbstractConnectedEnvironment> subenvironments,
			Collection<AbstractConnectedEnvironment> neighbouringenvironments) {
		this.envconManager.initialiseLocalSubEnvironments(subenvironments,
				neighbouringenvironments);
		if (this.envconManager.isDistributed()) {
			initialiseRemoteEnvironmentConnections();
		}
		this.getPhysics().initialiseSynchronisers(subenvironments,
				neighbouringenvironments);
	}

	private void initialiseRemoteEnvironmentConnections() {
		if (initialConnections != null) {
			// perform all initial remote connections
			initialConnections.forEach((addr, relation) -> {
				this.envconManager.connectToEnvironment(addr.getFirst(),
						addr.getSecond(), relation);
			});
		}
		initialConnections = null;
	}

	public void addRemoteConnection(String addr, Integer port,
			AmbientRelation relation) {
		this.initialConnections.put(new Pair<>(addr, port), relation);
	}

	/**
	 * This method is called after all {@link Environment}s have been created
	 * and are connected. It should be used for setting parameters in the
	 * {@link Ambient} etc.
	 */
	@Override
	public void postInitialisation() {
		this.initialActionSubscribe();
	}

	private void initialiseMessageProcessors() {
		messageProcessors = new HashMap<>();
		messageProcessors.put(SUBSCRIBE, new SubscriptionMessageProcessor());
		messageProcessors.put(ACTION,
				(actionProcessor = new ActionMessageProcessor()));
		messageProcessors.put(PERCEPTION, new PerceptionMessageProcessor());
	}

	public void handleMessage(EnvironmentAppearance appearance, Event<?> message) {
		// System.out.println(this.appearance + " HANDLE MESSAGE: " + message
		// + System.lineSeparator() + "   FROM: " + appearance);
		if (CommandEvent.class.isAssignableFrom(message.getClass())) {
			CommandEvent<?> cm = (CommandEvent<?>) message;
			messageProcessors.get(cm.getCommand()).process(appearance,
					cm.getCommandPayload());
		} else {
			handleCustomMessage(appearance, message);
		}
	}

	/**
	 * This method should process any messages that the default processor cannot
	 * handle. I.e. any message that is other than {@link CommandEvent}.
	 * 
	 * @param appearance
	 * @param message
	 */
	public abstract void handleCustomMessage(EnvironmentAppearance appearance,
			Event<?> message);

	public void clearAndUpdateActionsAfterPropagation() {
		((ActionMessageProcessor) messageProcessors.get(ACTION))
				.clearAndUpdateActions();
	}

	@Override
	public void notify(AbstractEnvironmentalAction action,
			ActiveBodyAppearance toNotify,
			Collection<AbstractPerception<?>> perceptions, Ambient context) {
		System.out.println("   " + this + ":NOTIFY ATTEMPT: " + toNotify
				+ " WITH: " + perceptions);
		if (!this.appearance.equals(action.getLocalEnvironment())) {
			System.out.println("     Perception(s): " + System.lineSeparator()
					+ "        " + perceptions + System.lineSeparator()
					+ "        are destined for another environment -> "
					+ action.getLocalEnvironment());
			// send it to another environment
			sendPerception(actionProcessor.getSender(action), action,
					perceptions);
		} else {
			super.notify(action, toNotify, perceptions, context);
		}
	}

	public void notifyCommunication(AbstractEnvironmentalAction action,
			ActiveBodyAppearance bodyappearance,
			Collection<AbstractPerception<?>> perceptions, Ambient context) {
		super.notify(action, bodyappearance, perceptions, context);
	}

	public void sendPerception(EnvironmentAppearance environment,
			AbstractEnvironmentalAction action,
			Collection<AbstractPerception<?>> perceptions) {
		if (perceptions != null) {
			if (!perceptions.isEmpty()) {
				this.envconManager.sendToEnvironment(environment,
						new INetDefaultMessage(PERCEPTION, new Pair<>(action,
								perceptions)));
			}
		}
	}

	public EnvironmentConnectionManager getConnectedEnvironmentManager() {
		return envconManager;
	}

	/**
	 * This method should provide the {@link Class}es of the
	 * {@link AbstractEnvironmentalAction}s that this {@link Environment} should
	 * subscribe to at initialisation (this may be none, specified by an empty
	 * {@link Collection}). That is, the {@link Action}s that it wishes to be
	 * notified of if they occur in this {@link Environment}s sub, neighbouring
	 * or super {@link Environment}(s). This method should be overridden in a
	 * subclass for custom initial action subscription. If this
	 * {@link Environment} should not subscribe to all the prior mentioned
	 * {@link Environment}s the
	 * {@link AbstractConnectedEnvironment#initialActionSubscribe()} should be
	 * overridden. The default {@link Action}s to subscribe are
	 * {@link SensingAction} and {@link CommunicationAction}. </br> If the
	 * subscription is going to happen over network, the return collection must
	 * be {@link Serializable}.
	 * 
	 * @return a {@link Collection} of default
	 *         {@link AbstractConnectedEnvironment} {@link Class}es to subscribe
	 *         to
	 */
	protected Collection<Class<? extends AbstractEnvironmentalAction>> getInitialActionsToSubscribe() {
		return Collections.unmodifiableCollection(DEFAULTSUBSCRIPTIONACTIONS);
	}

	/**
	 * This method defines the initial action subscription procedure. By default
	 * the {@link AbstractEnvironmentalAction} {@link Class}es are retrieved
	 * from the method
	 * {@link AbstractConnectedEnvironment#getInitialActionsToSubscribe()}.
	 * These are then subscribed to this {@link Environment}'s sub, neighbouring
	 * and super {@link Environment}(s). This behaviour may be changed by
	 * overriding this method in a subclass.
	 */
	protected void initialActionSubscribe() {
		INetDefaultMessage sub = new INetDefaultMessage(SUBSCRIBE,
				(Serializable) getInitialActionsToSubscribe());
		this.envconManager.sendToAllNeighbouringEnvironments(sub);
		this.envconManager.sendToAllSubEnvironments(sub);
		this.envconManager.sendToSuperEnvironment(sub);
	}

	@Override
	public synchronized void updateAmbient(AbstractEnvironmentalAction action) {
		action.setLocalEnvironment(this.getAppearance());
		super.updateAmbient(action);
	}

	public <T extends AbstractEnvironmentalAction> void sendAction(T action) {
		if (action != null) {
			Collection<EnvironmentAppearance> toSend = this.subscriber
					.getEnvironmentsFromSubscribedAction(action.getClass());
			if (toSend != null) {
				// dont send to the environment that the action originated from!
				toSend.remove(actionProcessor.getSender(action));
				if (!toSend.isEmpty()) {
					this.envconManager.sendToEnvironments(toSend,
							new INetDefaultMessage(ACTION,
									(Serializable) action));
				}
			}
		}
	}

	public <T extends AbstractEnvironmentalAction> void sendActions(
			Collection<T> actions) {
		if (actions != null) {
			if (!actions.isEmpty()) {
				Collection<EnvironmentAppearance> toSend = this.subscriber
						.getEnvironmentsFromSubscribedAction(actions.iterator()
								.next().getClass());
				if (toSend != null) {
					if (!toSend.isEmpty()) {
						// TODO dont send to environments that have already
						// received it
						this.envconManager.sendToEnvironments(toSend,
								new INetDefaultMessage(ACTION,
										(Serializable) actions));
					}
				}
			}
		}
	}

	private interface MessageProcessor {
		public void process(EnvironmentAppearance appearance, Object payload);
	}

	private class PerceptionMessageProcessor implements MessageProcessor {

		@SuppressWarnings("unchecked")
		@Override
		public void process(EnvironmentAppearance appearance, Object payload) {
			// TODO chain forward!
			Pair<?, ?> pair = (Pair<?, ?>) payload;
			AbstractEnvironmentalAction action = (AbstractEnvironmentalAction) pair
					.getFirst();
			// TODO check type
			AbstractConnectedEnvironment.this.notify(action, action.getActor(),
					(Collection<AbstractPerception<?>>) pair.getSecond(),
					ambient);
		}
	}

	private class ActionMessageProcessor implements MessageProcessor {

		// all of the actions received after action propagation
		private Map<String, AbstractEnvironmentalAction> actions;
		// where to send the resulting perceptions
		private Map<String, EnvironmentAppearance> actionReceiveMap;

		public ActionMessageProcessor() {
			actions = new HashMap<>();
			actionReceiveMap = new HashMap<>();
		}

		@Override
		public void process(EnvironmentAppearance appearance, Object payload) {
			if (payload != null) {
				if (Collection.class.isAssignableFrom(payload.getClass())) {
					Collection<?> actions = (Collection<?>) payload;
					actions.forEach((Object o) -> handleAction(
							(AbstractEnvironmentalAction) o, appearance));
				} else {
					handleAction((AbstractEnvironmentalAction) payload,
							appearance);
				}
			}
		}

		private void handleAction(AbstractEnvironmentalAction action,
				EnvironmentAppearance sender) {
			if (!actions.containsKey(action.getId())) {
				this.actions.put(action.getId(), action);
				this.actionReceiveMap.put(action.getId(), sender);
				AbstractConnectedEnvironment.this.sendAction(action);
			}
		}

		protected void clearAndUpdateActions() {
			// TODO optimise
			actions.values()
					.forEach(
							(AbstractEnvironmentalAction a) -> AbstractConnectedEnvironment.super
									.updateAmbient(a));
			actions.clear();
		}

		protected EnvironmentAppearance getSender(
				AbstractEnvironmentalAction action) {
			return this.actionReceiveMap.get(action.getId());
		}

		protected Map<String, EnvironmentAppearance> getActionReceiveMap() {
			return actionReceiveMap;
		}
	}

	// TODO create a subscribable class
	private class SubscriptionMessageProcessor implements MessageProcessor {

		@SuppressWarnings("unchecked")
		// TODO
		@Override
		public void process(EnvironmentAppearance appearance, Object payload) {
			AbstractConnectedEnvironment.this.subscriber
					.subscribeEnvironment(
							appearance,
							(Collection<Class<? extends AbstractEnvironmentalAction>>) payload);
		}
	}

	@Override
	public AbstractConnectedPhysics getPhysics() {
		return (AbstractConnectedPhysics) this.physics;
	}
}
