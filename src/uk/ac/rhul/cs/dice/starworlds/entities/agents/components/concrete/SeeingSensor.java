package uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.SensorSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;

public class SeeingSensor extends AbstractSensor {

	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION1 = DefaultPerception.class;

	@Override
	public boolean canSense(AbstractEnvironmentalAction action,
			AbstractPerception<?> perception, State context) {
		return SensingAction.class.isAssignableFrom(action.getClass())
				|| PhysicalAction.class.isAssignableFrom(action.getClass());
	}

}
