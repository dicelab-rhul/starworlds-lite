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
	public String represent();
	public Action getAction();
	public void setAction(Action action);
	public long getTimestamp();
	public void setTimestamp(long timestamp);
	public Actor getActor();
	public void setActor(Actor actor);
	public boolean isPossible(Physics physics, EnvironmentalSpace context);
	public boolean isNecessary(Physics physics, EnvironmentalSpace context);
	public Result attempt(Physics physics, EnvironmentalSpace context);
	public Result perform(Physics physics, EnvironmentalSpace context);
	public boolean succeeded(Physics physics, EnvironmentalSpace context);
}