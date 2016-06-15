package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.common.UniverseAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * A subclass of {@link ComplexEnvironment} which is always bounded. Thus, the {@link #isBounded()} method will always return true.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public class Universe extends ComplexEnvironment {

	/**
	 * The default class constructor.
	 * 
	 * @param state : an {@link EnvironmentalSpace} instance.
	 * @param admissibleActions : the {@link Set} of performable {@link Action} instances.
	 * @param bodies : a {@link Set} of {@link Body} elements.
	 * @param physics : the {@link Physics} of the environment.
	 * @param appearance : the {@link UniverseAppearance} of the environment.
	 */
	public Universe(EnvironmentalSpace state, Set<Action> admissibleActions, Set<Body> bodies, Physics physics, UniverseAppearance appearance) {
		super(state, admissibleActions, bodies, physics, true, appearance);
	}
}