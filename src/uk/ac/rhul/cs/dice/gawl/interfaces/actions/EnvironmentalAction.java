package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * The interface for all the actions that should be performed in some
 * environment with some physics.<br/>
 * <br/>
 * Implements: {@link EnvironmentalAction}. </br>
 * Known implementations: {@link AbstractAction}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public interface EnvironmentalAction<P extends Perception> extends Action {
	/**
	 * Checks the pre-conditions for the action.
	 * 
	 * @param physics
	 *            : the {@link Physics} of the context where the action is
	 *            executed.
	 * @param context
	 *            : the {@link Space} where the action is executed.
	 * @return <code>true</code> if the action can be executed,
	 *         <code>false</code> otherwise.
	 */
	public abstract boolean isPossible(Physics<P> physics, Space context);

	/**
	 * Checks whether the action being executed is a mandatory condition.
	 * 
	 * @param physics
	 *            : the {@link Physics} of the context where the action is
	 *            executed.
	 * @param context
	 *            : the {@link Space} where the action is executed.
	 * @return <code>true</code> if the action must be executed,
	 *         <code>false</code> otherwise.
	 */
	public abstract boolean isNecessary(Physics<P> physics, Space context);

	/**
	 * Attempts to execute the action by calling
	 * {@link #isPossible(physics, context)}, {@link #perform(physics, context)}
	 * and {@link #succeeded(physics, event)}.
	 * 
	 * @param physics
	 *            : the {@link Physics} of the context where the action is
	 *            executed.
	 * @param context
	 *            : the {@link Space} where the action is executed.
	 * @return a {@link Result} instance which shows the result of the
	 *         execution.
	 */
	public abstract Result<P> attempt(Physics<P> physics, Space context);

	/**
	 * Performs an action whose pre-conditions were already met.
	 * 
	 * @param physics
	 *            : the {@link Physics} of the context where the action is
	 *            executed.
	 * @param context
	 *            : the {@link Space} where the action is executed.
	 * @return a {@link Result} instance which shows the result of the
	 *         execution.
	 */
	public abstract Result<P> perform(Physics<P> physics, Space context);

	/**
	 * Checks the post-conditions (effects) of the action.
	 * 
	 * @param physics
	 *            : the {@link Physics} of the context where the action is
	 *            executed.
	 * @param context
	 *            : the {@link Space} where the action is executed.
	 * @return <code>true</code> if the action post-conditions are met,
	 *         <code>false</code> otherwise.
	 */
	public abstract boolean succeeded(Physics<P> physics, Space context);
}