package uk.ac.rhul.cs.dice.starworlds.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
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
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.PhysicalBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment.AmbientRelation;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;
import uk.ac.rhul.cs.dice.starworlds.initialisation.ReflectiveMethodStore;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.GraphTreeNode;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.GraphTree;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.Node;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

public class Parser {

	public static final String[] CLASSKEYS = new String[] { "@agents",
			"@agentminds", "@agentactuators", "@agentsensors",
			"@possibleactions", "@environments", "@physics", "@states",
			"@activebodies", "@passivebodies", "@appearances" };
	public static final String AGENTS = CLASSKEYS[0],
			AGENTMINDS = CLASSKEYS[1], AGENTACTUATORS = CLASSKEYS[2],
			AGENTSENSORS = CLASSKEYS[3], POSSIBLEACTIONS = CLASSKEYS[4],
			ENVIRONMENTS = CLASSKEYS[5], PHYSICS = CLASSKEYS[6],
			STATES = CLASSKEYS[7], ACTIVEBODIES = CLASSKEYS[8],
			PASSIVEBODIES = CLASSKEYS[9], APPEARANCES = CLASSKEYS[10];

	public static final String[] INSTANCEKEYS = new String[] { "~agents",
			"~activebodies", "~passivebodies", "~physics", "~states",
			"~appearances" };
	public static final String AGENTINSTANCES = INSTANCEKEYS[0],
			ACTIVEINSTANCE = INSTANCEKEYS[1],
			PASSIVEINSTANCE = INSTANCEKEYS[2],
			PHYSICSINSTANCE = INSTANCEKEYS[3], STATEINSTANCE = INSTANCEKEYS[4],
			APPEARANCEINSTANCE = INSTANCEKEYS[5];

	public static final String STRUCTURE = "~structure",
			ENVIRONMENTINSTANCES = "~environments";

	public static final String[] PROPERTYKEYS = new String[] { "@remote",
			"@connections", "@port", "@ctype", "@address", "@construct", };

	public static final String REMOTE = PROPERTYKEYS[0],
			CONNECTIONS = PROPERTYKEYS[1], PORT = PROPERTYKEYS[2],
			CONNECTIONTYPE = PROPERTYKEYS[3], ADDRESS = PROPERTYKEYS[4],
			CONSTRUCT = PROPERTYKEYS[5];

	private final static DefaultConstructorStore CONSTRUCTORSTORE = DefaultConstructorStore
			.getInstance();

	private JSONObject total;

	// TODO optimise the parser
	private Map<String, Class<?>> classmap = new HashMap<>();
	private Map<String, JSONObject> instancemap = new HashMap<>();
	private Map<String, Class<?>> instanceclassmap = new HashMap<>();
	private Map<String, Constructor<?>> instanceconstructmap = new HashMap<>();
	private Map<String, String> instanceOfMap = new HashMap<>();

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
		List<GraphTree<AbstractEnvironment>> trees = new ArrayList<>();
		for (int i = 0; i < structure.length(); i++) {
			trees.add(new GraphTree<AbstractEnvironment>(
					(GraphTreeNode<AbstractEnvironment>) recurseStructure(
							structure.getJSONObject(i), total)));
		}
		List<Universe> multiverse = new ArrayList<>();
		for (GraphTree<AbstractEnvironment> t : trees) {
			System.out.println(t);
			t.accept(new InitialConnectVisitor());
			t.accept(new PostInitialisationVisitor());
			multiverse.add((Universe) t.getRoot().getValue());
		}
		return multiverse;
	}

	public void validate(JSONObject json) {
		System.out.println("VALIDATING CONFIGURATION... ");
		for (String key : CLASSKEYS) {
			addToClassMap(json, key);
		}
		System.out.println("   FOUND CLASSES: ");
		classmap.forEach((s, c) -> System.out.println("       " + s + "->" + c));

		for (String key : INSTANCEKEYS) {
			if (json.has(key)) {
				if (!key.equals(ENVIRONMENTS))
					addToInstanceMap(json, key);
			}
		}
		System.out.println("   FOUND INSTANCES: ");
		instancemap.forEach((s, c) -> System.out.println("       " + s + "->"
				+ c));
		this.instancemap.forEach((key, obj) -> {
			System.out.println(key);
			this.instanceconstructmap.put(key, this.getConstructor(key,
					this.instanceclassmap.get(key), obj));
		});

		System.out.println("   FOUND INSTANCE CLASSES: ");
		instanceclassmap.forEach((s, c) -> System.out.println("       " + s
				+ "->" + c));
		System.out.println("   FOUND INSTANCE CONSTRUCTORS: ");
		instanceconstructmap.forEach((s, c) -> System.out.println("       " + s
				+ "->" + c));
		System.out.println("   VALIDATING REFLECTIVE METHODS...");
		validateActionMethods(json);
		System.out.println("   FOUND ALL ACTION METHODS");
		validatePerceivableMethods(json);
		System.out.println("   FOUND ALL PERCEIVABLE METHODS");
		System.out.println("DONE!");
	}

	private void addToClassMap(JSONObject json, String superkey) {
		JSONObject superObject = json.getJSONObject(superkey);
		superObject.keySet().forEach(
				(key) -> this.classmap.put(key, ReflectiveMethodStore
						.getClassFromString(superObject.getString(key))));
	}

	private void addToInstanceMap(JSONObject json, String superkey) {
		JSONObject superObject = json.getJSONObject(superkey);
		superObject.keySet().forEach((key) -> {
			JSONObject obj = superObject.getJSONObject(key);
			this.instancemap.put(key, superObject.getJSONObject(key));
			String classkey = getClassKey(superkey);
			Class<?> c = getInstanceClassProperty(classkey, key);
			this.instanceclassmap.put(key, c);
			this.instanceOfMap.put(key, classkey);
		});
	}

	private String getClassKey(String superkey) {
		return superkey.replaceFirst("~", "@");
	}

	private Class<?> getInstanceClassProperty(String classkey,
			String instancekey) {
		JSONObject instance = instancemap.get(instancekey);
		if (instance.has(classkey)) {
			Class<?> c = classmap.get(instance.getString(classkey));
			if (c != null) {
				return c;
			} else {
				throw new IllegalParseExeception("Instance: " + instancekey
						+ classkey + "class property doesnt exist: "
						+ instance.getString(classkey));
			}
		} else {
			throw new IllegalParseExeception(instancekey, classkey);
		}
	}

	private <T> Constructor<T> getConstructor(String instance,
			Class<T> instanceclass, JSONObject toConstruct) {
		Class<?>[] types;
		if (toConstruct.has(CONSTRUCT)) {
			JSONArray args = toConstruct.getJSONArray(CONSTRUCT);
			types = new Class<?>[args.length()];
			for (int i = 0; i < types.length; i++) {
				types[i] = resolveArgClass(args.get(i));
			}
		} else {
			types = DefaultConstructorStore.getConstructor(
					instanceOfMap.get(instance)).getParameterTypes();
		}
		try {
			return instanceclass.getConstructor(types);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalParseExeception("No "
					+ CONSTRUCT
					+ " property was provided for instance: "
					+ instance
					+ " and the default constructor:"
					+ ReflectiveMethodStore.methodToString(instanceclass, "",
							types) + " does not exist.");
		}
	}

	private Class<?> resolveArgClass(Object key) {
		if (key.getClass().isPrimitive()) {
			return key.getClass();
		} else if (this.classmap.containsKey(key)) {
			return classmap.get(key).getClass();
		} else if (classmap.containsKey(key)) {
			return classmap.get(key);
		} else if (key instanceof String) {
			return String.class;
		} else {
			// fix the string check, the arg will always be a string!
			throw new IllegalParseExeception("Could not find type for: " + key
					+ " " + CONSTRUCT + " parameter");
		}
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
		GraphTreeNode<AbstractEnvironment> current = new GraphTreeNode<>();
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

		// create active bodies //TODO
		// create passive bodies
		Set<PassiveBody> passivebodies = new HashSet<>();
		JSONArray passivebodiesjson = structure.getJSONArray(PASSIVEINSTANCE);
		for (int i = 0; i < passivebodiesjson.length(); i++) {
			// TODO add additional arguments for constructors
			constructPassiveBody(passivebodiesjson.getString(i));
		}

		JSONObject environmentjson = total.getJSONObject(ENVIRONMENTINSTANCES)
				.getJSONObject(envkey);
		// create physics
		AbstractPhysics physics = null;
		Class<?> physicsclass = getClassFromJson(PHYSICS,
				environmentjson.getString(PHYSICS), total);
		physics = (AbstractPhysics) physicsclass.newInstance();
		// create state
		AbstractAmbient state = null;
		try {
			state = (AbstractAmbient) getClassFromJson(STATES,
					environmentjson.getString(STATES), total).getConstructor(
					Set.class, Set.class, Set.class).newInstance(agents, null,
					passivebodies);
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
					.getConstructor(Integer.class, AbstractAmbient.class,
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
									AbstractAmbient.class,
									AbstractConnectedPhysics.class,
									Collection.class).newInstance(subs, state,
									physics, possibleactions));
				} else if (AbstractEnvironment.class
						.isAssignableFrom(environmentclass)) {
					if (subenvs.isEmpty()) {
						current.setValue((AbstractEnvironment) environmentclass
								.getConstructor(AbstractAmbient.class,
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

	private PassiveBody constructPassiveBody(String key) {
		System.out.println("Construct Passive Body: " + key);
		JSONObject passivebodyjson = total.getJSONObject(PASSIVEINSTANCE)
				.getJSONObject(key);
		Class<?> passivebodyclass = classmap.get(passivebodyjson
				.getString(PASSIVEBODIES));
		// check construct or force construct
		if (shouldConstruct(passivebodyjson)) {
			construct(passivebodyclass, key);
		}
		PhysicalBodyAppearance appearance = (PhysicalBodyAppearance) getAppearance(
				passivebodyjson.getString(APPEARANCES),
				new Class<?>[] { Class.class },
				new Object[] { passivebodyclass });
		System.out.println(appearance);
		// add to param types
		try {
			// return (PassiveBody) passivebodyclass.getConstructor(
			// concatArrays(
			// new Class<?>[] { PhysicalBodyAppearance.class },
			// paramtypes)).newInstance(
			// concatArrays(new Object[] { appearance }, params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private <T> T construct(Class<T> instanceclass, String key) {
		System.out.println(key);
		
		return null;
	}

	private Object[] parseArgsFromConstruct(String key) {
		JSONArray con = instancemap.get(key).getJSONArray(CONSTRUCT);
		Object[] obj = new Object[con.length()];
		for (int i = 0; i < con.length(); i++) {
			Object o = con.get(i);
			if(o.getClass().isPrimitive()) {
				obj[i] = o;
			} else if(classmap.containsKey(o)) {
				obj[i] = classmap.get(o);
			} else if(instancemap.containsKey(o)) {
				//obj[i] = construct(instanceclassmap.get(o));
			}
		}
	}

	private boolean shouldConstruct(JSONObject toConstruct) {
		return toConstruct.has(CONSTRUCT);
	}

	private Appearance getAppearance(String key, Class<?>[] paramtypes,
			Object[] params) {
		// is the key a class key
		Class<?> c = classmap.get(key);
		if (c != null) {
			// create the appearance as default
			Class<? extends Appearance> ac = c.asSubclass(Appearance.class);
			return constructInstance(ac, paramtypes, params);
		} else {

			Class<? extends Appearance> appearanceclass = getInstanceClassProperty(
					instanceOfMap.get(key), key).asSubclass(Appearance.class);
			JSONObject instance = total.getJSONObject(APPEARANCEINSTANCE)
					.getJSONObject(key);
			if (shouldConstruct(instance)) {
				return construct(appearanceclass, key);
			} else {
				return getAppearance(instance.getString(APPEARANCES),
						paramtypes, params);
			}
		}
	}

	private <T> T constructInstance(Class<T> c, Class<?>[] paramtypes,
			Object[] params) {
		try {
			return (T) c.getConstructor(paramtypes).newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
		checkProperties(key, agentjson, AGENTS, AGENTMINDS, AGENTSENSORS,
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
		String bodyjson = agentjson.getString(AGENTS);
		try {
			return (AbstractAgent) getClassFromJson(AGENTS, bodyjson, total)
					.getConstructor(List.class, List.class,
							AbstractAgentMind.class).newInstance(sensors,
							actuators, mind);
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
}
