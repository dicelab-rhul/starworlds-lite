package uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech;

public abstract class SpeechWrapper {
	private String senderId; //the recipient ids are useless in the wrapper, since it's created in the mind.
	private Payload payload;
	private boolean managed;
	
	public SpeechWrapper() {
		this.managed = false;
	}

	public String getSenderId() {
		return this.senderId;
	}

	protected void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public Payload getPayload() {
		return this.payload;
	}

	protected void setPayload(Payload payload) {
		this.payload = payload;
	}

	public boolean hasBeenManaged() {
		return this.managed;
	}
	
	public void setManaged() {
		this.managed = true;
	}
}