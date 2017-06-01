package uk.ac.rhul.cs.dice.starworlds.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.appearances.AbstractAppearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
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
	private List<AbstractEnvironment<?>> subEnvironments;

	/**
	 * Constructor.
	 * 
	 * @param state
	 *            : an {@link Space} instance.
	 * @param physics
	 *            : the {@link Physics} of the environment.
	 * @param bounded
	 *            : a {@link Boolean} value indicating whether the environment
	 *            is bounded or not.
	 * @param appearance
	 *            : the {@link AbstractAppearance} of the environment.
	 */
	public ComplexEnvironment(
			State state,
			Physics physics,
			Boolean bounded,
			Appearance appearance,
			Collection<Class<? extends AbstractEnvironmentalAction>> possibleActions) {
		super(state, physics, bounded, appearance, possibleActions);
		this.subEnvironments = new ArrayList<>();
	}

	/**
	 * Returns the {@link List} of sub-environments.
	 * 
	 * @return a {@link List} of {@link AbstractEnvironment} elements.
	 */
	public List<AbstractEnvironment<?>> getSubEnvironments() {
		return this.subEnvironments;
	}

	/**
	 * Adds an {@link AbstractEnvironment} to the {@link List} of
	 * sub-environments.
	 * 
	 * @param environment
	 *            : the {@link AbstractEnvironment} to add to the {@link List}
	 *            of sub-environments.
	 */
	public void addSubEnvironment(AbstractEnvironment<?> environment) {
		this.subEnvironments.add(environment);
	}

	@Override
	public Boolean isSimple() {
		return false;
	}
}