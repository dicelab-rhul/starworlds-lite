package uk.ac.rhul.cs.dice.starworlds.initialisation;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.entities.agents.concrete.DefaultAgent;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.AbstractConnectedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.concrete.ComplexEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractConnectedPhysics;

public class EnvironmentFactory {

	private static EnvironmentFactory instance = new EnvironmentFactory();
	private static IDFactory idfactory = IDFactory.getInstance();

	private EnvironmentFactory() {
	}

	public AbstractConnectedEnvironment createDefaultEnvironment(
			Collection<AbstractConnectedEnvironment> subenvironments,
			Collection<AbstractConnectedEnvironment> neighbouringenvironments,
			AbstractConnectedPhysics physics,
			AbstractState state,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		return new ComplexEnvironment(subenvironments, state, physics, possibleActions)
		
		
		
	}
	
	
	

	public static EnvironmentFactory getInstance() {
		return instance;
	}
}
