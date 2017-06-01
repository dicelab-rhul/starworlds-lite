package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.concrete.SeeingSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractSubscriber.SensiblePerception;

public class BadSeeingSensor extends SeeingSensor {

	@SensiblePerception
	public static final Class<?> MOVEPERCEPTION = MovePerception.class;

}
