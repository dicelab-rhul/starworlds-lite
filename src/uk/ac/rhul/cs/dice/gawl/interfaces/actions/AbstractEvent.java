package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import uk.ac.rhul.cs.dice.gawl.interfaces.entities.Actor;

/**
 * The most general class implementing {@link Event}. It contains an {@link Action}, a timestamp in the form of a {@link Long}, and an {@link Actor}.<br/><br/>
 * 
 * Known direct subclasses: //TODO
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

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}
}