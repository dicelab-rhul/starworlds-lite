package uk.ac.rhul.cs.dice.gawl.interfaces.environment;

import java.util.Set;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.common.SimpleEnvironmentAppearance;
import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Body;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * A subclass of {@link AbstractEnvironment} which cannot have sub-environments. Thus the {@link #isSimple()} method will always return true.<br/><br/>
 * 
 * Known direct subclasses: //TODO
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class SimpleEnvironment extends AbstractEnvironment {

	public SimpleEnvironment(EnvironmentalSpace state, Set<Action> admissibleActions, Set<Body> bodies, Physics physics, boolean bounded, SimpleEnvironmentAppearance appearance) {
		super(state, admissibleActions, bodies, physics, bounded, appearance);
	}
	
	@Override
	public boolean isSimple() {
		return true;
	}
}