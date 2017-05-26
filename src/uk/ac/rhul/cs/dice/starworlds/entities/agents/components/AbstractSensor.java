package uk.ac.rhul.cs.dice.starworlds.entities.agents.components;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * The most generic class implementing {@link Sensor}. It also extends
 * {@link CustomObservable}. It may contain a {@link Perception}.<br/>
 * <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractSensor implements Sensor {

	private Set<Perception<?>> perceptions;
	private String id;
	private ActiveBody body;

	/**
	 * Constructor.
	 */
	public AbstractSensor() {
		this.perceptions = new HashSet<>();
	}

	@Override
	public void notify(Perception<?> perception) {
		// System.out.println("AGENT: " + body.getId() + " SENSOR: " + this
		// + " RECEIVED PERCEPTION: " + perception);
		this.perceptions.add(perception);
		body.sensorActive(this);
	}

	@Override
	public ActiveBody getBody() {
		return body;
	}

	@Override
	public void attemptActivePerceive(SensingAction action) {
		body.getEnvironment().updateState(action);
	}

	@Override
	public void attempt(AbstractEnvironmentalAction action) {
		this.attemptActivePerceive((SensingAction) action);
	}

	@Override
	public void setBody(ActiveBody body) {
		this.body = body;
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Getter for the {@link Perception}s currently stored in the internal
	 * buffer of this {@link AbstractSensor}. These are all of the perceptions
	 * that were received by this {@link AbstractSensor} in the previous cycle.
	 * The internal buffer of {@link Perception}s will be cleared after this
	 * method call.
	 * 
	 * @return the set of {@link Perception}s
	 */
	@Override
	public Set<Perception<?>> getPerceptions() {
		// System.out.println("AGENT: " + body.getId() + " SENSOR: " + this
		// + " GETTING PERCEPTIONS: "
		// + Arrays.toString(this.perceptions.toArray()));
		HashSet<Perception<?>> result = new HashSet<>(this.perceptions);
		this.perceptions.clear();
		return result;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}