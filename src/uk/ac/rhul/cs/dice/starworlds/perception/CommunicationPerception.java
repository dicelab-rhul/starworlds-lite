package uk.ac.rhul.cs.dice.starworlds.perception;

import uk.ac.rhul.cs.dice.starworlds.actions.speech.Payload;

public class CommunicationPerception<T extends Payload<?>> extends
		AbstractPerception<T> {

	private static final long serialVersionUID = 3145862835583209260L;

	public CommunicationPerception(T content) {
		super(content);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + "\""
				+ this.getPerception() + "\"";
	}
}
