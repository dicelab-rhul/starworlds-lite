package uk.ac.rhul.cs.dice.starworlds.environment.base;

import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;

public abstract class AbstractMessage<T> {

	protected Appearance appearance;
	protected T payload;

	public AbstractMessage(Appearance appearance, T payload) {
		this.setPayload(payload);
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " : " + this.getPayload();
	}

	public Appearance getSenderAppearance() {
		return appearance;
	}
}
