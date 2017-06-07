package uk.ac.rhul.cs.dice.starworlds.utils.inet;

import java.io.Serializable;

public abstract class INetAbstractMessage<T extends Serializable> implements
		Serializable {

	private static final long serialVersionUID = -7398687670848928068L;

	private T payload;

	public INetAbstractMessage(T payload) {
		this.setPayload(payload);
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

}
