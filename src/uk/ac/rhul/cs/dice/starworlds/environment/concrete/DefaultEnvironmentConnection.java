package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.AbstractEnvironmentConnection;
import uk.ac.rhul.cs.dice.starworlds.environment.interaction.Event;

public class DefaultEnvironmentConnection extends AbstractEnvironmentConnection {

	public DefaultEnvironmentConnection(
			DefaultEnvironmentConnection mutualConnector,
			EnvironmentAppearance appearance) {
		super(mutualConnector, appearance);
		// System.out.println("CONNECTED: " + this);
	}

	public DefaultEnvironmentConnection(EnvironmentAppearance appearance) {
		super(appearance);
	}

	@Override
	public void send(Event<?> obj) {
		super.send(obj);
	}

	@Override
	public void receive(Event<?> obj) {
		if (isOpen()) {
			this.notifyReceivers(obj);
		}
	}
}
