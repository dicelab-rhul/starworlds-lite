package uk.ac.rhul.cs.dice.starworlds.entities.agent.concrete;

import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAutonomousAgent;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.AbstractAgentMind;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;

public class DefaultAgent extends AbstractAutonomousAgent {

	public DefaultAgent(ActiveBodyAppearance appearance, List<Sensor> sensors,
			List<Actuator> actuators, AbstractAgentMind mind) {
		super(appearance, sensors, actuators, mind);
	}

	public DefaultAgent(List<Sensor> sensors, List<Actuator> actuators,
			AbstractAgentMind mind) {
		super(sensors, actuators, mind);
	}
}
