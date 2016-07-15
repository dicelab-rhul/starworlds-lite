package uk.ac.rhul.cs.dice.gawl.interfaces.actions.speech;

public interface Payload<T> {

  public void setPayload(T payload);

  public T getPayload();
}