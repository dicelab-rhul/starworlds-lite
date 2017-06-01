package uk.ac.rhul.cs.dice.starworlds.utils.inet;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServerINet implements INetState {

	private ServerSocket socket;
	private Map<SocketAddress, ClientINet> clients = new HashMap<>();
	private List<Thread> clientThreads = new ArrayList<>();

	public ServerINet(int port) {
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SERVER: " + socket);
		Thread socketthread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Socket socket = ServerINet.this.socket.accept();
						System.out.println("SERVER ACCEPTED: " + socket);
						ClientINet client = new ClientINet(socket, false);
						ServerINet.this.clients.put(
								socket.getRemoteSocketAddress(), client);
						Thread clientThread = new Thread(client);
						ServerINet.this.clientThreads.add(clientThread);
						clientThread.start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		socketthread.start();
	}

	public Set<SocketAddress> getClientAddresses() {
		return Collections.unmodifiableSet(clients.keySet());
	}

	@Override
	public boolean isServer() {
		return true;
	}

	@Override
	public void send(Serializable message, SocketAddress... addresses) {
		for (SocketAddress a : addresses) {
			clients.get(a).send(message);
		}
	}
}
