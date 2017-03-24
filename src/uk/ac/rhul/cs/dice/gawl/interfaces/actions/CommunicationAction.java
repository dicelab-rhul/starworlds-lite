package uk.ac.rhul.cs.dice.gawl.interfaces.actions;

import java.util.List;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech.Payload;

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
public abstract class CommunicationAction<T> extends AbstractAction {
    private String senderId;
    private List<String> recipientsIds;
    private Payload<T> payload;

    /**
     * The constructor with senderId, recipientsIds and payload.
     * 
     * @param senderId: the ID of the sender.
     * @param recipientsIds: a {@link List} of IDs of the recipients.
     * @param payload: the {@link Payload} of the communication.
     */
    public CommunicationAction(String senderId, List<String> recipientsIds, Payload<T> payload) {
	this.senderId = senderId;
	this.recipientsIds = recipientsIds;
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
}