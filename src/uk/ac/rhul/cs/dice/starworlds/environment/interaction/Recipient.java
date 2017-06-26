package uk.ac.rhul.cs.dice.starworlds.environment.interaction;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple Observable class that contains some {@link Receiver}s who should be
 * notified using the {@link Recipient#notifyReceivers(Object)} method when any
 * data is received by this {@link Recipient}.
 * 
 * @author Ben
 *
 */
public abstract class Recipient {

	private Set<Receiver> receivers;

	public Recipient() {
		receivers = new HashSet<>();
	}

	public void notifyReceivers(Event<?> message) {
		for (Receiver r : receivers) {
			r.receive(this, message);
		}
	}

	public void addReciever(Receiver receiver) {
		this.receivers.add(receiver);
	}
}
