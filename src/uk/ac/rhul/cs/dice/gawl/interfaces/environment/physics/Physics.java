package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.EnvironmentalSpace;

/**
 * The general interface for all the physics.<br/><br/>
 * 
 * Known implementations: {@link AbstractPhysics}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
@FunctionalInterface
public interface Physics {
	
	/**
	 * Attempts the execution of the {@link Action} wrapped by the event in the {@link EnvironmentalSpace}
	 * passed as a parameter.
	 * 
	 * @param event : the {@link Event} wrapping the {@link Action} to be attempted.
	 * @param context : the {@link EnvironmentalSpace} where the {@link Action} is attempted.
	 * @return a {@link Result} instance which shows the result of the attempt.
	 */
	public Result attempt(Event event, EnvironmentalSpace context);
}