package uk.ac.rhul.cs.dice.starworlds.environment.base;

/**
 * A simple Observer class that should
 * {@link Receiver#receive(Recipient, Object)} data from a {@link Recipient}
 * that it is observing. This {@link Receiver} {@link Recipient} pair should be
 * used when a {@link Recipient} wants to off load any data that it has received
 * from elsewhere.
 * 
 * @author Ben
 *
 */
public interface Receiver {

	public void receive(Recipient recipient, AbstractMessage<?> message);

}
