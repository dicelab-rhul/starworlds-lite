package uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.SensorSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;

public class ListeningSensor extends AbstractSensor {

	@SensiblePerception
	public static Class<? extends AbstractPerception> PERCEPTION = CommunicationPerception.class;

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean canSense(AbstractEnvironmentalAction action,
			AbstractPerception<?> perception, State context) {
		return CommunicationAction.class.isAssignableFrom(action.getClass());
	}

}
