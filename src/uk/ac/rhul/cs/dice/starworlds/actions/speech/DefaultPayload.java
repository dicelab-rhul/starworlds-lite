package uk.ac.rhul.cs.dice.starworlds.actions.speech;

public class DefaultPayload<T> implements Payload<T> {

	private T payload;

	public DefaultPayload(T payload) {
		this.payload = payload;
	}

	@Override
	public void setPayload(T payload) {
		this.payload = payload;
	}

	@Override
	public T getPayload() {
		return this.payload;
	}

	@Override
	public String toString() {
		return payload.toString();
	}

}
