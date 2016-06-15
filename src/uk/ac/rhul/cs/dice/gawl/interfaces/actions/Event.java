package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.EnvironmentalSpace;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The interface for events.<br/><br/>
 * 
 * Known implementations {@link AbstractEvent}.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public interface Event {
	/**
	 * Represents the event with a {@link String}.
	 * 
	 * @return a {@link String} represetation of the event.
	 */
	public String represent();
	
	/**
	 * Returns the wrapped {@link Action}.
	 * 
	 * @return the {@link Action} wrapped by the event.
	 */
	public Action getAction();
	
	/**
	 * Sets a new {@link Action}.
	 * 
	 * @param action : the new {@link Action} to be contained in the event.
	 */
	public void setAction(Action action);
	
	/**
	 * Returns the timestamp of the event.
	 * 
	 * @return a {@link Long} representation of the timestamp of the event.
	 */
	public Long getTimestamp();
	
	/**
	 * Sets a timestamp for the event.
	 * 
	 * @param timestamp : the new timestamp, in the formate of a {@link Long}.
	 */
	public void setTimestamp(Long timestamp);
	
	/**
	 * Returns the {@link Actor} which performed or will perform the event.
	 * 
	 * @return the {@link Actor} which performed or will perform the event.
	 */
	public Actor getActor();
	
	/**
	 * Sets an {@link Actor} which will perform the event.
	 * 
	 * @param actor : the {@link Actor} which will perform the event.
	 */
	public void setActor(Actor actor);
	
	/**
	 * Checks the pre-conditions for the event.
	 * 
	 * @param physics : the {@link Physics} of the context where the event is executed.
	 * @param context : the {@link EnvironmentalSpace} where the event is executed.
	 * @return <code>true</code> if the event can take place, <code>false</code> otherwise.
	 */
	public boolean isPossible(Physics physics, EnvironmentalSpace context);
	
	/**
	 * Checks whether the event taking place is a mandatory condition.
	 * 
	 * @param physics : the {@link Physics} of the context where the event is executed.
	 * @param context : the {@link EnvironmentalSpace} where the event is executed.
	 * @return <code>true</code> if the event must take place, <code>false</code> otherwise.
	 */
	public boolean isNecessary(Physics physics, EnvironmentalSpace context);
	
	/**
	 * Attempts to execute the event by calling {@link isPossible(physics, context)},
	 * {@link perform(physics, context)} and {@link succeeded(physics, event)}.
	 * 
	 * @param physics : the {@link Physics} of the context where the event is executed.
	 * @param context : the {@link EnvironmentalSpace} where the event is executed.
	 * @return a {@link Result} instance which shows the result of the execution.
	 */
	public Result attempt(Physics physics, EnvironmentalSpace context);
	
	/**
	 * Performs an event whose pre-conditions were already met.
	 * 
	 * @param physics : the {@link Physics} of the context where the event is executed.
	 * @param context : the {@link EnvironmentalSpace} where the event is executed.
	 * @return a {@link Result} instance which shows the result of the execution.
	 */
	public Result perform(Physics physics, EnvironmentalSpace context);
	
	/**
	 * Checks the post-conditions (effects) of the event.
	 * 
	 * @param physics : the {@link Physics} of the context where the event is executed.
	 * @param context : the {@link EnvironmentalSpace} where the event is executed.
	 * @return <code>true</code> if the event post-conditions are met, <code>false</code> otherwise.
	 */
	public boolean succeeded(Physics physics, EnvironmentalSpace context);
}