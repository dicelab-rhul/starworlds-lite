package uk.ac.rhul.cs.dice.starworlds.environment.inet;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetServer;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.INetSlave;

public class INetDefaultServer extends INetServer {

	public INetDefaultServer(int port) {
		super(port);
	}

	@Override
	public INetSlave newSlave(Socket socket) {
		try {
			return new INetDefaultSlave(socket, INetDefaultMessage.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void send(SocketAddress addr, Object message) {
		this.slaves.get(addr).send(message);
	}
}
