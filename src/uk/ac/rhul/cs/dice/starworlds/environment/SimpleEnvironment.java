package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.NonDistributedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;

/**
 * TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class SimpleEnvironment extends NonDistributedEnvironment {

	public SimpleEnvironment(
			AbstractState state,
			AbstractPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(state, physics, true, appearance, possibleActions);
	}

	@Override
	public void addSubEnvironment(AbstractEnvironment environment) {
		System.err.println("Error: Cannot add subenvironment to "
				+ this.getClass().getSimpleName());
	}

	public Boolean isSimple() {
		return true;
	}
}