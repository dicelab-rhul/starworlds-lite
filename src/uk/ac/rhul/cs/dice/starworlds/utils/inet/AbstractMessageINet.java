package uk.ac.rhul.cs.dice.starworlds.utils.inet;

import java.io.Serializable;

public abstract class AbstractMessageINet<T extends Serializable> {

	private T[] payload;

	public AbstractMessageINet(T[] payload) {
		this.setPayload(payload);
	}

	public T[] getPayload() {
		return payload;
	}

	public void setPayload(T[] payload) {
		this.payload = payload;
	}

}
