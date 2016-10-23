package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.SimpleEnvironmentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * A subclass of {@link AbstractEnvironment} which cannot have sub-environments. Thus the {@link #isSimple()} method will always return true.<br/><br/>
 * 
 * Known direct subclasses: {@link ComplexEnvironment}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class SimpleEnvironment extends AbstractEnvironment {

	/**
	 * The default class constructor.
	 * 
	 * @param state : an {@link Space} instance.
	 * @param physics : the {@link Physics} of the environment.
	 * @param bounded : a {@link Boolean} value indicating whether the environment is bounded or not.
	 * @param appearance : the {@link SimpleEnvironmentAppearance} of the environment.
	 */
	public SimpleEnvironment(Space state, Physics physics, Boolean bounded, SimpleEnvironmentAppearance appearance) {
		super(state, physics, bounded, appearance);
	}
	
	@Override
	public Boolean isSimple() {
		return true;
	}
}