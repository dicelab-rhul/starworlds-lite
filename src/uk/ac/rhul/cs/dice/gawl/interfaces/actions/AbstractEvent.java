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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((actor == null) ? 0 : actor.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEvent other = (AbstractEvent) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (actor == null) {
			if (other.actor != null)
				return false;
		} else if (!actor.equals(other.actor))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
}