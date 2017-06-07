package uk.ac.rhul.cs.dice.starworlds.entities.agents.components;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;

/**
 * The most generic class implementing {@link Actuator}. It also extends
 * {@link CustomObservable}. It may contain an {@link Event} to perform and the
 * corresponding {@link EnvironmentalAction}.<br/>
 * <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractActuator implements Actuator {

	private String id;
	private ActiveBody body;

	public AbstractActuator() {
	}

	@Override
	public ActiveBody getBody() {
		return this.body;
	}

	@Override
	public void setBody(ActiveBody body) {
		this.body = body;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void attempt(AbstractEnvironmentalAction action) {
		// System.out.println(this + " ATTEMPTING: " + action);
		body.getEnvironment().updateState(action);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}