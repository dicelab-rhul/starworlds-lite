package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.EnvironmentalSpace;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The interface for all the actions.<br/><br/>
 * 
 * Known implementations: {@link AbstractAction}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface Action {
	/**
	 * Checks the pre-conditions for the action.
	 * 
	 * @param physics : the {@link Physics} of the context where the action is executed.
	 * @param context : the {@link EnvironmentalSpace} where the action is executed.
	 * @return <code>true</code> if the action can be executed, <code>false</code> otherwise.
	 */
	public boolean isPossible(Physics physics, EnvironmentalSpace context);
	
	/**
	 * Checks whether the action being executed is a mandatory condition.
	 * 
	 * @param physics : the {@link Physics} of the context where the action is executed.
	 * @param context : the {@link EnvironmentalSpace} where the action is executed.
	 * @return <code>true</code> if the action must be executed, <code>false</code> otherwise.
	 */
	public boolean isNecessary(Physics physics, EnvironmentalSpace context);
	
	/**
	 * Attempts to execute the action by calling {@link #isPossible(physics, context)},
	 * {@link #perform(physics, context)} and {@link #succeeded(physics, event)}.
	 * 
	 * @param physics : the {@link Physics} of the context where the action is executed.
	 * @param context : the {@link EnvironmentalSpace} where the action is executed.
	 * @return a {@link Result} instance which shows the result of the execution.
	 */
	public Result attempt(Physics physics, EnvironmentalSpace context);
	
	/**
	 * Performs an action whose pre-conditions were already met.
	 * 
	 * @param physics : the {@link Physics} of the context where the action is executed.
	 * @param context : the {@link EnvironmentalSpace} where the action is executed.
	 * @return a {@link Result} instance which shows the result of the execution.
	 */
	public Result perform(Physics physics, EnvironmentalSpace context);
	
	/**
	 * Checks the post-conditions (effects) of the action.
	 * 
	 * @param physics : the {@link Physics} of the context where the action is executed.
	 * @param context : the {@link EnvironmentalSpace} where the action is executed.
	 * @return <code>true</code> if the action post-conditions are met, <code>false</code> otherwise.
	 */
	public boolean succeeded(Physics physics, EnvironmentalSpace context);
}