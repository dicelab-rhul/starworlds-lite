package uk.ac.rhul.cs.dice.starworlds.perception;

import uk.ac.rhul.cs.dice.starworlds.actions.speech.Payload;

/**
 * A concrete implementation of {@link Perception}
 * @author Ben
 *
 * @param <T>
 */
public class CommunicationPerception<T extends Payload<?>> extends
		AbstractPerception<T> {

	public CommunicationPerception(T content) {
		super(content);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + "\""
				+ this.getPerception() + "\"";
	}
}
