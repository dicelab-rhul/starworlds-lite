package uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech;

public abstract class SpeechWrapper<T> {
    private String senderId;
    private Payload<T> payload;
    private boolean managed;

    public SpeechWrapper() {
	this.managed = false;
    }

    /**
     * 
     * Returns the sender id.
     * 
     * @return the sender id.
     * 
     */
    public String getSenderId() {
	return this.senderId;
    }

    protected void setSenderId(String senderId) {
	this.senderId = senderId;
    }

    /**
     * 
     * Returns the actual {@link Payload} of the speech.
     * 
     * @return the actual {@link Payload} of the speech.
     * 
     */
    public Payload<T> getPayload() {
	return this.payload;
    }

    protected void setPayload(Payload<T> payload) {
	this.payload = payload;
    }

    public boolean hasBeenManaged() {
	return this.managed;
    }

    public void setManaged() {
	this.managed = true;
    }
}