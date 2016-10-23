package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;

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
public interface Physics {	
	public abstract boolean isPossible(Event event, Space context);
	public abstract boolean isNecessary(Event event, Space context);
	public abstract Result attempt(Event event, Space context);
	public abstract Result perform(Event event, Space context);
	public abstract boolean succeeded(Event event, Space context);
	
	public abstract boolean isPossible(EnvironmentalAction action, Space context);
	public abstract boolean isNecessary(EnvironmentalAction action, Space context);
	public abstract Result attempt(EnvironmentalAction action, Space context);
	public abstract Result perform(EnvironmentalAction action, Space context);
	public abstract boolean succeeded(EnvironmentalAction action, Space context);
	
	
	public abstract Result getCurrentResult();
	public abstract void setCurrentResult(Result currentMoveResult);
}