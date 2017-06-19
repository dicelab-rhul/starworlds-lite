package uk.ac.rhul.cs.dice.starworlds.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.NeighbourhoodNode;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.NeighbourhoodTree;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.tree.Node;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Acceptor;
import uk.ac.rhul.cs.dice.starworlds.utils.datastructure.visitor.Visitor;

public class Parser {

	public static final String[] KEYWORDS = new String[] { "@agentbodies",
			"@agentminds", "@agentactuators", "@agentsensors",
			"@possibleactions", "@environments", "@physics", "@states",
			"~structure", "~agents", "~environments", "~remote",
			"@connections", "@port", "@ctype", "@address" };
	public static final String AGENTBODIES = KEYWORDS[0],
			AGENTMINDS = KEYWORDS[1], AGENTACTUATORS = KEYWORDS[2],
			AGENTSENSORS = KEYWORDS[3], POSSIBLEACTIONS = KEYWORDS[4],
			ENVIRONMENTS = KEYWORDS[5], PHYSICS = KEYWORDS[6],
			STATES = KEYWORDS[7], REMOTE = KEYWORDS[11],
			CONNECTIONS = KEYWORDS[12], PORT = KEYWORDS[13],
			CONNECTIONTYPE = KEYWORDS[14], ADDRESS = KEYWORDS[15];
	public static final String STRUCTURE = KEYWORDS[8], AGENTS = KEYWORDS[9],
			REALENVIRONMENTS = KEYWORDS[10];

	private JSONObject total;

	public Parser(String initstructure) throws IOException {
		File sf = new File(initstructure);
		if (sf.exists()) {
			total = getJson(sf);
		} else {
			throw new FileNotFoundException("Cannot find init file: " + sf);
		}
	}

	public Collection<Universe> parse() throws Exception {
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

	private Node<AbstractEnvironment> recurseStructure(JSONObject structure,
			JSONObject total) throws Exception {
		NeighbourhoodNode<AbstractEnvironment> current = new NeighbourhoodNode<>();
		String envkey = structure.getString(REALENVIRONMENTS);
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
		JSONArray agentkeys = structure.getJSONArray(AGENTS);
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < agentkeys.length(); i++) {
			String ak = agentkeys.getString(i);
			agents.add(constructAgent(ak, total.getJSONObject(AGENTS)
					.getJSONObject(ak), total));
		}
		JSONObject environmentjson = total.getJSONObject(REALENVIRONMENTS)
				.getJSONObject(envkey);

		// create physics
		// TODO add active bodies and passive bodies
		AbstractPhysics physics = null;

		Class<?> physicsclass = getClassFromJson(PHYSICS,
				environmentjson.getString(PHYSICS), total);

		physics = (AbstractPhysics) physicsclass.getConstructor(Set.class,
				Set.class, Set.class).newInstance(agents, null, null);

		// create state
		AbstractState state = null;
		try {
			state = (AbstractState) getClassFromJson(STATES,
					environmentjson.getString(STATES), total).getConstructor(
					AbstractPhysics.class).newInstance(physics);
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
				ArrayList<AbstractEnvironment> subs = new ArrayList<>();
				subenvs.forEach((n) -> subs.add(n.getValue()));
				current.setValue((AbstractEnvironment) environmentclass
						.getConstructor(Collection.class, AbstractState.class,
								AbstractConnectedPhysics.class,
								Collection.class).newInstance(subs, state,
								physics, possibleactions));
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
			((AbstractConnectedEnvironment) ((Node<AbstractEnvironment>) acceptor)
					.getValue()).initialConnect();
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
