package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.ActiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Entity;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.PassiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Environment;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.State;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.CommunicationPerception;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;
import uk.ac.rhul.cs.dice.gawl.interfaces.utils.Pair;

/**
 * The most general class representing physics. It extends
 * {@link CustomObservable} and it implements {@link Physics} and
 * {@link CustomObserver}.<br/>
 * <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractPhysics implements Physics {

	private Environment environment;
	private Map<String, AbstractAgent> agents;
	private Map<String, ActiveBody> activeBodies;
	private Map<String, PassiveBody> passiveBodies;
	private boolean active = false;
	private long framegap = 1000;

	public AbstractPhysics(Set<AbstractAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies) {
		this.agents = (agents != null) ? setToMap(agents) : new HashMap<>();
		this.activeBodies = (activeBodies != null) ? setToMap(activeBodies)
				: new HashMap<>();
		this.passiveBodies = (passiveBodies != null) ? setToMap(passiveBodies)
				: new HashMap<>();
	}

	private <T extends Entity> Map<String, T> setToMap(Set<T> set) {
		Map<String, T> map = new HashMap<>();
		set.forEach((T t) -> {
			;
			map.put((String) t.getId(), t);
		});
		return map;
	}

	public void start() {
		try {
			active = true;
			while (active) {
				agentsPerceive();
				agentsDecide();
				agentsExecute();
				executeActions();
				Thread.sleep(framegap);
				System.out.println("---------------------------");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void executeActions() {
		environment
				.getState()
				.getCommunicationActions()
				.forEach(
						(CommunicationAction<?> c) -> this.perform(c,
								environment.getState()));
		environment.getState().getCommunicationActions().clear();
		environment
				.getState()
				.getSensingActions()
				.forEach(
						(SensingAction s) -> this.execute(s,
								environment.getState()));
		environment.getState().getSensingActions().clear();
		// TODO physical actions
	}

	@Override
	public void execute(SensingAction action, State context) {
		Set<Pair<String, Object>> perceptions = null;
		if (isPossible(action, context)) {
			perceptions = perform(action, context);
			verify(action, context); // TODO what to verify?
		}
		notify(action.getSensor(), (ActiveBody) action.getActor(),
				new DefaultPerception<Set<Pair<String, Object>>>(perceptions));
	}

	@Override
	public Set<Pair<String, Object>> perform(SensingAction action, State context) {
		System.out.println(Arrays.toString(action.getKeys()));
		return context.filterActivePerception(action.getKeys());
	}

	@Override
	public boolean isPossible(SensingAction action, State context) {
		Set<String> vars = context.getEnvironmentVariableKeys();
		String[] keys = action.getKeys();
		int count = 0;
		for (int i = 0; i < keys.length; i++) {
			// TODO check agent permissions
			if (!vars.contains(keys[i])) {
				action.getKeys()[i] = null;
				count++;
			}
		}
		System.out.println(count);
		return count < keys.length;
	}

	@Override
	public boolean verify(SensingAction action, State context) {
		return true;
	}

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
		action.getRecipientsIds().forEach(
				(String s) -> {
					AbstractAgent agent = agents.get(s);
					if (agent != null) {
						notify(action.getSensor(),
								agent,
								new CommunicationPerception<>(action
										.getPayload()));
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
	public void notify(Class<?> sensorclass, ActiveBody body,
			AbstractPerception<?> perception) {
		if (sensorclass != null) {
			Sensor sensor = body
					.getSensors()
					.stream()
					.filter((Sensor s) -> sensorclass.isAssignableFrom(s
							.getClass())).findFirst().get();
			if (sensor != null) {
				sensor.notify(perception);
				return;
			}
		}
		if (body.getDefaultListeningSensor() != null) {
			body.getDefaultListeningSensor().notify(perception);
		}
	}

	public void agentsPerceive() {
		agents.values().forEach((AbstractAgent a) -> {
			a.getBrain().perceive();
		});
	}

	public void agentsDecide() {
		agents.values().forEach((AbstractAgent a) -> {
			a.getMind().decide();
		});
	}

	public void agentsExecute() {
		agents.values().forEach((AbstractAgent a) -> {
			a.getBrain().execute();
		});
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
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}