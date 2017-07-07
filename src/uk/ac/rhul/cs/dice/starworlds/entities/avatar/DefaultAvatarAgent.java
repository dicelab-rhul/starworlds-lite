package uk.ac.rhul.cs.dice.starworlds.entities.avatar;

import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.ActiveBodyAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Actuator;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.Sensor;

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
