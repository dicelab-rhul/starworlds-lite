package uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces;

public interface CommandMessage<T> {

	public String getCommand();

	public T getCommandPayload();
}
