package uk.ac.rhul.cs.dice.starworlds.test;

import java.net.SocketAddress;
import java.util.Set;

import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetComponent;

public class TestClientServerCommunication {

	private static final int port = 10001, NUMCLIENTS = 10;
	private static final String host = "localhost";

	public static void main(String[] args) {
		INetComponent server = new INetComponent(10001);
		for (int i = 0; i < NUMCLIENTS; i++) {
			INetComponent client = new INetComponent("localhost", 10001);
			client.send("hello from client: " + i);
			client.send("hello again from client: " + i);
		}
		Set<SocketAddress> clientaddrs = server.getClientAddresses();
		for (int i = 0; i < NUMCLIENTS; i++) {
			SocketAddress rclient = clientaddrs.stream()
					.skip((long) (Math.random() * clientaddrs.size()))
					.findFirst().get();
			server.send("Hello from server", rclient);
		}

	}
}
