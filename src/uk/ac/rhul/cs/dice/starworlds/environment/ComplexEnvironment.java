package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractState;
import uk.ac.rhul.cs.dice.starworlds.environment.base.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.NonDistributedEnvironment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.State;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.AbstractPhysics;
import uk.ac.rhul.cs.dice.starworlds.environment.physics.Physics;

/**
 * A subclass of {@link SimpleEnvironment} which can contain an arbitrary number
 * of {@link AbstractEnvironment} instances as sub-environments. Thus, the
 * method {@link #isSimple()} will always return false.<br/>
 * <br/>
 * 
 * Known direct subclasses: {@link Universe}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class ComplexEnvironment extends NonDistributedEnvironment {

	/**
	 * Constructor.
	 * 
	 * @param state
	 *            : an {@link AbstractState} instance.
	 * @param physics
	 *            : the {@link AbstractPhysics} of the environment.
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not.
	 * @param appearance
	 *            : the {@link EnvironmentAppearance}
	 */
	public ComplexEnvironment(
			AbstractState state,
			AbstractPhysics physics,
			Boolean bounded,
			EnvironmentAppearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions,
			AbstractEnvironment... subenvironments) {
		super(state, physics, bounded, appearance, possibleActions);
		for (AbstractEnvironment e : subenvironments) {
			this.connectedEnvironmentManager.addSubEnvironment(e);
		}
	}

	@Override
	public Boolean isSimple() {
		return false;
	}
}