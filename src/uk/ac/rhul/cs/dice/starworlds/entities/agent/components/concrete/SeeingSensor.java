package uk.ac.rhul.cs.dice.starworlds.entities.agent.components.concrete;

import uk.ac.rhul.cs.dice.starworlds.entities.agent.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.subscription.AbstractSubscriptionHandler.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;

public class SeeingSensor extends AbstractSensor {

	@SensiblePerception
	public static final Class<?> ACTIVEPERCEPTION = ActivePerception.class;
	@SensiblePerception
	public static final Class<?> DEFAULTPERCEPTION = DefaultPerception.class;
	

}
