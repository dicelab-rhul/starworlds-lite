package uk.ac.rhul.cs.dice.starworlds.environment;

import uk.ac.rhul.cs.dice.starworlds.appearances.AbstractAppearance;
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
public class Universe extends ComplexEnvironment {

	/**
	 * The default class constructor.
	 * 
	 * @param state
	 *            : an {@link State} instance.
	 * @param physics
	 *            : the {@link Physics} of the environment.
	 * @param appearance
	 *            : the {@link UniverseAppearance} of the environment.
	 */
	public Universe(State state, Physics physics, AbstractAppearance appearance) {
		super(state, physics, true, appearance);
	}
}