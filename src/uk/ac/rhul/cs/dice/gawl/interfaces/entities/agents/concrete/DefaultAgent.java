package uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.concrete;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentBrain;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.AbstractAgentMind;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.agents.components.Sensor;

public class DefaultAgent extends AbstractAgent {

	public DefaultAgent(AbstractAppearance appearance, List<Sensor> sensors,
			List<Actuator> actuators, AbstractAgentMind mind,
			AbstractAgentBrain brain) {
		super(appearance, sensors, actuators, mind, brain);
	}
}
