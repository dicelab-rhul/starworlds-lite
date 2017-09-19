package uk.ac.rhul.cs.dice.starworlds.environment.ambient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.Agent;
import uk.ac.rhul.cs.dice.starworlds.entities.Entity;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.query.AppearanceQuery;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.query.Query;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.query.RandomQuery;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.query.SelfQuery;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

public abstract class AbstractAmbient implements Ambient {

	private final static String AGENTSKEY = "AGENTS",
			ACTIVEBODIESKEY = "ACTIVEBODIES",
			PASSIVEBODIESKEY = "PASSIVEBODIES", RANDOM = "RANDOM",
			SELF = "SELF", APPEARANCE = "APPEARANCE", NULL = "NULL";

	private HashMap<String, Object> ambientAttributes;
	private HashMap<String, Query> queries; // TODO name change

	private List<SensingAction> sensingActions;
	private List<PhysicalAction> physicalActions;
	private List<CommunicationAction<?>> communicationActions;

	// TODO Portals
	// TODO close/open

	protected Map<String, Process> processes; // TODO
	protected Map<String, AbstractAvatarAgent<?>> avatars;
	protected Map<String, AbstractAutonomousAgent> agents;
	protected Map<String, ActiveBody> activeBodies;
	protected Map<String, PassiveBody> passiveBodies;

	public AbstractAmbient(Set<AbstractAutonomousAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies,
			Set<AbstractAvatarAgent<?>> avatars) {
		this.avatars = (avatars != null) ? setToMap(avatars) : new HashMap<>();
		this.agents = (agents != null) ? setToMap(agents) : new HashMap<>();
		this.activeBodies = (activeBodies != null) ? setToMap(activeBodies)
				: new HashMap<>();
		this.passiveBodies = (passiveBodies != null) ? setToMap(passiveBodies)
				: new HashMap<>();
		this.sensingActions = new ArrayList<>();
		this.physicalActions = new ArrayList<>();
		this.communicationActions = new ArrayList<>();
		this.ambientAttributes = new HashMap<>();
		this.queries = new HashMap<>();
		initialiseAmbientAttributes(agents, activeBodies, passiveBodies);
	}

	/**
	 * Initialises the default environment variables, namely, {@link Agent}s,
	 * {@link ActiveBody}s, {@link PassiveBody}s. This method should be
	 * Overridden if a user wishes to add more environment variables. When
	 * adding more use the {@link Ambient#addAmbientAttribute(String, Object)}
	 * method.
	 */
	protected void initialiseAmbientAttributes(
			Set<AbstractAutonomousAgent> agents, Set<ActiveBody> activeBodies,
			Set<PassiveBody> passiveBodies) {
		ambientAttributes.put(AGENTSKEY, agents);
		ambientAttributes.put(ACTIVEBODIESKEY, activeBodies);
		ambientAttributes.put(PASSIVEBODIESKEY, passiveBodies);
		ambientAttributes.put(NULL, null);
		queries.put(RANDOM, new RandomQuery());
		queries.put(SELF, new SelfQuery());
		queries.put(APPEARANCE, new AppearanceQuery());
	}

	public AbstractAutonomousAgent getAgent(String id) {
		return this.agents.get(id);
	}

	public ActiveBody getActiveBody(String id) {
		return this.activeBodies.get(id);
	}

	public PassiveBody getPassiveBody(String id) {
		return this.passiveBodies.get(id);
	}

	@Override
	public void addAvatar(AbstractAvatarAgent<?> avatar) {
		this.avatars.put(avatar.getId(), avatar);
	}

	@Override
	public void addAgent(AbstractAutonomousAgent agent) {
		this.agents.put(agent.getId(), agent);
	}

	@Override
	public void addActiveBody(ActiveBody body) {
		this.activeBodies.put(body.getId(), body);
	}

	@Override
	public void addPassiveBody(PassiveBody body) {
		this.passiveBodies.put(body.getId(), body);
	}

	@Override
	public Collection<AbstractAvatarAgent<?>> getAvatars() {
		return this.avatars.values();
	}

	@Override
	public Collection<AbstractAutonomousAgent> getAgents() {
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
	public Collection<CommunicationAction<?>> flushCommunicationActions() {
		List<CommunicationAction<?>> actions = new ArrayList<>();
		actions.addAll(this.communicationActions);
		this.communicationActions.clear();
		return actions;
	}

	@Override
	public Collection<PhysicalAction> flushPhysicalActions() {
		List<PhysicalAction> actions = new ArrayList<>();
		actions.addAll(this.physicalActions);
		this.physicalActions.clear();
		return actions;
	}

	@Override
	public Collection<SensingAction> flushSensingActions() {
		List<SensingAction> actions = new ArrayList<>();
		actions.addAll(this.sensingActions);
		this.sensingActions.clear();
		return actions;
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
	 * Getter for the {@link AmbientAttribute} Key chain as a single
	 * {@link String}.
	 * 
	 * @return the key chain as a {@link String}
	 */
	@Override
	public String getAmbientAttributeKeysAsString() {
		return Arrays.toString(ambientAttributes.keySet().toArray());
	}

	/**
	 * Getter for the {@link AmbientAttribute} key chain.
	 * 
	 * @return all keys for {@link AmbientAttribute}s.
	 */
	@Override
	public Set<String> getAmbientAttributeKeys() {
		return Collections.unmodifiableSet(ambientAttributes.keySet());
	}

	/**
	 * Adds a new {@link AmbientAttribute} with the given key. Adding a new
	 * {@link AmbientAttribute} with an existing key will have no effect on the
	 * key chain - the new value will not replace the old.
	 * 
	 * @return true if the variable is added successfully, false otherwise
	 */
	@Override
	public boolean addAmbientAttribute(String key, Object variable) {
		return this.ambientAttributes.putIfAbsent(key, variable) == null;
	}

	/**
	 * Removes an existing {@link AmbientAttribute} given its key. This will
	 * have no effect if the key does not exist.
	 * 
	 * @param key
	 *            : of the {@link AmbientAttribute} to remove
	 * @return true if the key was removed, false otherwise
	 */
	public boolean removeAmbientAttribute(String key) {
		return this.ambientAttributes.remove(key) != null;
	}
	
	@Override
	public boolean environmentAmbientAttribute(String key) {
		return ambientAttributes.containsKey(key);
	}

	@Override
	public Set<String> getQueryKeys() {
		return Collections.unmodifiableSet(queries.keySet());
	}

	@Override
	public String getQueryKeysAsString() {
		return Arrays.toString(ambientAttributes.keySet().toArray());
	}

	@Override
	public boolean addQuery(String key, Query query) {
		return this.queries.putIfAbsent(key, query) == null;
	}

	public boolean removeQuery(String key) {
		return this.queries.remove(key) != null;
	}

	@Override
	public boolean queryExists(String key) {
		return queries.containsKey(key);
	}

	@Override
	public Map<String, Object> queryActivePerception(String[] keys,
			AbstractEnvironmentalAction action) {

		Map<String, Object> perceptions = new HashMap<>();
		for (String key : keys) {
			if (key == null)
				continue;
			/*
			 * TODO optimise, we don't want to do this twice (also done in
			 * physics#ispossible)
			 */
			String[] subkeys = key.split("\\.");
			// TODO handle integer parameters
			Object result = ambientAttributes.get(subkeys[0]);
			for (int i = 1; i < subkeys.length; i++) {
				result = queries.get(subkeys[i]).get(action, result);
			}
			perceptions.put(key, result);
		}
		return perceptions;
	}

	/**
	 * Filters the actions provided by an {@link Actuator} or {@link Sensor}
	 * into groups depending on their type. Actions will be accessed later,
	 * during the executing of the actions by the {@link Physics}. This method
	 * will insert actions in order based on their priority. //TODO
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
			System.err.println("ERROR HANDLING ACTION TYPE "
					+ action.getClass());
			Thread.dumpStack();
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
