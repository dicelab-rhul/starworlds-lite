package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.ActiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Agent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.PassiveBody;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.DefaultPerception;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;
import uk.ac.rhul.cs.dice.gawl.interfaces.utils.Pair;

public abstract class AbstractState implements State {

	private final static String AGENTSKEY = "AGENTS",
			ACTIVEBODIESKEY = "ACTIVEBODIES",
			PASSIVEBODIESKEY = "PASSIVEBODIES";

	private HashMap<String, Object> environmentVariables;

	private List<SensingAction> sensingActions;
	private List<PhysicalAction> physicalActions;
	private List<CommunicationAction<?>> communicationActions;

	public AbstractState(AbstractPhysics physics) {
		this.sensingActions = new ArrayList<>();
		this.physicalActions = new ArrayList<>();
		this.communicationActions = new ArrayList<>();
		this.environmentVariables = new HashMap<>();
		initialiseEnvironmentVariables(physics);
	}

	/**
	 * Initialises the default environment variables, namely, {@link Agent}s,
	 * {@link ActiveBody}s, {@link PassiveBody}s. This method should be
	 * Overridden if a user wishes to add more environment variables. When
	 * adding more use the {@link State#addEnvironmentVariable(String, Object)}
	 * method.
	 */
	protected void initialiseEnvironmentVariables(AbstractPhysics physics) {
		environmentVariables.put(AGENTSKEY, physics.getAgents());
		environmentVariables.put(ACTIVEBODIESKEY, physics.getActiveBodies());
		environmentVariables.put(PASSIVEBODIESKEY, physics.getPassiveBodies());
	}

	@Override
	public void addSenseAction(SensingAction action) {
		this.sensingActions.add(action);
	}

	@Override
	public void addPhysicalAction(PhysicalAction action) {
		this.physicalActions.add(action);
	}

	@Override
	public void addCommunicationAction(CommunicationAction<?> action) {
		this.communicationActions.add(action);
	}

	@Override
	public List<SensingAction> getSensingActions() {
		return sensingActions;
	}

	@Override
	public List<PhysicalAction> getPhysicalActions() {
		return physicalActions;
	}

	@Override
	public List<CommunicationAction<?>> getCommunicationActions() {
		return communicationActions;
	}

	/**
	 * Getter for the environment variable key chain as a single {@link String}.
	 * 
	 * @return the key chain as a {@link String}
	 */
	@Override
	public String getEnvironmentVariableKeysAsString() {
		return Arrays.toString(environmentVariables.keySet().toArray());
	}

	/**
	 * Getter for the environment variable key chain.
	 * 
	 * @return all keys for environment variables.
	 */
	@Override
	public Set<String> getEnvironmentVariableKeys() {
		return Collections.unmodifiableSet(environmentVariables.keySet());
	}

	/**
	 * Adds a new environment variable with the given key. Adding a new variable
	 * with an existing key will have no effect on the key chain - the new value
	 * will not replace the old.
	 * 
	 * @return true if the variable is added successfully, false otherwise
	 */
	@Override
	public boolean addEnvironmentVariable(String key, Object variable) {
		return this.environmentVariables.putIfAbsent(key, variable) == null;
	}

	@Override
	public Set<Pair<String, Object>> filterActivePerception(String[] keys) {
		// TODO optimise the recursive environment variables e.g. agents that
		// are in some space, or sub environment
		Set<Pair<String, Object>> perceptions = new HashSet<>();

		for (String key : keys) {
			if (key == null)
				continue;
			perceptions.add(new Pair<String, Object>(key, environmentVariables
					.get(key)));
		}
		return perceptions;
	}

	/**
	 * Filters the actions provided by an {@link Actuator} or {@link Sensor}
	 * into groups depending on their type. Actions will be accessed later,
	 * during the executing of the actions by the physics. This method will
	 * insert actions in order based on their priority.
	 */
	@Override
	public synchronized void filterAction(AbstractEnvironmentalAction action) {
		// TODO filter based on priority
		if (SensingAction.class.isAssignableFrom(action.getClass())) {
			this.addSenseAction((SensingAction) action);
		} else if (CommunicationAction.class
				.isAssignableFrom(action.getClass())) {
			this.addCommunicationAction((CommunicationAction<?>) action);
		} else if (PhysicalAction.class.isAssignableFrom(action.getClass())) {
			this.addPhysicalAction((PhysicalAction) action);
		} else {
			// TODO provide more types of actions!
			System.out.println("ERROR HANDLING ACTION TYPE "
					+ action.getClass());
			Thread.dumpStack();
		}
	}
}
