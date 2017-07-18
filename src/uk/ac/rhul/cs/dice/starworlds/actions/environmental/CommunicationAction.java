package uk.ac.rhul.cs.dice.starworlds.actions.environmental;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.starworlds.actions.speech.DefaultPayload;
import uk.ac.rhul.cs.dice.starworlds.actions.speech.Payload;
import uk.ac.rhul.cs.dice.starworlds.environment.subscriber.AbstractSubscriber.SensiblePerception;
import uk.ac.rhul.cs.dice.starworlds.perception.CommunicationPerception;

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
public class CommunicationAction<T extends Serializable> extends
		AbstractEnvironmentalAction {

	private static final long serialVersionUID = 1L;

	@SensiblePerception
	public static final Class<?> POSSIBLEPERCEPTION = CommunicationPerception.class;

	private List<String> recipientsIds;
	private Payload<T> payload;

	/**
	 * Constructor.
	 */
	public CommunicationAction() {
	}

	/**
	 * Constructor.
	 * 
	 * @param recipientsIds
	 *            : a {@link List} of IDs of the recipients.
	 * @param payload
	 *            : the {@link Payload} of the communication.
	 */
	public CommunicationAction(Payload<T> payload, List<String> recipientsIds) {
		this.recipientsIds = (recipientsIds != null) ? recipientsIds
				: new ArrayList<>();
		this.payload = payload;
	}

	/**
	 * Constructor.
	 * 
	 * @param recipientsIds
	 *            : a {@link List} of IDs of the recipients.
	 * @param payload
	 *            : the {@link Payload} of the communication.
	 */
	public CommunicationAction(T payload, List<String> recipientsIds) {
		this.recipientsIds = (recipientsIds != null) ? recipientsIds
				: new ArrayList<>();
		this.payload = new DefaultPayload<T>(payload);
	}

	/**
	 * Constructor. Clones the action given. The payload of the action is NOT
	 * cloned.
	 * 
	 * @param action
	 *            to clone.
	 */
	public CommunicationAction(CommunicationAction<T> action) {
		this.recipientsIds = new ArrayList<>(action.getRecipientsIds());
		this.payload = new DefaultPayload<T>(action.getPayload().getPayload());
		this.setLocalEnvironment(action.getLocalEnvironment());
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
		return "Message: " + payload + " to: "
				+ ((recipientsIds.size() != 0) ? recipientsIds : "Everyone");
	}
}