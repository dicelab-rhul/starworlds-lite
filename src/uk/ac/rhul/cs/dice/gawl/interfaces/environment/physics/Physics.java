package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
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
@FunctionalInterface
public interface Physics {
	public Result attempt(Event event, EnvironmentalSpace context);
}