package uk.ac.rhul.cs.dice.starworlds.initialisation;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.ListeningSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.PhysicalActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SpeechActuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.concrete.DefaultAgent;

public class AgentFactory {

	private static AgentFactory instance = new AgentFactory();
	private static IDFactory idfactory = IDFactory.getInstance();

	private AgentFactory() {
	}

	public DefaultAgent createCustomDefaultAgent(List<Sensor> sensors,
			List<Actuator> actuators, AbstractAgentMind mind) {
		DefaultAgent agent = new DefaultAgent(sensors, actuators, mind);
		agent.setId(idfactory.getNewID());
		return agent;
	}

	public DefaultAgent createDefaultAgent(AbstractAgentMind mind) {
		DefaultAgent agent = new DefaultAgent(getDefaultSensors(),
				getDefaultActuators(), mind);
		agent.setId(idfactory.getNewID());
		return agent;
	}

	// TODO
	public List<Sensor> getDefaultSensors() {
		List<Sensor> sensors = new ArrayList<>();
		sensors.add(new SeeingSensor());
		sensors.add(new ListeningSensor());
		return sensors;
	}

	// TODO
	public List<Actuator> getDefaultActuators() {
		List<Actuator> actuators = new ArrayList<>();
		actuators.add(new SpeechActuator());
		actuators.add(new PhysicalActuator());
		return actuators;
	}

	public static AgentFactory getInstance() {
		return instance;
	}
}
