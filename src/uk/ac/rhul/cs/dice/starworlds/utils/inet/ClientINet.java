package uk.ac.rhul.cs.dice.starworlds.utils.inet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientINet implements INetState, Runnable {

	private Thread inThread;
	private Socket socket;
	private ObjectOutputStream out;

	public ClientINet(String host, int port, boolean start) {
		try {
			this.socket = new Socket(host, port);
			this.out = new ObjectOutputStream(socket.getOutputStream());
			createReceiveThread();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (start) {
			receive();
		}
	}

	public ClientINet(Socket socket, boolean start) {
		try {
			this.socket = socket;
			this.out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		createReceiveThread();
		if (start) {
			receive();
		}
	}

	private void createReceiveThread() {
		inThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(socket + " RECEIVING...");
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(socket.getInputStream());
					while (true) {
						System.out.println(socket + " RECEIEVED: "
								+ in.readObject());
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void receive() {
		inThread.start();
	}

	public Socket getSocket() {
		return this.socket;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.socket.toString();
	}

	@Override
	public void run() {
		receive();
	}

	@Override
	public boolean isServer() {
		return false;
	}

	@Override
	public void send(Serializable message, SocketAddress... addresses) {
		System.out.println(socket + " SENDING: " + message);
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
