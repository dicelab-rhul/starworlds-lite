package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.UniverseAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * A subclass of {@link ComplexEnvironment} which is always bounded. Thus, the {@link #isBounded()} method will always return true.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class Universe<P extends Perception> extends ComplexEnvironment<P> {

	/**
	 * The default class constructor.
	 * 
	 * @param state : an {@link Space} instance.
	 * @param admissibleActions : the {@link Set} of performable {@link AbstractAction} instances.
	 * @param bodies : a {@link Set} of {@link Body} elements.
	 * @param physics : the {@link Physics} of the environment.
	 * @param appearance : the {@link UniverseAppearance} of the environment.
	 */
	public Universe(Space state, Set<Class<? extends EnvironmentalAction<P>>> admissibleActions, Set<Body> bodies, Physics<P> physics, UniverseAppearance appearance) {
		super(state, admissibleActions, bodies, physics, true, appearance);
	}
}