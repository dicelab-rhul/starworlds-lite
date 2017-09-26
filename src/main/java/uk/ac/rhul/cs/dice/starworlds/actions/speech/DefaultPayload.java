package uk.ac.rhul.cs.dice.starworlds.actions.speech;

import java.io.Serializable;

public class DefaultPayload<T extends Serializable> implements Payload<T> {
    private static final long serialVersionUID = -2348821514838661744L;

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