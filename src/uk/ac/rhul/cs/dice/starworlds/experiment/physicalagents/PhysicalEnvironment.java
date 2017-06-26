package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultUniverse;
import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;

public class PhysicalEnvironment extends DefaultUniverse {

	private static Integer dimension = 10;

	public PhysicalEnvironment(
			Collection<AbstractConnectedEnvironment> subenvironments,
			AbstractAmbient state,
			AbstractConnectedPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(null, subenvironments, state, physics, possibleActions);
	}

	@Override
	public void postInitialisation() {
		super.postInitialisation();
		this.getState().setDimension(dimension);
	}

	@Override
	public PhysicalState getState() {
		return (PhysicalState) super.getState();
	}

//	@Override
//	public PhysicalPhysics getPhysics() {
//		return (PhysicalPhysics) super.getPhysics();
//	}
}
