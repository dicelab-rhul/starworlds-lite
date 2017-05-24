package uk.ac.rhul.cs.dice.gawl.interfaces.perception;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech.Payload;

public class CommunicationPerception<T extends Payload<?>> extends
		AbstractPerception<T> {

	public CommunicationPerception(T content) {
		super(content);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + this.getPerception();
	}
}
