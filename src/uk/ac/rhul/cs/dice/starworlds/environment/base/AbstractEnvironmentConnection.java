package uk.ac.rhul.cs.dice.starworlds.environment.base;

import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.EnvironmentConnection;

public abstract class AbstractEnvironmentConnection extends Recipient implements
		EnvironmentConnection {

	private Boolean open = true;
	// The connector of the environment that this connector is connected to
	private AbstractEnvironmentConnection mutualConnector;
	// The appearance of the local environment
	private EnvironmentAppearance environmentAppearance;

	public AbstractEnvironmentConnection(
			EnvironmentAppearance environmentAppearance) {
		this.environmentAppearance = environmentAppearance;
	}

	public AbstractEnvironmentConnection(
			AbstractEnvironmentConnection mutualConnector,
			EnvironmentAppearance environmentAppearance) {
		this.mutualConnector = mutualConnector;
		this.environmentAppearance = environmentAppearance;
		mutualConnector.setMutualConnector(this);
	}

	@Override
	public void send(AbstractMessage<?> message) {
		// System.out.println(this + " SENDING: " + message);
		mutualConnector.receive(message);
	}

	@Override
	public void receive(AbstractMessage<?> message) {
		if (open) {
			// System.out.println(this + " RECEIVED: " + message);
		}
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void close() {
		open = false;
	}

	@Override
	public void open() {
		open = true;
	}

	@Override
	public EnvironmentConnection getMutualConnector() {
		return mutualConnector;
	}

	@Override
	public EnvironmentAppearance getRemoteAppearance() {
		return this.mutualConnector.getAppearance();
	}

	private void setMutualConnector(EnvironmentConnection mutualConnector) {
		this.mutualConnector = (AbstractEnvironmentConnection) mutualConnector;
	}

	@Override
	public EnvironmentAppearance getAppearance() {
		return this.environmentAppearance;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+ " ["
				+ this.getAppearance()
				+ "<->"
				+ ((this.getMutualConnector() != null) ? this
						.getMutualConnector().getAppearance() : " ") + "]";
	}
}
