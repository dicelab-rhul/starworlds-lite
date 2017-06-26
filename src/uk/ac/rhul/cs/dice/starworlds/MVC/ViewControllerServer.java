package uk.ac.rhul.cs.dice.starworlds.MVC;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.environment.interfaces.Universe;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;

/**
 * An extension of {@link AbstractViewController} that allows remote views.
 * Connections are handled via a {@link INetServer}. </br> To make a new
 * connection to a remote view use the
 * {@link ViewControllerServer#connect(String, Integer)} method. To send
 * messages use the {@link ViewControllerServer#send(SocketAddress, Object)}
 * method. All received data is routed to the abstract
 * {@link ViewControllerServer#messageReceived(SocketAddress, Object)} method
 * which should be concretely implemented in a subclass.
 * 
 * @author Ben
 *
 */
public abstract class ViewControllerServer extends AbstractViewController {

	private INetServer server;

	public ViewControllerServer() {
	}

	public ViewControllerServer(Universe universe) {
		super(universe);
	}

	public ViewControllerServer(INetServer server, Universe universe) {
		super(universe);
		this.server = server;
		this.server.addObserver(this);
	}

	/**
	 * All messages that have been received by this {@link ViewController} are
	 * routed to this method. Concretely implement it to handle messages.
	 * 
	 * @param viewaddr
	 *            : the {@link SocketAddress} of the view that sent the message
	 * @param message
	 *            : that was sent
	 */
	public abstract void messageReceived(SocketAddress viewaddr, Object message);

	public final void update(Observable o, Object arg) {
		Pair<?, ?> p = (Pair<?, ?>) arg;
		messageReceived((SocketAddress) p.getFirst(), p.getSecond());
	}

	public Set<SocketAddress> getClientAddresses() {
		return server.getClientAddresses();
	}

	public INetSlave getSlave(SocketAddress addr) {
		return server.getSlave(addr);
	}

	public void send(SocketAddress addr, Object message) {
		server.send(addr, message);
	}

	public final SocketAddress connect(String host, Integer port)
			throws UnknownHostException, IOException {
		return server.connect(host, port);
	}

	public INetServer getServer() {
		return server;
	}

	public final void setServer(INetServer server) {
		this.server = server;
		this.server.addObserver(this);
	}
}
