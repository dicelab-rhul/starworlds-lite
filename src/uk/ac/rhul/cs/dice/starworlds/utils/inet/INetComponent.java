package uk.ac.rhul.cs.dice.starworlds.utils.inet;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Set;

public class INetComponent {

	private INetState state;

	public INetComponent(int port) {
		state = new ServerINet(port);
	}

	public INetComponent(String host, int port) {
		state = new ClientINet(host, port, true);
	}

	public void send(Serializable message, SocketAddress... addresses) {
		state.send(message, addresses);
	}

	public Set<SocketAddress> getClientAddresses() {
		if (state.isServer()) {
			return ((ServerINet) state).getClientAddresses();
		}
		return null;
	}

}
