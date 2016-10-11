package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.Space;
import uk.ac.rhul.cs.dice.gawl.interfaces.environment.physics.Physics;

/**
 * The most general class implementing {@link Event}. It contains an {@link EnvironmentalAction}, a timestamp in the form of a {@link Long}, and an {@link Actor}.<br/><br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractEvent implements Event {
	private EnvironmentalAction action;
	private Long timestamp;
	private Actor actor;
	
	/**
	 * Constructor with an {@link EnvironmentalAction}, a {@link Long} timestamp and an {@link Actor}.
	 * 
	 * @param action : the {@link EnvironmentalAction} to be executed during the event.
	 * @param timestamp : the timestamp of the event expressed as a {@link Long}.
	 * @param actor : the {@link Actor} which executes the {@link EnvironmentalAction}.
	 */
	public AbstractEvent(EnvironmentalAction action, Long timestamp, Actor actor) {
		this.action = action;
		this.timestamp = timestamp;
		this.actor = actor;
	}

	@Override
	public EnvironmentalAction getAction() {
		return this.action;
	}

	@Override
	public void setAction(EnvironmentalAction action) {
		this.action = action;
	}

	@Override
	public Long getTimestamp() {
		return this.timestamp;
	}

	@Override
	public void setTimestamp(Long timestamp) {
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
	public Result attempt(Physics physics, Space context) {
		return physics.attempt(this, context);
	}
}