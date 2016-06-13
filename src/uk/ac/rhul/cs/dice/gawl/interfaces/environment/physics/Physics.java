package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.EnvironmentalSpace;

/**
 * The general interface for all the physics.<br/><br/>
 * 
 * Known implementations: {@link AbstractPhysics}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public interface Physics {
	public boolean isPossible(Action action, EnvironmentalSpace context);
	public boolean isNecessary(Action action, EnvironmentalSpace context);
	public Result attempt(Action action, EnvironmentalSpace context);
	public Result perform(Action action, EnvironmentalSpace context);
	public boolean succeeded(Action action, EnvironmentalSpace context);
}