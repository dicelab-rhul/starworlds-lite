package uk.ac.rhul.cs.dice.starworlds.environment.base;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.components.AbstractSensor;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Subscriber;

public abstract class NonDistributedEnvironment extends AbstractEnvironment {

	public NonDistributedEnvironment(
			AbstractState state,
			AbstractPhysics physics,
			Boolean bounded,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(new ConnectedEnvironmentManager(
				ConnectedEnvironmentManager.LOCAL, null), new Subscriber(),
				state, physics, bounded, appearance, possibleActions);
	}

	public void addSuperEnvironment(AbstractEnvironment environment) {
		this.connectedEnvironmentManager.addSuperEnvironment(environment);
	}

	public void addSubEnvironment(AbstractEnvironment environment) {
		this.connectedEnvironmentManager.addSubEnvironment(environment);
	}

	public void addNeighbouringEnvironment(AbstractEnvironment environment) {
		this.connectedEnvironmentManager
				.addNeighbouringEnvironment(environment);
	}

	@Override
	public synchronized void subscribe(ActiveBody body, AbstractSensor[] sensors) {
		super.subscribe(body, sensors);
	}

	@Override
	public boolean isDistributed() {
		return false;
	}
}
