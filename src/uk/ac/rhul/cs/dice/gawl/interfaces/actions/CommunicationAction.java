package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech.Payload;
import uk.ac.rhul.cs.dice.gawl.interfaces.perception.Perception;

/**
 * A subclass of {@link AbstractAction} representing communication actions.<br/>
 * <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public abstract class CommunicationAction<T, P extends Perception> extends AbstractAction<P> {
	private String senderId;
	private List<String> recipientsIds;
	private Payload<T> payload;

	public CommunicationAction(String senderId, List<String> recipientsIds, Payload<T> payload) {
		this.senderId = senderId;
		this.recipientsIds = recipientsIds;
		this.payload = payload;
	}

	public String getSenderId() {
		return this.senderId;
	}

	public List<String> getRecipientsIds() {
		return this.recipientsIds;
	}

	public Payload<T> getPayload() {
		return this.payload;
	}
}