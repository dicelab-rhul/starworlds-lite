package uk.ac.rhul.cs.dice.starworlds.environment.concrete;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import uk.ac.rhul.cs.dice.starworlds.appearances.Appearance;
import uk.ac.rhul.cs.dice.starworlds.appearances.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.starworlds.environment.base.AbstractEnvironmentConnection;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Environment;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.EnvironmentConnection;
import uk.ac.rhul.cs.dice.starworlds.environment.base.interfaces.Message;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;

public class INetEnvironmentConnection extends AbstractEnvironmentConnection
		implements Observer {

	private INetSlave slave;
	private Appearance remoteAppearance;

	/**
	 * Constructor. Sets up a new connection via the server using the given host
	 * and port arguments. Sends an initial connect message containing the given
	 * {@link EnvironmentAppearance} which should be the {@link Appearance} of
	 * the local {@link Environment}.
	 * 
	 * @param environmentAppearance
	 * @param server
	 * @param host
	 * @param port
	 */
	public INetEnvironmentConnection(
			EnvironmentAppearance environmentAppearance, INetServer server,
			String host, Integer port) {
		super(environmentAppearance);
		try {
			this.slave = server.getSlave(server.connect(host, port));
			this.slave.addObserver(this);
			this.slave.sendAndWaitForReply(
					new InitialisationMessage(this.getAppearance()), 3000L);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor. After a connection has been set up (via the other
	 * constructor at a different network address). This constructor finalises
	 * the connection.
	 * 
	 * @param environmentAppearance
	 * @param slave
	 */
	public INetEnvironmentConnection(
			EnvironmentAppearance environmentAppearance, INetSlave slave) {
		super(environmentAppearance);
		this.slave = slave;
		this.slave.addObserver(this);
	}

	@Override
	public void send(Message<?> message) {
		slave.send(message);
	}

	@Override
	public void receive(Message<?> message) {
		if (InitialisationMessage.class.isAssignableFrom(message.getClass())) {
			InitialisationMessage imessage = (InitialisationMessage) message;
			if (checkValidAppearance(imessage.getPayload())) {
				this.remoteAppearance = (EnvironmentAppearance) imessage
						.getPayload();
				slave.send(new InitialisationMessage(this.getAppearance()));
			}
		} else {
			System.out.println("Connection received message: " + message);
		}
	}

	/**
	 * A check performed during the connection initialisation. Connecting
	 * {@link Environment} should share their {@link Appearance}s, this method
	 * checks that the received {@link Appearance} is valid. TODO If the
	 * {@link Appearance} is not valid. The connection will be aborted.
	 * 
	 * @param appearance
	 * @return
	 */
	protected boolean checkValidAppearance(Appearance appearance) {
		return EnvironmentAppearance.class.isAssignableFrom(appearance
				.getClass());
	}

	@Override
	public Appearance getRemoteAppearance() {
		return this.remoteAppearance;
	}

	@Override
	public EnvironmentConnection getMutualConnector() {
		// TODO
		return null;
	}

	@Override
	public void update(Observable o, Object arg) {
		receive((Message<?>) arg);
	}
}
