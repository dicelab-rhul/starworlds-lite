package uk.ac.rhul.cs.dice.starworlds.entities.agents.concrete;

import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;

public class DefaultAgent extends AbstractAgent {

	public DefaultAgent(ActiveBodyAppearance appearance, List<Sensor> sensors,
			List<Actuator> actuators, AbstractAgentMind mind) {
		super(appearance, sensors, actuators, mind);
	}

	public DefaultAgent(List<Sensor> sensors, List<Actuator> actuators,
			AbstractAgentMind mind) {
		super(sensors, actuators, mind);
	}
}
