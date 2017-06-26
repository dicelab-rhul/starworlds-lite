package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import uk.ac.rhul.cs.dice.starworlds.environment.interaction.AbstractEvent;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.CommandEvent;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class DefaultMessage<T> extends AbstractEvent<Pair<String, T>>
		implements CommandEvent<T> {

	public DefaultMessage(String command, T payload) {
		super(new Pair<String, T>(command, payload));
	}

	@Override
	public String getCommand() {
		return this.payload.getFirst();
	}

	@Override
	public T getCommandPayload() {
		return this.payload.getSecond();
	}

}
