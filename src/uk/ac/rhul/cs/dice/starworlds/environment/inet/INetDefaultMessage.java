package uk.ac.rhul.cs.dice.starworlds.environment.inet;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.environment.concrete.DefaultMessage;

public class INetDefaultMessage extends DefaultMessage<Serializable> implements
		Serializable {

	private static final long serialVersionUID = -1105794538281856367L;

	public INetDefaultMessage(String command, Serializable payload) {
		super(command, payload);
	}
}
