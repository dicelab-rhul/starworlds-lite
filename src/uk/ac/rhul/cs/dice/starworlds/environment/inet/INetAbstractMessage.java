package uk.ac.rhul.cs.dice.starworlds.environment.inet;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Message;

public abstract class INetAbstractMessage<T extends Serializable> implements
		Message<T>, Serializable {

	private static final long serialVersionUID = -7398687670848928068L;

	// this must be here for serialisation to work properly
	private T payload;

	public INetAbstractMessage() {
	}

	public INetAbstractMessage(T payload) {
		this.payload = payload;
	}

	@Override
	public T getPayload() {
		return payload;
	}

	@Override
	public void setPayload(T payload) {
		this.payload = payload;
	}
}
