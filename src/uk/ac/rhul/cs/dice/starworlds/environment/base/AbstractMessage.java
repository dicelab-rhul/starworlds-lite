package uk.ac.rhul.cs.dice.starworlds.environment.base;

import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Message;

public abstract class AbstractMessage<T> implements Message<T> {

	protected T payload;

	public AbstractMessage() {
	}

	public AbstractMessage(T payload) {
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
}
