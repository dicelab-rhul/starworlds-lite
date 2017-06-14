package uk.ac.rhul.cs.dice.starworlds.environment.inet;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

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
	protected void initialiseSlave(INetSlave slave) {
		// removed adding observer, the observer will be the
		// INetEnvironmentConnection
		// notify the connection manager that a new slave has been created
		setChanged();
		notifyObservers(slave);
		subinitslave(slave);
	}

	private void subinitslave(INetSlave slave) {
		this.slaves.put(slave.getSocket().getRemoteSocketAddress(), slave);
		Thread slaveThread = new Thread(slave);
		this.slavethreads.add(slaveThread);
		slaveThread.start();
	}

	@Override
	public SocketAddress connect(String host, Integer port)
			throws UnknownHostException, IOException {
		System.out.println(this + " INITIATING COMMUNICATION WITH: " + host
				+ ":" + port);
		Socket socket = new Socket(host, port);
		INetSlave slave = newSlave(socket);
		subinitslave(slave);
		System.out.println(this + " CONNECTION SUCCESSFUL: " + slave);
		return slave.getSocket().getRemoteSocketAddress();
	}

	@Override
	public void send(SocketAddress addr, Object message) {
		this.slaves.get(addr).send(message);
	}
}
