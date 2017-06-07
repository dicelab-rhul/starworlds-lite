package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.Collection;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.DistributedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

public abstract class DistributedComplexEnvironment extends DistributedEnvironment {

	public DistributedComplexEnvironment(
			State state,
			Physics physics,
			Boolean bounded,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			int port) {
		super(state, physics, bounded, appearance, possibleActions, port);

	}

	@Override
	public Boolean isSimple() {
		return false;
	}

	@Override
	public boolean isDistributed() {
		return true;
	}
}
