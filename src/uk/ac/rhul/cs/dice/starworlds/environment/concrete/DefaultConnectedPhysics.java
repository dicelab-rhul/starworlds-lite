package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;

public class DefaultConnectedPhysics extends AbstractConnectedPhysics {

	public DefaultConnectedPhysics(Set<AbstractAgent> agents,
			Set<ActiveBody> activeBodies, Set<PassiveBody> passiveBodies) {
		super(agents, activeBodies, passiveBodies);
	}

}
