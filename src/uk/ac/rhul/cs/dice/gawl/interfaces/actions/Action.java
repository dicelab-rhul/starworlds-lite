package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.EnvironmentalSpace;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The interface for all the actions.<br/><br/>
 * 
 * Known implementations: {@link AbstractAction}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public interface Action {
	public boolean isPossible(Physics physics, EnvironmentalSpace context);
	public boolean isNecessary(Physics physics, EnvironmentalSpace context);
	public Result attempt(Physics physics, EnvironmentalSpace context);
	public Result perform(Physics physics, EnvironmentalSpace context);
	public boolean succeeded(Physics physics, EnvironmentalSpace context);
}