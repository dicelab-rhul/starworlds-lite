package uk.ac.rhul.cs.dice.starworlds.environment.inet;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetAbstractMessage;

public class INetDefaultMessage extends
		INetAbstractMessage<Pair<String, Serializable>> {

	private static final long serialVersionUID = -1105794538281856367L;

	public INetDefaultMessage(String command, Serializable payload) {
		super(new Pair<String, Serializable>(command, payload));
	}

}
