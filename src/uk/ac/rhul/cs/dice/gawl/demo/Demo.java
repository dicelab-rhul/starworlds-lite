package uk.ac.rhul.cs.dice.gawl.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.concrete.DefaultAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.concrete.DefaultAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.concrete.DefaultAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.concrete.DefaultEnvironment;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.concrete.DefaultPhysics;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.concrete.DefaultState;

public class Demo {

	private final static int NUMAGENTS = 2;

	public static void main(String[] args) {

		DefaultPhysics physics = new DefaultPhysics(
				getDefaultAgents(NUMAGENTS), null, null);

		DefaultEnvironment environment = new DefaultEnvironment(
				new DefaultState(physics), physics, null);
		physics.start();
	}

	public static Set<AbstractAgent> getDefaultAgents(int num) {
		Set<AbstractAgent> agents = new HashSet<>();
		for (int i = 0; i < num; i++) {
			AbstractAgent a = createDefaultAgent();
			a.setId(String.valueOf(i));
			agents.add(a);
		}
		return agents;
	}

	public static DefaultAgent createDefaultAgent() {
		return new DefaultAgent(null, getDefaultSensors(),
				getDefaultActuators(), new DefaultAgentMind(),
				new DefaultAgentBrain());
	}

	public static List<Sensor> getDefaultSensors() {
		List<Sensor> sensors = new ArrayList<>();
		sensors.add(new SeeingSensor());
		sensors.add(new ListeningSensor());
		return sensors;
	}

	public static List<Actuator> getDefaultActuators() {
		List<Actuator> actuators = new ArrayList<>();
		actuators.add(new SpeechActuator());
		return actuators;
	}
}
