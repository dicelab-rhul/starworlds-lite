package uk.ac.rhul.cs.dice.starworlds.experiment.physicalagents;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultUniverse;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;
import uk.ac.rhul.cs.dice.starworlds.initialisation.IDFactory;

public class PhysicalUniverse extends DefaultUniverse {

	private static Integer dimension = 10;

	public PhysicalUniverse(
			AbstractAmbient ambient,
			AbstractConnectedPhysics physics,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(ambient, physics, new EnvironmentAppearance(IDFactory
				.getInstance().getNewID(), false, false), possibleActions);
	}

	@Override
	public void postInitialisation() {
		super.postInitialisation();
		this.getState().setDimension(dimension);
	}
	
	public void notifyObservers() {
		this.setChanged();
		super.notifyObservers();
	}

	@Override
	public PhysicalAmbient getState() {
		return (PhysicalAmbient) super.getState();
	}

	@Override
	public PhysicalPhysics getPhysics() {
		return (PhysicalPhysics) super.getPhysics();
	}
}
