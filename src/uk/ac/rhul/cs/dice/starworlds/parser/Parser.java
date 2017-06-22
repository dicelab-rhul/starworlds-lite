package uk.ac.rhul.cs.dice.starworlds.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.rhul.cs.dice.starworlds.actions.Action;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment.AmbientRelation;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.NeighbourhoodNode;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.NeighbourhoodTree;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.Node;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

public class Parser {

	public static final String[] CLASSKEYS = new String[] { "@agentbodies",
			"@agentminds", "@agentactuators", "@agentsensors",
			"@possibleactions", "@environments", "@physics", "@states" };
	public static final String AGENTBODIES = CLASSKEYS[0],
			AGENTMINDS = CLASSKEYS[1], AGENTACTUATORS = CLASSKEYS[2],
			AGENTSENSORS = CLASSKEYS[3], POSSIBLEACTIONS = CLASSKEYS[4],
			ENVIRONMENTS = CLASSKEYS[5], PHYSICS = CLASSKEYS[6],
			STATES = CLASSKEYS[7];

	public static final String[] INSTANCEKEYS = new String[] { "~structure",
			"~agents", "~environments" };
	public static final String STRUCTURE = INSTANCEKEYS[0],
			AGENTINSTANCES = INSTANCEKEYS[1],
			ENVIRONMENTINSTANCES = INSTANCEKEYS[2];

	public static final String[] PROPERTYKEYS = new String[] { "@remote",
			"@connections", "@port", "@ctype", "@address" };

	public static final String REMOTE = PROPERTYKEYS[0],
			CONNECTIONS = PROPERTYKEYS[1], PORT = PROPERTYKEYS[2],
			CONNECTIONTYPE = PROPERTYKEYS[3], ADDRESS = PROPERTYKEYS[4];

	private JSONObject total;

	// TODO optimise the parser
	private Map<String, Class<?>> classmap = new HashMap<>();

	public Parser(String initstructure) throws IOException {
		File sf = new File(initstructure);
		if (sf.exists()) {
			total = getJson(sf);
		} else {
			throw new FileNotFoundException("Cannot find init file: " + sf);
		}
	}

	public Collection<Universe> parse() throws Exception {
		validate(total);
		JSONArray structure = total.getJSONArray(STRUCTURE);
		List<NeighbourhoodTree<AbstractEnvironment>> trees = new ArrayList<>();
		for (int i = 0; i < structure.length(); i++) {
			trees.add(new NeighbourhoodTree<AbstractEnvironment>(
					(NeighbourhoodNode<AbstractEnvironment>) recurseStructure(
							structure.getJSONObject(i), total)));
		}
		List<Universe> multiverse = new ArrayList<>();
		for (NeighbourhoodTree<AbstractEnvironment> t : trees) {
			System.out.println(t);
			t.accept(new InitialConnectVisitor());
			t.accept(new PostInitialisationVisitor());
			multiverse.add((Universe) t.getRoot().getValue());
		}
		return multiverse;
	}

	public void addToClassMap(JSONObject json, String superkey) {
		JSONObject superObject = json.getJSONObject(superkey);
		superObject.keySet().forEach(
				(key) -> this.classmap.put(key, ReflectiveMethodStore
						.getClassFromString(superObject.getString(key))));
	}

	public void validate(JSONObject json) {
		System.out.println("VALIDATING CONFIGURATION... ");
		for (String key : CLASSKEYS) {
			addToClassMap(json, key);
		}
		System.out.println("   FOUND CLASSES: ");
		classmap.forEach((s, c) -> System.out.println("       " + s + "->" + c));
		System.out.println("   VALIDATING REFLECTIVE METHODS...");
		validateActionMethods(json);
		System.out.println("   FOUND ALL ACTION METHODS");
		validatePerceivableMethods(json);
		System.out.println("   FOUND ALL PERCEIVABLE METHODS");
		System.out.println("DONE!");
	}

	private void validatePerceivableMethods(JSONObject json) {
		JSONArray struct = json.getJSONArray(STRUCTURE);
		Map<String, Set<String>> map = validateRecurseStruct(struct);
		map.forEach((physics, agents) -> {
			Set<Class<? extends Sensor>> usensors = new HashSet<>();
			agents.forEach((agent) -> {
				JSONArray sensors = json.getJSONObject(AGENTINSTANCES)
						.getJSONObject(agent).getJSONArray(AGENTSENSORS);
				for (int i = 0; i < sensors.length(); i++) {
					usensors.add(classmap.get(sensors.getString(i)).asSubclass(
							Sensor.class));
				}
			});
			ReflectiveMethodStore.validateReflectiveSensors(
					classmap.get(physics).asSubclass(Physics.class), usensors);
		});
	}

	private Map<String, Set<String>> validateRecurseStruct(JSONArray struct) {
		// map physics to agents
		Map<String, Set<String>> pamap = new HashMap<>();
		for (int i = 0; i < struct.length(); i++) {
			JSONObject structobj = struct.getJSONObject(i);
			if (structobj.has(STRUCTURE)) {
				Map<String, Set<String>> sub = validateRecurseStruct(structobj
						.getJSONArray(STRUCTURE));
				sub.forEach((physics, agents) -> {
					Set<String> lagents = pamap.putIfAbsent(physics, agents);
					if (lagents != null) {
						lagents.addAll(agents);
					}
				});
			}
			String physics = total.getJSONObject(ENVIRONMENTINSTANCES)
					.getJSONObject(structobj.getString(ENVIRONMENTINSTANCES))
					.getString(PHYSICS);
			pamap.putIfAbsent(physics, new HashSet<>());
			JSONArray agents = structobj.getJSONArray(AGENTINSTANCES);
			for (int j = 0; j < agents.length(); j++) {
				pamap.get(physics).add(agents.getString(j));
			}
		}
		return pamap;
	}

	private void validateActionMethods(JSONObject json) {
		JSONObject environments = json.getJSONObject(ENVIRONMENTINSTANCES);
		environments
				.keySet()
				.forEach(
						(key) -> {
							JSONObject instance = environments
									.getJSONObject(key);
							Class<? extends Physics> physics = classmap.get(
									instance.getString(PHYSICS)).asSubclass(
									Physics.class);
							JSONArray possibleactions = instance
									.getJSONArray(POSSIBLEACTIONS);
							Collection<Class<? extends Action>> toValidate = new HashSet<>();
							for (int i = 0; i < possibleactions.length(); i++) {
								Class<?> a = classmap.get(possibleactions
										.getString(i));
								if (PhysicalAction.class.isAssignableFrom(a)) {
									toValidate.add(a.asSubclass(Action.class));
								}
							}
							ReflectiveMethodStore.validateReflectiveActions(
									physics, toValidate);
						});
	}

	private Node<AbstractEnvironment> recurseStructure(JSONObject structure,
			JSONObject total) throws Exception {
		NeighbourhoodNode<AbstractEnvironment> current = new NeighbourhoodNode<>();
		String envkey = structure.getString(ENVIRONMENTINSTANCES);
		final List<Node<AbstractEnvironment>> subenvs = new ArrayList<>();
		if (structure.has(STRUCTURE)) {
			JSONArray substructarray = structure.getJSONArray(STRUCTURE);
			for (int i = 0; i < substructarray.length(); i++) {
				try {
					subenvs.add(recurseStructure(
							substructarray.getJSONObject(i), total));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			current.setChildren(subenvs);
		}
		// create agents
		JSONArray agentkeys = structure.getJSONArray(AGENTINSTANCES);
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < agentkeys.length(); i++) {
			String ak = agentkeys.getString(i);
			agents.add(constructAgent(ak, total.getJSONObject(AGENTINSTANCES)
					.getJSONObject(ak), total));
		}
		JSONObject environmentjson = total.getJSONObject(ENVIRONMENTINSTANCES)
				.getJSONObject(envkey);

		// create physics
		// TODO add active bodies and passive bodies
		AbstractPhysics physics = null;
		Class<?> physicsclass = getClassFromJson(PHYSICS,
				environmentjson.getString(PHYSICS), total);
		physics = (AbstractPhysics) physicsclass.newInstance();
		// create state
		AbstractState state = null;
		try {
			state = (AbstractState) getClassFromJson(STATES,
					environmentjson.getString(STATES), total).getConstructor(
					Set.class, Set.class, Set.class).newInstance(agents, null,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// possible actions
		Collection<Class<?>> possibleactions = new ArrayList<>();
		JSONArray pajson = environmentjson.getJSONArray(POSSIBLEACTIONS);
		for (int i = 0; i < pajson.length(); i++) {
			possibleactions.add(getClassFromJson(POSSIBLEACTIONS,
					pajson.getString(i), total));
		}
		// TODO create appearance via parsing
		// TODO mixed constructor
		// create environment
		Class<?> environmentclass = getClassFromJson(ENVIRONMENTS,
				environmentjson.getString(ENVIRONMENTS), total);
		if (structure.keySet().contains(REMOTE)) {
			// we should use the remote constructor
			JSONObject remote = structure.getJSONObject(REMOTE);
			Integer port = remote.getInt(PORT);
			AbstractConnectedEnvironment remoteEnvironment = (AbstractConnectedEnvironment) environmentclass
					.getConstructor(Integer.class, AbstractState.class,
							AbstractConnectedPhysics.class, Collection.class)
					.newInstance(port, state, physics, possibleactions);
			// check for any initial connections and add them
			if (remote.keySet().contains(CONNECTIONS)) {
				JSONArray connections = remote.getJSONArray(CONNECTIONS);
				for (int i = 0; i < connections.length(); i++) {
					addInitialConnection(connections.getJSONObject(i),
							remoteEnvironment);
				}
			}
			current.setValue(remoteEnvironment);
			return current;
		} else {
			// use the local constructor.
			try {
				if (AbstractConnectedEnvironment.class
						.isAssignableFrom(environmentclass)) {
					ArrayList<AbstractEnvironment> subs = new ArrayList<>();
					subenvs.forEach((n) -> subs.add(n.getValue()));
					current.setValue((AbstractEnvironment) environmentclass
							.getConstructor(Collection.class,
									AbstractState.class,
									AbstractConnectedPhysics.class,
									Collection.class).newInstance(subs, state,
									physics, possibleactions));
				} else if (AbstractEnvironment.class
						.isAssignableFrom(environmentclass)) {
					if (subenvs.isEmpty()) {
						current.setValue((AbstractEnvironment) environmentclass
								.getConstructor(AbstractState.class,
										AbstractPhysics.class, Collection.class)
								.newInstance(state, physics, possibleactions));
					} else {
						throw new ReflectiveMethodStore.IllegalConfigurationException(
								"Error: Provided an atomic ambient with sub ambients.");
					}
				}
				return current;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void addInitialConnection(JSONObject connection,
			AbstractConnectedEnvironment env) {

		AmbientRelation relation = getConnectionRelation(connection
				.getString(CONNECTIONTYPE));
		String[] ap = connection.getString(ADDRESS).split(":");
		env.addInitialConnection(ap[0], Integer.valueOf(ap[1]), relation);
	}

	private AmbientRelation getConnectionRelation(String ctype) {
		try {
			return AmbientRelation.valueOf(AmbientRelation.class, ctype);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ctype + " is not a valid "
					+ CONNECTIONTYPE + System.lineSeparator() + "Valid: "
					+ CONNECTIONTYPE + "s are: "
					+ Arrays.toString(AmbientRelation.values()));
		}
	}

	private AbstractAgent constructAgent(String key, JSONObject agentjson,
			JSONObject total) {
		checkProperties(key, agentjson, AGENTBODIES, AGENTMINDS, AGENTSENSORS,
				AGENTACTUATORS);
		JSONArray sensorjson = agentjson.getJSONArray(AGENTSENSORS);
		List<Sensor> sensors = new ArrayList<>();
		for (int i = 0; i < sensorjson.length(); i++) {
			sensors.add((Sensor) constructInstanceNoConstructor(getClassFromJson(
					AGENTSENSORS, sensorjson.getString(i), total)));
		}
		JSONArray actuatorjson = agentjson.getJSONArray(AGENTACTUATORS);
		List<Actuator> actuators = new ArrayList<>();
		for (int i = 0; i < actuatorjson.length(); i++) {
			actuators
					.add((Actuator) constructInstanceNoConstructor(getClassFromJson(
							AGENTACTUATORS, actuatorjson.getString(i), total)));
		}
		String mindjson = agentjson.getString(AGENTMINDS);
		AbstractAgentMind mind = (AbstractAgentMind) constructInstanceNoConstructor(getClassFromJson(
				AGENTMINDS, mindjson, total));
		String bodyjson = agentjson.getString(AGENTBODIES);
		try {
			return (AbstractAgent) getClassFromJson(AGENTBODIES, bodyjson,
					total).getConstructor(List.class, List.class,
					AbstractAgentMind.class).newInstance(sensors, actuators,
					mind);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Class<?> getClassFromJson(String key, String value, JSONObject total) {
		try {
			return Class.forName(total.getJSONObject(key).getString(value));
		} catch (ClassNotFoundException | JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	private <T> T constructInstanceNoConstructor(Class<T> c) {
		try {
			return c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void checkProperties(String key, JSONObject json,
			String... properities) {
		Set<String> keyset = json.keySet();
		for (String s : properities) {
			if (!keyset.contains(s)) {
				throw new IllegalParseExeception(key, s);
			}
		}
	}

	public JSONObject getJson(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		reader.close();
		return new JSONObject(builder.toString());
	}

	// visits each environment and connects any networked environments
	private static class InitialConnectVisitor implements
			Visitor<AbstractEnvironment> {
		@Override
		public void visit(Acceptor<AbstractEnvironment> acceptor) {
			if (AbstractConnectedEnvironment.class
					.isAssignableFrom(((Node<AbstractEnvironment>) acceptor)
							.getValue().getClass())) {
				((AbstractConnectedEnvironment) ((Node<AbstractEnvironment>) acceptor)
						.getValue()).initialConnect();
			}
		}
	}

	// visits each environment and initialises it
	private static class PostInitialisationVisitor implements
			Visitor<AbstractEnvironment> {
		@Override
		public void visit(Acceptor<AbstractEnvironment> acceptor) {
			((Node<AbstractEnvironment>) acceptor).getValue()
					.postInitialisation();
		}
	}

	// visits each environment and prints information about it. e.g. all its
	// connections, appearance etc.
	@SuppressWarnings("unused")
	private static class InfoPrintVisitor implements
			Visitor<AbstractEnvironment> {
		@Override
		public void visit(Acceptor<AbstractEnvironment> acceptor) {
			System.out
					.println(((AbstractConnectedEnvironment) ((Node<AbstractEnvironment>) acceptor)
							.getValue()).getConnectedEnvironmentManager());
		}
	}
}
