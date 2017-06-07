package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

public abstract class DistributedUniverse extends DistributedComplexEnvironment {

	public DistributedUniverse(
			State state,
			Physics physics,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			int port, AbstractEnvironment... environments) {
		super(state, physics, false, appearance, possibleActions, port);
		for (AbstractEnvironment ae : environments) {
			this.addSubEnvironment(ae);
		}
	}
}
