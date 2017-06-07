package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.DistributedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

public abstract class DistributedSimpleEnvironment extends DistributedEnvironment
		implements SimpleEnvironment {

	public DistributedSimpleEnvironment(
			State state,
			Physics physics,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			int port) {
		super(state, physics, false, appearance, possibleActions, port);
	}

	@Override
	public boolean isDistributed() {
		return true;
	}
}
