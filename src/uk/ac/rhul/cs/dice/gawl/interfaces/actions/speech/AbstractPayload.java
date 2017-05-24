package uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech;

public class AbstractPayload<T> implements Payload<T> {

	private T payload;

	public AbstractPayload(T payload) {
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
