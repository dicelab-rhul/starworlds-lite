package uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.ActivePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;

public class SeeingSensor extends AbstractSensor {

	@SensiblePerception
	public static final Class<?> ACTIVEPERCEPTION = ActivePerception.class;
	@SensiblePerception
	public static final Class<?> DEFAULTPERCEPTION = DefaultPerception.class;
	

}
