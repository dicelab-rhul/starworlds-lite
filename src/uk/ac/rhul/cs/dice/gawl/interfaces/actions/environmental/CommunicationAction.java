package uk.ac.rhul.cs.dice.gawl.interfaces.actions.environmental;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech.Payload;

/**
 * A subclass of {@link AbstractEnvironmentalAction} representing communication
 * actions.<br/>
 * <br/>
 * 
 * Known direct subclasses: none.
 * 
 * @author cloudstrife9999 a.k.a. Emanuele Uliana
 * @author Ben Wilkins
 * @author Kostas Stathis
 *
 */
public class CommunicationAction<T> extends AbstractEnvironmentalAction {

	private static final String ANONYMOUSSENDER = "Anonymous";

	private String senderId;
	private List<String> recipientsIds;
	private Payload<T> payload;

	/**
	 * The constructor with senderId, recipientsIds and payload.
	 * 
	 * @param senderId
	 *            : the ID of the sender.
	 * @param recipientsIds
	 *            : a {@link List} of IDs of the recipients.
	 * @param payload
	 *            : the {@link Payload} of the communication.
	 */
	public CommunicationAction(String senderId, List<String> recipientsIds,
			Payload<T> payload) {
		this.senderId = (senderId != null) ? senderId : ANONYMOUSSENDER;
		this.recipientsIds = (recipientsIds != null) ? recipientsIds
				: new ArrayList<>();
		this.payload = payload;
	}

	/**
	 * Returns the sender ID.
	 * 
	 * @return the sender ID.
	 */
	public String getSenderId() {
		return this.senderId;
	}

	/**
	 * Returns a {@link List} of recipients IDs.
	 * 
	 * @return a {@link List} of recipients IDs.
	 */
	public List<String> getRecipientsIds() {
		return this.recipientsIds;
	}

	/**
	 * Returns the {@link Payload} of the communication.
	 * 
	 * @return the {@link Payload} of the communication.
	 */
	public Payload<T> getPayload() {
		return this.payload;
	}

	@Override
	public String toString() {
		return "MESSAGE: " + payload;
	}
}