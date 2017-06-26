package uk.ac.rhul.cs.dice.starworlds.environment.interaction;

public interface Event<T> {

	public T getPayload();

	public void setPayload(T payload);
}
