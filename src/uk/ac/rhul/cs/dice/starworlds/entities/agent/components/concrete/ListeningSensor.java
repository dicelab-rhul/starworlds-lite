package uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.AbstractSubscriptionHandler.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;

public class ListeningSensor extends AbstractSensor {

	@SuppressWarnings("rawtypes")
	@SensiblePerception
	public static Class<? extends AbstractPerception> COMMUNICATIONPERCEPTION = CommunicationPerception.class;

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
