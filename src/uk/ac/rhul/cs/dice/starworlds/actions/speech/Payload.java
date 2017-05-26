package uk.ac.rhul.cs.dice.starworlds.actions.speech;

public interface Payload<T> {
    public void setPayload(T payload);
    public T getPayload();
}