package uk.ac.rhul.cs.dice.starworlds.experiment.communicatingagents;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.PhysicalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.DefaultPerception;

public class TestAction extends PhysicalAction {
	private static final long serialVersionUID = 1L;

	@SensiblePerception
	public static Class<?> PERCEPTION = DefaultPerception.class;

}
