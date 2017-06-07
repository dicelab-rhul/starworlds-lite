package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

/**
 * A subclass of {@link ComplexEnvironment} which is always bounded. Thus, the
 * {@link #isBounded()} method will always return true.<br/>
 * <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class Universe extends ComplexEnvironment implements Simulator {

	/**
	 * The default class constructor.
	 * 
	 * @param state
	 *            : an {@link State} instance.
	 * @param physics
	 *            : the {@link Physics} of the environment.
	 * @param appearance
	 *            : the {@link UniverseAppearance} of the environment.
	 * @param server
	 *            :
	 * @param possibleActions
	 */
	public Universe(
			AbstractState state,
			AbstractPhysics physics,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			AbstractEnvironment... subenvironments) {
		super(state, physics, false, appearance, possibleActions);
	}

	@Override
	public void simulate() {
		physics.simulate();
	}

}