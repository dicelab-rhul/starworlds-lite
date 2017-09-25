package uk.ac.rhul.cs.dice.starworlds.actions.speech;

import java.io.Serializable;

public interface Payload<T extends Serializable> extends Serializable {
	public void setPayload(T payload);

	public T getPayload();
}