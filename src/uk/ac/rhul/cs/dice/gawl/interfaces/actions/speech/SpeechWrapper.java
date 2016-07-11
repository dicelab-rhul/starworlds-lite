package uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech;

import uk.ac.rhul.cs.dice.gawl.interfaces.actions.CommunicationAction;

public abstract class SpeechWrapper {
	public String senderId; //the recipient ids are useless in the wrapper, since it's created in the mind.
	public Payload payload;
	public boolean managed;
	
	public SpeechWrapper(CommunicationAction speechAct) {
		this.senderId = speechAct.getSenderId();
		this.payload = speechAct.getPayload();
		this.managed = false;
	}
}