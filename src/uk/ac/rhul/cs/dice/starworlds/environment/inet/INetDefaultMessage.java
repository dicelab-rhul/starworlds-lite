package uk.ac.rhul.cs.dice.starworlds.environment.inet;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractMessage;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.CommandMessage;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class INetDefaultMessage extends
		AbstractMessage<Pair<String, Serializable>> implements
		CommandMessage<Serializable>, Serializable {

	private static final long serialVersionUID = -2656016119798671723L;

	public INetDefaultMessage(String command, Serializable payload) {
		super(new Pair<String, Serializable>(command, payload));
	}

	@Override
	public String getCommand() {
		return this.payload.getFirst();
	}

	@Override
	public Serializable getCommandPayload() {
		return this.payload.getSecond();
	}
}
