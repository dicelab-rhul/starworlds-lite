package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractMessage;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.CommandMessage;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;

public class DefaultMessage<T> extends AbstractMessage<Pair<String, T>>
		implements CommandMessage<T> {

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
