package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.AbstractAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.appearances.SimpleEnvironmentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

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
public abstract class SimpleEnvironment<P extends Perception> extends AbstractEnvironment<P> {

	/**
	 * The default class constructor.
	 * 
	 * @param state : an {@link Space} instance.
	 * @param admissibleActions : the {@link Set} of performable {@link AbstractAction} instances.
	 * @param bodies : a {@link Set} of {@link Body} elements.
	 * @param physics : the {@link Physics} of the environment.
	 * @param bounded : a {@link Boolean} value indicating whether the environment is bounded or not.
	 * @param appearance : the {@link SimpleEnvironmentAppearance} of the environment.
	 */
	public SimpleEnvironment(Space state, Set<Class<? extends EnvironmentalAction<P>>> admissibleActions, Set<Body> bodies, Physics<P> physics, Boolean bounded, SimpleEnvironmentAppearance appearance) {
		super(state, admissibleActions, bodies, physics, bounded, appearance);
	}
	
	@Override
	public Boolean isSimple() {
		return true;
	}
}