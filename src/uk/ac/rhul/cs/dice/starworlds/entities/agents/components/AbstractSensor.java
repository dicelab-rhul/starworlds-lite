package uk.ac.rhul.cs.dice.starworlds.entities.agents.components;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.actions.environmental.AbstractEnvironmentalAction;
import uk.ac.rhul.cs.dice.starworlds.actions.environmental.SensingAction;
import uk.ac.rhul.cs.dice.starworlds.entities.ActiveBody;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.parser.DefaultConstructorStore.DefaultConstructor;
import uk.ac.rhul.cs.dice.starworlds.perception.AbstractPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.NullPerception;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;

/**
 * An abstract class implementing {@link Sensor}. //TODO <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class AbstractSensor implements Sensor {

	// All sensors can receive the null perception
	@SensiblePerception
	public static Class<? extends AbstractPerception<?>> NULLPERCEPTION = NullPerception.class;

	private String id;
	private Set<Perception<?>> perceptions;
	private ActiveBody body;

	/**
	 * Constructor.
	 */
	@DefaultConstructor
	public AbstractSensor() {
		this.perceptions = new HashSet<>();
	}

	@Override
	public void notify(Perception<?> perception) {
		this.perceptions.add(perception);
		body.sensorActive(this);
	}

	@Override
	public ActiveBody getBody() {
		return body;
	}

	@Override
	public void attemptActivePerceive(SensingAction action) {
		body.getEnvironment().updateAmbient(action);
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

	@Override
	public void setId(String id) {
		this.id = id;
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