package uk.ac.rhul.cs.dice.starworlds.experiments.synchronisationexperiment;

import java.util.Collection;
import java.util.HashSet;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.CommunicationAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.ambient.AbstractAmbient;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultUniverse;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;

public class TestUniverse extends DefaultUniverse {

	private static Collection<Class<? extends AbstractEnvironmentalAction>> INITIALSUBSCRIPTION;
	static {
		INITIALSUBSCRIPTION = new HashSet<>();
		INITIALSUBSCRIPTION.add(TestAction.class);
		INITIALSUBSCRIPTION.add(CommunicationAction.class);
		INITIALSUBSCRIPTION.add(SensingAction.class);
	}

	public TestUniverse(
			Integer port,
			AbstractAmbient ambient,
			AbstractConnectedPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(port, ambient, physics, appearance, possibleActions);
	}

	@Override
	protected Collection<Class<? extends AbstractEnvironmentalAction>> getInitialActionsToSubscribe() {
		return INITIALSUBSCRIPTION;
	}
}
