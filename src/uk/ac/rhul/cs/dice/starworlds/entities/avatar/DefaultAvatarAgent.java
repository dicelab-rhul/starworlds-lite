package uk.ac.rhul.cs.dice.starworlds.entities.avatar;

import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.Sensor;

public class DefaultAvatarAgent extends
		AbstractAvatarAgent<AbstractEnvironmentalAction> {

	public DefaultAvatarAgent(List<Sensor> sensors, List<Actuator> actuators,
			AbstractAvatarMind<AbstractEnvironmentalAction> mind) {
		super(sensors, actuators, mind);
	}

	public DefaultAvatarAgent(ActiveBodyAppearance appearance,
			List<Sensor> sensors, List<Actuator> actuators,
			AbstractAvatarMind<AbstractEnvironmentalAction> mind) {
		super(appearance, sensors, actuators, mind);
	}
}
