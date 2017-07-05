package uk.ac.rhul.cs.dice.starworlds.environment.interaction.inet;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.starworlds.environment.interaction.CommandEvent;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class INetDefaultMessage extends
		INetAbstractMessage<Pair<String, Serializable>> implements
		CommandEvent<Serializable>, Serializable {

	private static final long serialVersionUID = -2656016119798671723L;

	public INetDefaultMessage(String command, Serializable payload) {
		super(new Pair<String, Serializable>(command, payload));
	}

	@Override
	public String getCommand() {
		return this.getPayload().getFirst();
	}

	@Override
	public Serializable getCommandPayload() {
		return this.getPayload().getSecond();
	}
}