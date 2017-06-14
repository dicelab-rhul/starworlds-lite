package uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces;

public interface Message<T> {

	public T getPayload();

	public void setPayload(T payload);
}
