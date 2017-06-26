package uk.ac.rhul.cs.dice.starworlds.environment.interaction;


public abstract class AbstractEvent<T> implements Event<T> {

	protected T payload;

	public AbstractEvent() {
	}

	public AbstractEvent(T payload) {
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
