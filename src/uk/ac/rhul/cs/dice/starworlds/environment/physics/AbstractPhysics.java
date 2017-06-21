package uk.ac.rhul.cs.dice.starworlds.environment.physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.Entity;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Simulator;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.State;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultPhysics;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.NullPerception;
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
public abstract class AbstractPhysics implements Physics, Simulator {

	private static final long FRAMELENGTH = 1000;

	private String id;
	protected AbstractEnvironment environment;
	protected Map<String, AbstractAgent> agents;
	protected Map<String, ActiveBody> activeBodies;
	protected Map<String, PassiveBody> passiveBodies;
	// TODO change to not default
	private TimeState timestate = new TimeStateSerial();

	public AbstractPhysics(Set<AbstractAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies) {
		this.agents = (agents != null) ? setToMap(agents) : new HashMap<>();
		this.activeBodies = (activeBodies != null) ? setToMap(activeBodies)
				: new HashMap<>();
		this.passiveBodies = (passiveBodies != null) ? setToMap(passiveBodies)
				: new HashMap<>();
	}

	public void simulate() {
		while (true) {
			this.runAgents();
			this.executeActions();
			try {
				Thread.sleep(FRAMELENGTH);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void runAgents() {
		timestate.simulate();
	}

	@Override
	public void executeActions() {
		doPhysicalActions(environment.getState().flushPhysicalActions());
		doSensingActions(environment.getState().flushSensingActions());
		doCommunicationActions(environment.getState()
				.flushCommunicationActions());
	}

	protected void doPhysicalActions(Collection<PhysicalAction> actions) {
		Collection<PhysicalAction> failedactions = new ArrayList<>();
		actions.forEach((PhysicalAction a) -> {
			if (!this.execute(a, environment.getState())) {
				failedactions.add(a);
			}
		});
		actions.removeAll(failedactions);
		// get the perceptions of all non-failed actions, this must be done so
		// that any perceptions are not out of sync
		actions.forEach((PhysicalAction a) -> {
			this.doPhysicalPerceptions(a, environment.getState());
		});
	}

	protected void doSensingActions(Collection<SensingAction> actions) {
		// System.out.println("DO SENSING ACTIONS"
		// + environment.getState().getSensingActions());
		actions.forEach((SensingAction s) -> this.execute(s,
				environment.getState()));
	}

	protected void doCommunicationActions(
			Collection<CommunicationAction<?>> actions) {
		actions.forEach((CommunicationAction<?> c) -> this.execute(c,
				environment.getState()));
	}

	// ***************************************************** //
	// ****************** PHYSICAL ACTIONS ***************** //
	// ***************************************************** //

	@Override
	public boolean execute(PhysicalAction action, State context) {
		AbstractPerception<?> perception = null;
		if (isPossible(action, context)) {
			if (perform(action, context)) {
				if (verify(action, context)) {
					return true;
				}
			}
		}
		// notify the agent that their action has failed
		return false;
	}

	// finds the perceptions generated by a given physical action
	private void doPhysicalPerceptions(PhysicalAction action, State context) {
		Collection<AbstractPerception<?>> agentPerceptions = action
				.getAgentPerceptions(this, context);
		Collection<AbstractPerception<?>> otherPerceptions = action
				.getOtherPerceptions(this, context);
		environment
				.notify(action, action.getActor(), agentPerceptions, context);
		for (ActiveBody a : this.getAgents()) {
			if (!a.getAppearance().equals(action.getActor())) {
				environment.notify(action, a.getAppearance(), otherPerceptions,
						context);
			}
		}
	}

	@Override
	public boolean perform(PhysicalAction action, State context) {
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
		if (perceptions != null) {
			Collection<AbstractPerception<?>> perceptionsToNotify = new HashSet<>();
			for (Pair<String, Object> p : perceptions) {
				if (p != null) {
					perceptionsToNotify
							.add(new DefaultPerception<Pair<String, Object>>(p));
				} else {
					perceptionsToNotify.add(new NullPerception());
				}
			}
			environment.notify(action, action.getActor(), perceptionsToNotify,
					context);
		}
	}

	@Override
	public Set<Pair<String, Object>> perform(SensingAction action, State context) {
		return context.filterActivePerception(action.getKeys(), action);
	}

	@Override
	public boolean isPossible(SensingAction action, State context) {
		String[] keys = action.getKeys();
		int count = 0;
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
						environment.notify(action, a.getAppearance(),
								perceptionsToNotify, context);
					});
		}
		// Clone the action as a special case - the perception should be
		// returned to the recipients that (may) reside in this environment
		CommunicationAction<?> clone = new CommunicationAction<>(action);
		clone.setLocalEnvironment(this.environment.getAppearance());
		action.getRecipientsIds()
				.forEach(
						(String s) -> {
							AbstractAgent agent = agents.get(s);
							if (agent != null) {
								ActiveBodyAppearance appearance = agent
										.getAppearance();
								Collection<AbstractPerception<?>> perceptionsToNotify = new HashSet<>();
								perceptionsToNotify
										.add(new CommunicationPerception<>(
												action.getPayload()));
								// the agent resides within this environment
								environment.notify(clone, appearance,
										perceptionsToNotify, context);
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

	public ActiveBody getAgent(ActiveBodyAppearance appearance) {
		return this.agents.get(appearance.getName());
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
		if (this.environment == null) {
			this.environment = environment;
			this.setId(environment.getId());
		}
	}

	protected AbstractEnvironment getEnvironment() {
		return this.environment;
	}

	/**
	 * The state of a {@link Physics} that may be either serial or parallel see
	 * {@link TimeStateSerial}, {@link TimeStateParallel}. These
	 * {@link TimeState}s define the order in which the {@link Agent}s should
	 * run, namely whether the system is multi-threaded.
	 * 
	 * @author Ben Wilkins
	 *
	 */
	protected interface TimeState {
		public void start();

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
			agents.values().forEach((AbstractAgent a) -> {
				a.run();
			});
		}

		@Override
		public void start() {

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
		public void start() {

		}

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
		}

		private void waitForAgents(Collection<Thread> threads) {
			try {
				for (Thread t : threads)
					t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Perceivable method for all {@link SeeingSensor}s. See
	 * {@link Physics#perceivable(AbstractSensor, AbstractPerception, State)}.
	 */
	public boolean perceivable(SeeingSensor sensor,
			AbstractPerception<?> perception, State context) {
		return SeeingSensor.DEFAULTPERCEPTION.isAssignableFrom(perception
				.getClass())
				|| SeeingSensor.NULLPERCEPTION.isAssignableFrom(perception
						.getClass());
	}

	/**
	 * Perceivable method for all {@link ListeningSensor}s. See
	 * {@link Physics#perceivable(AbstractSensor, AbstractPerception, State)}.
	 */
	public boolean perceivable(ListeningSensor sensor,
			AbstractPerception<?> perception, State context) {
		return ListeningSensor.COMMUNICATIONPERCEPTION
				.isAssignableFrom(perception.getClass());
	}

	@Override
	public boolean perceivable(AbstractSensor sensor,
			AbstractPerception<?> perception, State context) {
		return AbstractSensor.NULLPERCEPTION.isAssignableFrom(perception
				.getClass());
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	protected void setTimeState(boolean serial) {
		if (serial) {
			timestate = new TimeStateSerial();
		} else {
			timestate = new TimeStateParallel();
		}
	}

	private <T extends Entity> Map<String, T> setToMap(Set<T> set) {
		Map<String, T> map = new HashMap<>();
		set.forEach((T t) -> {
			;
			map.put((String) t.getId(), t);
		});
		return map;
	}

}