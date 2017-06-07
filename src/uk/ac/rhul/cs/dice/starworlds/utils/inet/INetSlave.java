package uk.ac.rhul.cs.dice.starworlds.utils.inet;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;

import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetReceiver;
import uk.ac.rhul.cs.dice.starworlds.utils.inet.sendreceive.INetSender;

public abstract class INetSlave extends Observable implements Runnable {

	private ConcurrentLinkedQueue<Object> received;
	private Socket socket;
	private INetSender sender;
	private INetReceiver receiver;

	public INetSlave(String host, int port, INetSender sender,
			INetReceiver receiver) {
		try {
			init(new Socket(host, port), receiver, sender);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public INetSlave(Socket socket, INetSender sender, INetReceiver receiver) {
		init(socket, receiver, sender);
	}

	private void init(Socket socket, INetReceiver receiver, INetSender sender) {
		this.socket = socket;
		this.received = new ConcurrentLinkedQueue<>();
		this.receiver = receiver;
		this.sender = sender;
	}

	public Socket getSocket() {
		return this.socket;
	}

	@Override
	public void run() {
		System.out.println(this + " LISTENING...");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Object received = receiver.receive();
					System.out.println("RECEIVED: " + received);
					setChanged();
					notifyObservers(received);
				}
			}
		});
		thread.start();
	}

	public void send(Object message) {
		sender.send(message);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.socket.toString();
	}
}
