package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.util.Collection;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.PassiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.AbstractAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

public class DefaultPhysics extends AbstractPhysics {

	public DefaultPhysics(
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			Set<AbstractAgent> agents, Set<ActiveBody> activeBodies,
			Set<PassiveBody> passiveBodies) {
		super(possibleActions, agents, activeBodies, passiveBodies);
	}
}
