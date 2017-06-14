package uk.ac.rhul.cs.dice.starworlds.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

public class Parser {

	public static final String[] KEYWORDS = new String[] { "@agentbodies",
			"@agentminds", "@agentactuators", "@agentsensors",
			"@possibleactions", "@environments", "@physics", "@states",
			"~structure", "~agents", "~environments", "~remote",
			"@connections", "@port" };
	public static final String AGENTBODIES = KEYWORDS[0],
			AGENTMINDS = KEYWORDS[1], AGENTACTUATORS = KEYWORDS[2],
			AGENTSENSORS = KEYWORDS[3], POSSIBLEACTIONS = KEYWORDS[4],
			ENVIRONMENTS = KEYWORDS[5], PHYSICS = KEYWORDS[6],
			STATES = KEYWORDS[7], REMOTE = KEYWORDS[11],
			CONNECTIONS = KEYWORDS[12], PORT = KEYWORDS[13];
	public static final String STRUCTURE = KEYWORDS[8], AGENTS = KEYWORDS[9],
			REALENVIRONMENTS = KEYWORDS[10];

	private Set<String> environmentkeys;
	// store for post initialisation phase
	private ArrayList<AbstractEnvironment> environments = new ArrayList<>();
	private JSONObject total;

	public Parser(String initstructure) throws IOException {
		File sf = new File(initstructure);
		if (sf.exists()) {
			total = getJson(sf);
		} else {
			throw new FileNotFoundException("Cannot find init file: " + sf);
		}
	}

	public Universe parse() throws Exception {
		JSONObject structure = total.getJSONObject(STRUCTURE);
		if (structure.keySet().size() == 1) {
			environmentkeys = total.getJSONObject(REALENVIRONMENTS).keySet();
			String universekey = (String) structure.keySet().toArray()[0];
			Universe universe = (Universe) recurseStructure(universekey,
					structure.getJSONObject(universekey), total);
			// do post initialisation
			environments.add((AbstractEnvironment) universe);
			for (AbstractEnvironment e : environments) {
				((AbstractConnectedEnvironment) e).initialConnect();
			}
			for (AbstractEnvironment e : environments) {
				e.postInitialisation();
			}
			// for (AbstractEnvironment e : environments) {
			// ((AbstractConnectedEnvironment) e).gatherMessages();
			// }
			return universe;
		} else {
			return null;
		}
	}

	private AbstractEnvironment recurseStructure(String key,
			JSONObject structure, JSONObject total) throws Exception {
		List<AbstractEnvironment> subenvs = new ArrayList<>();
		for (String s : structure.keySet()) {
			if (environmentkeys.contains(s)) {
				subenvs.add(recurseStructure(s, structure.getJSONObject(s),
						total));
			}
		}
		environments.addAll(subenvs);
		// create agents
		JSONArray agentkeys = structure.getJSONArray(AGENTS);
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < agentkeys.length(); i++) {
			String ak = agentkeys.getString(i);
			agents.add(constructAgent(ak, total.getJSONObject(AGENTS)
					.getJSONObject(ak), total));
		}

		JSONObject environmentjson = total.getJSONObject(REALENVIRONMENTS)
				.getJSONObject(key);

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
					String[] ap = connections.getString(i).split(":");
					remoteEnvironment.addInitialConnection(ap[0],
							Integer.valueOf(ap[1]));
				}
			}
			return remoteEnvironment;
		} else {
			// use the local constructor.
			try {
				return (AbstractEnvironment) environmentclass.getConstructor(
						Collection.class, AbstractState.class,
						AbstractConnectedPhysics.class, Collection.class)
						.newInstance(subenvs, state, physics, possibleactions);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
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
		JSONTokener tokener = new JSONTokener(new FileReader(file));
		return new JSONObject(tokener);
	}
}
