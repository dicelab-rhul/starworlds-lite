package uk.ac.rhul.cs.dice.starworlds.environment.physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.Entity;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.State;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultPhysics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

/**
 * An abstract implementation of {@link Physics}. This physics handles the
 * Perceive, Decide, Execute cycle of all {@link Agent}s in its
 * {@link Environment} - it is the time keeper. The {@link Physics} is
 * responsible for executing any {@link Action}s that an {@link Agent} or
 * {@link ActiveBody} performs. These {@link Action}s default as follows: </br>
 * {@link SensingAction}, {@link CommunicationAction}, {@link PhysicalAction}.
 * For details on each type see their documentation. </br>
 * 
 * 
 * Known direct subclasses: {@link DefaultPhysics}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractPhysics implements Physics {

	protected AbstractEnvironment environment;
	protected Map<String, AbstractAgent> agents;
	protected Map<String, ActiveBody> activeBodies;
	protected Map<String, PassiveBody> passiveBodies;
	protected SensorSubscriber sensorSubscriber;
	protected boolean active = false;
	protected long framegap = 1000;
	protected TimeState timestate;

	public AbstractPhysics(
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Set<AbstractAgent> agents, Set<ActiveBody> activeBodies,
			Set<PassiveBody> passiveBodies) {
		this.agents = (agents != null) ? setToMap(agents) : new HashMap<>();
		this.activeBodies = (activeBodies != null) ? setToMap(activeBodies)
				: new HashMap<>();
		this.passiveBodies = (passiveBodies != null) ? setToMap(passiveBodies)
				: new HashMap<>();
		this.sensorSubscriber = new SensorSubscriber();
		this.sensorSubscriber.setPossibleActions(possibleActions);
	}

	public final void subscribe(ActiveBody body, Sensor... sensors) {
		sensorSubscriber.subscribe(body, sensors);
	}

	private <T extends Entity> Map<String, T> setToMap(Set<T> set) {
		Map<String, T> map = new HashMap<>();
		set.forEach((T t) -> {
			;
			map.put((String) t.getId(), t);
		});
		return map;
	}

	public void start(boolean serial) {
		setTimeState(serial);
		try {
			active = true;
			while (active) {
				timestate.simulate();
				Thread.sleep(framegap);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void executeActions() {
		doPhysicalActions();
		doSensingActions();
		doCommunicationActions();
	}

	private void doPhysicalActions() {
		environment
				.getState()
				.getPhysicalActions()
				.forEach(
						(PhysicalAction s) -> this.execute(s,
								environment.getState()));
		environment.getState().getPhysicalActions().clear();
	}

	private void doSensingActions() {
		// System.out.println("DO SENSING ACTIONS"
		// + environment.getState().getSensingActions());
		environment
				.getState()
				.getSensingActions()
				.forEach(
						(SensingAction s) -> this.execute(s,
								environment.getState()));
		environment.getState().getSensingActions().clear();
	}

	private void doCommunicationActions() {
		environment
				.getState()
				.getCommunicationActions()
				.forEach(
						(CommunicationAction<?> c) -> this.execute(c,
								environment.getState()));
		environment.getState().getCommunicationActions().clear();
	}

	// ***************************************************** //
	// ****************** PHYSICAL ACTIONS ***************** //
	// ***************************************************** //

	@Override
	public void execute(PhysicalAction action, State context) {
		AbstractPerception<?> perception = null;
		Pair<Set<AbstractPerception<?>>, Set<AbstractPerception<?>>> perceptions = null;
		if (isPossible(action, context)) {
			perceptions = perform(action, context);
			verify(action, context); // TODO do something if failed?
		}
		if (perceptions != null) {
			// notify the agent who performed
			notify(action, (ActiveBody) action.getActor(),
					perceptions.getFirst(), context);
			// notify the other agents
			
			//TODO optimise
			for (ActiveBody a : this.getAgents()) {
				if(!a.equals(action.getActor())) {
					notify(action, a, perceptions.getSecond(), context);
				}
			}
		}
	}

	@Override
	public Pair<Set<AbstractPerception<?>>, Set<AbstractPerception<?>>> perform(
			PhysicalAction action, State context) {
		return action.perform(this, context);
	}

	@Override
	public boolean isPossible(PhysicalAction action, State context) {
		return action.isPossible(this, context);
	}

	@Override
	public boolean verify(PhysicalAction action, State context) {
		return action.verify(this, context);
	}

	// ***************************************************** //
	// ****************** SENSING ACTIONS ****************** //
	// ***************************************************** //

	@Override
	public void execute(SensingAction action, State context) {
		Set<Pair<String, Object>> perceptions = null;
		if (isPossible(action, context)) {
			perceptions = perform(action, context);
			verify(action, context); // TODO what to verify?
		}
		// should other agents be able to sense that this agent is sensing?
		Collection<AbstractPerception<?>> perceptionsToNotify = new HashSet<>();
		perceptionsToNotify
				.add(new DefaultPerception<Set<Pair<String, Object>>>(
						perceptions));
		notify(action, (ActiveBody) action.getActor(), perceptionsToNotify, context);
	}

	@Override
	public Set<Pair<String, Object>> perform(SensingAction action, State context) {
		// System.out.println(Arrays.toString(action.getKeys()));
		return context.filterActivePerception(action.getKeys(), action);
	}

	@Override
	public boolean isPossible(SensingAction action, State context) {
		String[] keys = action.getKeys();
		int count = 0;
		// System.out.println("KEYS: " + Arrays.toString(keys));
		for (int i = 0; i < keys.length; i++) {
			String[] subkeys = keys[i].split("\\.");
			// TODO handle integer parameters
			if (context.environmentVariableExists(subkeys[0])) {
				for (int j = 1; j < subkeys.length; j++) {
					if (!context.filterExists(subkeys[j])) {
						keys[i] = null;
						count++;
						break;
					}
				}
			} else {
				keys[i] = null;
				count++;
			}
		}
		return count < keys.length;
	}

	@Override
	public boolean verify(SensingAction action, State context) {
		return true;
	}

	// ***************************************************** //
	// *************** COMMUNICATION ACTIONS *************** //
	// ***************************************************** //

	@Override
	public void execute(CommunicationAction<?> action, State context) {
		if (isPossible(action, context)) {
			perform(action, context);
			verify(action, context);
		}
		// TODO notify - should the sending agent be notified?
	}

	@Override
	public boolean perform(CommunicationAction<?> action, State context) {
		if (action.getRecipientsIds().isEmpty()) {
			// send to all agents except the sender.
			Collection<AbstractAgent> recipients = new HashSet<>(
					this.getAgents());
			recipients.remove(action.getActor());
			recipients
					.forEach((AbstractAgent a) -> {
						Collection<AbstractPerception<?>> perceptionsToNotify = new HashSet<>();
						perceptionsToNotify.add(new CommunicationPerception<>(
								action.getPayload()));
						notify(action, a, perceptionsToNotify, context);
					});
		}
		action.getRecipientsIds()
				.forEach(
						(String s) -> {
							AbstractAgent agent = agents.get(s);
							if (agent != null) {
								Collection<AbstractPerception<?>> perceptionsToNotify = new HashSet<>();
								perceptionsToNotify
										.add(new CommunicationPerception<>(
												action.getPayload()));
								notify(action, agent, perceptionsToNotify, context);
							}
						});
		// TODO if something failed
		return true;
	}

	@Override
	public boolean isPossible(CommunicationAction<?> action, State context) {
		// TODO check if the message is for sub/super environments
		return true;
	}

	@Override
	public boolean verify(CommunicationAction<?> action, State context) {
		// TODO check that the state has the proper perceptions
		return true;
	}

	@Override
	public void notify(AbstractEnvironmentalAction action, ActiveBody body,
			Collection<AbstractPerception<?>> perceptions, State context) {
		
		Map<Class<? extends AbstractPerception>, Set<AbstractSensor>> sensors = sensorSubscriber
				.findSensors(body, action);
		for (AbstractPerception<?> perception : perceptions) {
			Set<AbstractSensor> ss = sensors.get(perception.getClass());
			if (ss != null) {
				for (AbstractSensor s : ss) {
					if (s.canSense(action, perception, context)) {
						System.out.println("NOTIFY: " + body + " WITH: " + perceptions);
						s.notify(perception);
					}
				}
			} else {
				// TODO remove
				System.err
						.println("WARNING: No subscribed Sensor to receive perception type: "
								+ perception);
			}
		}
	}

	@Override
	public Collection<AbstractAgent> getAgents() {
		return this.agents.values();
	}

	@Override
	public Collection<ActiveBody> getActiveBodies() {
		return this.activeBodies.values();
	}

	@Override
	public Collection<PassiveBody> getPassiveBodies() {
		return this.passiveBodies.values();
	}

	@Override
	public void setEnvironment(AbstractEnvironment environment) {
		this.environment = environment;
	}

	protected void setTimeState(boolean serial) {
		if (serial) {
			timestate = new TimeStateSerial();
		} else {
			timestate = new TimeStateParallel();
		}
	}

	/**
	 * The state of a {@link Physics} that may be either serial or parallel see
	 * {@link TimeStateSerial}, {@link TimeStateParallel}. These
	 * {@link TimeState}s define how the system should run, namely whether the
	 * system is multi-threaded.
	 * 
	 * @author Ben Wilkins
	 *
	 */
	protected interface TimeState {
		public void simulate();
	}

	/**
	 * The implementation of {@link TimeState} for serial, agents will run in an
	 * arbitrary order one at a time.
	 * 
	 * @author Ben Wilkins
	 *
	 */
	protected class TimeStateSerial implements TimeState {

		public void simulate() {

			System.out.println("CYCLE");
			agents.values().forEach((AbstractAgent a) -> {
				a.run();
			});
			executeActions();
		}

	}

	/**
	 * The implementation of {@link TimeState} for parallel, agents will run in
	 * their own thread in parallel.
	 * 
	 * @author Ben Wilkins
	 *
	 */
	protected class TimeStateParallel implements TimeState {

		@Override
		public void simulate() {
			// split into threads
			Collection<Thread> threads = new ArrayList<>();
			agents.values().forEach((AbstractAgent a) -> {
				Thread t = new Thread(a);
				threads.add(t);
				t.start();
			});
			waitForAgents(threads);
			executeActions();
		}

		private void waitForAgents(Collection<Thread> threads) {
			try {
				for (Thread t : threads)
					t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("ALL AGENTS DONE!");
		}
	}

}