package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.EnvironmentalSpace;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The most general class implementing {@link Event}. It contains an {@link Action}, a timestamp in the form of a {@link Long}, and an {@link Actor}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Kostas Stathis
 *
 */
public abstract class AbstractEvent implements Event {
	private Action action;
	private long timestamp;
	private Actor actor;
	
	public AbstractEvent(Action action, long timestamp, Actor actor) {
		this.action = action;
		this.timestamp = timestamp;
		this.actor = actor;
	}

	@Override
	public Action getAction() {
		return this.action;
	}

	@Override
	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public Actor getActor() {
		return this.actor;
	}

	@Override
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	@Override
	public Result attempt(Physics physics, EnvironmentalSpace context) {
		return physics.attempt(this, context);
	}
}