package uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Action;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.EnvironmentalAction;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Event;
import uk.ac.rhul.cs.dice.gawl.interfaces.actions.Result;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

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
public interface Physics<P extends Perception> {
	
	/**
	 * Attempts the execution of the {@link EnvironmentalAction} wrapped by the event in the {@link Space}
	 * passed as a parameter.
	 * 
	 * @param event : the {@link Event} wrapping the {@link EnvironmentalAction} to be attempted.
	 * @param context : the {@link Space} where the {@link EnvironmentalAction} is attempted.
	 * @return a {@link Result} instance which shows the result of the attempt.
	 */
	public abstract Result<P> attempt(Event<P> event, Space context);
	public abstract boolean isPossible(Action action, Space context);
	public abstract boolean isNecessary(Action action, Space context);
	public abstract Result<P> attempt(Action action, Space context);
	public abstract Result<P> perform(Action action, Space context);
	public abstract boolean succeeded(Action action, Space context);
	public abstract Result<P> getCurrentResult();
	public abstract void setCurrentResult(Result<P> currentMoveResult);
}