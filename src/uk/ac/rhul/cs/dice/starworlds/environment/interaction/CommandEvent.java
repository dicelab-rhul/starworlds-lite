package uk.ac.rhul.cs.dice.starworlds.environment.interaction;

public interface CommandEvent<T> {

	public String getCommand();

	public T getCommandPayload();
}
